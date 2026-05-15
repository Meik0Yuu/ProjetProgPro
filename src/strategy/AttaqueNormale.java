package strategy;

import model.Ennemi;
import model.Joueur;

public class AttaqueNormale implements StrategieAttaque{
	@Override
    public int calculerDegats(Ennemi e, Joueur j) {
        int d = e.getForce() + e.getCapaciteAttaque();
        System.out.printf(" %s attaque normalement : %d dégâts bruts.%n", e.getNom(), d);
        return d;
    }
}
