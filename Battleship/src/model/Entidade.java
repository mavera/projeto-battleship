package model;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Entidade implements Serializable {
	
	//lista contendo os objetos observadores
	protected ArrayList<Observer> listObs = new ArrayList<Observer>();

	protected void notificar(){
            for (Observer observer : listObs)
                observer.atualizar();
	}

	public void addObservers(Observer o){
		listObs.add(o);
	}
}
