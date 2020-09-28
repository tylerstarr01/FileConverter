/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

//import com.asprise.ocr.Ocr;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import processing.ImageFrame;
import static processing.ImageOutput.frame;
import static processing.ImageOutput.s;
import processing.Letter;


/**
 *
 * @author user
 */
public class ChooseFileButtonHandler extends Thread implements ActionListener {

    private static String s = "";
    public InputFile ifr = null;
    public static File file;
    public static ArrayList<Letter> letterList;
    public JButton button;
    public SaveToFileButton sfb;
    public GUI g;

    public ChooseFileButtonHandler(JButton j, GUI g) {
        this.button = j;
        this.g = g;
        //this.o = o;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            ifr = GUI.jcb.isSelected() ? new InputFile() : new InputFile(System.getProperty("user.home") + File.separator + GUI.folderName.getText() + File.separator + GUI.jt.getText() + GUI.jc.getSelectedItem());
        } catch (IOException ex) {
            Logger.getLogger(ChooseFileButtonHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

            if (this.ifr.getImageFrame() != null) {
                sfb = new SaveToFileButton(this.ifr.getImageFrame().getImageComponent().getImage(), this.ifr);
                sfb.start();
                GUI.makeFile.addActionListener(sfb);
            } else {
                sfb = new SaveToFileButton(this.ifr);
                sfb.start();
                GUI.makeFile.addActionListener(sfb);
            }

            //System.out.println(ifr.getImageComponent().getHeight() + " " + ifr.getImageComponent().getHeight());
            this.start();

        
    }

    public ImageFrame getIf() {
        return this.ifr.getImageFrame();
    }

    public ArrayList<Letter> getList() {
        return this.getIf().makeLetters();
    }

    public File getFile() {
        return this.getIf().getFile();
    }

    public ArrayList<Letter> scanImage() {
        ArrayList<Letter> e = null;
        if (this.ifr.isImage) {
            e = this.getIf().makeLetters();
        }
        return e;
    }

    public String getData() {
        return s;
    }

    @Override
    public void run() {

        while (true) {
            if (this.ifr.isImage && null != this.getIf()) {
                System.out.println("inside");
                letterList = this.scanImage();
                GUI.fileContents = (GUI.readInLetters(letterList, this.ifr));
                GUI.contentsField.setText(GUI.fileContents);
                break;
            }
        }
    }

}
