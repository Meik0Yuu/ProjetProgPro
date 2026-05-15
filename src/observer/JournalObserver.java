package observer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JournalObserver implements Observer{
	private final List<String> journal = new ArrayList<>();
    @Override
    public void mettreAJour(String evenement, Object source) {
        journal.add("[" + java.time.LocalTime.now().toString().substring(0,8) + "] " + evenement + " – " + source);
    }
    public List<String> getJournal() { return Collections.unmodifiableList(journal); }
    public void afficher() {
        System.out.println("=== Journal de partie ===");
        journal.forEach(System.out::println);
    }
}
