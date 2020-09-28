/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.asprise.ocr.Ocr;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import processing.ImageOutput;

/**
 *
 * @author user
 */
public class SaveToFileButton extends Thread implements ActionListener {

    public static boolean mustWait = true;
    private String type;
    public InputFile input;
    public String name;
    public BufferedImage bi;
    public static JLabel sizeLabel = new JLabel("Font Size: ");
    public static JTextField sizeField = new JTextField(5);
    public static JComboBox fontType = new JComboBox(getFonts());

    public SaveToFileButton(BufferedImage b, InputFile r) {
        this.bi = b;
        this.input = r;
    }

    public SaveToFileButton(InputFile r) {
        this.bi = null;
        this.input = r;

    }

    @Override
    public void run() {

        while (true) {
            //System.out.println("update");
            //GUI.contentsField.setText(GUI.fileContents);
            //System.out.println(GUI.fileContents);
        }
    }

    public static String[] getFonts() {
        Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
        String[] temp = new String[fonts.length];
        for (int i = 0; i < fonts.length; i++) {
            temp[i] = fonts[i].getFontName();
        }
        return temp;
    }

    public static void makeFrame() {

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        JFrame j = new JFrame();
        j.setTitle("Font Chooser");
        j.setPreferredSize(new Dimension(265, 130));
        j.setLayout(new FlowLayout());
        j.add(sizeLabel);
        j.add(sizeField);
        j.add(fontType);
        JButton finishButton = new JButton("Continue");
        finishButton.addActionListener(new FinishButton(j, this.input, this.bi));
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.add(finishButton);
        j.setVisible(true);
        j.pack();
        j.show();

    }

}
