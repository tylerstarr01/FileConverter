/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processing;

//import com.asprise.ocr.Ocr;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author user
 */
public class Letter {

    public int[] nums;
    public String numsStr = "";
    public int[][] pixels;
    public static int standardHeight = 25;
    public static int standardWidth = 19;
    public boolean isBlank = true;
    public double xSize;
    public double ySize;
    public double xIncr;
    public double yIncr;
    public Coordinate topLeft;
    public Coordinate bottomRight;

    public Letter(Coordinate topLeft, Coordinate bottomRight, ImageFrame f) {

        nums = new int[standardHeight + standardWidth];
        int numToBeLetter = 0;
        topLeft.setX(topLeft.getX());
        bottomRight.setX(bottomRight.getX());
        topLeft.setY(topLeft.getY());
        bottomRight.setY(bottomRight.getY() + 2);
        //standardWidth = bottomRight.getX() - topLeft.getX();
        //standardHeight = bottomRight.getY() - topLeft.getY();
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.pixels = new int[standardWidth][standardHeight];
        this.xSize = (bottomRight.getX() - topLeft.getX());
        this.ySize = (bottomRight.getY() - topLeft.getY());
        this.xIncr = (xSize / standardWidth);
        this.yIncr = (ySize / standardHeight);

        //int[][] makeSmallArr = makeSmallArr(topLeft, bottomRight, f);
        outer:
        for (double i = topLeft.getX(); i < bottomRight.getX(); i += this.xIncr) {
            for (double j = topLeft.getY(); j < bottomRight.getY(); j += this.yIncr) {
                //System.out.println(f.colour(i,j));
                if (f.isBelowThreshold((int) i, (int) j)) {
                    numToBeLetter++;
                    if (numToBeLetter > 10) {
                        this.isBlank = false;
                        break outer;
                    }
                    //this.pixels[(int) (Math.abs(topLeft.getX() - i) / this.xIncr)][(int) (Math.abs(topLeft.getY() - j) / this.yIncr)] = 1;
                }
            }
        }
//        int temp;
//        for (int i = 0; i < this.pixels[0].length; i++) {
//            temp = numCol(this.pixels, i);
//            nums[i] = temp;
//            numsStr += temp + "r";
//        }
//        for (int i = 0; i < this.pixels.length; i++) {
//            temp = numRow(this.pixels, i);
//            nums[i + this.pixels[0].length] = temp;
//            numsStr += temp + "r";
//        }

    }

    public static void addRows(int[][] in, int num) {

    }

    public int[][] makeSmallArr(Coordinate topLeft, Coordinate bottomRight, ImageFrame f) {
        int[][] temp = new int[standardWidth][standardHeight];
        this.xSize = (bottomRight.getX() - topLeft.getX());
        this.ySize = (bottomRight.getY() - topLeft.getY());
        this.xIncr = (xSize / standardWidth);
        this.yIncr = (ySize / standardHeight);
        for (double i = topLeft.getX() + 1; i < bottomRight.getX() - 1; i += this.xIncr) {
            for (double j = topLeft.getY() + 1; j < bottomRight.getY() - 1; j += this.yIncr) {
                if (f.isBelowThreshold((int) i, (int) j)) {
                    this.isBlank = false;
                    temp[(int) (Math.abs(topLeft.getX() - i) / this.xIncr)][(int) (Math.abs(topLeft.getY() - j) / this.yIncr)] = 1;
                }
            }
        }

        for (int[] e : temp) {
            System.out.println(Arrays.toString(e));
        }
        System.out.println();
        if (!Arrays.toString(temp[0]).contains("1")) {
            Coordinate tl = new Coordinate(topLeft.getX(), topLeft.getY() + 1);
            return makeSmallArr(tl, bottomRight, f);
        }

        return temp;
    }

    public Coordinate getTopLeft() {
        return this.topLeft;
    }

    public Coordinate getBottomRight() {
        return this.bottomRight;
    }

    public boolean isBlank() {
        return this.isBlank;
    }

    public static int getSize() {
        return standardHeight + standardWidth;
    }
    
    
    

    public String getVerticalValues(ImageFrame fr) {
        String out = "";
        for (int[] f : fr.getPixelArrayInt()) {
            out += f[0];
        }
        return out;
    }

    public static int numCol(int[][] a, int col) {
        int count = 0;
        for (int[] f : a) {
            if (f[col] == 1) {
                count++;
            }
        }
        return count;
    }

    public static int numRow(int[][] a, int row) {
        int count = 0;
        for (int i : a[row]) {
            if (i == 1) {
                count++;
            }
        }
        return count;
    }

    public double[] convertTo1D() {
        int[][] temp = this.getValues();
        int count = 0;
        double[] out = new double[temp.length * temp[0].length];
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                out[count++] = temp[i][j];
            }
        }
        return out;
    }

    public int[][] getValues() {
        return this.pixels;
    }

    public String getBounds() {
        return this.getTopLeft().getX() + " " + this.getBottomRight().getX() + " " + this.getTopLeft().getY() + " " + this.getBottomRight().getY() + " ";

    }

    @Override
    public String toString() {
        String buffer = "";

        for (int[] pixel : this.pixels) {

            for (int p : pixel) {
                buffer += p;
            }
            buffer += "\n";
        }
        //System.out.println(this.getBounds());
        return buffer;
        //return 
        
    }

    public String toString(boolean b) {
        String buffer = "";

        for (int[] pixel : this.pixels) {

            for (int p : pixel) {
                buffer += p;
            }
            buffer += "\n";
        }
        return buffer;
    }

};
