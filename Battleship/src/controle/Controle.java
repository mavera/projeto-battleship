/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import model.Grid;
import model.Mina;
import model.Mina;
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
    private int quantTorpedos;
    private boolean jogoIniciado;

    public static void main(String[] args) {

        Controle cntl = new Controle();
        cntl.geraGrid();
        cntl.getGrid().printGrid();
        try {
            cntl.inserirNavio("Battleship", new Point(6, 6), false);

        } catch (Exception E) {
            System.out.println(E.getMessage());
        }
    }

    public Controle() {
        quantTorpedos = 0;
        this.janela = new Janela(this);
        this.altura = 10;
        this.largura = 10;
        this.grid = new Grid(altura, largura);
        this.jogoIniciado = false;
        grid.addObservers(janela);
    }

    public boolean isJogoComecou() {
        return jogoIniciado;
    }

    public int iniciarJogo(String dificuldade) {
        if (grid.parseGrid()) {
            if (dificuldade.equalsIgnoreCase("Difícil")) {
                quantTorpedos = 30;
            } else if (dificuldade.equalsIgnoreCase("Médio")) {
                quantTorpedos = 45;
            } else if (dificuldade.equalsIgnoreCase("Fácil")) {
                quantTorpedos = 60;
            }
        }
        this.jogoIniciado = true;
        return quantTorpedos;
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

    public ArrayList whatChanged() {
        ArrayList naviosEMinas = new ArrayList();
        for (Navio n : this.grid.getNavios()) {
            naviosEMinas.add(n);
        }
        for (Mina m : this.grid.getMinas()) {
            naviosEMinas.add(m);
        }
        return naviosEMinas;
    }

    private int getTamanhoPorNome(String nome) {
        int tamanho = 0;
        if (nome.equalsIgnoreCase("Battleship")) {
            tamanho = 4;
        } else if (nome.equalsIgnoreCase("Cruiser")) {
            tamanho = 3;
        } else if (nome.equalsIgnoreCase("Submarine")) {
            tamanho = 3;
        } else if (nome.equalsIgnoreCase("Destroyer")) {
            tamanho = 2;
        } else if (nome.equalsIgnoreCase("Patrol Boat")) {
            tamanho = 1;
        }
        return tamanho;
    }

    public void inserirMina(Point ini) throws Exception {

        Mina mina = new Mina(ini);
        this.grid.inserirMina(mina);
        
    }

    public void inserirNavio(String nome, Point ini, boolean direcao) throws Exception {
        int tamanho = getTamanhoPorNome(nome);
        Navio navio = new Navio(nome, ini, tamanho, direcao);
        this.grid.inserirNavio(navio);
    }

    public void geraGrid() {
        geraNavios();
        geraMinas();
    }

    public void geraNavios() {
        int i;
        Navio[] navios = {new Navio("Battleship", 4),
            new Navio("Cruiser", 3),
            new Navio("Submarine", 3),
            new Navio("Destroyer", 2),
            new Navio("Patrol Boat", 1)};
        Random gerador = new Random(System.currentTimeMillis());
        for (i = 0; i < navios.length; i++) {
            Navio n = navios[i];
            int tamanhoLimite = 10 - n.getTamanho();
            int posicaoRandomica = gerador.nextInt(tamanhoLimite * tamanhoLimite);
            Point inicio = new Point(posicaoRandomica % tamanhoLimite, (int) (posicaoRandomica / tamanhoLimite));
            boolean direcao = gerador.nextBoolean();
            n.setInicio(inicio);
            n.setDirecao(direcao);
            Point fim;
            if (!direcao) {
                fim = new Point(inicio.x, inicio.y + n.getTamanho() - 1);
            } else {
                fim = new Point(inicio.x + n.getTamanho() - 1, inicio.y);
            }
            n.setFim(fim);
            try {
                grid.inserirNavio(n);
            } catch (Exception E) {
                i--;
            }
        }
    }

    public void geraMinas() {
        Random gerador = new Random(System.currentTimeMillis());
        int quantMinasInseridas = 0;
        while (quantMinasInseridas != 2) {
            quantMinasInseridas++;
            int posicaoRandomica = gerador.nextInt(81);
            Mina m = new Mina(posicaoRandomica % 9, posicaoRandomica / 9);
            try {
                grid.inserirMina(m);

            } catch (Exception E) {
                quantMinasInseridas--;
            }
        }
    }
}
