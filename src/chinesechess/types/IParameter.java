/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechess.types;

/**
 * a interface for parameter of the database accessing layer
 * @author Yinbin Zuo
 */
public interface IParameter {
    
    /**
     * Parameter type
     * Input or Outputs
     */
    public enum Type{
        IN,
        OUT
    }
    
    // set value for parameter
    void setValue(Object value);
    
    // set direction type for parameter
    void setType(Type type);
    
    // set sql type for parameter
    void setSQLType(int sqlType);
    
    // get value for parameter
    Object getValue();
    
    // get direction type for parameter
    Type getType();
    
    // set sql type for parameter
    int getSQLType();
}
