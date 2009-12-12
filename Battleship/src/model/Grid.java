package model;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Carlos Henrique Silva Galdino
 * @author Raphael Pereira de Faria
 *
 */
public class Grid {

    private int altura;
    private int largura;
    private int[][] mat;
    private Node nohInicial;
    private Node nohFinal;
    private ArrayList<Node> obstaculos;

    public Grid(int altura, int largura) {
        this.altura = altura;
        this.largura = largura;
        mat = new int[altura][largura];
        clearGrid();
        nohInicial = new Node(new Point(-1, -1), 1);
        nohFinal = new Node(new Point(-1, -1), 1);
        obstaculos = new ArrayList<Node>();
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

    public ArrayList<Node> getIrmaos(Node noh) {
        Point ponto = noh.getCoord();
        int x = ponto.x, y = ponto.y;
        ArrayList<Node> irmaos = new ArrayList<Node>();
        int peso;
        try {
            peso = getPeso(x - 1, y);
            irmaos.add(new Node(new Point(x - 1, y), noh, peso));
        } catch (Throwable t) {
        }
        try {
            peso = getPeso(x + 1, y);
            irmaos.add(new Node(new Point(x + 1, y), noh, peso));
        } catch (Throwable t) {
        }
        try {
            peso = getPeso(x, y - 1);
            irmaos.add(new Node(new Point(x, y - 1), noh, peso));
        } catch (Throwable t) {
        }
        try {
            peso = getPeso(x, y + 1);
            irmaos.add(new Node(new Point(x, y + 1), noh, peso));
        } catch (Throwable t) {
        }
        return irmaos;
    }

//    private void parseGrid() {
//        int i, j;
//        int[][] matrix = getMatriz();
//        for (i = 0; i < getLargura(); i++) {
//            for (j = 0; j < getAltura(); j++) {
//                if (matrix[i][j] == 0) {
//                    nohInicial = new Node(new Point(i, j), matrix[i][j]);
//                } else if (matrix[i][j] != 1) {
//                    obstaculos.add(new Node(new Point(i, j), matrix[i][j]));
//                    if (matrix[i][j] == 10) {
//                        nohFinal = new Node(new Point(i, j), matrix[i][j]);
//                    }
//                }
//            }
//        }
//    }
    private void clearGrid() {
        mat = new int[largura][altura];
        for (int i = 0; i < largura; i++) {
            for (int j = 0; j < altura; j++) {
                mat[i][j] = 1;
            }
        }
    }

    public int getPeso(int x, int y) {
        return getMatriz()[x][y];
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

    public Node getNohFinal() {
        return nohFinal;
    }

    public void setNohFinal(Node pontoFinal) {
        this.nohFinal = pontoFinal;
    }

    public Node getNohInicial() {
        return nohInicial;
    }

    public void setNohInicial(Node pontoInicial) {
        this.nohInicial = pontoInicial;
    }

    public ArrayList<Node> getObstaculos() {
        return obstaculos;
    }

    /**
     * Método que adiciona um tipo de terreno ao grid
     * @param noh - nó contendo as informações
     */
    public void adicionarTerreno(Node noh) {
        if (isTerrenoPreenchido(noh)) {
            removerPonto(noh.getCoord());
        }
        getObstaculos().add(noh);
        setCoord(noh.getCoord(), noh.getPeso());
    }

    /**
     * Método que verifica se um dado ponto no grid está preenchido por
     * algum terreno ou obstáculo
     * @param n - nó contendo as informações do ponto
     * @return true se o ponto está preenchido, false caso contrário
     */
    public boolean isTerrenoPreenchido(Node n) {
        if (!getObstaculos().isEmpty()) {
            for (Node noh : getObstaculos()) {
                if (noh.getCoord().equals(n.getCoord())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Método que remove um ponto do grid
     * @param ponto - ponto a ser removido
     */
    public void removerPonto(Point ponto) {
        Node n = null;
        if (!getObstaculos().isEmpty()) {
            for (Node noh : getObstaculos()) {
                if (noh.getCoord().equals(ponto)) {
                    n = noh;
                    setCoord(ponto, 1);
                }
            }
            getObstaculos().remove(n);
        }
        if (ponto.equals(getNohInicial().getCoord())) {
            setNohInicial(new Node(new Point(-1, -1), 1));
        }
        if (ponto.equals(getNohFinal().getCoord())) {
            setNohFinal(new Node(new Point(-1, -1), 1));
        }
    }
}
