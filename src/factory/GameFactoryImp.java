package factory;

import model.Difficulte;
import model.Ennemi;
import model.Objet;
import model.Piece;
import strategy.AttaqueFurieuse;
import strategy.AttaqueNormale;
import strategy.AttaquePoison;
import strategy.DeplacementAleatoire;
import strategy.DeplacementImmobile;

public class GameFactoryImp implements GameFactory{
	public Objet creerObjet(String type) {
        return switch (type.toLowerCase()) {
        	case "epee_bois" -> new Objet("Épée en bois", Objet.TypeObjet.ARME, 1, "+4 dégâts", "Une petite épée de bois que vous avez gardé de vos jeunes années", 2);
            case "epee" -> new Objet("Épée rouillée", Objet.TypeObjet.ARME, 2, "+8 dégâts", "Une vieille épée ébréchée mais encore efficace.", 8);
            case "epee_maudite"  -> new Objet("Épée maudite", Objet.TypeObjet.ARME, 3, "+18 dégâts", "Forgée dans l'ombre, elle pulse d'énergie sombre.", 18);
            case "arc" -> new Objet("Arc elfique", Objet.TypeObjet.ARME, 2, "+12 dégâts", "Léger et précis, taillé dans le bois de lune.", 12);
            case "hache" -> new Objet("Hache de guerre", Objet.TypeObjet.ARME, 4, "+15 dégâts", "Lourde mais dévastatrice.", 15);
            case "potion_soin" -> new Objet("Potion de soin", Objet.TypeObjet.POTION, 1, "+25 PV", "Un liquide rouge qui sent la cerise.", 25);
            case "potion_grande" -> new Objet("Grande potion", Objet.TypeObjet.POTION, 1, "+50 PV", "Un flacon épais, bouillonnant d'énergie vitale.", 50);
            case "antidote" -> new Objet("Antidote", Objet.TypeObjet.POTION, 1, "Cure poison", "Dissipe immédiatement tout empoisonnement.", 0);
            case "cle_fer" -> new Objet("Clé en fer", Objet.TypeObjet.CLE, 1, "Ouvre la salle gardée", "Froide au toucher, elle porte une rune.", 0);
            case "cle_or" -> new Objet("Clé dorée", Objet.TypeObjet.CLE, 1, "Ouvre la salle du boss", "Brillante, gravée d'un crâne ailé.", 0);
            case "armure_cuir" -> new Objet("Armure de cuir", Objet.TypeObjet.ARMURE, 3, "+5 défense", "Souple et résistante.", 5);
            case "bouclier" -> new Objet("Bouclier en bois", Objet.TypeObjet.ARMURE, 4, "+8 défense", "Renforcé de bandes de métal.", 8);
            case "parchemin" -> new Objet("Parchemin mystérieux", Objet.TypeObjet.DIVERS, 1, "Indice", "Des symboles anciens dansent sur le papier jauni.", 0);
            case "torche" -> new Objet("Torche", Objet.TypeObjet.DIVERS, 1, "Éclaire", "Indispensable dans les couloirs sombres.", 0);
            case "herbe" -> new Objet("Herbe médicinale", Objet.TypeObjet.COMBINABLE, 1, "+10 PV cru","Des feuilles aux propriétés curatives.", 10);
            case "flacon_vide" -> new Objet("Flacon vide", Objet.TypeObjet.COMBINABLE, 1, "Contenant", "Un flacon en verre soufflé.", 0);
            default -> throw new IllegalArgumentException("Objet inconnu : " + type);
        };
    }

    @Override
    public Ennemi creerEnnemi(String type, Difficulte diff) {
        double m = diff.getModificateurEnnemis();
        return switch (type.toLowerCase()) {
            case "gobelin" -> new Ennemi(
                "Gobelin", "Gobelin", "Petit monstre laid et plein de malice.",
                "Grrrk... Hihihi... HI HI HI !",
                (int)(25*m), (int)(4*m), (int)(4*m), 1,
                new AttaqueNormale(), new DeplacementAleatoire());
            case "gobelin_archer" -> new Ennemi(
                "Gobelin archer", "Gobelin", "Gobelin plus intelligent doué de parole, armé d'un arc et de flèches.",
                "Je vais te transpercer !",
                (int)(20*m), (int)(6*m), (int)(3*m), 1,
                new AttaqueNormale(), new DeplacementAleatoire());
            case "squelette" -> new Ennemi(
                "Squelette guerrier", "Mort-vivant", "Mort-vivant revenu à la vie par magie noire, armé et animé d'une volonté de combattre.",
                "Clac... clac.. CLAC CLAC CLAC !",
                (int)(35*m), (int)(6*m), (int)(6*m), 3,
                new AttaqueNormale(), new DeplacementImmobile());
            case "momie" -> new Ennemi(
                "Momie guerrière", "Mort-vivant", "Mort-vivant de type égyptienne, ancien combattant embaumé et maudit, ressuscité par magie noire pour assouvir une vengeance.",
                "Uuuuuuuhhh... krrrhhh... UUUHHH !",
                (int)(30*m), (int)(5*m), (int)(5*m), 3,
                new AttaqueNormale(), new DeplacementImmobile());
            case "troll" -> new Ennemi(
                "Troll des cavernes", "Troll", "Une massive créature brutale et primitive tapie dans les profondeurs de la terre, dotée d'une force colossale et d'une peau épaisse comme de la roche.",
                "GROOAAH... ROUMF... GRAAAAH !",
                (int)(60*m), (int)(9*m), (int)(12*m), 4,
                new AttaqueFurieuse(), new DeplacementAleatoire());
            case "araignee" -> new Ennemi(
                "Araignée géante", "Bête", "Créature cauchemardesque aux proportions monstrueuses, tisseuse de toiles mortelles, paralyse ses proies avec un venin foudroyant.",
                "Tssssssss... tsssssss... TSSSKRIIII !",
                (int)(30*m), (int)(5*m), (int)(4*m), 2,
                new AttaquePoison(), new DeplacementAleatoire());
            case "garde_elite" -> new Ennemi(
                "Garde d'élite", "Humain", "Guerrier humain redoutable corrompu par la magie noire.",
                "Halt ! ...EN GARDE !",
                (int)(70*m), (int)(10*m), (int)(10*m), 6,
                new AttaqueFurieuse(), new DeplacementImmobile());
            case "boss" -> new Ennemi(
                "Seigneur des Ténèbres", "Boss", "Etre maléfique d'une puissance absolue, maître de la magie noire dont la seule présence répand la terreur et corruption autour de lui. C'est la source de toute l'obscurité du donjon.",
                "...Toi vulgaire humain.. TU OSE ME DEFIER !?? TA VIE PREND FIN ICI !",
                (int)(150*m), (int)(15*m), (int)(20*m), 8,
                new AttaqueFurieuse(), new DeplacementImmobile());
            default -> throw new IllegalArgumentException("Ennemi inconnu : " + type);
        };
    }
    
    @Override
    public Piece creerPiece(String type) {
        return switch (type.toLowerCase()) {
            case "entree" -> new Piece("Entrée du donjon",
                "Une massive porte en vieux chêne noirci et couverte de griffures profondes laissées par d'innombrables créatures. Vous sentez un souffle glacé venu des profondeurs du donjon venir vers vous, votre lampe vacille !",
                "gobelin", "torche", null, false);
            case "couloir_nord" -> new Piece("Couloir nord",
                "Un couloir étroit, glacial, les murs de pierre couverts de traînées de sang séché.",
                "gobelin_archer", "epee", null, false);
            case "salle_garde" -> new Piece("Salle des gardes",
                "Des armures vides ornent les murs de pierre. Une odeur de métal rouillé flotte dans l'air.",
                "squelette", "armure_cuir", "eliminer_gardes", false);
            case "crypte" -> new Piece("Crypte oubliée",
                "Des sarcophages éventrés gisent sur le sol, leurs couvercles de pierre fracassés. Dans l'obscurité du fond, une silhouette se déplace lentement.",
                "squelette", "parchemin", null, false);
            case "caverne" -> new Piece("Caverne des trolls",
                "Des stalactites immenses pendent du plafond dans l'obscurité. Un grondement sourd résonne.",
                "troll", "hache", "vaincre_troll", false);
            case "nid_araignees" -> new Piece("Nid d'araignées",
                "Un couloir entièrement tapissé de toiles épaisses et collantes, des cocons blanchâtres suspendus au plafond contenant des formes immobiles.",
                "araignee", "antidote", null, false);
            case "bibliotheque" -> new Piece("Bibliothèque maudite",
                "Des grimoires brûlés s'entassent sur des étagères brisées. Un personnage fouille fébrilement les ruines, retournant les livres calcinés un à un dans le silence.",
                null, "arc", null, false);
            case "armurerie" -> new Piece("Armurerie secrète",
                "Un passage dérobé mène à cette salle oubliée. Des armes de grande qualité reposent sur des râteliers intacts, comme si le temps ne les avait pas touchées.",
                null, "epee_maudite", null, false);
            case "salle_boss" -> new Piece("Trône des Ténèbres",
                "Une immense salle baignée d'une lumière rouge sang. Au fond, sur un trône de pierre noire, le Seigneur des Ombres attend, immobile, comme s'il savait que vous veniez.",
                "boss", null, "vaincre_boss", false);
            case "sortie" -> new Piece("Pont-levis – Sortie",
                "La lumière du jour filtre à travers la herse rouillée, projetant de longs rayons dorés sur le sol de pierre. La liberté est à portée de main.",
                null, null, null, true);
            default -> throw new IllegalArgumentException("Pièce inconnue : " + type);
        };
    }
}
