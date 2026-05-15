package strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Ennemi;
import model.Piece;

public class DeplacementAleatoire implements StrategieDeplacement{
	private static final Random RNG = new Random();
    @Override
    public void seDeplacer(Ennemi e, Piece p) {
        List<Piece> v = new ArrayList<>(p.getSorties().values());
        if (!v.isEmpty()) {
            Piece dest = v.get(RNG.nextInt(v.size()));
            System.out.println(" " + e.getNom() + " rôde vers " + dest.getNom());
        }
    }
}
