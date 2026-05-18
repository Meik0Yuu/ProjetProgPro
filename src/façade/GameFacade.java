package façade;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import factory.GameFactoryImp;
import model.Difficulte;
import model.Ennemi;
import model.Inventaire;
import model.Joueur;
import model.Objet;
import model.PNJ;
import model.Piece;
import model.Quete;
import observer.PVJoueurs;
import singleton.Gestionnaire;

public class GameFacade {
	private final Gestionnaire etat = Gestionnaire.getInstance();
    private final GameFactoryImp fabrique = new GameFactoryImp();
    
    private Piece entree;
    
    public void demarrerPartie(String nomJoueur, Difficulte diff) {
        int pvBase = 100 + diff.getPvJoueurBonus();
        Joueur joueur = new Joueur(nomJoueur, pvBase, 10, 5, 8);
        joueur.ajouterObservateur(new PVJoueurs());

        construireMonde(diff);
        etat.nouvellePartie(joueur, entree);
        ajouterQuetes();

        afficherBanniere();
        etat.getPieceCourante().afficher();
        afficherActionsDisponibles();
    }

    private void construireMonde(Difficulte diff) {
        Piece e = fabrique.creerPiece("entree");
        Piece cn = fabrique.creerPiece("couloir_nord");
        Piece sg = fabrique.creerPiece("salle_garde");
        Piece cr = fabrique.creerPiece("crypte");
        Piece cav = fabrique.creerPiece("caverne");
        Piece na = fabrique.creerPiece("nid_araignees");
        Piece bib = fabrique.creerPiece("bibliotheque");
        Piece arm = fabrique.creerPiece("armurerie");
        Piece boss = fabrique.creerPiece("salle_boss");
        Piece sort = fabrique.creerPiece("sortie");

        // Directions
        e.ajouterSortie(Piece.Direction.NORD, cn);
        e.ajouterSortie(Piece.Direction.EST, na);
        cn.ajouterSortie(Piece.Direction.SUD, e);
        cn.ajouterSortie(Piece.Direction.NORD, sg);
        cn.ajouterSortie(Piece.Direction.EST, cr);
        sg.ajouterSortie(Piece.Direction.SUD, cn);
        sg.ajouterSortie(Piece.Direction.OUEST, cav);
        cr.ajouterSortie(Piece.Direction.OUEST, cn);
        cr.ajouterSortie(Piece.Direction.NORD, bib);
        na.ajouterSortie(Piece.Direction.OUEST, e);
        na.ajouterSortie(Piece.Direction.NORD, arm);
        bib.ajouterSortie(Piece.Direction.SUD, cr);
        bib.ajouterSortie(Piece.Direction.NORD, boss);
        arm.ajouterSortie(Piece.Direction.SUD, na);
        cav.ajouterSortie(Piece.Direction.EST, sg);
        boss.ajouterSortie(Piece.Direction.SUD, bib);
        boss.ajouterSortie(Piece.Direction.NORD, sort);
        sort.ajouterSortie(Piece.Direction.SUD, boss);

        // Peuplement ennemis
        e.ajouterEnnemi(fabrique.creerEnnemi("gobelin", diff));
        cn.ajouterEnnemi(fabrique.creerEnnemi("gobelin_archer",diff));
        sg.ajouterEnnemi(fabrique.creerEnnemi("squelette", diff));
        sg.ajouterEnnemi(fabrique.creerEnnemi("squelette", diff));
        cr.ajouterEnnemi(fabrique.creerEnnemi("squelette", diff));
        cr.ajouterEnnemi(fabrique.creerEnnemi("momie", diff));
        na.ajouterEnnemi(fabrique.creerEnnemi("araignee", diff));
        na.ajouterEnnemi(fabrique.creerEnnemi("araignee", diff));
        cav.ajouterEnnemi(fabrique.creerEnnemi("troll", diff));
        bib.ajouterEnnemi(fabrique.creerEnnemi("garde_elite", diff));
        bib.ajouterEnnemi(fabrique.creerEnnemi("momie", diff));
        boss.ajouterEnnemi(fabrique.creerEnnemi("boss", diff));

        // Peuplement objets
        e.ajouterObjet(fabrique.creerObjet("licorne_bois"));
        e.ajouterObjet(fabrique.creerObjet("epee_bois"));
        e.ajouterObjet(fabrique.creerObjet("torche"));
        e.ajouterObjet(fabrique.creerObjet("potion_soin"));
        cn.ajouterObjet(fabrique.creerObjet("epee"));
        sg.ajouterObjet(fabrique.creerObjet("armure_cuir"));
        sg.ajouterObjet(fabrique.creerObjet("cle_fer"));
        cr.ajouterObjet(fabrique.creerObjet("parchemin"));
        cr.ajouterObjet(fabrique.creerObjet("antidote"));
        na.ajouterObjet(fabrique.creerObjet("herbe"));
        na.ajouterObjet(fabrique.creerObjet("flacon_vide"));
        bib.ajouterObjet(fabrique.creerObjet("arc"));
        bib.ajouterObjet(fabrique.creerObjet("potion_grande"));
        arm.ajouterObjet(fabrique.creerObjet("epee_maudite"));
        arm.ajouterObjet(fabrique.creerObjet("bouclier"));
        cav.ajouterObjet(fabrique.creerObjet("hache"));
        boss.ajouterObjet(fabrique.creerObjet("licorne_bois"));

        // PNJs
        PNJ sage = new PNJ("Sage Aldric", "Sage",
            "Un vieillard aux habits déchirés et couverts de suie, tapi dans les ruines. Ses yeux vifs trahissent une intelligence encore bien aiguisée malgré les années.",
            "Aventurier ! Le Seigneur des Ténèbres... il réside au nord de la bibliothèque.",
            "La clé dorée... le garde d'élite la possède. Du moins c'est ce qu'on murmure.",
            "L'Est est maudit. Le nid d'araignées... leur venin est traître, je te préviens !");
        sage.setObjetCadeau(fabrique.creerObjet("cle_or"));
        bib.ajouterPNJ(sage);

        PNJ marchande = new PNJ("Marchande Éléa", "Marchande",
            "Une femme robuste et déterminée, réfugiée dans un recoin sombre de la caverne. Ses affaires sont disposées à la hâte sur un vieux tissu élimé.",
            "Psst ! J'ai pu sauver des pilleurs... tu es intéressé ?",
            "Le troll de la caverne... il protège un passage secret vers l'armurerie. Méfie-toi.",
            "Bonne chance, aventurier. Tu en auras besoin.");
        cav.ajouterPNJ(marchande);

        // Secrets
        arm.ajouterSecret("Un panneau mural coulissant révèle une alcôve dissimulée. À l'intérieur, un vieux coffre poussiéreux contient une potion aux reflets dorés, intacte !");
        cr.ajouterSecret("Derrière un sarcophage déplacé, une inscription gravée profondément dans la pierre : 'La lumière vainc l'ombre.' Les lettres semblent briller faiblement dans le noir.");

        this.entree = e;
    }

    private void ajouterQuetes() {
        etat.ajouterQuete(new Quete(
            "Première lame", 
            "Trouvez une arme dans le couloir nord.",
            Quete.TypeQuete.RECUPERER_OBJET, 
            new Difficulte.Facile(), 
            "Épée rouillée", 
            false));
        etat.ajouterQuete(new Quete(
            "Extermination gobelin",
            "Éliminez le gobelin de l'entrée.",
            Quete.TypeQuete.ELIMINER_ENNEMIS, 
            new Difficulte.Facile(), 
            "Gobelin", 
            false));
        etat.ajouterQuete(new Quete(
            "Nettoyage des gardes",
            "Éliminez tous les squelettes de la salle des gardes.",
            Quete.TypeQuete.ELIMINER_ENNEMIS, 
            new Difficulte.Normal(), 
            "Mort-vivant", 
            false));
        etat.ajouterQuete(new Quete(
            "Conseil du Sage",
            "Parlez au Sage Aldric dans la bibliothèque.",
            Quete.TypeQuete.PARLER_PNJ, 
            new Difficulte.Facile(), 
            "Sage Aldric", 
            false));
        etat.ajouterQuete(new Quete(
            "Fin des Ténèbres",
            "Vaincre le Seigneur des Ténèbres.",
            Quete.TypeQuete.ELIMINER_ENNEMIS, 
            new Difficulte.Difficile(), 
            "Boss", 
            true));
        etat.ajouterQuete(new Quete(
            "Liberté !",
            "Rejoignez la sortie du donjon.",
            Quete.TypeQuete.ATTEINDRE_LIEU, 
            new Difficulte.Normal(), 
            "Pont-levis – Sortie", 
            true));
    }
    
    public boolean allerDirection(String dirStr) {
        try {
            Piece.Direction dir = Piece.Direction.valueOf(dirStr.toUpperCase());
            boolean ok = etat.deplacer(dir);
            if (ok) {
                etat.getPieceCourante().afficher();
                verifierVictoire();
                if (!etat.getPieceCourante().isEstSortie())
                    afficherActionsDisponibles();
            }
            return ok;
        } catch (IllegalArgumentException ex) {
            System.out.println(" Direction inconnue : " + dirStr
                + " (nord/sud/est/ouest/haut/bas)");
            return false;
        }
    }

    public void fouiller() {
        Piece p = etat.getPieceCourante();
        System.out.println("\n Vous fouillez la pièce...");
        if (p.isFouillée()) {
            System.out.println(" Vous avez déjà fouillé ici. Il ne reste rien.");
            return;
        }
        p.setFouillée(true);
        if (p.getObjets().isEmpty() && p.getSecrets().isEmpty()) {
            System.out.println(" Rien d'intéressant ici.");
        } else {
            if (!p.getObjets().isEmpty()) {
                System.out.println(" Objets trouvés :");
                p.getObjets().forEach(o -> System.out.println(" -> " + o.getNom() + " – " + o.getDescription()));
            }
            if (!p.getSecrets().isEmpty()) {
                System.out.println(" Passage secret découvert !");
                p.getSecrets().forEach(s -> System.out.println(" -> " + s));
            }
        }
    }

    public void ramasserObjet(String nom) {
        Piece p = etat.getPieceCourante();
        List<Objet> objets = new ArrayList<>(p.getObjets());
        Optional<Objet> found = objets.stream()
            .filter(o -> o.getNom().equalsIgnoreCase(nom)).findFirst();
        if (found.isEmpty()) {
            System.out.println(" Objet introuvable ici : " + nom);
            return;
        }
        Objet o = found.get();
        o.ramasser(etat.getJoueur());
        p.retirerObjet(o);
        etat.signalerObjetRecupere(o.getNom());
    }

    public void examinerObjet(String nom) {
        Piece p = etat.getPieceCourante();
        Optional<Objet> inRoom = p.getObjets().stream()
            .filter(o -> o.getNom().equalsIgnoreCase(nom)).findFirst();
        Optional<Objet> inInv  = etat.getJoueur().getInventaire().trouver(nom);
        Objet o = inRoom.or(() -> inInv).orElse(null);
        if (o == null) { System.out.println(" Objet introuvable : " + nom); return; }
        o.interagir(etat.getJoueur());
    }

    public void utiliserObjet(String nom) {
        Inventaire inv = etat.getJoueur().getInventaire();
        Optional<Objet> found = inv.trouver(nom);
        if (found.isEmpty()) { System.out.println(" Pas de '" + nom + "' dans l'inventaire."); return; }
        Objet o = found.get();
        o.utiliser(etat.getJoueur());
        if (o.getTypeObjet() == Objet.TypeObjet.POTION) {
        	inv.retirer(nom);
        } else if (o.getTypeObjet() == Objet.TypeObjet.MYTHIQUE) {
            System.out.println(" Où voulez-vous vous téléporter ?");
            toutesLesPieces().forEach(p -> System.out.println(" - " + p.getNom()));
            System.out.print(" > ");
            String choix = new Scanner(System.in).nextLine();
            Piece destination = trouverPieceParNom(choix);
            etat.setPieceCourante(destination);
            System.out.println(" Vous apparaissez dans : " + destination.getNom());
        }
    }
    private Piece trouverPieceParNom(String nom) {
        return etat.trouverPieceParNom(nom); 
    }

    private List<Piece> toutesLesPieces() {
        return etat.toutesLesPieces(); 
    }

    public void lancerCombat(Scanner sc) {
        Piece p = etat.getPieceCourante();
        List<Ennemi> ennemis = p.getEnnemis().stream()
            .filter(Ennemi::estVivant).collect(java.util.stream.Collectors.toList());
        if (ennemis.isEmpty()) {
            System.out.println("Aucun ennemi dans cette pièce.");
            return;
        }
        new SystemeDeCombat(etat, sc).menerCombat(ennemis, p);
        verifierVictoire();
    }

    public void parlerPNJ(String nom) {
        Optional<PNJ> found = etat.getPieceCourante().getPnjs().stream()
            .filter(pnj -> pnj.getNom().equalsIgnoreCase(nom)).findFirst();
        if (found.isEmpty()) { System.out.println(" Pas de PNJ nommé '" + nom + "' ici."); return; }
        found.get().parler(etat.getJoueur());
        etat.signalerPNJParle(found.get().getNom());
    }

    public void afficherInventaire() {
    	System.out.println();
        System.out.println(" -----INVENTAIRE----- ");
        etat.getJoueur().getInventaire().afficher();
    }

    public void afficherStatut() { 
    	etat.getJoueur().afficherStats(); 
    }
    public void afficherQuetes() { 
    	etat.afficherQuetes(); 
    }
    public void sauvegarder() { 
    	etat.sauvegarder(); 
    }
    
    public void charger() {
    	etat.charger();
    }
    
    private void verifierVictoire() {
        Joueur j = etat.getJoueur();
        if (!j.estVivant()) {
            afficherGameOver();
            etat.setEnCours(false);
            return;
        }
        if (etat.verifierVictoire()) {
            afficherVictoire();
            etat.setEnCours(false);
        }
    }

    private void afficherVictoire() {
    	System.out.println();
        System.out.println(" -----FÉLICITATIONS, AVENTURIER----- ");
        System.out.println(" Vous avez vaincu le Seigneur des Ténèbres et réussi à vous échapper du donjon ! ");
        System.out.printf(" Tours joués : %-31s%n", etat.getTourActuel());
        System.out.printf(" Ennemis vaincus : %-30s%n", etat.getCombat().getNbrEnnemi());
    }

    private void afficherGameOver() {
    	System.out.println();
        System.out.println(" -----GAME OVER----- ");
        System.out.println(etat.getJoueur().getNom() + " a succombé dans le donjon.");
        System.out.println( etat.getTourActuel());
    }

    public void afficherActionsDisponibles() {
        Piece p = etat.getPieceCourante();
        System.out.println();
        System.out.println(" Que voulez-vous faire ?");
        System.out.println(" [fouiller] -> Fouiller la pièce");
        if (p.aDesEnnemisVivants())
            System.out.println(" [combattre] -> Engager le combat");
        if (!p.getObjets().isEmpty())
            System.out.println(" [prendre <objet>] -> Ramasser un objet");
        if (!p.getPnjs().isEmpty())
            System.out.println(" [parler <pnj>] -> Parler à un personnage");
        if (!p.getSorties().isEmpty()) {
            p.getSorties().forEach((dir, dest) ->
                System.out.printf(" [aller %s] -> %s%n",
                    dir.toString().toLowerCase(),
                    dest.getNom()));
        }
        System.out.println(" [inventaire] -> Voir l'inventaire");
        System.out.println(" [statut] -> Voir vos statistiques");
        System.out.println(" [quetes] -> Journal de quêtes");
        System.out.println(" [examiner <objet>] -> Examiner un objet");
        System.out.println(" [utiliser <objet>] -> Utiliser un objet (inventaire)");
        System.out.println(" [sauvegarder] -> Sauvegarder la partie");
        System.out.println(" [charger] -> Charger une partie");
        System.out.println(" [aide] -> Afficher cette aide");
        System.out.println(" [quitter] -> Quitter la partie");
    }

    private void afficherBanniere() {
        System.out.println();
        System.out.println(" -----DONJON DES OMBRES----- ");
        System.out.println(" Un seigneur obscur a étendu sa corruption sur ces terres oubliées. Les ombres sont vivantes. Les murs ont une mémoire.");
        System.out.println(" Explorez. Combattez. Survivez. Et surtout... ne fuyez pas avant d'avoir mis fin à son règne.");
        System.out.println(" Bonne chnace. Vous en aurez besoin.");
    }

    public boolean isEnCours() { 
    	return etat.isEnCours(); 
    }
}
