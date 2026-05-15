package model.action;

import model.Joueur;
import model.Objet;

public class Creation implements ActionObjet{
	private final Objet second;
    private final Objet resultat;
    public Creation(Objet second, Objet resultat) {
        this.second = second;
        this.resultat = resultat;
    }
    @Override
    public void executer(Objet premier, Joueur j) {
        if (!j.getInventaire().contient(second.getNom())) {
            System.out.println(" Il manque : " + second.getNom());
            return;
        }
        j.getInventaire().retirer(premier.getNom());
        j.getInventaire().retirer(second.getNom());
        j.getInventaire().ajouter(resultat);
        System.out.println(" Combinaison réussie -> " + resultat.getNom() + " !");
    }
}
