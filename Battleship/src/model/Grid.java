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
    private boolean gridCompleto;
    private boolean ultimoTorpedoDestruiuNavio;
    private boolean ultimoTorpedoDestruiuMina;

    public ArrayList<Mina> getMinas() {
        return minas;
    }

    public ArrayList<Navio> getNavios() {
        return navios;
    }

    public boolean isUltimoTorpedoDestruiuMina() {
        return ultimoTorpedoDestruiuMina;
    }

    public boolean isUltimoTorpedoDestruiuNavio() {
        return ultimoTorpedoDestruiuNavio;
    }

    public String getDescricaoDoPonto(Point ponto) {
        int valor = mat[ponto.y][ponto.x];
        String descricao = "";
        switch (valor) {
            case 4:
                descricao = "água";
                break;
            case 5:
                descricao = "destruido";
                break;
            case 6:
                descricao = "mina";
                break;
            case 7:
                descricao = "navio";
                break;
            default:
                descricao = "erro";
        }
        return descricao;
    }

    public boolean isGridCompleto() {
        return gridCompleto;
    }

    public Grid(int altura, int largura) {
        this.altura = altura;
        this.largura = largura;
        mat = new int[altura][largura];
        navios = new ArrayList<Navio>();
        minas = new ArrayList<Mina>();
        this.gridCompleto = false;
        this.ultimoTorpedoDestruiuNavio = false;
        this.ultimoTorpedoDestruiuMina = false;
        clearGrid();
    }

    public int[][] getMat() {
        return mat;
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
        for (Mina m : minas) {
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

    public boolean parseGrid() {
        return (this.navios.size() + this.minas.size() == 7) ? true : false;
    }

    public void minaExiste(Mina mina) {
        for (Mina m : minas) {
            if (m.x == mina.x && m.y == mina.y) {
                minas.remove(mina);
                mat[mina.y][mina.x] = Enum.MAR.getValor();
            }
        }
    }

    public boolean inserirMina(Mina mina) throws Exception {
        if (minas.size() >= 2) {
            minaExiste(minas.get(0));
        }
        int[][] matriz = this.getMatriz();
        if (matriz[mina.y][mina.x] != Enum.MAR.getValor()) {
            throw new Exception("Posição do tabuleiro já ocupada.");
        }
        mat[mina.y][mina.x] = Enum.MINA.getValor();
        minas.add(mina);
        notificar();
        return true;
    }

    private void verificarNavioExistente(String nome) {

        for (Navio n : navios) {
            if (n.getNome().equalsIgnoreCase(nome)) {
                Point ini = n.getInicio();
                Point fim = n.getFim();
                int i, j;
                int[][] matriz = this.getMatriz();
                for (i = ini.y; i <= fim.y; i++) {
                    for (j = ini.x; j <= fim.x; j++) {
                        matriz[i][j] = Enum.MAR.getValor();
                    }
                }
                navios.remove(n);
                notificar();
                break;
            }
        }
    }

    public boolean inserirNavio(Navio navio) throws Exception {
        verificarNavioExistente(navio.getNome());

        Point ini = navio.getInicio();
        Point fim = navio.getFim();
        int i, j;
        if (!isDentroDosLimites(fim)) {
            throw new Exception("Não é possível posicionar o navio devido aos limites do tabuleiro.");
        }
        int[][] matriz = this.getMatriz();
        for (i = ini.y; i <= fim.y; i++) {
            for (j = ini.x; j <= fim.x; j++) {
                if (matriz[i][j] != Enum.MAR.getValor()) {
                    throw new Exception("Posição do tabuleiro já ocupada.");
                }
            }
        }

        for (i = ini.y; i <= fim.y; i++) {
            for (j = ini.x; j <= fim.x; j++) {
                matriz[i][j] = Enum.NAVIO.getValor();
            }
        }
        navios.add(navio);
        notificar();
        return true;
    }

    public boolean atirar(Point p) {
        this.ultimoTorpedoDestruiuNavio = false;
        this.ultimoTorpedoDestruiuMina = false;
        switch (mat[p.y][p.x]) {
            case 1://Enum.MAR.getValor();
                mat[p.y][p.x] = Enum.TIRO_AGUA.getValor();
                break;
            case 2://Enum.NAVIO.getValor();
                mat[p.y][p.x] = Enum.NAVIO_DESCOBERTO.getValor();
                verificaNavioDestruido();
                break;
            case 3://Enum.MINA.getValor();
                mat[p.y][p.x] = Enum.MINA_DESTRUIDA.getValor();
                this.ultimoTorpedoDestruiuMina = true;
                verificaMinaDestruida();
                break;
            default:
        }

        notificar();
        return true;
    }

    private void verificaMinaDestruida() {
        if (!minas.isEmpty()) {
            for (Mina m : minas) {
                if (mat[m.x][m.y] == Enum.MINA_DESTRUIDA.getValor()) {
                    m.setDestruida(true);
                    break;
                }
            }
        }
    }

    private boolean verificaNavioDestruido() {

        int i, j;
        boolean destruido = true;

        if (!navios.isEmpty()) {
            verificacaoNavios:
            for (Navio n : navios) {
                if (n.isDestruido()) {
                    continue;
                }
                Point ini = n.getInicio();
                Point fim = n.getFim();
                destruido = true;
                out:
                for (i = ini.y; i <= fim.y; i++) {
                    for (j = ini.x; j <= fim.x; j++) {
                        if (mat[i][j] != Enum.NAVIO_DESCOBERTO.getValor()) {
                            destruido = false;
                            break out;
                        }
                    }
                }
                if (destruido) {
                    for (i = ini.y; i <= fim.y; i++) {
                        for (j = ini.x; j <= fim.x; j++) {
                            mat[i][j] = Enum.NAVIO_DESTRUIDO.getValor();

                        }
                    }
                    this.ultimoTorpedoDestruiuNavio = true;
                    n.setDestruido(destruido);
                    break verificacaoNavios;
                }
            }
        }
        
        return destruido;
    }

    //verifica se as coordenadas de inicio e fim do Navio se encontram dentro
    //do limite do tabuleiro
    private boolean isDentroDosLimites(Point fim) {
        int x = fim.x, y = fim.y;
        if (x >= this.largura || y >= this.altura) {
            return false;
        }
        return true;
    }

    private void clearGrid() {
        mat = new int[largura][altura];
        for (int i = 0; i < largura; i++) {
            for (int j = 0; j < altura; j++) {
                mat[i][j] = Enum.MAR.getValor();
            }
        }
        notificar();
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

    public void resetarGrid() {
        clearGrid();
        navios = new ArrayList<Navio>();
        minas = new ArrayList<Mina>();
        gridCompleto = false;
        notificar();
    }
}
