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
    private boolean ultimoTorpedoDestruiu;
    public ArrayList<Mina> getMinas() {
        return minas;
    }

    public ArrayList<Navio> getNavios() {
        return navios;
    }

    public static void main(String[] args) {
        Grid x = new Grid(10, 10);

        Navio cruiser = new Navio("Cruiser", new Point(1, 1), 3, false);
        Navio battleship = new Navio("Battleship", new Point(2, 1), 4, false);
        Navio destroyer = new Navio("Destroyer", new Point(3, 1), 2, false);
        Navio submarine = new Navio("Submarine", new Point(4, 1), 1, false);
        Mina mine1 = new Mina(8, 8);
        Mina mine2 = new Mina(9, 9);
        try {
            x.inserirNavio(cruiser);
            x.inserirNavio(battleship);
            x.inserirNavio(new Navio("Battleship", new Point(6, 5), 4, false));
            x.inserirNavio(destroyer);
            x.inserirNavio(submarine);
            x.inserirMina(mine1);
            x.inserirMina(mine2);
        } catch (Exception E) {
            System.out.println(E.getMessage());
        }


        x.atirar(new Point(4, 1));
        x.atirar(new Point(5, 1));
        x.printGrid();

    }

    public boolean isUltimoTorpedoDestruiu() {
        return ultimoTorpedoDestruiu;
    }

    public String getDescricaoDoPonto(Point ponto) {
        int valor = mat[ponto.x][ponto.y+1];
        String descricao = "";
        switch (valor) {
            case 1:
                descricao = "água";
                break;
            case 2:
                descricao = "navio";
                break;
            case 3:
                descricao = "mina";
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
        this.ultimoTorpedoDestruiu = false;
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

    public boolean minaExiste(Mina mina) {
        for (Mina m : minas) {
            if (m.x == mina.x && m.y == mina.y) {
                return true;
            }
        }
        return false;
    }

    public boolean inserirMina(Mina mina) throws Exception {
        if (!minaExiste(mina)) {
            int[][] matriz = this.getMatriz();
            if (matriz[mina.x][mina.y] != Enum.MAR.getValor()) {
                throw new Exception("Posição do tabuleiro já ocupada.");
            }
            mat[mina.x][mina.y] = Enum.MINA.getValor();
            minas.add(mina);
        }
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
        this.ultimoTorpedoDestruiu = false;
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
                    this.ultimoTorpedoDestruiu = true;
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
    }
}
