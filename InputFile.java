/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import processing.ImageComponent;
import processing.ImageFrame;

/**
 *
 * @author student
 */
public class InputFile extends Component {

    public ImageFrame imageFrame = null;
    public Scanner sc;
    public final boolean isImage;
    public File inFile;

    public InputFile() throws IOException {

        String filePath = showOpenDialog();
        this.inFile = new File(filePath);
        this.isImage = isImageFile(filePath);
        System.out.println(this.isImage);
        if (this.isImage) {
            ImageComponent ic = new ImageComponent(this.inFile);
            if (needsResize(ic.getImage())) {
                this.imageFrame = new ImageFrame(resize(ic.getImage()).getImage());
            } else {
                this.imageFrame = new ImageFrame(this.inFile);
            }
        } else {
            sc = new Scanner(this.inFile);
            sc.useDelimiter(" ");
            GUI.fileContents = "";
            while (sc.hasNext()) {
//                if (sc.next() == null) {
//                    continue;
//                }
                GUI.fileContents += sc.next() + " ";

            }
            GUI.contentsField.setText(GUI.fileContents);
        }
    }

    public InputFile(String filePath) throws FileNotFoundException {
        this.inFile = new File(filePath);
        this.isImage = isImageFile(filePath);
        System.out.println(this.isImage);
        if (this.isImage) {
            this.imageFrame = new ImageFrame(this.inFile);
        } else {
            sc = new Scanner(this.inFile);
            sc.useDelimiter(" ");
            GUI.fileContents = "";
            while (sc.hasNext()) {
//                if (sc.next() == null) {
//                    continue;
//                }
                GUI.fileContents += sc.next() + " ";

            }
        }
    }

    public boolean needsResize(BufferedImage frame) {
        return frame.getWidth() < 135;
    }

    public ImageComponent resize(BufferedImage in) {
        BufferedImage outputImage = new BufferedImage(135, in.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(in, 0, 0, 135, in.getHeight(), null);
        return new ImageComponent(outputImage);
    }

    public File getFile() {
        return this.inFile;
    }

    public ImageFrame getImageFrame() {
        return this.imageFrame;
    }

    public String showOpenDialog() {
        JFileChooser jfc = new JFileChooser();
        int status = jfc.showOpenDialog(this);
        if (status == JFileChooser.APPROVE_OPTION) {

            File theFile = jfc.getSelectedFile();

            //String thePath = theFile.getAbsolutePath();
            return theFile.getAbsolutePath();
        }

        return null;
    }

    public File showSaveDialog() {
        JFileChooser jfc = new JFileChooser();
        int status = jfc.showSaveDialog(this);
        if (status == JFileChooser.APPROVE_OPTION) {
            File theFile = jfc.getSelectedFile();
            String thePath = theFile.getAbsolutePath();
            return theFile;
        }

        return null;
    }

    public static boolean isImageFile(String path) {
        String fileName = path.substring(path.lastIndexOf(".") + 1);
        String[] imageTypes = {"png", "jpg", "jpeg", "gif", "wbpm"};
        for (String s : imageTypes) {
            if (s.equals(fileName)) {
                return true;
            }
        }
        return false;
    }
}
