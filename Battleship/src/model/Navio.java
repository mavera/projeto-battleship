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
public class Navio {

    private String nome;
    private int tamanho;
    private Point inicio, fim;
    private boolean destruido;
    //false - horizontal | true - vertical
    private boolean direcao;

    public Navio(String nome, int tamanho, Point inicio, Point fim) {

        this.nome = nome;
        this.tamanho = tamanho;
        this.inicio = inicio;
        this.fim = fim;
        this.destruido = false;
        this.direcao = (inicio.x == fim.x ? false : true);
    }

    public Navio(String nome, Point inicio, Point fim) {
        this(nome, ((inicio.x == fim.x) ? fim.y - inicio.y : fim.x - inicio.x) + 1, inicio, fim);
    }

    public Navio(String nome, Point inicio, int tamanho, boolean direcao) {
        this.nome = nome;
        this.inicio = inicio;
        this.tamanho = tamanho;
        this.fim = ((direcao == false) ? new Point(inicio.x, inicio.y + tamanho - 1) : new Point(inicio.x + tamanho - 1, inicio.y));
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
