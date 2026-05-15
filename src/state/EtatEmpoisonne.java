package state;

import model.Joueur;

public class EtatEmpoisonne implements Etat{
	private int tours;
    public EtatEmpoisonne(int tours){ 
    	this.tours = tours; 
    }
    @Override 
    public String getNomEtat(){ 
    	return " Empoisonné (" + tours + " tours)"; 
    }
    @Override 
    public void appliquerEffet(Joueur j) {
        if (tours > 0) {
            System.out.println(" Poison ! " + j.getNom() + " perd 4 PV.");
            j.recevoirDegats(4);
            tours--;
            if (tours == 0){ 
            	System.out.println(" Le poison s'est dissipé."); 
            	j.changerEtat(new Etatnormal()); 
            }
        }
    }
    @Override 
    public int modifierAttaque(Joueur j, int base){ 
    	return (int)(base * 0.75); 
    }

}
