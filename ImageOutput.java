package processing;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import gui.SaveToFileButton;

public class ImageOutput {

    public static String s = "";
    public static ImageFrame frame;

    public static void convertToFile(String contents, String folder, String fileName, String type) throws IOException {
        String home = System.getProperty("user.home");
        File file = new File(home + File.separator + folder + File.separator + fileName + type);
        //file.createNewFile();
        //Create the file
        //if (!file.createNewFile()) {
        //  throw new RuntimeException("File Name Already Exists");
        //}
        //Write Content
        FileWriter writer = new FileWriter(file);
        PrintWriter pw = new PrintWriter(writer);
        pw.println(contents);
        writer.close();
        pw.close();

    }

    public static void convertToFile(String contents, File file) throws IOException {
        FileWriter writer = new FileWriter(file);
        PrintWriter pw = new PrintWriter(writer);
        pw.println(contents);
        writer.close();
        pw.close();
    }

    public static void writeToImageFile(BufferedImage bi, String type, String folder, String fileName) throws IOException {
        String home = System.getProperty("user.home");
        String fs = File.separator;
        ImageIO.write(bi, type.substring(1), new File(home + fs + folder + fs + fileName + type));
    }

    public static void writeToImageFile(BufferedImage bi, File file) throws IOException {
        String home = System.getProperty("user.home");
        String fs = File.separator;
        ImageIO.write(bi, file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(".") + 1), file);
    }

    public static void writeToImageFile(String contents, String folder, String fileName, String type) throws IOException {

        int letterHeight = 20;
        Scanner sc = new Scanner(contents);
        ArrayList<String> s = new ArrayList<>();
        int longest = 0;
        sc.useDelimiter("\n");
        while (sc.hasNext()) {
            String temp = sc.next();
            if (temp.length() > longest) {
                longest = temp.length();
            }
            s.add(temp);
        }
        int width = longest * 17;
        int height = (int) (letterHeight * s.size()*1.5);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Arial Black", Font.BOLD, letterHeight));
        int txtHeight = 25;
        for (String str : s) {
            graphics.drawString(str, 10, txtHeight);
            txtHeight += letterHeight;
        }
        ImageOutput.writeToImageFile(bufferedImage, type, folder, fileName);
        System.out.println("Image Created");
    }

    public static void writeToImageFile(String contents, File file) throws IOException {

        int letterHeight = Integer.parseInt(SaveToFileButton.sizeField.getText());
        Scanner sc = new Scanner(contents);
        ArrayList<String> s = new ArrayList<>();
        int longest = 0;
        sc.useDelimiter("\n");
        while (sc.hasNext()) {
            String temp = sc.next();
            if (temp.length() > longest) {
                longest = temp.length();
            }
            s.add(temp);
        }
        int width = longest * 17;
        int height = (int) (letterHeight * s.size()*1.5);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font(SaveToFileButton.fontType.getSelectedItem().toString(), Font.PLAIN, letterHeight));
        int txtHeight = 25;
        for (String str : s) {
            graphics.drawString(str, 10, txtHeight);
            txtHeight += letterHeight;
        }

        ImageIO.write(bufferedImage, file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(".") + 1), file);
        System.out.println("Image Created");
    }


}
