package com.deepsenselab.automation.gui;

import javax.swing.*;
import java.applet.Applet;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Ashok K. Pant (ashokpant87@gmail.com) on 8/26/16.
 */
public class GUI extends Applet implements ActionListener {
    private JLabel filePath;
    private JButton browseButton;
    private JTextField textField1;
    private JTextField textField2;
    private JLabel msg;
    private JButton registerButton;
    private JFileChooser fileChooser = new JFileChooser();
    public void setData(GUI data) {
    }

    public void getData(GUI data) {
    }

    public boolean isModified(GUI data) {
        return false;
    }

    public void init() {
          fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        browseButton.addActionListener(this);
        //setLayout(new GridLayout(7,2))
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "Browse") {
            //fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                filePath.setText(selectedFile.getAbsolutePath());
            }
        }
    }
}
