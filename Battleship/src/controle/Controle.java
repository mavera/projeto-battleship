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

    private Grid grid;
    private Janela janela;
    private int largura, altura;

    public static void main(String[] args) {

        Controle cntl = new Controle();

    }

    public Controle() {
        this.janela = new Janela(this);
        this.altura = 10;
        this.largura = 10;
        this.grid = new Grid(altura, largura);
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

    public boolean inserirNavio(String nome, Point ini, int tamanho, boolean direcao) {
        Navio navio = new Navio(nome, ini, tamanho, direcao);
        boolean insercao = true;
        try {
            this.grid.inserirNavio(navio);
        } catch (Exception E) {
            insercao = false;
        }
        return insercao;
    }

    public void geraGrid(){
        
    }
}
