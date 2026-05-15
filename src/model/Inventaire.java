package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Inventaire {
	private static final int CAPACITE_MAX = 12;
    private final List<Objet> objets = new ArrayList<>();

    public boolean ajouter(Objet o) {
        if (objets.size() >= CAPACITE_MAX) {
            System.out.println(" Inventaire plein ! (" + CAPACITE_MAX + "/" + CAPACITE_MAX + ")");
            return false;
        }
        objets.add(o);
        return true;
    }

    public Objet retirer(String nom) {
        return objets.stream()
            .filter(o -> o.getNom().equalsIgnoreCase(nom))
            .findFirst()
            .map(o -> { objets.remove(o); return o; })
            .orElse(null);
    }

    public boolean contient(String nom) {
        return objets.stream().anyMatch(o -> o.getNom().equalsIgnoreCase(nom));
    }

    public Optional<Objet> trouver(String nom) {
        return objets.stream().filter(o -> o.getNom().equalsIgnoreCase(nom)).findFirst();
    }

    public Optional<Objet> meilleureArme() {
        return objets.stream()
            .filter(o -> o.getTypeObjet() == Objet.TypeObjet.ARME)
            .max(Comparator.comparingInt(Objet::getValeurCombat));
    }

    public void afficher() {
        if (objets.isEmpty()) {
            System.out.println("(inventaire vide)");
            return;
        }
        System.out.printf(" %-3s %-20s %-10s %s%n", "Num", "Nom", "Type", "Utilité");
        for (int i = 0; i < objets.size(); i++) {
            System.out.printf(" [%d] %s%n", i + 1, objets.get(i));
        }
        System.out.println("\n Slots : " + objets.size() + "/" + CAPACITE_MAX);
    }

    public List<Objet> getObjets(){ 
    	return Collections.unmodifiableList(objets); 
    }
    public boolean estVide(){ 
    	return objets.isEmpty(); 
    }
    public int getTaille(){ 
    	return objets.size(); 
    }
}
