/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processing;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFileChooser;

/**
 *
 * @author user
 */
public class ImageComponent extends JComponent {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private BufferedImage image;

    public File myFile;

    public ImageComponent() {
        try {
            this.myFile = showOpenDialog();
            //this.image = new BufferedImage();
            this.image = ImageIO.read(this.myFile);
            //System.out.println(this.image.getRGB(100, 100));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ImageComponent(BufferedImage bi) {
            this.image = bi;
    }

    public ImageComponent(File file) {
        try {
            this.image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File showOpenDialog() {
        JFileChooser jfc = new JFileChooser();
        int status = jfc.showOpenDialog(this);
        if (status == JFileChooser.APPROVE_OPTION) {

            File theFile = jfc.getSelectedFile();

            //String thePath = theFile.getAbsolutePath();
            return theFile;
        }

        return null;
    }

    public BufferedImage getImage() {
        return this.image;
    }

    @Override
    public int getHeight() {
        return this.image.getHeight(null);
    }

    @Override
    public int getWidth() {
        return this.image.getWidth(null);
    }

    public void paintComponent(Graphics g) {
        if (image == null) {
            return;
        }
        int imageWidth = this.getWidth();
        int imageHeight = this.getHeight();

        g.drawImage(image, 0, 0, this);

        for (int i = 0; i * imageWidth <= getWidth(); i++) {
            for (int j = 0; j * imageHeight <= getHeight(); j++) {
                if (i + j > 0) {
                    g.copyArea(0, 0, imageWidth, imageHeight, i * imageWidth, j * imageHeight);
                }
            }
        }
    }

}
