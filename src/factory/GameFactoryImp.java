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
            case "licorne_bois" -> new Objet("Une petite licorne en bois", Objet.TypeObjet.MYTHIQUE, 10, "Moyen de transport invoquable", "Une petite licorne en bois touuuuuuuuuuuuuuuuute mignonne (les programmeuses étaient fatiguées ce jour)", 0);
            default -> throw new IllegalArgumentException("Objet inconnu : " + type);
        };
    }

    @Override
    public Ennemi creerEnnemi(String type, Difficulte diff) {
        double m = diff.getModificateurEnnemis();
        return switch (type.toLowerCase()) {
            case "gobelin" -> new Ennemi(
                "Gobelin", "Gobelin", "Un petit être vert malodorant.",
                "Grrr ! Intrus dans mon donjon !",
                (int)(25*m), (int)(4*m), (int)(4*m), 1,
                new AttaqueNormale(), new DeplacementAleatoire());
            case "gobelin_archer" -> new Ennemi(
                "Gobelin archer", "Gobelin", "Un gobelin avec un arc de fortune.",
                "Je vais te transpercer !",
                (int)(20*m), (int)(6*m), (int)(3*m), 1,
                new AttaqueNormale(), new DeplacementAleatoire());
            case "squelette" -> new Ennemi(
                "Squelette guerrier", "Mort-vivant", "Des os animés par une magie obscure.",
                "*cliquetis d'os*",
                (int)(35*m), (int)(6*m), (int)(6*m), 3,
                new AttaqueNormale(), new DeplacementImmobile());
            case "momie" -> new Ennemi(
                "Momie guerrière", "Mort-vivant", "Une momie en putréfaction recouverte de bandages éfilochés.",
                "*grognement guttural*",
                (int)(30*m), (int)(5*m), (int)(5*m), 3,
                new AttaqueNormale(), new DeplacementImmobile());
            case "troll" -> new Ennemi(
                "Troll des cavernes", "Troll", "Massif, puant, redoutablement fort.",
                "RAAAH ! Moi ÉCRASER !",
                (int)(60*m), (int)(9*m), (int)(12*m), 4,
                new AttaqueFurieuse(), new DeplacementAleatoire());
            case "araignee" -> new Ennemi(
                "Araignée géante", "Bête", "Ses crocs suintent de venin.",
                "*sifflement*",
                (int)(30*m), (int)(5*m), (int)(4*m), 2,
                new AttaquePoison(), new DeplacementAleatoire());
            case "garde_elite" -> new Ennemi(
                "Garde d'élite", "Humain", "Un soldat corrompu par les ténèbres.",
                "Tu ne passeras pas !",
                (int)(70*m), (int)(10*m), (int)(10*m), 6,
                new AttaqueFurieuse(), new DeplacementImmobile());
            case "boss" -> new Ennemi(
                "Seigneur des Ténèbres", "Boss", "La source de toute l'obscurité du donjon.",
                "Tu oses me défier ?! Ta vie prend fin ici, aventurier !",
                (int)(150*m), (int)(15*m), (int)(20*m), 8,
                new AttaqueFurieuse(), new DeplacementImmobile());
            default -> throw new IllegalArgumentException("Ennemi inconnu : " + type);
        };
    }
    
    @Override
    public Piece creerPiece(String type) {
        return switch (type.toLowerCase()) {
            case "entree" -> new Piece("Entrée du donjon",
                "Une grande porte en bois pourri grince derrière vous. La torche que vous portez vacille.",
                "gobelin", "torche", null, false);
            case "couloir_nord" -> new Piece("Couloir nord",
                "Un couloir humide et froid. Des traces de sang sèchent sur les murs de pierre.",
                "gobelin_archer", "epee", null, false);
            case "salle_garde" -> new Piece("Salle des gardes",
                "Des armures vides ornent les murs. Une odeur de métal rouillé flotte dans l'air.",
                "squelette", "armure_cuir", "eliminer_gardes", false);
            case "crypte" -> new Piece("Crypte oubliée",
                "Des sarcophages brisés jonchent le sol. Quelque chose bouge dans l'ombre.",
                "squelette", "parchemin", null, false);
            case "caverne" -> new Piece("Caverne des trolls",
                "Des stalactites immenses pendent du plafond. Un grondement sourd résonne.",
                "troll", "hache", "vaincre_troll", false);
            case "nid_araignees" -> new Piece("Nid d'araignées",
                "Des toiles épaisses recouvrent tout. Des cocons suspendus pendent des parois.",
                "araignee", "antidote", null, false);
            case "bibliotheque" -> new Piece("Bibliothèque maudite",
                "Des grimoires brûlés s'entassent sur des étagères brisées. Un PNJ fouille les ruines.",
                null, "arc", null, false);
            case "armurerie" -> new Piece("Armurerie secrète",
                "Un passage dérobé mène ici. Des armes de grande qualité sont encore utilisables.",
                null, "epee_maudite", null, false);
            case "salle_boss" -> new Piece("Trône des Ténèbres",
                "Une immense salle baignée de lumière rouge. Sur le trône siège le Seigneur des Ombres.",
                "boss", null, "vaincre_boss", false);
            case "sortie" -> new Piece("Pont-levis – Sortie",
                "La lumière du jour filtre à travers la herse rouillée. La liberté est à portée de main !",
                null, null, null, true);
            default -> throw new IllegalArgumentException("Pièce inconnue : " + type);
        };
    }
}
