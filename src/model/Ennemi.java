package model;

import strategy.StrategieAttaque;
import strategy.StrategieDeplacement;

public class Ennemi extends Personnage{
	private String dialogue;
    private int pv;
    private int pvMax;
    private int capaciteAttaque;
    private int force;
    private int  defense;
    private String typeEnnemi; 

    private StrategieAttaque strategieAttaque;
    private StrategieDeplacement strategieDeplacement;

    public Ennemi(String nom, String typeEnnemi, String description, String dialogue,
                  int pvMax, int capaciteAttaque, int force, int defense,
                  StrategieAttaque sa, StrategieDeplacement sd) {
        super(nom, typeEnnemi, description);
        this.dialogue = dialogue;
        this.typeEnnemi = typeEnnemi;
        this.pvMax = pvMax;
        this.pv = pvMax;
        this.capaciteAttaque = capaciteAttaque;
        this.force = force;
        this.defense = defense;
        this.strategieAttaque = sa;
        this.strategieDeplacement = sd;
    }
 
    @Override
    public String sePresenter(){ 
    	return dialogue; 
    }

    public void attaquer(Joueur joueur) {
        int degats = strategieAttaque.calculerDegats(this, joueur);
        joueur.recevoirDegats(degats);
    }

    public void recevoirDegats(int degats) {
        int reels = Math.max(0, degats - defense);
        pv = Math.max(0, pv - reels);
    }

    public void seDeplacer(Piece pieceCourante) {
        strategieDeplacement.seDeplacer(this, pieceCourante);
    }

    public String pvBarre() {
        int filled = (int) Math.round(10.0 * pv / pvMax);
        return "[" + "█".repeat(filled) + "░".repeat(10 - filled) + "] " + pv + "/" + pvMax;
    }

    public void afficherFiche() {
        System.out.printf(" %-29s%n", getNom() + " [" + typeEnnemi + "]");
        System.out.printf(" PV %-23s%n", pvBarre());
        System.out.printf(" Force %-23s%n", force);
        System.out.printf(" Attaque %-23s%n", capaciteAttaque);
        System.out.printf(" Défense %-23s%n", defense);
        System.out.printf(" %s%-" + (32 - Math.min(32, getDescription().length())) + "s%n",
                getDescription().substring(0, Math.min(30, getDescription().length())), "");
    }

    public void changerStrategieAttaque(StrategieAttaque sa){ 
    	this.strategieAttaque = sa; 
    }
    public void changerStrategieDeplacement(StrategieDeplacement sd){ 
    	this.strategieDeplacement = sd; 
    }

    public String getDialogue(){ 
    	return dialogue; 
    }
    public int getPv(){ 
    	return pv; 
    }
    public int getPvMax(){ 
    	return pvMax; 
    }
    public int getCapaciteAttaque(){ 
    	return capaciteAttaque; 
    }
    public int getForce(){ 
    	return force; 
    }
    public int  getDefense(){ 
    	return defense; 
    }
    public String getTypeEnnemi(){ 
    	return typeEnnemi; 
    }
    public boolean estVivant(){
    	return pv > 0; 
    }
}
