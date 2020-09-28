/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import processing.ImageOutput;

/**
 *
 * @author user
 */
public class FinishButton implements ActionListener {

    public JFrame j;
    public InputFile input;
    public BufferedImage bi;

    public FinishButton(JFrame j, InputFile input, BufferedImage bi) {
        this.bi = bi;
        this.j = j;
        this.input = input;
    }
    
    
    public BufferedImage getBuffImg() {
        return this.bi;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        SaveToFileButton.mustWait = false;
        this.j.setVisible(false);
        boolean imageSave = true;
        File file = null;

        if (GUI.jcb.isSelected()) {
            file = input.showSaveDialog();
            String s = file.getAbsolutePath();
            imageSave = InputFile.isImageFile(s);
        }

        if (!InputFile.isImageFile(GUI.jc.getSelectedItem().toString()) || !imageSave) {

            System.out.println("8");
            if (GUI.fileContents == null || GUI.contentsField.getText().equals("")) {
                System.out.println("jjjjjj");
            } else {

                if (!GUI.jcb.isSelected()) {
                    System.out.println("c1");
                    try {
                        ImageOutput.convertToFile(GUI.contentsField.getText(), GUI.folderName.getText(), GUI.jt.getText(), GUI.jc.getSelectedItem().toString());
                    } catch (IOException ex) {
                        Logger.getLogger(SaveToFileButton.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    System.out.println("c1-1");

                    try {
                        ImageOutput.convertToFile(GUI.contentsField.getText(), file);
                    } catch (IOException ex) {
                        Logger.getLogger(SaveToFileButton.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }

        } else {
            System.out.println("wack");

            if (null != this.getBuffImg()) {
                System.out.println("c2");
                if (GUI.jcb.isSelected()) {

                    try { 
                        ImageOutput.writeToImageFile(GUI.contentsField.getText(), file);
                    } catch (IOException ex) {
                        Logger.getLogger(SaveToFileButton.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        ImageOutput.writeToImageFile(GUI.contentsField.getText(), GUI.folderName.getText(), GUI.jt.getText(), GUI.jc.getSelectedItem().toString());
                    } catch (IOException ex) {
                        Logger.getLogger(SaveToFileButton.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                System.out.println("c3");
                if (GUI.jcb.isSelected()) {
                    try {
                        ImageOutput.writeToImageFile(GUI.contentsField.getText(), file);
                    } catch (IOException ex) {
                        Logger.getLogger(SaveToFileButton.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        ImageOutput.writeToImageFile(GUI.contentsField.getText(), GUI.folderName.getText(), GUI.jt.getText(), GUI.jc.getSelectedItem().toString());
                    } catch (IOException ex) {
                        Logger.getLogger(SaveToFileButton.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }
    }

}
