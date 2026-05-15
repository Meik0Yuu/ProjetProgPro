package model.action;

import model.Joueur;
import model.Objet;

public class Interagir implements ActionObjet{
	@Override
    public void executer(Objet o, Joueur j) {
        System.out.println(" " + o.getNom() + " : " + o.getDescription());
        System.out.println(" Utilité : " + o.getUtilite());
    }
}
