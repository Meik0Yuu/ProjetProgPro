package strategy;

import model.Ennemi;
import model.Joueur;
import state.EtatEmpoisonne;

public class AttaquePoison implements StrategieAttaque{
	@Override
    public int calculerDegats(Ennemi e, Joueur j) {
        int d = e.getForce();
        System.out.printf(" %s empoisonne %s ! Dégâts : %d + poison 4 tours.%n",
            e.getNom(), j.getNom(), d);
        j.changerEtat(new EtatEmpoisonne(4));
        return d;
    }
}
