package observer;

import model.Joueur;

public class PVJoueurs implements Observer {
	@Override
    public void mettreAJour(String evenement, Object source) {
        if (!(source instanceof Joueur j)) return;
        switch (evenement) {
            case "pv_change" -> {
                double ratio = (double) j.getPv() / j.getPvMax();
                if (ratio <= 0.0){}
                else if (ratio <= 0.25) System.out.println("  🔴 DANGER CRITIQUE ! " + j.getNom() + " est à l'agonie !");
                else if (ratio <= 0.5)  System.out.println("  🟡 " + j.getNom() + " est sérieusement blessé.");
            }
            case "mort" -> System.out.println(" " + j.getNom() + " est mort...");
            case "etat_change" -> System.out.println(" État changé : " + j.getEtatCourant().getNomEtat());
        }
    }
}
