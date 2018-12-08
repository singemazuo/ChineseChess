/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechess;

import javax.swing.JOptionPane;
import chinesechess.ChessPanel;

/**
 *
 * @author singemazuo
 */
public class GameRule {
    private ChessPanel panel = null;
    private Chessman chessman = null;
    private ChessPosition pos[][];
    private int startI, startJ, endI, endJ;
    
    public GameRule(ChessPanel panel, ChessPosition pos[][]) {
        this.panel = panel;
        this.pos = pos;
    }
    
    public void isWin(Chessman chessman) {
        this.chessman = chessman;
        
        if (chessman.getName() == "将" || chessman.getName() == "帅") {
            if (chessman.getColorType() == "红方") {
                JOptionPane.showMessageDialog(null, "AI win！");
            } else {
                JOptionPane.showMessageDialog(null, "Player win！");
            }
        }
    }
    
    public boolean movePieceRule(Chessman chessman, int startI, int startJ, int endI, int endJ) {
        this.chessman = chessman;
        this.startI = startI;
        this.startJ = startJ;
        this.endI = endI;
        this.endJ = endJ;
        
        int minI = Math.min(startI, endI);
        int maxI = Math.max(startI, endI);
        int minJ = Math.min(startJ, endJ);
        int maxJ = Math.max(startJ, endJ);
        
        boolean canBegin = false;
        if (chessman.getName().equals("车")) {
            if (startI == endI) {
                int j = 0;
                for (j = minJ + 1; j <= maxJ - 1; j++) {
                    if (pos[startI][j].hasChess()) {
                        canBegin = false;
                        break;
                    }
                    
                    if (j == maxJ) {
                        canBegin = true;
                    }
                }
            } else if (startJ == endJ) {
                int i = 0;
                for (i = minI + 1; i <= maxI - 1; i++) {
                    if (pos[i][startJ].hasChess()) {
                        canBegin = false;
                        break;
                    }
                }
                
                if (i == maxI) {
                    canBegin = true;
                }
            } else {
                canBegin = false;
            }
        } else if (chessman.getName().equals("車")) {
            if (startI == endI) {
                int j = 0;
                for (j = minJ + 1; j <= maxJ - 1; j++) {
                    if (pos[startI][j].hasChess()) {
                        canBegin = false;
                        break;
                    }
                    
                    if (j == maxJ) {
                        canBegin = true;
                    }
                }
            } else if (startJ == endJ) {
                int i = 0;
                for (i = minI + 1; i <= maxI - 1; i++) {
                    if (pos[i][startJ].hasChess()) {
                        canBegin = false;
                        break;
                    }
                }
                
                if (i == maxI) {
                    canBegin = true;
                }
            } else {
                canBegin = false;
            }
        }else if (chessman.getName().equals("马")) {
            int xAxle = Math.abs(startI - endI);
            int yAxle = Math.abs(startJ - endJ);
            
            if (xAxle == 2 && yAxle == 1) {
                if (endI > startI) {
                    if (pos[startI + 1][startJ].hasChess()) {
                        canBegin = false;
                    } else {
                        canBegin = true;
                    }
                }
                
                if (endI < startI) {
                    if (pos[startI - 1][startJ].hasChess()) {
                        canBegin = false;
                    } else {
                        canBegin = true;
                    }
                }
            }else if (xAxle == 1 && yAxle == 2) {
                if (endJ > startJ) {
                    if (pos[startI][startJ + 1].hasChess()) {
                        canBegin = false;
                    } else {
                        canBegin = true;
                    }
                }
                
                if (endJ < startJ) {
                    if (pos[startI][startJ - 1].hasChess()) {
                        canBegin = false;
                    } else {
                        canBegin = true;
                    }
                }
            } else {
                canBegin = false;
            }
        } else if (chessman.getName().equals("馬")) {
            int xAxle = Math.abs(startI - endI);
            int yAxle = Math.abs(startJ - endJ);
            
            if (xAxle == 2 && yAxle == 1) {
                if (endI > startI) {
                    if (pos[startI + 1][startJ].hasChess()) {
                        canBegin = false;
                    } else {
                        canBegin = true;
                    }
                }
                
                if (endI < startI) {
                    if (pos[startI - 1][startJ].hasChess()) {
                        canBegin = false;
                    }else{
                        canBegin = true;
                    }
                } 
            }else if (xAxle == 1 && yAxle == 2) {
                if (endJ > startJ) {
                    if (pos[startI][startJ + 1].hasChess()) {
                        canBegin = false;
                    } else {
                        canBegin = true;
                    }
                }
                
                if (endJ < startJ) {
                    if (pos[startI][startJ - 1].hasChess()) {
                        canBegin = false;
                    } else {
                        canBegin = true;
                    }
                }
            } else {
                canBegin = false;
            }
        } else if (chessman.getName().equals("象")) {
            int centerI = (startI + endI) / 2;
            int centerJ = (startJ + endJ) / 2;
            int xAxle = Math.abs(startI - endI);
            int yAxle = Math.abs(startJ - endJ);
            
            if (xAxle == 2 && yAxle == 2 && endJ <= 5) {
                if (pos[centerI][centerJ].hasChess()) {
                    canBegin = false;
                } else {
                    canBegin = true;
                }
            } else {
                canBegin = false;
            }
        } else if (chessman.getName().equals("相")) {
            int centerI = (startI + endI) / 2;
            int centerJ = (startJ + endJ) / 2;
            int xAxle = Math.abs(startI - endI);
            int yAxle = Math.abs(startJ - endJ);
            
            if (xAxle == 2 && yAxle == 2 && endJ >= 6) {
                if (pos[centerI][centerJ].hasChess()) {
                    canBegin = false;
                } else {
                    canBegin = true;
                }
            } else {
                canBegin = false;
            }
        } else if (chessman.getName().equals("炮")) {
            int number = 0;
            if (startI == endI) {
                int j = 0;
                for (j = minJ + 1; j <= maxJ - 1; j++) {
                    if (pos[startI][j].hasChess()) {
                        number++;
                    }
                }
                
                if (number > 1) {
                    canBegin = false;
                } else if (number == 1) {
                    if (pos[endI][endJ].hasChess()) {
                        canBegin = true;
                    }
                } else if (number == 0 && !pos[endI][endJ].hasChess()) {
                    canBegin = true;
                }
            } else if (startJ == endJ) {
                int i = 0;
                for (i = minI + 1; i <= maxI - 1; i++) {
                    if (pos[i][startJ].hasChess()) {
                        number++;
                    }
                }
                
                if (number > 1) {
                    canBegin = false;
                } else if (number == 1) {
                    if (pos[endI][endJ].hasChess()) {
                        canBegin = true;
                    }
                } else if (number == 0 && !pos[endI][endJ].hasChess()) {
                    canBegin = true;
                }
            } else {
                canBegin = false;
            }
        } else if (chessman.getName().equals("兵")) {
            int xAxle = Math.abs(startI - endI);
            int yAxle = Math.abs(startJ - endJ);
            
            if (endJ >= 6) {
                if (startJ - endJ == 1 && xAxle == 0) {
                    canBegin = true;
                }else {
                    canBegin = false;
                }
            } else if (endJ <= 5) {
                if ((startJ - endJ == 1) && (xAxle == 0)) {
                    canBegin = true;
                } else if ((endJ - startJ == 0) && (xAxle == 1)) {
                    canBegin = true;
                } else {
                    canBegin = false;
                }
            }
        } else if (chessman.getName().equals("卒")) {
            int xAxle = Math.abs(startI - endI);
            int yAxle = Math.abs(startJ - endJ);
            
            if (endJ <= 5) {
                if (endJ - startJ == 1 && xAxle == 0) {
                    canBegin = true;
                } else {
                    canBegin = false;
                }
            } else if (endJ >= 6) {
                if ((endJ - startJ == 1) && (xAxle == 0)) {
                    canBegin = true;
                } else if ((endJ - startJ == 0) && (xAxle == 1)) {
                    canBegin = true;
                } else {
                    canBegin = false;
                }
            }
        }else if (chessman.getName().equals("士")) {
            int xAxle = Math.abs(startI - endI);
            int yAxle = Math.abs(startJ - endJ);
            
            if (endI <= 6 && endI >= 4 && xAxle == 1 && yAxle == 1) {
                canBegin = true;
            } else {
                canBegin = false;
            }
        } else if (chessman.getName().equals("仕")) {
            int xAxle = Math.abs(startI - endI);
            int yAxle = Math.abs(startJ - endJ);
            
            if (endI <= 6 && endI >= 4 && xAxle == 1 && yAxle == 1) {
                canBegin = true;
            } else {
                canBegin = false;
            }
        } else if ((chessman.getName().equals("帅")) || (chessman.getName().equals("将"))) {
            int xAxle = Math.abs(startI - endI);
            int yAxle = Math.abs(startJ - endJ);
            
            if (endI <= 6 && endI >= 4) {
                if ((xAxle == 1 && yAxle == 0) || (xAxle == 0 && yAxle == 1)) {
                    canBegin = true;
                } else {
                    canBegin = false;
                }
            } else {
                canBegin = false;
            }
        }
        return canBegin;
    }
}
