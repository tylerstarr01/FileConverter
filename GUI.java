/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.asprise.ocr.Ocr;
import com.asprise.ocr.Ocr;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import processing.Letter;
import processing.ImageFrame;
import processing.ImageOutput;


/**
 *
 * @author user
 */
public final class GUI extends JFrame {

    private JPanel panel;
    private JButton chooseButton;
    public static JButton makeFile;
    private JButton train;
    private JButton translate;
    public static JTextField folderName = new JTextField(18);
    public static String fileContents = "";
    public static JTextField jt;
    public static JLabel contentLabel = new JLabel("File Content");
    public static JTextArea contentsField = new JTextArea(16, 25);
    public static JCheckBox jcb = new JCheckBox("Use PopUp Directory");
    /**
     *
     */
    public static JScrollPane scroll = new JScrollPane(contentsField, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    public static final String[] langArr = new String[]{"en", "fr", "de"};
    public static final String[] typesArr = new String[]{".txt", ".pdf", ".jpg", ".png", ".jpeg", ".rtf", ".wbpm"};
    public static JLabel fromLang = new JLabel("Translate from: ");
    public static JLabel toLang = new JLabel("to: ");
    public static JLabel folder = new JLabel("Folder Name: ");
    public static JLabel fileName = new JLabel("    File Name:");
    public static JLabel spacer = new JLabel("                                                                              ");
    public static JComboBox jc;
    public static JComboBox ol = new JComboBox(langArr);
    public static JComboBox il = new JComboBox(langArr);
    public static Ocr ocr = new Ocr();

    /**
     *
     */
    //public static Teal letterNetwork = new Teal(new File(System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "networkData" + ".txt"));
    public GUI() throws IOException {

        Border blackLine = BorderFactory.createLineBorder(Color.black);
        Border raisedbevel = BorderFactory.createRaisedBevelBorder();
        Border loweredbevel = BorderFactory.createLoweredBevelBorder();
        Border compoundBorder = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
        scroll.setBorder(compoundBorder);
        Ocr.setUp();
        ocr.startEngine("eng", Ocr.SPEED_FASTEST); // English

        this.createAndShowGUI();
        contentsField.setLineWrap(true);
        jt = new JTextField(11);
        Arrays.sort(typesArr);
        jc = new JComboBox(typesArr);
        folderName.setPreferredSize(new Dimension(10, 26));
        jt.setPreferredSize(new Dimension(10, 26));
        //train = new JButton("Train");
        this.makeFile = new JButton("  Convert to File  ");
        this.makeFile.setVisible(true);
        this.translate = new JButton("Translate");
        translate.setPreferredSize(new Dimension(275, 25));
        this.translate.addActionListener(new Translate());
        this.translate.setVisible(true);
        this.chooseButton = new JButton("    Choose File    ");
        //                              "  Convert to File  "   
        this.chooseButton.setVisible(true);

        ChooseFileButtonHandler cf = new ChooseFileButtonHandler(train, this);

        this.chooseButton.addActionListener(cf);

        this.add(folder);

        this.add(folderName);
        this.add(fileName);
        this.getContentPane().add(jt);
        this.getContentPane().add(jc);
        this.getContentPane().add(this.chooseButton);
        this.getContentPane().add(this.makeFile);
        add(jcb);
        add(spacer);
        add(contentLabel);
        add(scroll);
        this.add(fromLang);
        this.add(il);
        this.add(toLang);
        this.add(ol);
        this.getContentPane().add(translate);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.show();
    }

    public void createAndShowGUI() {
        this.setTitle("User Interface");
        this.setPreferredSize(new Dimension(325, 540));
        this.setLayout(new FlowLayout());
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static String readInLetters(ArrayList<Letter> l, InputFile ifr) {
        File f = ifr.getFile();
        String temp = "";
        int prevHeight = 0;
        System.out.println(l.size());
        for (int i = 0; i < l.size(); i++) {

            Letter e = l.get(i);
            if (e.getBottomRight().getX() == prevHeight) {
                continue;
            }
            prevHeight = e.getBottomRight().getX();

            int height = e.getBottomRight().getX();
            int width = ifr.imageFrame.getWidth() - e.getTopLeft().getY();

            //System.out.println(e.getTopLeft().getY() + " " + width + " " + e.getTopLeft().getX() + " " + height);
            temp += GUI.ocr.recognize(f.toString(), -1, e.getTopLeft().getY() - 1, e.getTopLeft().getX() - 1, width, height, Ocr.RECOGNIZE_TYPE_ALL, Ocr.OUTPUT_FORMAT_PLAINTEXT);
            temp += " ";

            //temp += o.recognize(f.toString(), -1, 0, 0, 100, 100, Ocr.RECOGNIZE_TYPE_TEXT, Ocr.OUTPUT_FORMAT_PLAINTEXT);
            //System.out.println("wack " + temp);
            //System.out.println(GUI.ocr.recognize(new File[]{f}, Ocr.RECOGNIZE_TYPE_ALL, Ocr.OUTPUT_FORMAT_PLAINTEXT));
            //System.exit(0);
        }
        //System.out.println("kfk" + temp);
        return temp;
    }


    public static void main(String[] args) throws IOException {


        /*
         Teal letterNetwork = new Teal(Letter.getSize(), (int) (Letter.getSize() * 1.25), 1, 0.5, 0.5, new double[Letter.getSize()], new double[]{0 / 26.0});

         int sampleNum, picNum;
         String in, picName, fileName;
         ImageFrame ifr;
         ArrayList<String> a;
         File ffile;
         Scanner sc;
         String file2;
         String content = "";
         in = "";
         for (int i = 0; i < 1; i++) {

         for (int j = 1; j < 2; j++) {

         sampleNum = i;
         picNum = j;
         in = "";
         if (sampleNum < 10) {
         in = "00" + sampleNum;
         } else if (sampleNum > 9) {
         in = "0" + sampleNum;
         }

         picName = "img" + in + "-0" + String.format("%04d", picNum) + ".png";
         fileName = home + File.separator + "Desktop" + File.separator + "eq" + i + ".jpg";
         file = new File(home + File.separator + "Desktop" + File.separator + "case1.jpg");
         //file = (i != 0) ? new File(fileName) : new File(file2);
         ifr = new ImageFrame(file);
         ArrayList<Letter> e = ifr.makeLetters();
         System.out.println(e.size());
         int u = (i == 0) ? 1 : 2;
         int start = (i == 0) ? 0 : 1;
         for (int id = 0; id < e.size(); id += u) {
         content += e.get(id) + "j";
         System.out.println(e.get(id).toString(true));
         }
         }
         ImageOutput.convertToFile(content, "Desktop", "eq" + i, ".txt");

         content = "";
         }*/
        
//        ocr.recognize(new File[]{new File("C:\\Users\\user\\Desktop\\case1.jpg")},
//                Ocr.RECOGNIZE_TYPE_ALL, Ocr.OUTPUT_FORMAT_PDF,
//                Ocr.PROP_PDF_OUTPUT_FILE, "C:\\Users\\user\\Desktop\\case1.pdf",
//                Ocr.PROP_PDF_OUTPUT_TEXT_VISIBLE, true);

    }

}
