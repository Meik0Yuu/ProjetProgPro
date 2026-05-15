package model.action;

import model.Joueur;
import model.Objet;

public interface ActionObjet {

    void executer(Objet objet, Joueur joueur);
}
