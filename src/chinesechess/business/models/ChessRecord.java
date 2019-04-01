/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechess.business.models;

import java.io.File;
import java.io.Serializable;
import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * the data model of the chess record
 * @author Yinbin Zuo
 */
public class ChessRecord implements Serializable{
    // the record Id in the data table of the database
    @Getter @Setter
    private int recordId;
    
    // the create date of the record in the data table of the database
    @Getter @Setter
    private Date createDate;
    
    // the record file in the data table of the database
    @Getter @Setter
    private File recordFile;
}
