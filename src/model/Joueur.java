package model;

import java.util.ArrayList;
import java.util.List;

import observer.SujetObserver;

import observer.Observer;
import state.Etat;
import state.Etatnormal;

public class Joueur extends Personnage implements SujetObserver{
	private int pv;
    private int pvMax;
    private int force;
    private int defense;
    private int capaciteAttaque;
    private int bonusArme; 

    private final Inventaire inventaire;
    private Etat etatCourant;
    private final List<Observer> observateurs = new ArrayList<>();

    public Joueur(String nom, int pvMax, int force, int defense, int capaciteAttaque) {
        super(nom, "Joueur", "Héros de l'aventure");
        this.pvMax = pvMax;
        this.pv = pvMax;
        this.force = force;
        this.defense = defense;
        this.capaciteAttaque = capaciteAttaque;
        this.bonusArme = 0;
        this.inventaire = new Inventaire();
        this.etatCourant = new Etatnormal();
    }

    @Override
    public String sePresenter() {
        return "Je suis " + getNom() + " ! Que l'aventure commence !";
    }

    public int calculerDegats() {
        int base = force + capaciteAttaque + bonusArme;
        return etatCourant.modifierAttaque(this, base);
    }

    public void recevoirDegats(int degats) {
        int reels = Math.max(0, degats - defense);
        pv = Math.max(0, pv - reels);
        System.out.println(" " + getNom() + " reçoit " + reels + " dégâts (réduit de " + defense + "). PV : " + pvBarre());
        notifierObservateurs("pv_change");
        if (pv == 0) notifierObservateurs("mort");
    }

    public void soigner(int soin) {
        pv = Math.min(pvMax, pv + soin);
        System.out.println(" " + getNom() + " récupère " + soin + " PV. PV : " + pvBarre());
        notifierObservateurs("pv_change");
    }

    public String pvBarre() {
        int filled = (int) Math.round(10.0 * pv / pvMax);
        return "[" + "█".repeat(filled) + "░".repeat(10 - filled) + "] " + pv + "/" + pvMax;
    }

    public void changerEtat(Etat e) {
        this.etatCourant = e;
        System.out.println(" " + getNom() + " est maintenant : " + e.getNomEtat());
        notifierObservateurs("etat_change");
    }

    public void appliquerEffetEtat() { etatCourant.appliquerEffet(this); }

    @Override 
    public void ajouterObservateur(Observer o){ 
    	observateurs.add(o); 
    }
    @Override 
    public void retirerObservateur(Observer o){ 
    	observateurs.remove(o); 
    }
    @Override 
    public void notifierObservateurs(String event) {
        for (Observer o : observateurs) o.mettreAJour(event, this);
    }

    public void afficherStats() {
        System.out.printf(" %-34s%n" + getNom() + " [" + etatCourant.getNomEtat() + "]");
        System.out.printf(" PV %-27s%n", pvBarre());
        System.out.printf(" Force %-27s%n", force);
        System.out.printf(" Défense %-27s%n", defense);
        System.out.printf(" Attaque %-27s%n", capaciteAttaque + bonusArme + " (+" + bonusArme + " arme)");
    }

    public int getPv(){ 
    	return pv; 
    }
    public int getPvMax(){ 
    	return pvMax; 
    }
    public int getForce(){ 
    	return force; 
    }
    public int getDefense(){ 
    	return defense; 
    }
    public int getCapaciteAttaque(){ 
    	return capaciteAttaque; 
    }
    public int getBonusArme(){ 
    	return bonusArme; 
    }
    public void setBonusArme(int b){ 
    	this.bonusArme = b; 
    }
    public Inventaire getInventaire(){ 
    	return inventaire; 
    }
    public Etat getEtatCourant(){ 
    	return etatCourant; 
    }
    public boolean estVivant(){ 
    	return pv > 0; 
    }
}
