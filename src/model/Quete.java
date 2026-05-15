package model;

public class Quete {
	public enum TypeQuete { 
		ELIMINER_ENNEMIS, RECUPERER_OBJET, ATTEINDRE_LIEU, PARLER_PNJ 
	}

    private final String titre;
    private final String description;
    private final TypeQuete type;
    private final Difficulte difficulte;
    private final String cible;      // nom ennemi / objet / pièce / pnj visé
    private boolean terminee = false;
    private boolean principale;  // si true, obligatoire pour finir le jeu

    public Quete(String titre, String description, TypeQuete type,
                 Difficulte difficulte, String cible, boolean principale) {
        this.titre = titre;
        this.description = description;
        this.type = type;
        this.difficulte = difficulte;
        this.cible = cible;
        this.principale = principale;
    }

    public void terminer() {
        if (terminee) return;
        terminee = true;
        System.out.println();
        System.out.println(" -----QUÊTE ACCOMPLIE !----- ");
        System.out.printf(" %-42s%n", titre);
        System.out.printf(" Difficulté : %-27s%n", difficulte.getNom());
    }

    public boolean verifierProgression(String eventNom) {
        if (terminee) return false;
        return cible.equalsIgnoreCase(eventNom);
    }

    public String getTitre(){ 
    	return titre; 
    }
    public String getDescription(){ 
    	return description; 
    }
    public TypeQuete getType(){ 
    	return type;
    }
    public Difficulte getDifficulte(){ 
    	return difficulte; 
    }
    public String getCible(){ 
    	return cible; 
    }
    public boolean isTerminee(){ 
    	return terminee; 
    }
    public boolean isPrincipale(){ 
    	return principale; 
    }

    @Override
    public String toString() {
        return String.format("  [%s] %-35s (%s)",
            terminee ? "✓" : " ", titre, difficulte.getNom());
    }
}
