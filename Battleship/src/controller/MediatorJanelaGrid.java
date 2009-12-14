package controller;

import java.awt.Point;
import java.util.Random;
import model.Grid;
import model.Mina;
import model.MinaFactory;
import model.Navio;
import model.NavioFactory;
import view.Janela;

/**
 *
 * @author Carlos Henrique Silva Galdino
 * @author Raphael Pereira de Faria
 *
 */
public class MediatorJanelaGrid extends Mediator {

    private Grid grid;
    private Janela janela;
    private int largura, altura;
    private int quantidadeTorpedos;
    private boolean jogoIniciado;
    private String log;
    private int quantidadeNaviosDestruidos;
    private boolean versusComputador;

    public MediatorJanelaGrid() {
        quantidadeTorpedos = 0;
        this.janela = new Janela("Aplicativo", this);
        this.altura = 10;
        this.largura = 10;
        this.grid = new Grid(altura, largura);
        this.jogoIniciado = false;
        this.log = "";
        this.quantidadeNaviosDestruidos = 0;
        grid.addObservers(janela);
    }

    public int iniciarJogoVersusComputador(int dificuldade) {
        this.resetarJogo();
        this.jogoIniciado = true;
        this.versusComputador = true;
        this.geraGrid();
        if (dificuldade == 2) {
            quantidadeTorpedos = 30;
        } else if (dificuldade == 1) {
            quantidadeTorpedos = 45;
        } else if (dificuldade == 0) {
            quantidadeTorpedos = 60;
        }
        log += "O jogo começou!";
        return quantidadeTorpedos;
    }

    public int iniciarJogoVersusJogador(int dificuldade) {
        this.jogoIniciado = true;
        this.versusComputador = false;
        if (dificuldade == 2) {
            quantidadeTorpedos = 30;
        } else if (dificuldade == 1) {
            quantidadeTorpedos = 45;
        } else if (dificuldade == 0) {
            quantidadeTorpedos = 60;
        }
        janela.atualizar();
        log += "O jogo começou!";
        return quantidadeTorpedos;
    }

    public void resetarJogo() {
        quantidadeTorpedos = 0;
        this.jogoIniciado = false;
        this.log = "";
        this.quantidadeNaviosDestruidos = 0;
        this.grid.resetarGrid();
    }

    public boolean isJogoComecou() {
        return jogoIniciado;
    }

    public int atirar(Point ponto) {
        quantidadeTorpedos--;
        grid.atirar(ponto);
        if(grid.isUltimoTorpedoDestruiuMina()){
            quantidadeTorpedos -= 5;
        }
        refreshLog(ponto);
        if (grid.isUltimoTorpedoDestruiuNavio())
            quantidadeNaviosDestruidos++;
        if (isJogoFinalizado())
            janela.mensagemFinalDeJogo(isVitoria());
        return quantidadeTorpedos;
    }

    //Método para atualizar o Log de ações do usuário atualizada a cada tiro realizado
    public void refreshLog(Point ponto) {
        char[] letras = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
        String coordenada = "\n(" + letras[ponto.x] + "," + (ponto.y + 1) + ")";
        log += coordenada;
        String descricao = grid.getDescricaoDoPonto(ponto);
        if (descricao.equalsIgnoreCase("água")) {
            log += "Você atirou na água!";
        } else if (descricao.equalsIgnoreCase("navio")) {
            log += "Você acertou um navio.";
        } else if (descricao.equalsIgnoreCase("destruido")) {
            log += this.grid.isUltimoTorpedoDestruiuNavio() ? "Você destruiu o navio " + grid.getNomeNavioDestruido() + "." : "Você perdeu um torpedo atirando em uma posição já descoberta!";
        } else if (descricao.equalsIgnoreCase("mina")) {
            log += "O seu torpedo acertou uma mina!\nVocê acaba de perder 5 torpedos!";
        } else if (descricao.equalsIgnoreCase("erro")) {
            log += "Você perdeu um torpedo atirando em uma posição já descoberta!";
        }
    }

    public String getLog() {
        return log;
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
        Mina mina = MinaFactory.criarMina(ini);
        this.grid.inserirMina(mina);
    }

    public void inserirNavio(String nome, Point ini, boolean direcao) throws Exception {
        int tamanho = getTamanhoPorNome(nome);
        Navio navio = NavioFactory.criarNavio(nome, ini, tamanho, direcao);
        this.grid.inserirNavio(navio);
    }

    public void geraGrid() {
        geraNavios();
        geraMinas();
    }

    public void geraNavios() {
        int i;
        Random gerador = new Random(System.currentTimeMillis());
        for (i = 0; i < NavioFactory.TipoNavio.values().length; i++) {
            Navio navio = NavioFactory.criarNavio(NavioFactory.TipoNavio.values()[i]);
            int tamanhoLimite = 10 - navio.getTamanho();
            int posicaoRandomica = gerador.nextInt(tamanhoLimite * tamanhoLimite);
            Point inicio = new Point(posicaoRandomica % tamanhoLimite, (int) (posicaoRandomica / tamanhoLimite));
            boolean direcao = gerador.nextBoolean();
            navio.setInicio(inicio);
            navio.setDirecao(direcao);
            Point fim;
            if (!direcao) {
                fim = new Point(inicio.x, inicio.y + navio.getTamanho() - 1);
            } else {
                fim = new Point(inicio.x + navio.getTamanho() - 1, inicio.y);
            }
            navio.setFim(fim);
            try {
                grid.inserirNavio(navio);
            } catch (Exception E) {
                i--;
            }
        }
    }

    public void geraMinas() {
        Random gerador = new Random(System.currentTimeMillis());
        int quantMinasInseridas = 0;
        while (quantMinasInseridas < 2) {
            quantMinasInseridas++;
            int posicaoRandomica = gerador.nextInt(81);
            Mina m = MinaFactory.criarMina(posicaoRandomica % 9, posicaoRandomica / 9);
            try {
                grid.inserirMina(m);

            } catch (Exception E) {
                quantMinasInseridas--;
            }
        }
    }

    public int[][] getMatriz() {
        return grid.getMat();
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }

    public int getTorpedos() {
        return quantidadeTorpedos;
    }
    
    public boolean isJogoFinalizado() {
        return ((quantidadeNaviosDestruidos == 5) || quantidadeTorpedos == 0) ? true : false;
    }
    
    private boolean isVitoria() {
        return (quantidadeNaviosDestruidos == 5) ? true : false;
    }

    public boolean isJogoConfigurado() {
        return grid.parseGrid();
    }

    public boolean isVersusComputador() {
        return versusComputador;
    }

}
