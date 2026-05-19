package model.action;

import model.Joueur;
import model.Objet;
import state.EtatRenforce;

public class Utiliser implements ActionObjet{
	@Override
    public void executer(Objet o, Joueur j) {
        switch (o.getTypeObjet()) {
            case POTION -> {
                int soin = o.getValeurCombat();
                j.soigner(soin);
                System.out.println(" Vous buvez " + o.getNom() + " (+"+soin+" PV).");
            }
            case ARME -> {
                j.setBonusArme(o.getValeurCombat());
                j.changerEtat(new EtatRenforce(5));
                System.out.println(" Vous équipez " + o.getNom() + " (+"+o.getValeurCombat()+" attaque, 5 tours).");
            }
            case ARMURE -> {
                System.out.println(" Vous enfilez " + o.getNom() + ". Défense renforcée.");
            }
            case MYTHIQUE -> {
            	System.out.println(" La licorne en bois scintille magiquement...");
            }
            default -> System.out.println(" " + o.getNom() + " ne peut pas être utilisé directement.");
        }
    }
}
