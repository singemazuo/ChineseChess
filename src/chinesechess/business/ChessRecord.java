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
 *
 * @author singemazuo
 */
public class ChessRecord {
    private RecordRepository repo;
    
    private static volatile ChessRecord instance;

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
    
    public List<chinesechess.business.models.ChessRecord> GetRecords(Date beginDate,Date endDate){
        return repo.retrieveRecord(beginDate, endDate);
    }
    
    public void InsertRecord(String path){
        repo.insertRecord(path);
    }
    
    private byte[] convertBytesFromFile(String path){
        byte[] buffer = null;  
        try {  
            File file = new File(path);  
            FileInputStream fis = new FileInputStream(file);  
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
