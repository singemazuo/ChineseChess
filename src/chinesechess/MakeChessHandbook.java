/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechess;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import lombok.Getter;

/**
 *
 * @author singemazuo
 */
public class MakeChessHandbook extends JPanel implements ActionListener {

    private JTextArea text = null;
    private JScrollPane scroll = null;
    private ChessPanel panel = null;
    private ChessPosition[][] pos;
    
    @Getter
    private LinkedList<MoveStep> handbooks = null;
    
    private LinkedList deaths = null;
    private JButton buttonUndo;
    private int i = 0;
    
    public MakeChessHandbook(ChessPanel panel, ChessPosition[][] pos) {
        this.panel = panel;
        this.pos = pos;
        
        text = new JTextArea();
        scroll = new JScrollPane(text);
        
        handbooks = new LinkedList<MoveStep>();
        deaths = new LinkedList();
        
        buttonUndo = new JButton("Undo");
        buttonUndo.setFont(new Font("Arial", Font.PLAIN, 18));
        
        setLayout(new BorderLayout());
        add(scroll, BorderLayout.CENTER);
//        add(buttonUndo, BorderLayout.SOUTH);
        buttonUndo.addActionListener(this);	
    }
    
    public char numberToLetter(int n) {
        char c = '\0';
        switch (n) {
            case 1:
                c = 'A';
                break;
            case 2:
                c = 'B';
                break;
            case 3:
                c = 'C';
                break;
            case 4:
                c = 'D';
                break;
            case 5:
                c = 'E';
                break;
            case 6:
                c = 'F';
                break;
            case 7:
                c = 'G';
                break;
            case 8:
                c = 'H';
                break;
            case 9:
                c = 'I';
                break;
            case 10:
                c = 'J';
                break;
        }
        return c;	
    }
    
    public void recordHandbook(Chessman chessman, int startI, int startJ, int endI, int endJ) {
        Point pStart = new Point(startI, startJ);
        Point pEnd = new Point(endI, endJ);
        MoveStep step = new MoveStep(pStart, pEnd);
        handbooks.add(step);
        
        String type = chessman.getColorType();
        String name = chessman.getName();
        String m = "#" + type + name + ": " + startI + numberToLetter(startJ) + " 到 " + endI + numberToLetter(endJ);
        text.append(m);
        
        if (chessman.getColorType().equals(panel.aiColor))
            text.append("\n");
    }
    
    public void recordDeathChessman(Object object) {
        deaths.add(object);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        int position = text.getText().lastIndexOf("#");
        
        if (position != -1)
            text.replaceRange("", position, text.getText().length());
        if (handbooks.size() > 0) {
            MoveStep lastStep = (MoveStep) handbooks.getLast();
            handbooks.removeLast();
            
            Object qizi = deaths.getLast();
            deaths.removeLast();
            
            String temp = qizi.toString();
            
            if (temp.equals("Not death")) {
                int startI = lastStep.pStart.x;
                int startJ = lastStep.pStart.y;
                
                int endI = lastStep.pEnd.x;
                int endJ = lastStep.pEnd.y;
                
                Chessman chessman = pos[endI][endJ].getChessman();
                pos[startI][startJ].setChessman(chessman, panel);
                (pos[endI][endJ]).setHasChess(true);
                
                if (chessman.getColorType().equals(panel.playerColor)) {
//                    panel.playerTurn = false;
//                    panel.aiTurn = true;
                    panel.turn = ChessPanel.ChessTurn.AITurn;
                }
                
                if (chessman.getColorType().equals(panel.aiColor)) {
//                    panel.aiTurn = false;
//                    panel.playerTurn = true;
                    panel.turn = ChessPanel.ChessTurn.PlayerTurn;
                }
            } else {
                Chessman removedPiece = (Chessman) qizi;
                int startI = lastStep.pStart.x;
                int startJ = lastStep.pStart.y;
                
                int endI = lastStep.pEnd.x;
                int endJ = lastStep.pEnd.y;
                
                Chessman chessman = pos[endI][endJ].getChessman();
                pos[startI][startJ].setChessman(chessman, panel);
                pos[endI][endJ].setChessman(removedPiece, panel);
                (pos[endI][endJ]).setHasChess(true);
                
                if (chessman.getColorType().equals(panel.playerColor)) {
//                    panel.playerTurn = false;
//                    panel.aiTurn = true;
                    panel.turn = ChessPanel.ChessTurn.AITurn;
                }
                
                if (chessman.getColorType().equals(panel.aiColor)) {
//                    panel.aiTurn = false;
//                    panel.playerTurn = true;
                    panel.turn = ChessPanel.ChessTurn.PlayerTurn;
                }
            }
        }
    }
    
}
