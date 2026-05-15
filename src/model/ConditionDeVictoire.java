package model;

import java.util.List;

public class ConditionDeVictoire {
	private boolean bossVaincu = false;
    private boolean sortieAtteinte = false;

    public void signalerBossVaincu() {
        bossVaincu = true;
        System.out.println();
        System.out.println(" Le boss a été vaincu ! La voie est libre...");
    }

    public void signalerSortieAtteinte() {
        sortieAtteinte = true;
    }

    public boolean estVictoire(List<Quete> quetes) {
        boolean quetesPrincipales = quetes.stream()
            .filter(Quete::isPrincipale)
            .allMatch(Quete::isTerminee);
        return bossVaincu && sortieAtteinte && quetesPrincipales;
    }

    public boolean peutSortir() { 
    	return bossVaincu; 
    }
    public boolean isBossVaincu() { 
    	return bossVaincu; 
    }
    public boolean isSortieAtteinte() { 
    	return sortieAtteinte; 
    }
}
