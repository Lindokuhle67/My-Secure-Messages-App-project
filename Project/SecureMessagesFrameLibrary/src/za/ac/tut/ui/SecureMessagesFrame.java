/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import za.ac.tut.encryption.MessageEncryptor;
import za.ac.tut.in.MessageDB;
import za.ac.tut.message.Message;

/**
 *
 * @author Lindokuhle
 */
public class SecureMessagesFrame extends JFrame {
    //menu bar
    private JMenuBar menuBar;
    
    //menu 
    private JMenu fileMenu;
    
    //menu items
    private JMenuItem openFileMenuItem;
    private JMenuItem encryptFileMenuItem;
    private JMenuItem saveEncryptedFileMenuItem;
    private JMenuItem clearFileMenuItem;
    private JMenuItem exitFileMenuItem;
    private JMenuItem addtoDBFileMenuItem;
    
    //panels
    private JPanel headingPnl;
    private JPanel plainTextPnl;
    private JPanel encryptedTextPnl;
    private JPanel mainPnl;
    
    //label
    private JLabel headingLbl;
    
    //text area
    private JTextArea plainMsgTxtArea;
    private JTextArea encryptedMsgTxtArea;

    //text area
    private JScrollPane scrollablePlainMsgTxtArea;
    private JScrollPane scrollableEncryptedMsgTxtArea;
    
    MessageDB db;
    public SecureMessagesFrame() throws SQLException{
        
        db = new MessageDB("jdbc:derby://localhost:1527/MessagesDB", "app", "123");
                
        setTitle("Secure Messages");
        setSize(50, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        
        menuBar = new JMenuBar();
        
        
        fileMenu = new JMenu("File");
        
        
        openFileMenuItem = new JMenuItem("Open file...") ;
        openFileMenuItem.addActionListener(new OpenFileMenuItemListener());
        
        encryptFileMenuItem = new JMenuItem("Encrypt message...") ;
        encryptFileMenuItem.addActionListener(new EncryptFileMenuItemListener());
        
        saveEncryptedFileMenuItem = new JMenuItem("Save encrypted message...") ;
        saveEncryptedFileMenuItem.addActionListener(new SaveEncryptedFileMenuItemListener());
        
         addtoDBFileMenuItem = new JMenuItem("Add to Database...");
         addtoDBFileMenuItem.addActionListener(new AddBtnListener());
         
        clearFileMenuItem = new JMenuItem("Clear") ;
        clearFileMenuItem.addActionListener(new ClearFileMenuItemListener());

        exitFileMenuItem = new JMenuItem("Exit") ;
        exitFileMenuItem.addActionListener(new ExitFileMenuItemListener());
        
        
        fileMenu.add(openFileMenuItem);
        fileMenu.add(encryptFileMenuItem);
        fileMenu.add(saveEncryptedFileMenuItem);
        fileMenu.add(addtoDBFileMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(clearFileMenuItem);
        fileMenu.add(exitFileMenuItem);
        
        
        menuBar.add(fileMenu);

        
        headingPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        plainTextPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        plainTextPnl.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 1), "Plain message"));
        
        encryptedTextPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));
        encryptedTextPnl.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 1), "Encrypted message"));
        
        mainPnl = new JPanel(new BorderLayout());
        
        
        headingLbl = new JLabel("Message Encryptor");
        headingLbl.setForeground(Color.BLUE);
        headingLbl.setFont(new Font("SERIF", Font.BOLD + Font.ITALIC, 30));
        headingLbl.setBorder(new BevelBorder(BevelBorder.RAISED));
        
       
        plainMsgTxtArea = new JTextArea(10,30);
        plainMsgTxtArea.setEditable(false);
        
        encryptedMsgTxtArea = new JTextArea(10, 30);
        encryptedMsgTxtArea.setEditable(false);
        
        
        scrollablePlainMsgTxtArea = new JScrollPane(plainMsgTxtArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollableEncryptedMsgTxtArea = new JScrollPane(encryptedMsgTxtArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        
        headingPnl.add(headingLbl);
        plainTextPnl.add(scrollablePlainMsgTxtArea);
        encryptedTextPnl.add(scrollableEncryptedMsgTxtArea);
        
        mainPnl.add(headingPnl, BorderLayout.NORTH);
        mainPnl.add(plainTextPnl, BorderLayout.WEST);
        mainPnl.add(encryptedTextPnl, BorderLayout.EAST);
                 
        
        setJMenuBar(menuBar);
        
        
        add(mainPnl);
        
        
        pack();
        
        
        setVisible(true);
    }
   
    
    private class OpenFileMenuItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {      
            
            String data = "", record;
            int val;
            File file;
            JFileChooser fc;
            BufferedReader br;
            
            
            fc = new JFileChooser();
            
           
            val = fc.showOpenDialog(SecureMessagesFrame.this);
                
            if(val == JFileChooser.APPROVE_OPTION){
                try {
                    
                    file = fc.getSelectedFile();
                    
                    
                    br = new BufferedReader(new FileReader(file));
                    
                    
                    while((record = br.readLine()) != null){
                        
                        data = data + record + "\n";
                    }
                    
                   
                    br.close();
                    
                    
                    plainMsgTxtArea.setText(data);
                 } catch (IOException ex) {
                    Logger.getLogger(SecureMessagesFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(SecureMessagesFrame.this, "The if statement failed.The returned value is " + val);
            }
        }
    }

    
    private class EncryptFileMenuItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {      
           
            String plainMsg;
            Message message,encryptedMsg;
            MessageEncryptor me;
            
            
            plainMsg = plainMsgTxtArea.getText();
            
            
            message = new Message(plainMsg);
            
            
            me = new MessageEncryptor();
            
            
            encryptedMsg = me.encrypt(message);
            
            
            encryptedMsgTxtArea.setText(encryptedMsg.toString());
        }
    }
    private class AddBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {      
            try {
                //write source code here to accomplish the task
                String plainMsg,encrypted;
                //example here code
                plainMsg = plainMsgTxtArea.getText();
                
                encrypted = encryptedMsgTxtArea.getText();
               
               db.add(plainMsg, encrypted);
                
            } catch (SQLException ex) {
                Logger.getLogger(SecureMessagesFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }
    }
    
    private class SaveEncryptedFileMenuItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {      
            
            String encryptedMsg;
            int val;
            File file;
            JFileChooser fc;
            BufferedWriter bw;
            
           
            fc = new JFileChooser();
            
            
            val = fc.showSaveDialog(SecureMessagesFrame.this);
                
            if(val == JFileChooser.APPROVE_OPTION){
                try {
                    
                    file = fc.getSelectedFile();
                    
                    
                    bw = new BufferedWriter(new FileWriter(file));
                    
                    
                    encryptedMsg = encryptedMsgTxtArea.getText();
                    
                    
                    bw.write(encryptedMsg);
                    
                    
                    bw.newLine();
                    
                    
                    bw.close();
                 } catch (IOException ex) {
                    Logger.getLogger(SecureMessagesFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(SecureMessagesFrame.this, "The if statement failed.The returned value is " + val);
            }
        }
    }
  
    
    private class ClearFileMenuItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            
            encryptedMsgTxtArea.setText("");
            plainMsgTxtArea.setText("");
        }
        
    }
    
   
    private class ExitFileMenuItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
           
            System.exit(0);
        }
        
    }
}
