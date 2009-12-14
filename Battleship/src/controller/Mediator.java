package controller;

import java.awt.Point;

/**
 *
 * @author Carlos Henrique Silva Galdino
 * @author Raphael Pereira de Faria
 *
 */
public abstract class Mediator {

    public Mediator() {
    }

    public abstract int atirar(Point ponto);

    public abstract void geraGrid();

    public abstract void geraMinas();

    public abstract void geraNavios();

    public abstract int getAltura();

    public abstract int getLargura();

    public abstract String getLog();

    public abstract int[][] getMatriz();

    public abstract int getTorpedos();

    public abstract int iniciarJogoVersusComputador(int dificuldade);

    public abstract int iniciarJogoVersusJogador(int dificuldade);

    public abstract void inserirMina(Point ini) throws Exception;

    public abstract void inserirNavio(String nome, Point ini, boolean direcao) throws Exception;

    public abstract boolean isJogoFinalizado();

    public abstract boolean isJogoComecou();

    public abstract boolean isJogoConfigurado();

    public abstract boolean isVersusComputador();

    public abstract void refreshLog(Point ponto);

    public abstract void resetarJogo();

}
