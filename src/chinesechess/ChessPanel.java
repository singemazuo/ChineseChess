/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechess;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * This class implements the chess panel
 * @author Yinbin Zuo
 */
public class ChessPanel extends JPanel implements MouseListener, MouseMotionListener{
    // the enumeration of the chess turn
    public enum ChessTurn{
        PlayerTurn,
        AITurn
    }
    
    // to store the chess posistion on the panel
    public ChessPosition pos[][];
    
    // the width of the piece, and the height of the piece
    public int unitWidth, unitHeight;
    
    // the location of the horizonal and vertical line
    private int hor, ver;
    private int x, y;
    private Image img;
    protected Image pieceImg;
    private boolean move = false;
    public String playerColor = "Player", aiColor = "AI";
    
    // the variables of each chessman for player side
    public Chessman playerCar1, playerCar2, playerHorse1, playerHorse2, playerMinister1, playerMinister2, playerCommander, playerGuard1, playerGuard2, playerSoldier1, playerSoldier2, playerSoldier3, playerSoldier4, playerSoldier5, playerCannon1, playerCannon2;
    
    // the variables of each chessman for AI side
    public Chessman aiCar1, aiCar2, aiHorse1, aiHorse2, aiMinister1, aiMinister2, aiCommander, aiGuard1, aiGuard2, aiSoldier1, aiSoldier2, aiSoldier3, aiSoldier4, aiSoldier5, aiCannon1, aiCannon2;
    
//    private final String[][] chessmanInterface = {
//        {"車","車","馬","馬","炮","炮","相","相","仕","仕","帅","兵","兵","兵","兵","兵"},
//        {"车","车","马","马","炮","炮","象","象","士","士","将","卒","卒","卒","卒","卒"}
//    };
    
    public int startX, startY;
    public int startI, startJ;
//    public boolean playerTurn = true, aiTurn = false;
    
    // the game rule
    public GameRule rule = null;
    public MakeChessHandbook record = null;
    
    public ChessTurn turn;
    
    /**
     * the constructor accept the width of the panel, the height of the panel, horizonal count and vertical count
     * @param width
     * @param height
     * @param horizonal
     * @param vertical 
     */
    public ChessPanel(int width, int height, int horizonal, int vertical) {
        setLayout(null);
        addMouseListener(this);
        addMouseMotionListener(this);
        
        turn = ChessTurn.PlayerTurn;
        
        Color bc = getBackground();
        unitWidth = width;
        unitHeight = height;
        
        hor = horizonal;
        ver = vertical;
        
        pos = new ChessPosition[horizonal + 1][vertical + 1];
        
        for (int i = 1; i <= horizonal; i++) {
            for (int j = 1; j <= vertical; j++) {
                pos[i][j] = new ChessPosition(i * unitWidth, j * unitHeight, false);
            }
        }
        
        rule = new GameRule(this, pos);
        record = new MakeChessHandbook(this, pos);
        img = Toolkit.getDefaultToolkit().getImage("images/board.png");
        pieceImg = Toolkit.getDefaultToolkit().getImage("images/chessman.png");
        
//        for (int i = 0; i < chessmanInterface[0].length; i++) {
//            Chessman cm = new Chessman(chessmanInterface[0][i], Color.red, bc, width - 4, height - 4, this);
//            cm.setColorType(playerColor);
//        }
//        
//        for (int i = 0; i < chessmanInterface[1].length; i++) {
//            Chessman cm = new Chessman(chessmanInterface[1][i], Color.black, bc, width - 4, height - 4, this);
//            cm.setColorType(playerColor);
//        }

        // to generate the chessmans according to the player side
        
        playerCar1 = new Chessman("車", Color.red, bc, width - 4, height - 4, this);
        playerCar1.setColorType(playerColor);
        
        playerCar2 = new Chessman("車", Color.red, bc, width - 4, height - 4, this);
        playerCar2.setColorType(playerColor);
        
        playerHorse1 = new Chessman("馬", Color.red, bc, width - 4, height - 4, this);
        playerHorse1.setColorType(playerColor);
        
        playerHorse2 = new Chessman("馬", Color.red, bc, width - 4, height - 4, this);
        playerHorse2.setColorType(playerColor);
        
        playerCannon1 = new Chessman("炮", Color.red, bc, width - 4, height - 4, this);
        playerCannon1.setColorType(playerColor);
        
        playerCannon2 = new Chessman("炮", Color.red, bc, width - 4, height - 4, this);
        playerCannon2.setColorType(playerColor);
        
        playerMinister1 = new Chessman("相", Color.red, bc, width - 4, height - 4, this);
        playerMinister1.setColorType(playerColor);
        
        playerMinister2 = new Chessman("相", Color.red, bc, width - 4, height - 4, this);
        playerMinister2.setColorType(playerColor);
        
        playerGuard1 = new Chessman("仕", Color.red, bc, width - 4, height - 4, this);
        playerGuard1.setColorType(playerColor);
        
        playerGuard2 = new Chessman("仕", Color.red, bc, width - 4, height - 4, this);
        playerGuard2.setColorType(playerColor);
        
        playerCommander = new Chessman("帅", Color.red, bc, width - 4, height - 4, this);	
        playerCommander.setColorType(playerColor);
        
        playerSoldier1 = new Chessman("兵", Color.red, bc, width - 4, height - 4, this);
        playerSoldier1.setColorType(playerColor);
        
        playerSoldier2 = new Chessman("兵", Color.red, bc, width - 4, height - 4, this);
        playerSoldier2.setColorType(playerColor);
        
        playerSoldier3 = new Chessman("兵", Color.red, bc, width - 4, height - 4, this);
        playerSoldier3.setColorType(playerColor);
        
        playerSoldier4 = new Chessman("兵", Color.red, bc, width - 4, height - 4, this);
        playerSoldier4.setColorType(playerColor);
        
        playerSoldier5 = new Chessman("兵", Color.red, bc, width - 4, height - 4, this);
        playerSoldier5.setColorType(playerColor);
        
        ////////////////////////////////////////////////////////////////////////
        
        // to generate the chessmans according to the AI side
        
        aiCommander = new Chessman("将", Color.black, bc, width - 4, height - 4, this);
        aiCommander.setColorType(aiColor);
        
        aiGuard1 = new Chessman("士", Color.black, bc, width - 4, height - 4, this);
        aiGuard1.setColorType(aiColor);
        
        aiGuard2 = new Chessman("士", Color.black, bc, width - 4, height - 4, this);
        aiGuard2.setColorType(aiColor);
        
        aiCar1 = new Chessman("车", Color.black, bc, width - 4, height - 4, this);
        aiCar1.setColorType(aiColor);
        
        aiCar2 = new Chessman("车", Color.black, bc, width - 4, height - 4, this);
        aiCar2.setColorType(aiColor);
        
        aiCannon1 = new Chessman("炮", Color.black, bc, width - 4, height - 4, this);
        aiCannon1.setColorType(aiColor);
        
        aiCannon2 = new Chessman("炮", Color.black, bc, width - 4, height - 4, this);
        aiCannon2.setColorType(aiColor);
        
        aiMinister1 = new Chessman("象", Color.black, bc, width - 4, height - 4, this);
        aiMinister1.setColorType(aiColor);
        
        aiMinister2 = new Chessman("象", Color.black, bc, width - 4, height - 4, this);
        aiMinister2.setColorType(aiColor);
        
        aiHorse1 = new Chessman("马", Color.black, bc, width - 4, height - 4, this);
        aiHorse1.setColorType(aiColor);
        
        aiHorse2 = new Chessman("马", Color.black, bc, width - 4, height - 4, this);
        aiHorse2.setColorType(aiColor);
        
        aiSoldier1 = new Chessman("卒", Color.black, bc, width - 4, height - 4, this);
        aiSoldier1.setColorType(aiColor);
        
        aiSoldier2 = new Chessman("卒", Color.black, bc, width - 4, height - 4, this);
        aiSoldier2.setColorType(aiColor);
        
        aiSoldier3 = new Chessman("卒", Color.black, bc, width - 4, height - 4, this);
        aiSoldier3.setColorType(aiColor);
        
        aiSoldier4 = new Chessman("卒", Color.black, bc, width - 4, height - 4, this);
        aiSoldier4.setColorType(aiColor);
        
        aiSoldier5 = new Chessman("卒", Color.black, bc, width - 4, height - 4, this);
        aiSoldier5.setColorType(aiColor);
        
        ////////////////////////////////////////////////////////////////////////
        
        // Initialize the those chessmans on the above on the panel
        pos[1][10].setChessman(playerCar1, this);
        pos[2][10].setChessman(playerHorse1, this);
        pos[3][10].setChessman(playerMinister1, this);
        pos[4][10].setChessman(playerGuard1, this);
        pos[5][10].setChessman(playerCommander, this);
        pos[6][10].setChessman(playerGuard2, this);
        pos[7][10].setChessman(playerMinister2, this);
        pos[8][10].setChessman(playerHorse2, this);
        pos[9][10].setChessman(playerCar2, this);
        
        pos[2][8].setChessman(playerCannon1, this);
        pos[8][8].setChessman(playerCannon2, this);
        
        pos[1][7].setChessman(playerSoldier1, this);
        pos[3][7].setChessman(playerSoldier2, this);
        pos[5][7].setChessman(playerSoldier3, this);
        pos[7][7].setChessman(playerSoldier4, this);
        pos[9][7].setChessman(playerSoldier5, this);
        
        ////////////////////////////////////////////////////////////////////////
        
        pos[1][1].setChessman(aiCar1, this);
        pos[2][1].setChessman(aiHorse1, this);
        pos[3][1].setChessman(aiMinister1, this);
        pos[4][1].setChessman(aiGuard1, this);
        pos[5][1].setChessman(aiCommander, this);
        pos[6][1].setChessman(aiGuard2, this);
        pos[7][1].setChessman(aiMinister2, this);
        pos[8][1].setChessman(aiHorse2, this);
        pos[9][1].setChessman(aiCar2, this);
        
        pos[2][3].setChessman(aiCannon1, this);
        pos[8][3].setChessman(aiCannon2, this);
        
        pos[1][4].setChessman(aiSoldier1, this);
        pos[3][4].setChessman(aiSoldier2, this);
        pos[5][4].setChessman(aiSoldier3, this);
        pos[7][4].setChessman(aiSoldier4, this);
        pos[9][4].setChessman(aiSoldier5, this);
    }
    
    /**
     * Custom paint component
     * @param g 
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // get the width of image
        int imgWidth = img.getWidth(this);
        // get the height of image
        int imgHeight = img.getHeight(this);
        
        // get the width of frame
        int FWidth = getWidth();
        // get the height of frame
        int FHeight = getHeight();
        
        int x = (FWidth - imgWidth) / 2;
        int y = (FHeight - imgHeight) / 2;
        
        g.drawImage(img, x, y, null);
        for (int j = 1; j <= ver; j++) {
            g.drawLine(pos[1][j].x, pos[1][j].y, pos[hor][j].x,pos[hor][j].y);
        }
        
        for (int i = 1; i <= hor; i++) {
            if (i != 1 && i != hor) {
                g.drawLine(pos[i][1].x, pos[i][1].y, pos[i][ver - 5].x, pos[i][ver - 5].y);
                g.drawLine(pos[i][ver - 4].x, pos[i][ver - 4].y, pos[i][ver].x, pos[i][ver].y);
            } else {
                g.drawLine(pos[i][1].x, pos[i][1].y, pos[i][ver].x, pos[i][ver].y);
            }
        }
        
        g.drawLine(pos[4][1].x, pos[4][1].y, pos[6][3].x, pos[6][3].y);
        g.drawLine(pos[6][1].x, pos[6][1].y, pos[4][3].x, pos[4][3].y);
        g.drawLine(pos[4][8].x, pos[4][8].y, pos[6][ver].x, pos[6][ver].y);
        g.drawLine(pos[4][ver].x, pos[4][ver].y, pos[6][8].x, pos[6][8].y);
        
        for (int i = 1; i <= hor; i++) {
            g.drawString("" + i, i * unitWidth, unitHeight / 2);
        }
        
        int j = 1;
        for (char c = 'A'; c <= 'J'; c++) {
            g.drawString("" + c, unitWidth / 4, j * unitHeight);
            j++;
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    /**
     * To validate the mouse pressed event is which trigger object
     * @param e the event context
     */
    @Override
    public void mousePressed(MouseEvent e) {
        Chessman chessman = null;
        Rectangle rect = null;
        
        if (e.getSource() == this)
            move = false;
        
        if (move == false) {
            if (e.getSource() instanceof Chessman) {
                chessman = (Chessman) e.getSource();
                startX = chessman.getBounds().x;
                startY = chessman.getBounds().y;
                
                rect = chessman.getBounds();
                for (int i = 1; i <= hor; i++) {
                    for (int j = 1; j <= ver; j++) {
                        int x = pos[i][j].getX();
                        int y = pos[i][j].getY();
                        if (rect.contains(x, y)) {
                            startI = i;
                            startJ = j;
                            
                            System.out.println("The started location for the chessman: x = "+x+",y = "+y);
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * To validate the mouse released event is which trigger object
     * @param e the event context
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        Chessman chessman = null;
        move = false;
        Rectangle rect = null;
        
        // the event trigger is chessman
        if (e.getSource() instanceof Chessman) {
            chessman = (Chessman) e.getSource();
            rect = chessman.getBounds();
            e = SwingUtilities.convertMouseEvent(chessman, e, this);
        }
        
        // the event trigger is chess panel
        if (e.getSource() == this) {
            boolean containChessPoint = false;
            int x = 0, y = 0;
            int m = 0, n = 0;
            
            if (chessman != null) {
                for (int i = 1; i <= hor; i++) {
                    for (int j = 1; j <= ver; j++) {
                        x = pos[i][j].getX();
                        y = pos[i][j].getY();
                        
                        if (rect.contains(x, y)) { 
                            containChessPoint = true;
                            m = i;
                            n = j;
                            break;
                        }
                    }
                }
            }
            
            // once the user release chessman, there is a chessman on this position
            if (chessman != null && containChessPoint) {
                System.out.println("The location for the released chessman: x = "+pos[m][n].x+",y = "+pos[m][n].y);
                Color pieceColor = chessman.getForeColor();
                // if there is a chessman on this position
                if (pos[m][n].hasChess()) {
                    Color c = (pos[m][n].getChessman()).getForeColor();
                    // check the chessman color to differetiate which aspect chessman has moved
                    if (pieceColor.getRGB() == c.getRGB()) {
                        chessman.setLocation(startX, startY);
                        (pos[startI][startJ]).setHasChess(true);
                    } else {
                        // validate the move rules for chessman
                        boolean ok = rule.movePieceRule(chessman, startI, startJ, m, n);
                        if (ok) {// if it's ok processing next step
                            Chessman pieceRemoved = pos[m][n].getChessman();
                            pos[m][n].removeChessman(pieceRemoved, this);
                            pos[m][n].setChessman(chessman, this);
                            (pos[startI][startJ]).setHasChess(false);
                            record.recordHandbook(chessman, startI, startJ, m, n);
                            record.recordDeathChessman(pieceRemoved);
                            rule.isWin(pieceRemoved);
                            if (chessman.getColorType().equals(playerColor)) {
//                                playerTurn = false;
//                                aiTurn = true;
                                turn = ChessTurn.AITurn;
                            }
                            
                            if (chessman.getColorType().equals(aiColor)) {
//                                playerTurn = true;
//                                aiTurn = false;
                                turn = ChessTurn.PlayerTurn;
                            }
                            validate();
                            repaint();
                        } else {// otherwise move this chessman back to the start position
                            chessman.setLocation(startX, startY);
                            (pos[startI][startJ]).setHasChess(true);
                        }
                    }
                } else {
                    boolean ok = rule.movePieceRule(chessman, startI, startJ, m, n);
                    if (ok) {
                        pos[m][n].setChessman(chessman, this);
                        (pos[startI][startJ]).setHasChess(false);
                        record.recordHandbook(chessman, startI, startJ, m, n);
                        record.recordDeathChessman("Not death");
                        
                        if (chessman.getColorType().equals(playerColor)) {
//                            playerTurn = false;
//                            aiTurn = true;
                            turn = ChessTurn.AITurn;
                        }
                        
                        if (chessman.getColorType().equals(aiColor)) {
//                            aiTurn = false;
//                            playerTurn = true;
                            turn = ChessTurn.PlayerTurn;
                        }
                    }else{
                        chessman.setLocation(startX, startY);
                        (pos[startI][startJ]).setHasChess(true);
                    }
                }
            }
            
            if (chessman != null && !containChessPoint) {
                chessman.setLocation(startX, startY);
                (pos[startI][startJ]).setHasChess(true);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    /**
     * To validate the mouse dragged event is which trigger object
     * @param e the event context
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        Chessman chessman = null;
        if (e.getSource() instanceof Chessman) {
            chessman = (Chessman) e.getSource();
            move = true;
            e = SwingUtilities.convertMouseEvent(chessman, e, this);
        }
        
        if (e.getSource() == this) {
            if (move && chessman != null) {
                x = e.getX();
                y = e.getY();
                
                if (turn == ChessTurn.PlayerTurn && ((chessman.getColorType()).equals(playerColor))) {
                    chessman.setLocation(x - chessman.getWidth() / 2, y - chessman.getHeight() / 2);
                }
                
                if (turn == ChessTurn.AITurn && (chessman.getColorType().equals(aiColor))) {
                    chessman.setLocation(x - chessman.getWidth() / 2, y - chessman.getHeight() / 2);
                }
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
    }
    
}
