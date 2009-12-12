package model;

import java.awt.Color;

/**
 *
 * @author Carlos Henrique Silva Galdino
 * @author Raphael Pereira de Faria
 *
 */
public enum Enum {

    MAR(1, Color.BLUE), NAVIO(2, Color.YELLOW), NAVIO_DESTRUIDO(3, Color.BLACK), MINA(4, Color.RED), TIRO_AGUA(5, Color.CYAN);

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
