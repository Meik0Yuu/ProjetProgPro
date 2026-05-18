package other;

import java.util.Scanner;

import façade.GameFacade;
import model.Difficulte;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
        GameFacade jeu = new GameFacade();

        System.out.println(" -----DONJON DES OMBRES----- ");
        System.out.println(" Jeu d'aventure textuel ");

        System.out.print("\n Entrez votre nom d'aventurier : ");
        String nom = sc.nextLine().trim();
        if (nom.isBlank()) nom = "Héros";

        Difficulte diff = null;
        while (diff == null) {
            System.out.println(" Choisissez une difficulté :");
            System.out.println(" [1] Facile – ennemis affaiblis, PV bonus");
            System.out.println(" [2] Normal – expérience équilibrée");
            System.out.println(" [3] Difficile – ennemis renforcés");
            System.out.print(" > ");
            switch (sc.nextLine().trim()) {
                case "1" -> diff = new Difficulte.Facile();
                case "2" -> diff = new Difficulte.Normal();
                case "3" -> diff = new Difficulte.Difficile();
                default  -> System.out.println(" Entrez 1, 2 ou 3.");
            }
        }

        jeu.demarrerPartie(nom, diff);

        while (jeu.isEnCours()) {
            System.out.print("\n > ");
            String ligne = sc.nextLine().trim();
            if (ligne.isBlank()) continue;

            String[] parts = ligne.split("\\s+", 2);
            String cmd = parts[0].toLowerCase();
            String arg = parts.length > 1 ? parts[1] : "";

            switch (cmd) {
                // Navigation
                case "aller" -> jeu.allerDirection(arg);
                case "nord","n" -> jeu.allerDirection("nord");
                case "sud","s" -> jeu.allerDirection("sud");
                case "est","e" -> jeu.allerDirection("est");
                case "ouest","o" -> jeu.allerDirection("ouest");
                case "haut" -> jeu.allerDirection("haut");
                case "bas" -> jeu.allerDirection("bas");

                // Exploration
                case "fouiller","f" -> jeu.fouiller();
                case "prendre","p" -> {
                    if (arg.isBlank()) System.out.println(" Syntax : prendre <nom objet>");
                    else jeu.ramasserObjet(arg);
                }
                case "examiner","ex" -> {
                    if (arg.isBlank()) System.out.println(" Syntax : examiner <nom objet>");
                    else jeu.examinerObjet(arg);
                }
                
                case "combattre","c","attaquer" -> jeu.lancerCombat(sc);

                case "inventaire","i" -> jeu.afficherInventaire();
                case "utiliser","u" -> {
                    if (arg.isBlank()) System.out.println(" Syntax : utiliser <nom objet>");
                    else jeu.utiliserObjet(arg);
                }

                case "parler" -> {
                    if (arg.isBlank()) System.out.println(" Syntax : parler <nom pnj>");
                    else jeu.parlerPNJ(arg);
                }

                case "statut", "st" -> jeu.afficherStatut();
                case "quetes", "qt" -> jeu.afficherQuetes();
                case "aide" -> jeu.afficherActionsDisponibles();

                case "sauvegarder", "save" -> jeu.sauvegarder();

                case "quitter" -> {
                    System.out.println(" À bientôt, aventurier !");
                    return;
                }
                
                case "charger", "load" -> jeu.charger();

                default -> System.out.println(" Commande inconnue. Tapez [aide] pour la liste.");
            }
        }

        sc.close();
	}

}
