package model;

import java.awt.Point;

/**
 *
 * @author Carlos Henrique Silva Galdino
 * @author Raphael Pereira de Faria
 *
 */
public class NavioFactory {
    public enum TipoNavio {
        Battleship,
        Cruiser,
        Submarine,
        Destroyer,
        PatrolBoat
    }

    public static Navio criarNavio(TipoNavio tipo) {
        switch (tipo) {
            case Battleship:
                return new Navio("Battleship", 4);
            case Cruiser:
                return new Navio("Cruiser", 3);
            case Submarine:
                return new Navio("Submarine", 3);
            case Destroyer:
                return new Navio("Destroyer", 2);
            case PatrolBoat:
                return new Navio("Patrol Boat", 1);
        }
        throw new IllegalArgumentException("O tipo de navio " + tipo + " não é reconhecido.");
    }

    public static Navio criarNavio(String nome, Point ponto, int tamanho, boolean direcao) {
        return new Navio(nome, ponto, tamanho, direcao);
    }
    
}
