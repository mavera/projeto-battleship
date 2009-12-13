/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Point;

/**
 *
 * @author Raphael
 *
 */
public class Mina extends Point {

    boolean destruida;

    public Mina(Point p) {
        super(p);
        this.destruida = false;
    }

    public Mina(int x, int y) {
        super(x, y);
        this.destruida = false;
    }

    public boolean isDestruida() {
        return destruida;
    }

    public void setDestruida(boolean destruida) {
        this.destruida = destruida;
    }
}
