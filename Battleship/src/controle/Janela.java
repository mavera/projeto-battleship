/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controle;

import model.Observer;

/**
 *
 * @author Raphael
 *
 */
public class Janela implements Observer{
    private Controle cntl;

    public Janela(Controle cntl) {
        this.cntl = cntl;
    }
    
    public void atualizar(){

    }
}
