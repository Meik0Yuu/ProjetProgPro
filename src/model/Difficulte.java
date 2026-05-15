package model;

import model.Difficulte;

public abstract class Difficulte {
	public abstract String getNom();
    public abstract double getModificateurEnnemis();
    public abstract double getModificateurRecompenses();
    public abstract int getPvJoueurBonus();

    @Override public String toString(){ 
    	return getNom(); 
    }

    public static class Facile extends Difficulte {
        @Override public String getNom(){ 
        	return "Facile"; 
        }
        @Override public double getModificateurEnnemis(){ 
        	return 0.7; 
        }
        @Override public double getModificateurRecompenses(){ 
        	return 0.8; 
        }
        @Override public int    getPvJoueurBonus(){ 
        	return 30; 
        }
    }
    public static class Normal extends Difficulte {
        @Override public String getNom(){ 
        	return "Normal"; 
        }
        @Override public double getModificateurEnnemis(){ 
        	return 1.0; 
        }
        @Override public double getModificateurRecompenses(){ 
        	return 1.0; 
        }
        @Override public int getPvJoueurBonus(){ 
        	return 0; 
        }
    }
    public static class Difficile extends Difficulte {
        @Override public String getNom(){ 
        	return "Difficile"; 
        }
        @Override public double getModificateurEnnemis(){ 
        	return 1.5; 
        }
        @Override public double getModificateurRecompenses(){ 
        	return 1.3; 
        }
        @Override public int getPvJoueurBonus(){ 
        	return -20; 
        }
    }
}
