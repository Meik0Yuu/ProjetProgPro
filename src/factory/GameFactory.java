package factory;

import model.Difficulte;
import model.Ennemi;
import model.Objet;
import model.Piece;

public interface GameFactory {
	Objet creerObjet(String type);
    Ennemi creerEnnemi(String type, Difficulte diff);
    Piece creerPiece(String type);
}
