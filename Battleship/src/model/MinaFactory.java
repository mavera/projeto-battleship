package model;

import java.awt.Point;

/**
 *
 * @author Carlos Henrique Silva Galdino
 * @author Raphael Pereira de Faria
 *
 */
public class MinaFactory {
    
    public static Mina criarMina(int x, int y) {
        return new Mina(x, y);
    }

    public static Mina criarMina(Point ponto) {
        return new Mina(ponto);
    }
    
}
