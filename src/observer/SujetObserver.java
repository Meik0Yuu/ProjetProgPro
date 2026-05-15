package observer;

public interface SujetObserver {
	void ajouterObservateur(Observer o);
    void retirerObservateur(Observer o);
    void notifierObservateurs(String evenement);
}
