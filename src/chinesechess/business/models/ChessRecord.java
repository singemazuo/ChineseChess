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
 *
 * @author singemazuo
 */
public class ChessRecord implements Serializable{
    @Getter @Setter
    private int recordId;
    
    @Getter @Setter
    private Date createDate;
    
    @Getter @Setter
    private File recordFile;
}
