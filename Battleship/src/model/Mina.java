package model;

import java.awt.Point;

/**
 *
 * @author Carlos Henrique Silva Galdino
 * @author Raphael Pereira de Faria
 *
 */
public class Mina extends Point {

    boolean destruida;

    public Mina(Point ponto) {
        super(ponto);
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
