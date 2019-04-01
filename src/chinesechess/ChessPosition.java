/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechess;

import lombok.Getter;
import lombok.Setter;
import chinesechess.ChessPanel;

/**
 * this class implement the position of the chessman
 * @author Yinbin Zuo
 */
public class ChessPosition {
    
    @Getter @Setter
    public int x, y;// chess position
    
    private boolean hasChess; // detemine there is a chess on the position
    
    @Getter
    public Chessman chessman = null;// the chessman that has changed
    
    private ChessPanel panel = null;// the panel which has this position
    
    /**
     * this constructor obtains the location of the chessman and whether there is a chessman on the position
     * @param x
     * @param y
     * @param hasChess 
     */
    public ChessPosition(int x, int y, boolean hasChess) {
        this.x = x;
        this.y = y;	
        this.hasChess = hasChess;
    }
    
    /**
     * Has a chessman on the position
     * @return 
     */
    public boolean hasChess() {
        return this.hasChess;
    }
    
    /**
     * set the boolean whether there is chessman on this position
     * @param hasChess 
     */
    public void setHasChess(boolean hasChess){
        this.hasChess = hasChess;
    }
    
//    public void setChessman(Chessman chessman, ChessPanel panel) {
//        this.panel = panel;
//        this.chessman = chessman;
//        
//        panel.add(chessman);
//        
//        int w = (panel.unitWidth);
//        int h = (panel.unitHeight);
//        
//        chessman.setBounds(x - w / 2, y - h / 2, w, h);// chessman's position, width, height
//        hasChess = true;
//        panel.validate();
//    }
    
    /**
     * set the chessman on the chess panel
     * @param chessman
     * @param panel 
     */
    public void setChessman(Chessman chessman, ChessPanel panel) {
        this.panel = panel;
        this.chessman = chessman;
        
        panel.add(chessman);
//        if(panel.turn == ChessPanel.ChessTurn.AITurn){
//            if(chessman.getColorType().equals(panel.playerColor)){
//                panel.setLayer(chessman, 3);
//            }else if(chessman.getColorType().equals(panel.aiColor)){
//                panel.setLayer(chessman, 4);
//            }
//            
//        }else if(panel.turn == ChessPanel.ChessTurn.PlayerTurn){
//            if(chessman.getColorType().equals(panel.playerColor)){
//                panel.setLayer(chessman, 4);
//            }else if(chessman.getColorType().equals(panel.aiColor)){
//                panel.setLayer(chessman, 3);
//            }
//        }    
        
        int w = (panel.unitWidth);
        int h = (panel.unitHeight);
        
        chessman.setBounds(x - w / 2, y - h / 2, w, h);// chessman's position, width, height
        hasChess = true;
        panel.validate();
    }
    
    /**
     * remove the chess man from the chess panel
     * @param piece
     * @param panel 
     */
    public void removeChessman(Chessman piece, ChessPanel panel) {
        this.panel = panel;
        this.chessman = chessman;
        
        panel.remove(chessman);
        panel.validate();
        
        hasChess = false;	
    }
}
