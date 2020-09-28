package processing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import static processing.ImageOutput.frame;

public class ImageFrame extends JFrame {

    public int[][] numbers;
    public ImageComponent c;
    public int threshold;
    public ArrayList<Integer> h;
    public File f;
    public ArrayList<Integer> v;
    public int distance;

    public ImageFrame() {

        setTitle("Processing...");

        this.c = new ImageComponent();
        f = c.myFile;
        setSize(c.getWidth(), c.getHeight() + 40);
        this.numbers = new int[c.getHeight()][c.getWidth()];
        c.setVisible(true);
        add(c);
        this.threshold = 40;
        this.show();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.v = this.scanVertical(0, this.getHeight() - 40, 0, this.c.getWidth(), false);
        this.h = this.scanHorizontal(0, this.getHeight() - 40, 0, this.c.getWidth(), false);

        //makeOutline();
        //System.out.println(v);
        //System.out.println(h);
    }

    public ImageFrame(File file) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Window");
        this.distance = 1;
        this.c = new ImageComponent(file);
        this.threshold = 40;
        this.numbers = new int[c.getHeight()][c.getWidth()];
        setSize(new Dimension(c.getWidth(), c.getHeight() + 40));
        add(c);
        this.show();
        this.v = this.scanVertical(0, this.getHeight() - 40, 0, this.getWidth(), false);
        this.h = this.scanHorizontal(0, this.getHeight() - 40, 0, this.getWidth(), false);

    }

    public ImageFrame(BufferedImage image) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Window");
        this.distance = 1;
        this.c = new ImageComponent(image);
        this.threshold = 40;
        this.numbers = new int[c.getHeight()][c.getWidth()];
        setSize(new Dimension(c.getWidth(), c.getHeight() + 40));
        add(c);
        this.show();
        this.v = this.scanVertical(0, this.getHeight() - 40, 0, this.getWidth(), false);
        this.h = this.scanHorizontal(0, this.getHeight() - 40, 0, this.getWidth(), false);

    }

    public static void main(String[] args) {
        ImageFrame ifr = new ImageFrame();
        System.out.println("Done");
        // System.out.println(new Letter(new Coordinate(67, 83), new Coordinate(107, 122), ifr));
        ArrayList<Letter> e = ifr.makeLetters();

        for (Letter l : e) {
            System.out.println(l.getTopLeft().getX() + " " + l.getTopLeft().getY());
            //System.exit(0);
            System.out.println(l);
            System.out.println();
        }

    }

    public int getThreshold() {
        return this.threshold;
    }

    public ArrayList<Letter> makeLetters() {
        ArrayList<Letter> l = new ArrayList<>();
        Coordinate c1;
        Coordinate c2;
        Letter letter;
        //System.out.println(this.v);
        //System.out.println(this.h);
        for (int i = 0; i < this.v.size() - 1; i += 2) {
            for (int j = 0; j < this.h.size() - 1; j += 2) {
                c1 = new Coordinate(this.v.get(i), this.h.get(j));
                c2 = new Coordinate(this.v.get(i + 1), this.h.get(j + 1));
                //System.out.println(c1 + " " + c2);
                this.getImageComponent().getImage().setRGB(c1.getY(), c1.getX(), -16777216);
                this.getImageComponent().getImage().setRGB(c2.getY(), c2.getX(), -16777216);

                letter = new Letter(c1, c2, this);
                if (!letter.isBlank()) {
                    l.add(letter);
                }

            }
        }

        return l;
    }

    public ImageComponent getImageComponent() {
        return this.c;
    }

    public File getFile() {
        return this.f;
    }

    public Color[][] getPixelArray() {

        int width = this.c.getWidth();
        int height = this.c.getHeight();
        Color[][] result = new Color[height][width];
        outer:
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {

                if (row == this.getHeight()) {
                    //row--;
                    break outer;
                }
                if (col == this.getWidth()) {
                    //col--;
                    break outer;
                }
                //System.out.println(row + " " + col + " " + height + " " + width);
                result[row][col] = new Color(this.getImageComponent().getImage().getRGB(col, row));
            }
        }

        return result;
    }

    public int[][] getPixelArrayInt() {

        int width = this.getWidth();
        int height = this.getHeight();
        int[][] result = new int[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                result[row][col] = this.getImageComponent().getImage().getRGB(col, row);
                //System.out.println(this.getImageComponent().getImage().getRGB(col, row));
            }
        }

        return result;
    }

    public boolean isBelowThreshold(int i, int j) {
        return (this.getPixelArray()[i][j].getRed() < this.getThreshold() && this.getPixelArray()[i][j].getGreen() < this.getThreshold() && this.getPixelArray()[i][j].getBlue() < this.getThreshold());
    }

    public String colour(int i, int j) {
        return this.getPixelArray()[i][j].getRed() + " " + this.getPixelArray()[i][j].getGreen() + " " + this.getPixelArray()[i][j].getBlue();
    }

    public ArrayList<Integer> scanVertical(int fromHeight, int toHeight, int fromWidth, int toWidth, boolean t) {
        //System.out.println((toHeight - 2) + " " + (toWidth - 2));
        t = false;
        int confidenceInterval = 1;
        ArrayList<Integer> horizontalLines = new ArrayList<>();
        boolean foundBlack, foundBlackPrev, foundBlackPost, noText;

        int black = new Color(10, 10, 10, 1).getRGB();
        Color[][] pixels = this.getPixelArray();
        for (int i = fromHeight + 1 + confidenceInterval; i < toHeight - 5 - confidenceInterval; i++) {
            foundBlack = false;
            noText = false;
            foundBlackPrev = false;
            foundBlackPost = false;
            for (int j = fromWidth + 0; j < toWidth - 3; j++) {
                if ((pixels[i][j].getRed() < this.getThreshold() || pixels[i][j].getGreen() < this.getThreshold() || pixels[i][j].getBlue() < this.getThreshold())) {
                    this.numbers[i][j] = 1;
                    foundBlack = true;
                }
                if ((pixels[i - 1][j].getRed() < this.getThreshold() && pixels[i - 1][j].getGreen() < this.getThreshold() && pixels[i - 1][j].getBlue() < this.getThreshold())) {
                    foundBlackPrev = true;
                }
                //System.out.println(pixels.length + " " + pixels[0].length + "" + (i + 1) + " " + j);
                if ((pixels[i + 1][j].getRed() < this.getThreshold() && pixels[i + 1][j].getGreen() < this.getThreshold() && pixels[i + 1][j].getBlue() < this.getThreshold())) {
                    foundBlackPost = true;
                }

//                for (int k = -confidenceInterval; k <= confidenceInterval; k++) {
//                    if ((pixels[i + k][j].getRed() < this.getThreshold() && pixels[i + k][j].getGreen() < this.getThreshold() && pixels[i + k][j].getBlue() < this.getThreshold())) {
//                        noText = true;
//                    }
//                }
            }
            //System.out.println(foundBlack + " " + foundBlackPrev);
            if ((foundBlack && !foundBlackPrev)) {
                //if (!noText) {
                if (i < this.distance) {
                    horizontalLines.add(i);
                } else {
                    horizontalLines.add(i - this.distance);
                }

                //   if (true) {
//                for (int x = fromWidth; x < toWidth - 1; x++) {
//                    //System.out.println("running");
//                    this.getImageComponent().getImage().setRGB(x, i - 0 * 1, black);
//                }
                //   }
            } else if (foundBlack && !foundBlackPost) {
                if (i < this.distance) {
                    horizontalLines.add(i);
                } else {
                    horizontalLines.add(i + this.distance);
                }
                //  if (true) {

//                for (int x = 0; x < toWidth - 1; x++) {
//                    this.getImageComponent().getImage().setRGB(x, i, black);
//                }

                // }
            }

        }
        return horizontalLines;
    }

    public ArrayList<Integer> scanHorizontal(int fromHeight, int toHeight, int fromWidth, int toWidth, boolean t) {
        int confidenceInterval = 1;
        t = false;
        ArrayList<Integer> verticalLines = new ArrayList<>();
        boolean foundBlack, foundBlackPrev, foundBlackPost, noText;

        int black = new Color(10, 10, 10, 1).getRGB();
        Color[][] pixels = this.getPixelArray();
        for (int i = fromWidth + confidenceInterval + 1; i < toWidth - confidenceInterval - 1; i++) {
            foundBlack = false;
            noText = false;
            foundBlackPrev = false;
            foundBlackPost = false;
            for (int j = fromHeight + 1; j < toHeight - 0; j++) {
                //System.out.println(colour(i, j));
                if ((pixels[j][i].getRed() < this.getThreshold() && pixels[j][i].getGreen() < this.getThreshold() && pixels[j][i].getBlue() < this.getThreshold())) {
                    foundBlack = true;
                }
                if ((pixels[j][i - 1].getRed() < this.getThreshold() && pixels[j][i - 1].getGreen() < this.getThreshold() && pixels[j][i - 1].getBlue() < this.getThreshold())) {
                    foundBlackPrev = true;
                }
                if ((pixels[j][i + 1].getRed() < this.getThreshold() && pixels[j][i + 1].getGreen() < this.getThreshold() && pixels[j][i + 1].getBlue() < this.getThreshold())) {
                    foundBlackPost = true;
                }
                for (int k = -confidenceInterval; k < confidenceInterval; k++) {
                    if ((pixels[j][i + k].getRed() < this.getThreshold() && pixels[j][i + k].getGreen() < this.getThreshold() && pixels[j][i + k].getBlue() < this.getThreshold())) {
                        noText = true;
                    }
                }

            }

            if (foundBlack && !foundBlackPrev) {
                if (i < this.distance) {
                    verticalLines.add(i);
                } else {
                    verticalLines.add(i - this.distance);
                }
                // if (true) {
//                for (int x = fromHeight; x < toHeight - 0; x++) {
//                      this.getImageComponent().getImage().setRGB(i - 1 * 1, x, black);
//                    //  }
//                }

            } else if (foundBlack && !foundBlackPost) {
                if (i < this.distance) {
                    verticalLines.add(i);
                } else {
                    verticalLines.add(i + this.distance);
                }
                // if (true) {
//                for (int x = fromHeight; x < toHeight - 0; x++) {
//                      this.getImageComponent().getImage().setRGB(i + 0 * 1, x, black);
//                }
                // }

            }

        }
        //System.out.println(verticalLines);

        return verticalLines;
    }

    public void makeOutline() {
        int black = new Color(10, 10, 10, 1).getRGB();
        ArrayList<Integer> vertical = this.scanVertical(0, this.getHeight() - 40, 0, this.getWidth(), true);
        ArrayList<Integer> horizontal = this.scanHorizontal(0, this.getHeight() - 40, 0, this.getWidth(), true);
        for (Integer i : vertical) {
            for (int x = 0; x < this.getHeight() - 1; x++) {
                this.getImageComponent().getImage().setRGB(i, x, black);
            }
        }
        for (Integer i : horizontal) {
            for (int x = 0; x < this.getWidth() - 1; x++) {
                this.getImageComponent().getImage().setRGB(x, i, black);
            }
        }

        /*
         int black = -16777216;
         int xBoundMin = horizontal.get(0);
         int xBoundMax = horizontal.get(horizontal.size() - 1);
         int yBoundMin = vertical.get(0);
         int yBoundMax = vertical.get(vertical.size() - 1);
         for (Integer h : horizontal) {
         for (int j = yBoundMin; j < yBoundMax; j++) {
         // this.getImageComponent().getImage().setRGB(h, j, black);
         }
         }

         for (Integer v : vertical) {
         for (int j = xBoundMin; j < xBoundMax; j++) {
         // this.getImageComponent().getImage().setRGB(j, v, black);
         }
         }
         */
    }

}
/*



 */
