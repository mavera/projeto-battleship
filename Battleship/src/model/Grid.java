package model;

import java.awt.Point;

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


    public Grid(int altura, int largura) {
        this.altura = altura;
        this.largura = largura;
        mat = new int[altura][largura];
        clearGrid();

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


    public boolean inserirNavio(Navio navio) throws Exception{
        Point ini = navio.getInicio();
        Point fim = navio.getFim();
        int i, j;
        if(checarCoordenadasFinais(fim)){
            throw new Exception("Não é possível se posicionar o navio devido aos limites do tabuleiro");
        }
        int[][] matriz = this.getMatriz();
        for(i = ini.x; i < fim.x; i++){
            for(j = ini.y; j < fim.y; j++){
                if(matriz[i][j] != Enum.MAR.getValor()){
                    throw new Exception("Posição do tabuleiro já ocupada.");
                }
            }
        }

        
        return true;
    }

    //verifica se as coordenadas de inicio e fim do Navio se encontram dentro
    //do limite do tabuleiro

    private boolean checarCoordenadasFinais(Point fim){
        int x = fim.y, y = fim.y;
        if (x >= this.largura || y >=this.altura){
            return true;
        }
        return false;
    }

    private void parseGrid() {
        int i, j;
        int[][] matrix = getMatriz();

    }

    private void clearGrid() {
        mat = new int[largura][altura];
        for (int i = 0; i < largura; i++) {
            for (int j = 0; j < altura; j++) {
                mat[i][j] = 0;//Enum.MAR;
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

//    /**
//     * Método que adiciona um tipo de terreno ao grid
//     * @param noh - nó contendo as informações
//     */
//    public void adicionarTerreno(Node noh) {
//        if (isTerrenoPreenchido(noh)) {
//            removerPonto(noh.getCoord());
//        }
//        getObstaculos().add(noh);
//        setCoord(noh.getCoord(), noh.getPeso());
//    }
//
//    /**
//     * Método que verifica se um dado ponto no grid está preenchido por
//     * algum terreno ou obstáculo
//     * @param n - nó contendo as informações do ponto
//     * @return true se o ponto está preenchido, false caso contrário
//     */
//    public boolean isTerrenoPreenchido(Node n) {
//        if (!getObstaculos().isEmpty()) {
//            for (Node noh : getObstaculos()) {
//                if (noh.getCoord().equals(n.getCoord())) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    /**
//     * Método que remove um ponto do grid
//     * @param ponto - ponto a ser removido
//     */
//    public void removerPonto(Point ponto) {
//        Node n = null;
//        if (!getObstaculos().isEmpty()) {
//            for (Node noh : getObstaculos()) {
//                if (noh.getCoord().equals(ponto)) {
//                    n = noh;
//                    setCoord(ponto, 1);
//                }
//            }
//            getObstaculos().remove(n);
//        }
//        if (ponto.equals(getNohInicial().getCoord())) {
//            setNohInicial(new Node(new Point(-1, -1), 1));
//        }
//        if (ponto.equals(getNohFinal().getCoord())) {
//            setNohFinal(new Node(new Point(-1, -1), 1));
//        }
//    }
}
