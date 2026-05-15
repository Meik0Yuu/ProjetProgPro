package strategy;

import model.Ennemi;
import model.Joueur;

public interface StrategieAttaque {

    int calculerDegats(Ennemi ennemi, Joueur cible);
}
