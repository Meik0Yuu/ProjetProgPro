package model;


public class PNJ extends Personnage{
	private String[] dialogues;
    private int indexDialogue = 0;
    private Objet cadeauPossible;    // null si pas de cadeau

    public PNJ(String nom, String role, String description, String... dialogues) {
        super(nom, role, description);
        this.dialogues = dialogues.length > 0 ? dialogues : new String[]{"..."};
    }

    @Override
    public String sePresenter() {
        return getNom() + " dit : \"" + dialogueActuel() + "\"";
    }

    public void parler(Joueur joueur) {
        String d = dialogueActuel();
        System.out.println("\n " + getNom() + " : \"" + d + "\"");
        avancerDialogue();
        if (cadeauPossible != null) {
            System.out.println(" -> " + getNom() + " vous tend : " + cadeauPossible.getNom());
            joueur.getInventaire().ajouter(cadeauPossible);
            cadeauPossible = null;
        }
    }

    public void setObjetCadeau(Objet o){ 
    	this.cadeauPossible = o; 
    }

    private String dialogueActuel(){ 
    	return dialogues[indexDialogue]; 
    }
    private void avancerDialogue(){
        indexDialogue = (indexDialogue + 1) % dialogues.length;
    }

    public String getDialogue(){ 
    	return dialogueActuel(); 
    }
}
