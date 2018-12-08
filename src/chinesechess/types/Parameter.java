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
 *
 * @author singemazuo
 */
public class Parameter{
    
    @Setter @Getter
    private Object value;
    
    @Setter @Getter
    private Type type;
    
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
