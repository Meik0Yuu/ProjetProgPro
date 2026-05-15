package model.action;

import model.Joueur;
import model.Objet;

public class Ramasser implements ActionObjet{
	@Override
    public void executer(Objet o, Joueur j) {
        if (j.getInventaire().ajouter(o)) {
            System.out.println(" " + o.getNom() + " ajouté à l'inventaire.");
        }
    }
}
