package com.deepsenselab.automation.gui;

import com.deepsenselab.automation.ieltsregister.RegisterIelts;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Ashok K. Pant (ashokpant87@gmail.com) on 8/29/16.
 */
public class IELTS {
    private JButton browseBtn;
    private JTextField threadsTxt;
    private JTextField waitSecsTxt;
    private JLabel msgLbl;
    private JButton registerBtn;
    private JPanel ieltsView;
    private JSeparator titleSep;
    private JTextField filePathTxt;

    private RegisterIelts registerIelts;

    public IELTS() {
        browseBtn.addActionListener(new BrowseBtnClicked());
        registerBtn.addActionListener(new RegisterBtnClicked());
        registerIelts = new RegisterIelts();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("IELTS");
        frame.setContentPane(new IELTS().ieltsView);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    private class BrowseBtnClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                filePathTxt.setText(selectedFile.getAbsolutePath());
                filePathTxt.setEnabled(true);
            }
        }
    }
    private class RegisterBtnClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String filePath = filePathTxt.getText();
            if(filePath.isEmpty()){
                msgLbl.setText("Select candidate list file.");
                return;
            }
            Integer threads = Integer.parseInt(threadsTxt.getText());
            Integer waitBeforeNextRound = Integer.parseInt(waitSecsTxt.getText());
            if(threads <0 || threads > 8){
                msgLbl.setText("Number of parallel threads is invalid, setting defaults (3).");
                threadsTxt.setText("3");
                threads = 3;
            }

            if(waitBeforeNextRound <0 ){
                msgLbl.setText("Wait for next round time is invalid, setting defaults (120 sec).");
                waitSecsTxt.setText("120");
                waitBeforeNextRound = 120;
            }

            msgLbl.setText("");
            String msg = registerIelts.register(filePath,threads,waitBeforeNextRound);

            if(msg == null){
                msgLbl.setText("Registering ...");
            }else{
                msgLbl.setText(msg);
            }
        }
    }
}
