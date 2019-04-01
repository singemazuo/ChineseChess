/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinesechess;

import chinesechess.business.ChessRecord;
import chinesechess.business.TempDataProcess;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

/**
 * The class implement frame for the main container
 * @author Yinbin Zuo
 */
public class ChineseChess extends JFrame implements ActionListener{
    // declare panel for hosting chess panel
    private ChessPanel panel = null;
    
    // declare demonstration for hosting Demonstration class
    private Demonstration demonstration = null;	
    
    // declare record for hosting MakeChessHandbook class
    private MakeChessHandbook record = null;
    
    // declare con for hosting all of the controls in the container
    private Container con = null;
    
    // declare bar for JMenuBar
    private JMenuBar bar;
    
    // declare fileMenu which will be in the menu bar
    private JMenu fileMenu;
    
    // declare new game, save, demonstrate menu item into menu
    private JMenuItem makeNewGame, saveHandbook, demonstrateHandbook;
    
    // store each step by user and put in the linkedlist
    private LinkedList handbook = null;
    
    /**
     * This constructor instantiate entire user interface
     */
    public ChineseChess() {
        bar = new JMenuBar();
        
        // generate approriate menu and menu items
        fileMenu = new JMenu("Chinese Chess");
        makeNewGame = new JMenuItem("New Game");
        saveHandbook = new JMenuItem("Save Handbook");
        demonstrateHandbook = new JMenuItem("Demonstrate Handbook");
        
        fileMenu.add(makeNewGame);
        fileMenu.add(saveHandbook);
        fileMenu.add(demonstrateHandbook);
        
        bar.add(fileMenu);
        setJMenuBar(bar);
        
        // set title
        setTitle(makeNewGame.getText());
        
        // add action listener for new game, save, and demon
        makeNewGame.addActionListener(this);
        saveHandbook.addActionListener(this);
        demonstrateHandbook.addActionListener(this);
        
        // set bounds for chess panel
        panel = new ChessPanel(45, 45, 9, 10);
        record = panel.record;
        
        // get the root content pane
        con = getContentPane();
        
        // generate split pane and set the divider location
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, panel, record);		
        split.setDividerSize(5);
        split.setDividerLocation(460);
        
        // add the split pane into the container
        con.add(split, BorderLayout.CENTER);
        
        // register window listener to exit the application
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }		
        });
        
        // refresh and performs this frame
        setVisible(true);
        setBounds(60, 20, 690, 540);
//        fileChooser = new JFileChooser();
        con.validate();
        validate();
    }

    /**
     * action processing event
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // resize the frame because of some reasons causes resize frame, therefore the frame must be resized to the original size
        this.setSize(690, 540);
        
        // validating whether the event source is makeNewGame
        if (e.getSource() == makeNewGame) {
            // remove all contents in the container and add new stuffs again
            con.removeAll();
            saveHandbook.setEnabled(true);
            
            this.setTitle(makeNewGame.getText());
            panel = new ChessPanel(45, 45, 9, 10);
            record = panel.record;
            
            JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, panel, record);			
            split.setDividerSize(5);
            split.setDividerLocation(460);
            con.add(split, BorderLayout.CENTER);
            validate();
        }		
        
        // validating whether the event source is saveHandbook
        if (e.getSource() == saveHandbook) {
            // generate the checkbox in the dialog for ensuring to update data on the database
            JCheckBox checkbox = new JCheckBox("Also save to database");
            String message = "Are you sure save these records?";
            Object[] params = {message, checkbox};
            int n = JOptionPane.showConfirmDialog(this, params, "Prompt", JOptionPane.YES_NO_OPTION);
            boolean confirmToSaveData = checkbox.isSelected();
            
            String path = TempDataProcess.getInstance().saveRecords(record.getHandbooks());
            
            if(confirmToSaveData && path != null){
                ChessRecord.getInstance().insertRecord(path);
            }
//            int state = fileChooser.showSaveDialog(null);
//            File saveFile = fileChooser.getSelectedFile();
//            if (saveFile != null && state == JFileChooser.APPROVE_OPTION) {		
//                try {
//                    FileOutputStream outOne = new FileOutputStream(saveFile);					
//                    ObjectOutputStream outTwo = new ObjectOutputStream(outOne);                                 
//                    outTwo.writeObject(record.getHandbooks());
//                    outOne.close();	
//                    outTwo.close();
//                } catch (IOException ex) {
//                    
//                }			
//            }		
        }
        
        // validating whether the event source is demonstrateHandbook
        if (e.getSource() == demonstrateHandbook) {
            // bring user to the demonstrating page
            this.setSize(990, 540);
            con.removeAll();
            con.repaint();
            con.validate();
            validate();
            
            saveHandbook.setEnabled(false);
            
            try {
                if(handbook == null){
                    handbook = new LinkedList();
                }
                handbook.addAll(TempDataProcess.getInstance().loadRecords());
                
                ChessPanel panel = new ChessPanel(45, 45, 9, 10);
                demonstration = new Demonstration(panel);
                demonstration.setHandbooks(handbook);
                con.add(demonstration, BorderLayout.CENTER);
                con.validate();
                validate();
            } catch (Exception ex) {
                // if no history records in the local folder, displaying regarding messages
                JLabel label = new JLabel("This is not handbook extension");
                    
                label.setFont(new Font("Arial", Font.BOLD, 14));
                label.setForeground(Color.red);
                label.setHorizontalAlignment(SwingConstants.CENTER);

                con.add(label, BorderLayout.CENTER);
                con.validate();

                this.setTitle("Not open the handbook.");
                validate();
            }
            
            
//            int state = fileChooser.showOpenDialog(null);
//            File openFile = fileChooser.getSelectedFile();
//            if (openFile != null && state == JFileChooser.APPROVE_OPTION) {		
//                try {
//                    FileInputStream inOne = new FileInputStream(openFile);					
//                    ObjectInputStream inTwo = new ObjectInputStream(inOne);					
//                    handbook = (LinkedList) inTwo.readObject();					
//                    inOne.close();					
//                    inTwo.close();
//                    
//                    ChessPanel panel = new ChessPanel(45, 45, 9, 10);
//                    demonstration = new Demonstration(panel);
//                    demonstration.setHandbooks(handbook);
//                    con.add(demonstration, BorderLayout.CENTER);
//                    con.validate();
//                    validate();
//                    
//                    this.setTitle(demostrateHandbook.getText() + ":" + openFile);				
//                } catch (Exception ex) {
//                    JLabel label = new JLabel("This is not handbook extension");
//                    
//                    label.setFont(new Font("Arial", Font.BOLD, 60));
//                    label.setForeground(Color.red);
//                    label.setHorizontalAlignment(SwingConstants.CENTER);
//                    
//                    con.add(label, BorderLayout.CENTER);
//                    con.validate();
//                    
//                    this.setTitle("Not open the handbook.");
//                    validate();
//                }
//            } else {
//                JLabel label = new JLabel("Handbook is not open.");
//                label.setFont(new Font("Arial", Font.BOLD, 50));
//                label.setForeground(Color.pink);
//                label.setHorizontalAlignment(SwingConstants.CENTER);
//                con.add(label, BorderLayout.CENTER);
//                con.validate();
//                
//                this.setTitle("Handbook is not open.");
//                validate();
//            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new ChineseChess();
    }
    
}
