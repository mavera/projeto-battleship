/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.awt.Point;
import model.Grid;
import model.Navio;

/**
 *
 * @author Raphael
 *
 */
public class Controle {

    Grid grid;
    Janela janela;

    public static void main(String[] args) {
        Controle cntl = new Controle();

    }

    public Controle() {
        this.janela = new Janela(this);
        this.grid = new Grid(10, 10);
        grid.addObservers(janela);
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public Janela getJanela() {
        return janela;
    }

    public void setJanela(Janela janela) {
        this.janela = janela;
    }

    public boolean inserirNavio(String nome, Point ini, Point fim) {
        Navio navio = new Navio(nome, ini, fim, false);
        try {
            grid.inserirNavio(navio);
        }catch(Exception E){

        }
        return true;
    }
}
