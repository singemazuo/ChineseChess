/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechess.repository;

import chinesechess.business.models.ChessRecord;
import chinesechess.dal.DataAccess;
import chinesechess.types.IParameter.Type;
import chinesechess.types.Parameter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.sql.rowset.CachedRowSet;

import org.apache.commons.io.IOUtils;

/**
 * the sql layer to access the data in the database
 * @author Yinbin Zuo
 */
public class RecordRepository {
    // the store procedure's name in the database
    private final String SPROC_GET_RECORD = "CALL uspGetRecord(?,?);";
    private final String SPROC_INSERT_RECORD = "CALL uspInsertRecord(?);";
    
    /**
     * Retrieve some associated records in the record table
     *
     * @param beginDate the begin date of the range
     * @param endDate the end date of the range
     * @return the requested record
     */
    public List<ChessRecord> retrieveRecord(Date beginDate,Date endDate) {
        List<ChessRecord> records = new ArrayList();

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            ArrayList<Object> params = new ArrayList() {
                {
                    add(format.format(beginDate));
                    add(format.format(endDate));
                }
            };
            
            DataAccess da = new DataAccess();
            try(ResultSet rs = da.executeFill(SPROC_GET_RECORD, params.toArray())){
                records = toListOfRecord(rs);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        if (records.size() > 0) {
            return records;
        } else {
            return null;
        }
    }
    
    /**
     * This inserts a record into the ChineseChess database mysql
     *
     * @param record an instance of the ChessRecord
     */
    public void insertRecord(String path) {
        try {
            List<Parameter> params = new ArrayList<Parameter>();
            params.add(new Parameter(path,Type.IN,java.sql.Types.BLOB));

            DataAccess da = new DataAccess();
            da.executeNonQuery(SPROC_INSERT_RECORD, params);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }
    
    /**
     * Convert a CachedRowSet to a list of ChessRecords
     *
     * @param rs the populated CachedRowSet
     * @return List of ChessRecords from within the CachedRowSet
     * @throws SQLException
     */
    private List<ChessRecord> toListOfRecord(ResultSet rs) throws SQLException, IOException {
        List<ChessRecord> records = new ArrayList();
        ChessRecord record;

        while (rs.next()) {
            record = new ChessRecord();
            record.setRecordId(rs.getInt("RecordId"));
            record.setCreateDate(rs.getDate("CreateDate"));
            InputStream in = rs.getBinaryStream("RecordFile");
            final File tempFile = File.createTempFile("records-"+record.getRecordId(), ".json");
            try (FileOutputStream out = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[1024];
                while(in.read(buffer) > 0){
                    out.write(buffer);
                }
            }
            in.close();
            record.setRecordFile(tempFile);
            records.add(record);
        }

        return records;
    }
}
