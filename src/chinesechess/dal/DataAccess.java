/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechess.dal;

import chinesechess.types.IParameter;
import chinesechess.types.IParameter.Type;
import chinesechess.types.Parameter;
import com.sun.rowset.CachedRowSetImpl;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author Yinbin
 */
public class DataAccess {
//    // singleton instance for the thread safty 
//    private static volatile DataAccess instance;
//
//    private DataAccess() {}
//
//    public static DataAccess getInstance() {
//        if (instance == null) {
//            synchronized (DataAccess.class) {
//                if (instance == null) {
//                    instance = new DataAccess();
//                }
//            }
//        }
//        return instance;
//    }
    
    /**
     * Loads db.properties file into an instance of properties object
     *
     * @return Properties object
     * @throws Exception
     */
    public Properties getProperties() throws Exception {
        Properties props = new Properties();

        try {
            //Get the path to my db.properties file
            //String propertiesPath = System.getProperty("user.dir") +"\\db.properties";

            //Class Loader for loading the durrenct binary lcoation
            ClassLoader classLoader = DataAccess.class.getClassLoader();

            //Stream in the contents of the db.properties file
            //FileInputStream in = new FileInputStream(propertiesPath);
            
            InputStream in = classLoader.getResourceAsStream("chinesechess/properties/db.properties");

            //LOad the stream into our props object
            props.load(in);

            in.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }

        return props;
    }
    
    private static String url = "";
    private static String userName = "";
    private static String password = "";
    private static String driver = "";

    /**
     * Executes a non query DML statement or DDL statement. Accepts parameters
     * as an object array
     *
     * @param statement This is the SQL statement wanted to execute
     * @param params The parameters for the SQL statement if any
     * @return nothing for now
     */
    public long executeNonQuery(String statement, Object[] params) {
        long returnValue = 0;

        try {
            //Properties Setup
            propertiesSetUp();

            try (Connection conn = (Connection) DriverManager.getConnection(url, userName, password)) {
                try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                    int i = 1;

                    for (Object o : params) {
                        pstmt.setObject(i++, o);
                    }

                    pstmt.execute();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return returnValue;
    }

    /**
     * Execute Non Query using IParamters to control the type (IN/OUT) and SQL
     * Datatype
     *
     * @param statement my SQL Statement to execute
     * @param params the incoming or outgoing parameters for my SProc
     * @return a list of return parameters if any
     */
    public List<Object> executeNonQuery(String statement, List<Parameter> params) {
        List<Object> returnValues = new ArrayList();

        try {
            propertiesSetUp();
            
            try(Connection conn = DriverManager.getConnection(url, userName, password)){
                try(CallableStatement cstmt = conn.prepareCall(statement)){
                    int i = 1;
                    
                    for(Parameter p : params){
                        if(p.getType() == Type.IN){
                            if(p.getSqlType() == java.sql.Types.BLOB && p.getValue() instanceof String){
                                File file = new File(p.getValue().toString());
                                InputStream in = new FileInputStream(file);
                                cstmt.setBinaryStream(i, in);
                            }else{
                                cstmt.setObject(i, p.getValue());
                            }
                        }else{
                            cstmt.registerOutParameter(i, p.getSqlType());
                        }
                        
                        i++;
                    }
                    
                    cstmt.execute();
                    
                    i = 1;
                    
                    for(Parameter p : params){
                        if(p.getType() == Type.OUT){
                            returnValues.add(cstmt.getString(i));
                        }
                        i++;
                    }                    
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return returnValues;
    }

    /**
     * Executes fills of data using Select statements
     *
     * @param statement The select statement to execute
     * @param params Parameters for the select statement if any. Options pass
     * null
     * @return A disconnected data set
     * @throws Exception
     */
    public CachedRowSet executeFill(String statement, Object[] params) throws Exception {
        CachedRowSet rowSet;
        propertiesSetUp();

        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                int i = 1;

                if (params != null) {
                    for (Object o : params) {
                        pstmt.setObject(i, o);
                        i++;
                    }
                }

                try (ResultSet rs = (ResultSet) pstmt.executeQuery()) {
                    rowSet = new CachedRowSetImpl();
                    rowSet.populate(rs);
                }
            }
        }

        return rowSet;
    }

    public Object executeScalar(String statement, Object[] params) throws Exception {
        propertiesSetUp();

        Object returnValue = null;

        try (Connection conn = (Connection) DriverManager.getConnection(url, userName, password)) {
            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                int i = 1;

                if (params != null) {
                    for (Object o : params) {
                        pstmt.setObject(i, o);
                        i++;
                    }
                }

                try (ResultSet rs = (ResultSet) pstmt.executeQuery()) {
                    if (rs.next()) {
                        returnValue = rs.getString(1);
                    }
                }
            }
        }

        return returnValue;
    }

    /**
     *
     * @throws Exception
     */
    private void propertiesSetUp() throws Exception {
        Properties props = this.getProperties();

        url = props.getProperty("database.url");
        userName = props.getProperty("database.username");
        password = props.getProperty("database.password");
        driver = props.getProperty("database.driver");
        Class.forName(driver).newInstance();
    }
}
