/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechess;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JLabel;
import lombok.Getter;
import lombok.Setter;

/**
 * this class implement the chessman to hosting the properties of each chess piece
 * @author Yinbin Zuo
 */
public class Chessman extends JLabel {
    
    // the name of the chessman (the characters on the piece)
    @Getter
    private String name;
    
    // the background color of the chessman
    private Color backColor = null;
    
    // the foreground color of the font for the chessman
    @Getter
    private Color foreColor;
    
    // the color type of the chessman
    @Getter @Setter
    private String colorType = null;
    
    // the chess panel in this class
    private ChessPanel panel = null;
    
    // the width of the chessman
    @Getter
    private int width;
    
    // the height of the chessman
    @Getter
    private int height;
    
    /**
     * the constructor obtain the name, the foreground color of font, the background color of the piece, width, height of the chessman and the chess panel
     * @param name
     * @param fc
     * @param bc
     * @param width
     * @param height
     * @param panel 
     */
    public Chessman(String name, Color fc, Color bc, int width, int height, ChessPanel panel) {
        this.name = name;
        this.panel = panel;
        this.width = width;
        this.height = height;
        
        foreColor = fc;
        backColor = bc;
        
        setSize(width, height);
        setBackground(bc);
        addMouseMotionListener(panel);
        addMouseListener(panel);
    } 	

    /**
     * paint chess
     * @param g a instance of Graphics
     */
    public void paint(Graphics g) {
        g.drawImage(panel.pieceImg, 2, 2, width-2, height-2, null);
        g.setColor(foreColor);
        g.setFont(new Font("隶书", Font.BOLD, 26));
        g.drawString(name, 7, height - 8);// draw the name on the chess
        
        g.setColor(Color.black);
        
        float lineWidth = 2.3f;
        
        ((Graphics2D)g).setStroke(new BasicStroke(lineWidth));
        ((Graphics2D)g).drawOval(2, 2, width-2, height-2);
    }
}
