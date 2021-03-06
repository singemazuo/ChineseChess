/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechess.types;

import chinesechess.types.IParameter.Type;
import lombok.Getter;
import lombok.Setter;

/**
 * To define the parameter for the store procedure
 * @author Yinbin Zuo
 */
public class Parameter{
    
    // the value of the parameter
    @Setter @Getter
    private Object value;
    
    // the data type of the parameter
    @Setter @Getter
    private Type type;
    
    // the sql data type of the parameter
    @Setter @Getter
    private int sqlType;

    public Parameter() {
    }

    public Parameter(Object value) {
        this.value = value;
        this.type = Type.IN;
    }
    
    public Parameter(Object value,Type type){
        this.value = value;
        this.type = type;
    }

    public Parameter(Object value, Type type, int sqlType) {
        this.value = value;
        this.type = type;
        this.sqlType = sqlType;
    }
}
