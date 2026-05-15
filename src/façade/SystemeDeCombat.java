package façade;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import model.Ennemi;
import model.Inventaire;
import model.Joueur;
import model.Objet;
import model.Piece;
import singleton.Gestionnaire;

public class SystemeDeCombat {
	private final Gestionnaire etat;
    private final Scanner sc;

    public SystemeDeCombat(Gestionnaire etat, Scanner sc) {
        this.etat = etat;
        this.sc = sc;
    }

    public void menerCombat(List<Ennemi> ennemis, Piece piece) {
        Joueur joueur = etat.getJoueur();
        afficherDebutCombat(ennemis);

        while (joueur.estVivant() && ennemis.stream().anyMatch(Ennemi::estVivant)) {
            List<Ennemi> vivants = ennemis.stream()
                .filter(Ennemi::estVivant)
                .collect(java.util.stream.Collectors.toList());

            afficherEtatCombat(joueur, vivants);
            boolean tourFini = false;

            while (!tourFini) {
                System.out.println();
                System.out.println(" -----VOTRE TOUR----- ");
                System.out.println(" [1] Attaquer un ennemi ");
                System.out.println(" [2] Voir les stats d'un ennemi ");
                System.out.println(" [3] Utiliser un objet ");
                System.out.println(" [4] Fuir le combat ");
                System.out.print(" > Choix : ");
                String choix = sc.nextLine().trim();

                switch (choix) {
                    case "1" -> {
                        Ennemi cible = choisirEnnemi(vivants);
                        if (cible != null) {
                            boolean vaincu = etat.executerCombat(joueur, cible);
                            if (vaincu) piece.retirerEnnemi(cible);
                            tourFini = true;
                        }
                    }
                    case "2" -> afficherStatsEnnemis(vivants);
                    case "3" -> {
                        boolean utilise = utiliserObjetCombat(joueur);
                        if (utilise) tourFini = true;
                    }
                    case "4" -> {
                        if (peutFuir()) {
                            System.out.println(" Vous fuyez le combat !");
                            return;
                        } else {
                            System.out.println(" Impossible de fuir face au boss !");
                        }
                    }
                    default -> System.out.println(" Option invalide.");
                }
            }

            if (joueur.estVivant()) {
                System.out.println();
                for (Ennemi e : vivants) {
                    if (e.estVivant()) {
                        System.out.println(e.getNom() + " attaque ! ");
                        e.attaquer(joueur);
                        if (!joueur.estVivant()) break;
                    }
                }
            }
        }

        if (!joueur.estVivant()) return;

        // Tous vaincus
        System.out.println();
        System.out.println(" -----COMBAT TERMINÉ – VICTOIRE !----- ");
        System.out.printf(" Ennemis vaincus : %-21s%n", etat.getCombat().getNbrEnnemi());
    }

    private void afficherDebutCombat(List<Ennemi> ennemis) {
        System.out.println();
        System.out.printf(" COMBAT !  %d ennemi(s) en vue !%-15s║%n",
            ennemis.size(), "");
        for (int i = 0; i < ennemis.size(); i++) {
            Ennemi e = ennemis.get(i);
            System.out.printf(" %d %-47s%n", i+1, 
            		e.getNom() + " – " + e.sePresenter().substring(0, Math.min(38, e.sePresenter().length())));
        }
    }

    private void afficherEtatCombat(Joueur joueur, List<Ennemi> vivants) {
        System.out.println();
        System.out.println(" État du combat ");
        System.out.printf(" %-20s PV: %-22s %n",
            joueur.getNom(), joueur.pvBarre());
        for (Ennemi e : vivants) {
            System.out.printf(" %-20s PV: %-22s %n",
                e.getNom(), e.pvBarre());
        }
    }

    private Ennemi choisirEnnemi(List<Ennemi> vivants) {
        if (vivants.size() == 1) {
            System.out.println(" Vous attaquez " + vivants.get(0).getNom());
            return vivants.get(0);
        }
        System.out.println(" Quel ennemi attaquer ?");
        for (int i = 0; i < vivants.size(); i++) {
            System.out.printf(" %d %s%n", i+1, vivants.get(i).getNom());
        }
        System.out.print(" > ");
        try {
            int idx = Integer.parseInt(sc.nextLine().trim()) - 1;
            if (idx >= 0 && idx < vivants.size()) return vivants.get(idx);
        } catch (NumberFormatException ignored) {}
        System.out.println(" Choix invalide.");
        return null;
    }

    private void afficherStatsEnnemis(List<Ennemi> vivants) {
        System.out.println(" Quel ennemi examiner ?");
        for (int i = 0; i < vivants.size(); i++) {
            System.out.printf(" %d %s%n", i+1, vivants.get(i).getNom());
        }
        System.out.print(" > ");
        try {
            int idx = Integer.parseInt(sc.nextLine().trim()) - 1;
            if (idx >= 0 && idx < vivants.size()) vivants.get(idx).afficherFiche();
            else System.out.println(" Choix invalide.");
        } catch (NumberFormatException ignored) {
            System.out.println(" Entrée invalide.");
        }
    }

    private boolean utiliserObjetCombat(Joueur joueur) {
        Inventaire inv = joueur.getInventaire();
        if (inv.estVide()) {
            System.out.println(" Inventaire vide.");
            return false;
        }
        inv.afficher();
        System.out.print(" > Nom de l'objet à utiliser (ou 'annuler') : ");
        String nom = sc.nextLine().trim();
        if (nom.equalsIgnoreCase("annuler")) return false;
        Optional<Objet> found = inv.trouver(nom);
        if (found.isEmpty()) { System.out.println(" Objet introuvable."); return false; }
        Objet o = found.get();
        o.utiliser(joueur);
        if (o.getTypeObjet() == Objet.TypeObjet.POTION) inv.retirer(nom);
        return true;
    }

    private boolean peutFuir() {
        // On ne peut pas fuir contre le boss
        return etat.getPieceCourante().getEnnemis().stream()
            .noneMatch(e -> e.getTypeEnnemi().equalsIgnoreCase("Boss") && e.estVivant());
    }
}
