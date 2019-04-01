/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechess;

import java.awt.Point;
import java.io.Serializable;

/**
 * this class implement the move step model
 * @author Yinbin Zuo
 */
public class MoveStep implements Serializable {
    public Point pStart, pEnd;
    
    public MoveStep(){}
    
    public MoveStep(Point p1, Point p2) {
        pStart = p1;
        pEnd = p2;
    }
}
