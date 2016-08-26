package com.deepsenselab.automation.gui;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Ashok K. Pant (ashokpant87@gmail.com) on 8/26/16.
 */
public class RegisterIeltsGUI extends Applet implements ActionListener {
    private boolean first = true;
    Label l1, l2, l3, l4;
    TextField t1, t2, t3;
    Button b1, b2;
    Choice m;
    String msg = "", name, roll, ad, c;
    Label lblThreads;
    Label lblWaitForNextRoundSecs;
    Label lblBrowse;
    Label lblFilePath;

    TextField tfThreads;
    TextField tfWaitForNextRoundSecs;
    JFileChooser fileChooser = new JFileChooser();

    String threads,waitForNextRoundSecs;
    public void init() {
        lblThreads = new Label("Threads");
        lblWaitForNextRoundSecs = new Label("WaitForNextRound(secs.)");
        lblBrowse = new Label("Select Candidate list File");
        lblFilePath = new Label("");

        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));


        tfThreads = new TextField();
        tfWaitForNextRoundSecs = new TextField();

        b1 = new Button("Browse");

        m = new Choice();
        m.add("Male");
        m.add("Female");
        add(lblBrowse);
        add(b1);
        add(lblFilePath);
        add(lblThreads);
        add(tfThreads);
        add(lblWaitForNextRoundSecs);
        add(tfWaitForNextRoundSecs);


        b1.addActionListener(this);
        //setLayout(new GridLayout(7,2))
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand() == "Browse"){
            //fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                lblFilePath.setText(selectedFile.getAbsolutePath());
            }
        }
        if (((t1.getText()).equals("")) || ((t2.getText()).equals("")) || ((t3.getText()).equals(""))) {
            JOptionPane.showMessageDialog(this, "Wrong Input");
            return;
        }

        String str = e.getActionCommand();
        if (str.equals("Register")) {
            msg = "Congratulations! You are Successfully Registered";

        } else if (str.equals("Cancel")) {
            msg = "Sorry You are not Registered";
            t1.setText("");
            t2.setText("");
            t3.setText("");
        }
        repaint();
    }

    public void paint(Graphics g) {
        if (first) {
            first = false;
            return;
        }
        name = t1.getText();
        roll = t2.getText();
        ad = t3.getText();
        c = m.getSelectedItem();
        g.drawString(msg, 6, 200);
        g.drawString("Name: " + name, 10, 100);
        g.drawString("Roll No.: " + roll, 10, 120);
        g.drawString("Address: " + ad, 10, 140);
        g.drawString("Gender: " + c, 10, 160);
    }
}