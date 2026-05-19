package state;

import model.Joueur;

public interface Etat {
	String getNomEtat();
    void appliquerEffet(Joueur joueur);
    int modifierAttaque(Joueur joueur, int base);
    int modifierDefense(Joueur joueur, int base);
}
