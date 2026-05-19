package state;

import model.Joueur;

public class Etatnormal implements Etat{
	@Override 
	public String getNomEtat(){ 
		return "Normal"; 
	}
    @Override 
    public void appliquerEffet(Joueur j){}
    @Override 
    public int modifierAttaque(Joueur j, int base){ 
    	return base; 
    }
	@Override
	public int modifierDefense(Joueur joueur, int base) {
		// TODO Auto-generated method stub
		return base;
	}

}
