/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechess.business;

import chinesechess.repository.RecordRepository;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

/**
 * this class implements the record functionalities of the chessman for writing and reading from the file
 * @author Yinbin Zuo
 */
public class ChessRecord {
    private RecordRepository repo;
    
    private static volatile ChessRecord instance;

    /**
     * the constructor instantiates the sql layer for the record table
     */
    private ChessRecord() {
        repo = new RecordRepository();
    }
    
    public static ChessRecord getInstance() {
        if (instance == null) {
            synchronized (ChessRecord.class) {
                if (instance == null) {
                    instance = new ChessRecord();
                }
            }
        }
        return instance;
    }
    
    /**
     * To read the records from the database side
     * @param beginDate the begin date of the range
     * @param endDate the end date of the range
     * @return 
     */
    public List<chinesechess.business.models.ChessRecord> GetRecords(Date beginDate,Date endDate){
        return repo.retrieveRecord(beginDate, endDate);
    }
    
    /**
     * To insert a record including create date, file in the database
     * @param path 
     */
    public void insertRecord(String path){
        repo.insertRecord(path);
    }
    
    /**
     * To fill byte array from a file with path
     * @param path file path
     * @return byte array
     */
    private byte[] convertBytesFromFile(String path){
        byte[] buffer = null;  
        try {
            // instantiate the file with path
            File file = new File(path);
            
            // instantiate the file input stream with the file
            FileInputStream fis = new FileInputStream(file);
            
            // instantiate the byte array output stream with 1000 size
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);  
            byte[] b = new byte[1000];  
            int n;  
            while ((n = fis.read(b)) != -1) {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return buffer;
    }
}
