/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechess;

import chinesechess.business.ChessRecord;
import chinesechess.business.TempDataProcess;
import com.github.lgooddatepicker.components.DatePicker;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * This class implement associated functionalities of demonstration
 * @author Yinbin Zuo
 * 
 */
public class Demonstration extends JPanel implements ActionListener, ListSelectionListener, Runnable{

    // declare replay, next, auto play, and stop of the buttons
    private JButton replay = null, next = null, auto = null, stop = null;
    
    // declare search button
    private JButton search = null;
    
    // declare date time picker for time range which are for searching
    private DatePicker dpBegin = null,dpEnd = null;
    
    // declare a list for records from database side
    private List<chinesechess.business.models.ChessRecord> records;
    
    // declare demonstration steps list
    @Getter @Setter
    private LinkedList handbooks = null;
    
    // declare a thread for playing asynchronously
    private Thread thread = null;
    
    // declare the index for each step
    private int index = -1;
    
    // declare the panel for saving the chess panel context
    private ChessPanel panel = null;
    
    // declare the text control for taking over the demonstration
    private JTextArea text;
    
    // declare the text field for duration between each step
    private JTextField duration = null;
    
    private int time = 1000;
    
    // declare the demonstration processing
    private String demonstrationProcessing = "";
    
    // declare the split panels
    private JSplitPane splitH = null, splitV = null, splitPanel = null;
    
    // declare the list for saving histories
    private JList histories;
    
    /**
     * this constructor accept the chess panel by passing
     * @param panel 
     */
    public Demonstration(ChessPanel panel) {
        // set the passing panel to local panel
        this.panel = panel;
        
        // instantiate replay, next step, auto play, and stop step buttons
        replay = new JButton("Replay");
        next = new JButton("Next step");
        auto = new JButton("Auto play");
        stop = new JButton("Pause");
        
        // instantiate thread for demonstrating
        thread = new Thread(this);
        
        // add action listeners to replay, next, auto, stop for user operation
        replay.addActionListener(this);
        next.addActionListener(this);
        auto.addActionListener(this);
        stop.addActionListener(this);
        
        // instantiate textarea
        text = new JTextArea();
        
        // instantiate textarea for duration
        duration = new JTextField("1");
        
        // set borderlayout for user operating area
        setLayout(new BorderLayout());
        
        // set operating user interface such as replay, stop, next, auto play buttons' location
        JScrollPane pane = new JScrollPane(text);
        JPanel p = new JPanel(new GridLayout(3, 2));
        p.add(next);
        p.add(replay);
        p.add(auto);
        p.add(stop);
        p.add(new JLabel("Duration(sec)", SwingConstants.CENTER));
        p.add(duration);
        
        // set above controls into approriate splite panel
        splitV = new JSplitPane(JSplitPane.VERTICAL_SPLIT, pane, p);
        splitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,generateHistorySelection(),panel);
        splitH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPanel, splitV);
        splitV.setDividerSize(5);
        splitV.setDividerLocation(400);
        splitPanel.setDividerSize(5);
        splitPanel.setDividerLocation(280);
        splitH.setDividerSize(5);
        splitH.setDividerLocation(760);
        
        // set above controls into this demonstration control and apply it
        add(splitH, BorderLayout.CENTER);
        validate();
    }
    
    /**
     * Generate history selection control
     * @return the final split pane
     */
    private JSplitPane generateHistorySelection(){
        // instantiate history list and add list selection listener
        histories = new JList();
        histories.addListSelectionListener(this);
        
        // generate begin and end date time picker
        dpBegin = new DatePicker();
        dpEnd = new DatePicker();
        dpBegin.setBounds(20,10,60,40);
        dpEnd.setBounds(20,60,60,40);
        
        // generate the search button following the date time pickers and add action listener
        search = new JButton("Search");
        search.setBounds(dpBegin.getLocation().x, (int) (dpEnd.getLocation().y + dpEnd.getSize().getHeight() + 10), 60, 40);
        search.addActionListener(this);
        
        // instantiate the top for hosting the above controls
        JPanel topPanel = new JPanel();
        topPanel.add(dpBegin);
        topPanel.add(dpEnd);
        topPanel.add(search);
        
        // instantiate the split pane for hosting the panels
        JSplitPane pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,topPanel,histories);
        pane.setDividerSize(5);
        pane.setDividerLocation(120);
        return pane;
    }

    /**
     * Convert the number to letter on the chess panel
     * @param n
     * @return 
     */
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
    
    /**
     * the action performed to apply next, replay, auto play, stop rules
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == next) {
                index++;
                if (index < handbooks.size()) {
                    demonstrateStep(index);
                } else {
                    demonstrateOver("The demonstration is over.");
                }
            }

            if (e.getSource() == replay) {
                panel = new ChessPanel(45, 45, 9, 10);
                splitPanel.remove(panel);
                splitPanel.setDividerSize(5);
                splitPanel.setDividerLocation(280);
                splitPanel.setRightComponent(panel);
                splitPanel.validate();
                index = -1;
                text.setText(null);
            }

            if (e.getSource() == auto) {
                next.setEnabled(false);
                replay.setEnabled(false);
                try {
                    time = 1000 * Integer.parseInt(duration.getText().trim());
                } catch (NumberFormatException ee) {
                    time = 1000;
                }

                if (!(thread.isAlive())) {
                    thread = new Thread(this);
                    panel = new ChessPanel(45, 45, 9, 10);
                    splitPanel.remove(panel);
                    splitPanel.setDividerSize(5);
                    splitPanel.setDividerLocation(280);
                    splitPanel.setRightComponent(panel);
                    splitPanel.validate();
                    text.setText(null);
                    thread.start();
                }
            }

            if (e.getSource() == stop) {
                if (e.getActionCommand().equals("Stop")) {
                    demonstrationProcessing = "Pause";
                    stop.setText("Continue");
                    stop.repaint();
                }

                if (e.getActionCommand().equals("Continue")) {
                    demonstrationProcessing = "Continue";
                    thread.interrupt();
                    stop.setText("Pause");
                    stop.repaint();
                }
            }

            if(e.getSource() == search){
                java.sql.Date begin = java.sql.Date.valueOf(dpBegin.getDate());
                java.sql.Date end = java.sql.Date.valueOf(dpEnd.getDate());
                records = ChessRecord.getInstance().GetRecords(begin, end);

                int i = 1;
                DefaultListModel listModel = new DefaultListModel();
                if(records == null) throw new Exception("No records can be found.");

                for(chinesechess.business.models.ChessRecord c: records){
                    listModel.addElement("Record "+i+" : "+c.getCreateDate());
                    i ++;
                }
                histories.setModel(listModel);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    @Override
    public synchronized void run() {
        for (index = 0; index < handbooks.size(); index++) {
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
            }
            while (demonstrationProcessing.equals("Pause")) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    notifyAll();
                }
            }
            demonstrateStep(index);
        }
        if (index >= handbooks.size()) {
            demonstrateOver("Demonstration is over.");
            next.setEnabled(true);
            replay.setEnabled(true);
        }
    }

    public void demonstrateStep(int index) {
        MoveStep step = (MoveStep) handbooks.get(index);
        Point pStart = step.pStart;
        Point pEnd = step.pEnd;
        int startI = pStart.x;
        int startJ = pStart.y;
        int endI = pEnd.x;
        int endJ = pEnd.y;
        Chessman piece = (panel.pos)[startI][startJ].getChessman();
        if ((panel.pos)[endI][endJ].hasChess() == true) {
            Chessman pieceRemoved = (panel.pos)[endI][endJ].getChessman();
            (panel.pos)[endI][endJ].removeChessman(pieceRemoved, panel);
            panel.repaint();
            (panel.pos)[endI][endJ].setChessman(piece, panel);
            (panel.pos)[startI][startJ].setHasChess(false);
            panel.repaint();
        } else {
            (panel.pos)[endI][endJ].setChessman(piece, panel);
            (panel.pos)[startI][startJ].setHasChess(false);
        }
        
        String type = piece.getColorType();
        String name = piece.getName();
        String m = "#" + type + name + ": " + startI + numberToLetter(startJ)
                        + " to " + endI + numberToLetter(endJ);
        text.append(m);
        if (piece.getColorType().equals(panel.aiColor))
            text.append("\n");
    }
 
    public void demonstrateOver(String message) {
        splitPanel.remove(panel);
        splitPanel.setDividerSize(5);
        splitPanel.setDividerLocation(280);
        JLabel label = new JLabel(message);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(Color.blue);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        splitPanel.setRightComponent(label);
        splitPanel.validate();
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        try {
            int index = histories.getSelectedIndex();
            
            chinesechess.business.models.ChessRecord record = records.get(index);
            
            handbooks.clear();
            handbooks.addAll(TempDataProcess.getInstance().loadRecords(record.getRecordFile()));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}
