package model;

import java.awt.Point;

/**
 *
 * @author Carlos Henrique Silva Galdino
 * @author Raphael Pereira de Faria
 *
 */
public class Navio {

    private String nome;
    private int tamanho;
    private Point inicio, fim;
    private boolean destruido;
    //false - horizontal | true - vertical
    private boolean direcao;

    public Navio(String nome, int tamanho){
        this.nome = nome;
        this.tamanho = tamanho;
        this.inicio = null;
        this.fim = null;
        this.destruido = false;
        this.direcao = false;
    }

    public Navio(String nome, Point inicio, int tamanho, boolean direcao) {
        this.nome = nome;
        this.inicio = inicio;
        this.tamanho = tamanho;
        this.fim = ((direcao == true) ? new Point(inicio.x, inicio.y + tamanho - 1) : new Point(inicio.x + tamanho - 1, inicio.y));
        this.destruido = false;
        this.direcao = direcao;

    }

    public boolean isDestruido() {
        return destruido;
    }

    public void setDestruido(boolean destruido) {
        this.destruido = destruido;
    }

    public boolean isDirecao() {
        return direcao;
    }

    public void setDirecao(boolean direcao) {
        this.direcao = direcao;
    }

    public Point getFim() {
        return fim;
    }

    public void setFim(Point fim) {
        this.fim = fim;
    }

    public Point getInicio() {
        return inicio;
    }

    public void setInicio(Point inicio) {
        this.inicio = inicio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public String toString() {
        return (this.getNome() + " com início na posição (" + this.inicio.x + "," + this.inicio.y + ") e com fim na posição (" + this.fim.x + "," + this.fim.y + ").");
    }
}
