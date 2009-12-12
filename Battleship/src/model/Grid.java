package model;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Carlos Henrique Silva Galdino
 * @author Raphael Pereira de Faria
 *
 */
public class Grid extends Entidade {

    private int altura;
    private int largura;
    private int[][] mat;
    private ArrayList<Navio> navios;
    private ArrayList<Mina> minas;

    public static void main(String[] args) {
        Grid x = new Grid(10, 10);

        Navio crusaider = new Navio("Cruiser", new Point(1, 1),3, false);
        Navio battleship = new Navio("Battleship", new Point(2, 1), 4, false);
        Navio destroyer = new Navio("Destroyer", new Point(3, 1), 2, false);
        Navio submarine = new Navio("Submarine", new Point(4, 1), 1, false);
        Mina mine1 = new Mina(8, 8);
        Mina mine2 = new Mina(9, 9);
        try {
            x.inserirNavio(crusaider);
            x.inserirNavio(battleship);
            x.inserirNavio(destroyer);
            x.inserirNavio(submarine);
            x.inserirMina(mine1);
            x.inserirMina(mine2);
        } catch (Exception E) {
            System.out.println(E.getMessage());
        }

        System.out.println("");
        x.atirar(new Point(4, 1));

        x.printGrid();
        
    }

    public Grid(int altura, int largura) {
        this.altura = altura;
        this.largura = largura;
        mat = new int[altura][largura];
        navios = new ArrayList<Navio>();
        minas = new ArrayList<Mina>();
        clearGrid();
    }

    public void printGrid() {
        int i, j;
        for (i = 0; i < getAltura(); i++) {
            for (j = 0; j < getLargura(); j++) {
                System.out.printf("%d", mat[i][j]);
            }
            System.out.println("");
        }
        for (Navio n : navios) {
            System.out.println(n.toString() + (n.isDestruido() ? " destruído" : " não destruído"));
        }
        for (Mina m : minas){
            System.out.println(m.toString() + (m.isDestruida() ? " destruída" : " não destruída"));
        }
    }

    public void setCoord(Point coord, int peso) {
        if (peso < 0) {
            throw new IllegalArgumentException("Valor de terreno < 0");
        }
        if (coord.x < 0 || coord.y < 0 || coord.x >= altura || coord.y >= largura) {
            throw new IllegalArgumentException("Ponto inexistente no grid.");
        }
        this.mat[coord.x][coord.y] = peso;
    }

    public boolean inserirMina(Mina mina) throws Exception {

        int[][] matriz = this.getMatriz();
        if (matriz[mina.x][mina.y] != Enum.MAR.getValor()) {
            throw new Exception("Posição do tabuleiro já ocupada.");
        }
        mat[mina.x][mina.y] = Enum.MINA.getValor();
        minas.add(mina);
        return true;
    }

    public boolean inserirNavio(Navio navio) throws Exception {
        Point ini = navio.getInicio();
        Point fim = navio.getFim();
        int i, j;
        if (checarCoordenadasFinais(fim)) {
            throw new Exception("Não é possível se posicionar o navio devido aos limites do tabuleiro");
        }
        int[][] matriz = this.getMatriz();
        for (i = ini.x; i <= fim.x; i++) {
            for (j = ini.y; j <= fim.y; j++) {
                if (matriz[i][j] != Enum.MAR.getValor()) {
                    throw new Exception("Posição do tabuleiro já ocupada.");
                }
            }
        }

        for (i = ini.x; i <= fim.x; i++) {
            for (j = ini.y; j <= fim.y; j++) {
                matriz[i][j] = Enum.NAVIO.getValor();
            }
        }
        navios.add(navio);
        return true;
    }

    public boolean atirar(Point p) {

        switch (mat[p.x][p.y]) {
            case 1://Enum.MAR.getValor();
                mat[p.x][p.y] = Enum.TIRO_AGUA.getValor();
                break;
            case 2://Enum.NAVIO.getValor();
                mat[p.x][p.y] = Enum.NAVIO_DESTRUIDO.getValor();
                break;
            case 3://Enum.MINA.getValor();
                mat[p.x][p.y] = Enum.MINA_DESTRUIDA.getValor();
                break;
        }
        notificar();
        return true;
    }

    private void verificaMinaDestruida() {
        if (!minas.isEmpty()) {
            for (Mina m : minas) {
                if (mat[m.x][m.y] == Enum.MINA_DESTRUIDA.getValor()) {
                    m.setDestruida(true);
                }
            }
        }
    }

    private void verificaNavioDestruido() {

        int i, j;
        if (!navios.isEmpty()) {
            for (Navio n : navios) {
                Point ini = n.getInicio();
                Point fim = n.getFim();
                boolean destruido = true;
                out:
                for (i = ini.x; i <= fim.x; i++) {
                    for (j = ini.y; j <= fim.y; j++) {
                        if (mat[i][j] != Enum.NAVIO_DESTRUIDO.getValor()) {
                            destruido = false;
                            break out;
                        }
                    }
                }
                n.setDestruido(destruido);
            }
        }
    }
    
    //verifica se as coordenadas de inicio e fim do Navio se encontram dentro
    //do limite do tabuleiro

    private boolean checarCoordenadasFinais(Point fim) {
        int x = fim.y, y = fim.y;
        if (x >= this.largura || y >= this.altura) {
            return true;
        }
        return false;
    }

    private void clearGrid() {
        mat = new int[largura][altura];
        for (int i = 0; i < largura; i++) {
            for (int j = 0; j < altura; j++) {
                mat[i][j] = Enum.MAR.getValor();
            }
        }
    }

    private int[][] getMatriz() {
        return this.mat;
    }

    public int getAltura() {
        return this.altura;
    }

    public int getLargura() {
        return this.largura;
    }
}
