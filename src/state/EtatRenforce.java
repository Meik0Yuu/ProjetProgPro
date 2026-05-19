package state;

import model.Joueur;

public class EtatRenforce implements Etat{
	private int tours;
    public EtatRenforce(int tours){ 
    	this.tours = tours; 
    }
    @Override 
    public String getNomEtat(){ 
    	return " Renforcé (" + tours + " tours)"; 
    }
    @Override 
    public void appliquerEffet(Joueur j) {
        tours--;
        if (tours <= 0){ 
        	System.out.println(" Le renforcement s'estompe."); 
        	j.changerEtat(new Etatnormal()); 
        }
    }
    @Override 
    public int modifierAttaque(Joueur j, int base){ 
    	return (int)(base * 1.6);
    }
	@Override
	public int modifierDefense(Joueur joueur, int base) {
		return (int)(base*1.6);
	}
}
