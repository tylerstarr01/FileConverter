/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processing;

/**
 *
 * @author user
 */
public class Coordinate {

    public int x;
    public int y;

    public Coordinate(int right, int up) {
        this.x = right;
        this.y = up;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return this.getX() + " " + this.getY();
    }
}
