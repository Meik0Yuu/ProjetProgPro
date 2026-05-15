package strategy;

import java.util.Random;

import model.Ennemi;
import model.Joueur;

public class AttaqueFurieuse implements StrategieAttaque{
	private static final Random RNG = new Random();
    @Override
    public int calculerDegats(Ennemi e, Joueur j) {
        if (RNG.nextDouble() < 0.25) {
            System.out.printf(" %s rate son attaque furieuse !%n", e.getNom());
            return 0;
        }
        int d = (e.getForce() + e.getCapaciteAttaque()) * 2;
        System.out.printf(" %s FRAPPE FURIEUSEMENT : %d dégâts bruts !%n", e.getNom(), d);
        return d;
    }
}
