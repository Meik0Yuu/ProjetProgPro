package singleton;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import model.ConditionDeVictoire;
import model.Ennemi;
import model.Joueur;
import model.Piece;
import model.Quete;
import model.Combat;
import singleton.Gestionnaire;

public class Gestionnaire {
	private static Gestionnaire instance;
    private Gestionnaire() {
        this.combat = new Combat();
        this.conditionVictoire = new ConditionDeVictoire();
    }
    public static Gestionnaire getInstance() {
        if (instance == null) instance = new Gestionnaire();
        return instance;
    }

    private Joueur joueur;
    private Piece pieceCourante;
    private final List<Quete> quetes = new ArrayList<>();
    private boolean enCours = false;
    private int tourActuel = 0;
    private final Combat combat;
    private final ConditionDeVictoire conditionVictoire;

    public void nouvellePartie(Joueur j, Piece depart) {
        this.joueur = j;
        this.pieceCourante = depart;
        quetes.clear();
        enCours = true;
        tourActuel = 0;
    }

    public void avancerTour() {
        tourActuel++;
        joueur.appliquerEffetEtat();
    }

    public boolean executerCombat(Joueur j, Ennemi e) {
        boolean vaincu = combat.executerTour(j, e);
        if (vaincu) {
            for (Quete q : quetes) {
                if (!q.isTerminee() && q.getType() == Quete.TypeQuete.ELIMINER_ENNEMIS
                        && q.verifierProgression(e.getTypeEnnemi())) {
                    q.terminer();
                }
            }
            if (e.getTypeEnnemi().equalsIgnoreCase("Boss")) {
                conditionVictoire.signalerBossVaincu();
                for (Quete q : quetes) {
                    if (!q.isTerminee() && q.getType() == Quete.TypeQuete.ELIMINER_ENNEMIS
                            && q.getCible().equalsIgnoreCase("Boss")) {
                        q.terminer();
                    }
                }
            }
        }
        return vaincu;
    }

    public boolean deplacer(Piece.Direction dir) {
        Piece suivante = pieceCourante.getSortie(dir);
        if (suivante == null) {
            System.out.println(" Aucune sortie dans cette direction.");
            return false;
        }
        if (suivante.isEstSortie() && !conditionVictoire.peutSortir()) {
            System.out.println(" La sortie est verrouillée par une force obscure.");
            System.out.println(" Vous devez vaincre le Seigneur des Ombres avant de pouvoir sortir !");
            return false;
        }
        pieceCourante = suivante;
        avancerTour();
        if (suivante.isEstSortie()) {
            conditionVictoire.signalerSortieAtteinte();
        }
        for (Quete q : quetes) {
            if (!q.isTerminee() && q.getType() == Quete.TypeQuete.ATTEINDRE_LIEU
                    && q.verifierProgression(suivante.getNom())) {
                q.terminer();
            }
        }
        return true;
    }

    public boolean verifierVictoire() {
        return conditionVictoire.estVictoire(quetes);
    }

    public void ajouterQuete(Quete q) { quetes.add(q); }

    public void signalerObjetRecupere(String nomObjet) {
        for (Quete q : quetes) {
            if (!q.isTerminee() && q.getType() == Quete.TypeQuete.RECUPERER_OBJET
                    && q.verifierProgression(nomObjet)) {
                q.terminer();
            }
        }
    }

    public void signalerPNJParle(String nomPNJ) {
        for (Quete q : quetes) {
            if (!q.isTerminee() && q.getType() == Quete.TypeQuete.PARLER_PNJ
                    && q.verifierProgression(nomPNJ)) {
                q.terminer();
            }
        }
    }

    public void afficherQuetes() {
        System.out.println(" -----JOURNAL DE QUÊTES----- ");
        for (Quete q : quetes) System.out.println(q);
    }

    private static final String SAVE_FILE = "sauvegarde.txt";

    public void sauvegarder() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(SAVE_FILE))) {
            pw.println(joueur.getNom());
            pw.println(joueur.getPv());
            pw.println(joueur.getPvMax());
            pw.println(joueur.getForce());
            pw.println(joueur.getDefense());
            pw.println(joueur.getCapaciteAttaque());
            pw.println(pieceCourante.getNom());
            pw.println(tourActuel);
            pw.println(combat.getNbrEnnemi());
            pw.println(conditionVictoire.isBossVaincu());
            System.out.println(" Partie sauvegardée dans '" + SAVE_FILE + "'.");
        } catch (IOException e) {
            System.err.println(" Erreur de sauvegarde : " + e.getMessage());
        }
    }

    public String[] chargerDonnees() {
        try (BufferedReader br = new BufferedReader(new FileReader(SAVE_FILE))) {
            String[] data = br.lines().toArray(String[]::new);
            System.out.println(" Sauvegarde chargée.");
            return data;
        } catch (IOException e) {
            System.err.println(" Impossible de charger : " + e.getMessage());
            return null;
        }
    }
    public void charger() {
        String[] data = chargerDonnees();
        if (data == null || data.length < 10) {
            System.err.println(" Sauvegarde invalide ou incomplète.");
            return;
        }

        // Restaurer le joueur
        joueur.setNom(data[0]);
        joueur.setPv(Integer.parseInt(data[1]));
        joueur.setPvMax(Integer.parseInt(data[2]));
        joueur.setForce(Integer.parseInt(data[3]));
        joueur.setDefense(Integer.parseInt(data[4]));
        joueur.setCapaciteAttaque(Integer.parseInt(data[5]));

        // Restaurer la pièce courante
        String nomPiece = data[6];
        pieceCourante = trouverPieceParNom(nomPiece); // voir ci-dessous

        // Restaurer le reste
        tourActuel = Integer.parseInt(data[7]);
        combat.setNbrEnnemi(Integer.parseInt(data[8]));
        conditionVictoire.setBossVaincu(Boolean.parseBoolean(data[9]));

        System.out.println(" Partie chargée !");
    }
    
    private Piece trouverPieceParNom(String nom) {
        Set<Piece> visitees = new HashSet<>();
        Queue<Piece> file = new LinkedList<>();
        file.add(pieceCourante); // point de départ

        while (!file.isEmpty()) {
            Piece p = file.poll();
            if (visitees.contains(p)) continue;
            visitees.add(p);

            if (p.getNom().equals(nom)) return p;

            // Ajouter les voisins
            for (Piece voisine : p.getSorties().values()) {
                if (!visitees.contains(voisine)) {
                    file.add(voisine);
                }
            }
        }

        System.err.println(" Pièce introuvable : " + nom);
        return pieceCourante; // fallback
    }

    public Joueur getJoueur(){
    	return joueur; 
    }
    public Piece getPieceCourante(){ 
    	return pieceCourante; 
    }
    public void setPieceCourante(Piece p){ 
    	pieceCourante = p; 
    }
    public boolean isEnCours(){ 
    	return enCours; 
    }
    public void setEnCours(boolean b){ 
    	enCours = b; 
    }
    public int getTourActuel(){ 
    	return tourActuel;
    }
    public List<Quete> getQuetes(){ 
    	return quetes; 
    }
    public Combat getCombat(){ 
    	return combat; 
    }
    public ConditionDeVictoire getConditionVictoire(){ 
    	return conditionVictoire; 
    }
}
