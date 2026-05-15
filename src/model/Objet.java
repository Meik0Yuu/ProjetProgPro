package model;

import model.Objet;
import model.action.ActionObjet;
import model.action.Creation;
import model.action.Interagir;
import model.action.Ramasser;
import model.action.Utiliser;

public class Objet {
	public enum TypeObjet { ARME, POTION, CLE, ARMURE, COMBINABLE, DIVERS }

    private String nom;
    private TypeObjet typeObjet;
    private int taille;
    private String utilite;
    private String description;
    private int valeurCombat;

    private final ActionObjet actionInteragir = new Interagir();
    private final ActionObjet actionRamasser = new Ramasser();
    private final ActionObjet actionUtiliser = new Utiliser();

    public Objet(String nom, TypeObjet typeObjet, int taille,
                 String utilite, String description, int valeurCombat) {
        this.nom = nom;
        this.typeObjet = typeObjet;
        this.taille = taille;
        this.utilite = utilite;
        this.description = description;
        this.valeurCombat = valeurCombat;
    }

    public void interagir(Joueur joueur){
    	actionInteragir.executer(this, joueur); 
    }
    public void ramasser(Joueur joueur){ 
    	actionRamasser.executer(this, joueur); 
    }
    public void utiliser(Joueur joueur){ 
    	actionUtiliser.executer(this, joueur); 
    }

    public void combiner(Joueur joueur, Objet second, Objet resultat) {
        new Creation(second, resultat).executer(this, joueur);
    }

    public String getNom(){ 
    	return nom; 
    }
    public TypeObjet getTypeObjet(){ 
    	return typeObjet; 
    }
    public int getTaille(){ 
    	return taille; 
    }
    public String getUtilite(){ 
    	return utilite; 
    }
    public String getDescription(){ 
    	return description; 
    }
    public int getValeurCombat(){ 
    	return valeurCombat; 
    }

    @Override
    public String toString() {
        return String.format("%-20s [%-8s]  %s", nom, typeObjet, utilite);
    }
}
