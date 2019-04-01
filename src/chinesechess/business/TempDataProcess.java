/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechess.business;

import chinesechess.ChineseChess;
import chinesechess.MoveStep;
import chinesechess.business.models.ChessRecord;
import chinesechess.dal.DataAccess;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * This class implements the functionality for storing the data into a local temporary file
 * @author Yinbin Zuo
 */
public class TempDataProcess {
    private static volatile TempDataProcess instance;

    private TempDataProcess() {}

    public static TempDataProcess getInstance() {
        if (instance == null) {
            synchronized (TempDataProcess.class) {
                if (instance == null) {
                    instance = new TempDataProcess();
                }
            }
        }
        return instance;
    }
    
    /**
     * To load records from a file with Jackson Java library
     * @param file
     * @return 
     */
    public List<MoveStep> loadRecords(File file){
        try {
            // load the file name we are using cities

            ObjectMapper mapper = new ObjectMapper();
            MoveStep[] records = mapper.readValue(file, MoveStep[].class);
            return Arrays.asList(records);
        } catch (Exception e) {
            out.println(e.getMessage());
            return null;
        }
    }
    
    /**
     * To load all of the records from local virtual folder
     * @return 
     */
    public List<MoveStep> loadRecords(){
        Properties props = new Properties();
        
        try {
            // load the file name we are using cities
            ClassLoader classLoader = ChineseChess.class.getClassLoader();
            
            InputStream in = classLoader.getResourceAsStream("chinesechess/properties/export.properties");
            props.load(in);
            in.close();
            
            String fileName = props.getProperty("json.tempfile");
            String pathToWrite = "./output";
            
            Path dir = Paths.get(pathToWrite);
            
            // check if the directory exists
            if(Files.notExists(dir)){
                Files.createDirectories(dir);
                out.println("The directory "+ pathToWrite + " has been created.");
            }else{
                out.println("The directory "+ pathToWrite + " is alreay exists.");
                
                out.println("Directory: "+dir.toAbsolutePath());
                out.println("Files: ");
                DirectoryStream<Path> dirStream = Files.newDirectoryStream(dir);
                
                for (Path p : dirStream) {
                    if(Files.isRegularFile(p)){
                        out.println(" "+p.getFileName());
                    }
                }
            }
            
            Path recordsFilePath = Paths.get(pathToWrite, fileName);
            File recordsFile = recordsFilePath.toFile();
            
            ObjectMapper mapper = new ObjectMapper();
            MoveStep[] records = mapper.readValue(recordsFile, MoveStep[].class);
            return Arrays.asList(records);
        } catch (Exception e) {
            out.println(e.getMessage());
            return null;
        }
    }
    
    /**
     * To save the records from a list of move step 
     * @param theRecord
     * @return 
     */
    public String saveRecords(List<MoveStep> theRecord){
        Properties props = new Properties();
        
        try {
            // load the file name we are using cities
            ClassLoader classLoader = ChineseChess.class.getClassLoader();
            
            InputStream in = classLoader.getResourceAsStream("chinesechess/properties/export.properties");
            props.load(in);
            in.close();
            
            String fileName = props.getProperty("json.tempfile");
            String pathToWrite = "./output";
            
            Path dir = Paths.get(pathToWrite);
            
            // check if the directory exists
            if(Files.notExists(dir)){
                Files.createDirectories(dir);
                out.println("The directory "+ pathToWrite + " has been created.");
            }else{
                out.println("The directory "+ pathToWrite + " is alreay exists.");
                
                out.println("Directory: "+dir.toAbsolutePath());
                out.println("Files: ");
                DirectoryStream<Path> dirStream = Files.newDirectoryStream(dir);
                
                for (Path p : dirStream) {
                    if(Files.isRegularFile(p)){
                        out.println(" "+p.getFileName());
                    }
                }
            }
            
            Path recordsFilePath = Paths.get(pathToWrite, fileName);
            File recordsFile = recordsFilePath.toFile();

            if (Files.exists(recordsFilePath)) {
                if (recordsFile.delete()) {
                    out.println("File deleted successfully.");
                }else{
                    out.println("Fail to delete the file.");
                }
            }
            
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(recordsFile, theRecord);
            
            return recordsFile.getCanonicalPath();
        } catch (Exception e) {
            out.println(e.getMessage());
            return null;
        }
    }
}
