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
 *
 * @author singemazuo
 */
public class Chessman extends JLabel {
    
    @Getter
    private String name; // the name of chessman
    
    private Color backColor = null;
    
    @Getter
    private Color foreColor;// background color and foreground color
    
    @Getter @Setter
    private String colorType = null;
    private ChessPanel panel = null;
    
    @Getter
    private int width;
    
    @Getter
    private int height;
    
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
