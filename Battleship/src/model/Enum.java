package model;

import java.awt.Color;

/**
 *
 * @author Carlos Henrique Silva Galdino
 * @author Raphael Pereira de Faria
 *
 */
public enum Enum {

    MAR(1, Color.CYAN),
    NAVIO(2, Color.YELLOW),
    MINA(3, Color.RED),
    TIRO_AGUA(4, Color.BLUE),
    NAVIO_DESTRUIDO(5, Color.BLACK),
    MINA_DESTRUIDA(6,Color.DARK_GRAY);

    int valor;
    Color cor;

    private Enum(int valor, Color cor) {
        this.valor = valor;
        this.cor = cor;
    }

    public int getValor(){
        return this.valor;
    }

    public Color getCor(){
        return this.cor;
    }

    public static Color getCorPorValor(int valor){
        Color corRespectiva = Color.WHITE;
        for(Enum enumeration: Enum.values()){
            if(enumeration.getValor() == valor){
                corRespectiva = enumeration.getCor();
                break;
            }
        }
        return corRespectiva;
    }

}
