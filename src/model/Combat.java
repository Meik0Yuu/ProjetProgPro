package model;

public class Combat {
	private int nbrEnnemi = 0;
	
    public boolean executerTour(Joueur joueur, Ennemi ennemi) {
        int degats = joueur.calculerDegats();
        System.out.printf(" %s attaque %s pour %d dégâts.%n",
            joueur.getNom(), ennemi.getNom(), degats);
        ennemi.recevoirDegats(degats);

        System.out.printf(" %s  %s%n", ennemi.getNom(), ennemi.pvBarre());

        if (!ennemi.estVivant()) {
            nbrEnnemi++;
            System.out.println(ennemi.getNom() + " est vaincu !");
            return true;
        }

        System.out.println(ennemi.getNom() + " contre-attaque !");
        ennemi.attaquer(joueur);
        return false;
    }

    public int  getNbrEnnemi() { 
    	return nbrEnnemi;
    }
}
