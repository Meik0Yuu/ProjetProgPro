package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import model.Piece;

public class Piece {
	public enum Direction{ 
		NORD, SUD, EST, OUEST, HAUT, BAS 
	}

    private final String nom;
    private final String description;
    private final String attributEnnemi;
    private final String attributObjet;
    private final String typeQuete;
    private boolean estSortie;
    private boolean fouillée = false;

    private final Map<Direction, Piece> sorties = new EnumMap<>(Direction.class);
    private final List<Objet> objets = new ArrayList<>();
    private final List<Ennemi> ennemis = new ArrayList<>();
    private final List<PNJ> pnjs = new ArrayList<>();
    private final List<String> secrets = new ArrayList<>();

    public Piece(String nom, String description,
                 String attributEnnemi, String attributObjet,
                 String typeQuete, boolean estSortie) {
        this.nom = nom;
        this.description = description;
        this.attributEnnemi = attributEnnemi;
        this.attributObjet = attributObjet;
        this.typeQuete = typeQuete;
        this.estSortie = estSortie;
    }

    public void ajouterSortie(Direction d, Piece p){ 
    	sorties.put(d, p); 
    }
    public Piece getSortie(Direction d){ 
    	return sorties.get(d); 
    }
    public Map<Direction, Piece> getSorties(){ 
    	return Collections.unmodifiableMap(sorties); 
    }

    public void ajouterObjet(Objet o){ 
    	objets.add(o); 
    }
    public boolean retirerObjet(Objet o){ 
    	return objets.remove(o); 
    }
    public void ajouterEnnemi(Ennemi e){
    	ennemis.add(e); 
    }
    public void retirerEnnemi(Ennemi e){ 
    	ennemis.remove(e); 
    }
    public void ajouterPNJ(PNJ p){ 
    	pnjs.add(p); 
    }
    public void ajouterSecret(String s){ 
    	secrets.add(s); 
    }

    public boolean aDesEnnemisVivants() {
        return ennemis.stream().anyMatch(Ennemi::estVivant);
    }

    public void afficher() {
        System.out.println();
        System.out.printf(" %-49s%n", nom);
        String desc = description;
        while (desc.length() > 50) {
            int cut = desc.lastIndexOf(' ', 50);
            if (cut == -1) cut = 50;
            System.out.printf(" %-50s%n", desc.substring(0, cut));
            desc = desc.substring(cut + 1);
        }
        System.out.printf(" %-50s%n", desc);

        // Sorties
        List<String> dirs = new ArrayList<>();
        for (Map.Entry<Direction, Piece> e : sorties.entrySet()) {
            dirs.add(e.getKey().toString().toLowerCase() + " -> " + e.getValue().getNom());
        }
        System.out.printf(" Sorties : %-39s%n",
            dirs.isEmpty() ? "aucune" : String.join(", ", dirs));

        // Ennemis vivants
        long nbEnnemis = ennemis.stream().filter(Ennemi::estVivant).count();
        if (nbEnnemis > 0) {
            System.out.printf(" Ennemis : %-39s%n", nbEnnemis + " ennemi(s) présent(s) !");
        }

        // Objets
        if (!objets.isEmpty()) {
            System.out.printf(" Objets : %-39s%n",
                objets.stream().map(Objet::getNom).reduce((a,b)->a+", "+b).orElse(""));
        }

        // PNJs
        if (!pnjs.isEmpty()) {
            System.out.printf(" PNJ : %-39s%n",
                pnjs.stream().map(Personnage::getNom).reduce((a,b)->a+", "+b).orElse(""));
        }

        if (estSortie) {
            System.out.println(" Cette pièce mène à la sortie du donjon ! ");
        }
    }

    public String getNom(){ 
    	return nom; 
    }
    public String getDescription(){ 
    	return description; 
    }
    public String getAttributEnnemi(){ 
    	return attributEnnemi; 
    }
    public String getAttributObjet(){ 
    	return attributObjet; 
    }
    public String getTypeQuete(){ 
    	return typeQuete; 
    }
    public boolean isEstSortie(){ 
    	return estSortie; 
    }
    public boolean isFouillée(){ 
    	return fouillée; 
    }
    public void setFouillée(boolean f){ 
    	this.fouillée = f; 
    }
    public List<Objet> getObjets(){ 
    	return Collections.unmodifiableList(objets); 
    }
    public List<Ennemi> getEnnemis(){ 
    	return Collections.unmodifiableList(ennemis); 
    }
    public List<PNJ> getPnjs(){ 
    	return Collections.unmodifiableList(pnjs); 
    }
    public List<String> getSecrets(){ 
    	return Collections.unmodifiableList(secrets); 
    }
}
