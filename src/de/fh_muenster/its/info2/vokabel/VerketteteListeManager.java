package de.fh_muenster.its.info2.vokabel;

import java.util.ArrayList;
import java.util.Random;

/**
 * Strategie die das VokabelManager Interface implementiert
 * @author Marcel Krups
 *
 */
public class VerketteteListeManager implements VokabelManager {

	private ListenVerwaltung verwaltung;

	/**
	 * Initialisiert die doppelte verkettete Liste
	 */
	public VerketteteListeManager() {
		verwaltung = new ListenVerwaltung();
	}

	/**
	 * Speichert eine Vokabel in der verketteten Liste
	 * @param v Vokabel die gespeichert werden soll
	 * @return boolean, ob die Vokabel erfolgreich gespeichert wurde
	 */
	@Override
	public boolean save(Vokabel v) {
		if (verwaltung.getAnfang() != null) {
			verwaltung.neuesElementAnhaengen(v);
		} else {
			verwaltung.erstesElementAnhaengen(v);
		}
		return true;
	}

	/**
	 * Loescht eine Vokabel in der verketteten Liste
	 * @param deutsch String der den deutschen Teil der zu loeschenden Vokabel enthaelt 
	 * @return boolean, ob die Vokabel erfolreich geloescht wurde
	 */
	@Override
	public boolean delete(String deutsch) {
		Vokabel liste = verwaltung.getAnfang();
		if (liste != null) {
			if (liste.getDeutsch().equals(deutsch)) {
				verwaltung.loescheElement(liste);
				return true;
			}
			while (liste.hasNext()) {
				liste = liste.getNext();
				if (liste.getDeutsch().equals(deutsch)) {
					verwaltung.loescheElement(liste);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Gibt eine zufaellige Vokabel zurueck
	 * @return Vokabel, die zufaellige Vokabel
	 */
	@Override
	public Vokabel getRandomVokabel() {
		Random rand = new Random();
		int vokabelWahl = rand.nextInt(1000);
		Vokabel liste = verwaltung.getAnfang();
		if (liste != null) {
			for (int i = 0; i < vokabelWahl; i++) {
				if (liste.hasNext()) {
					liste = liste.getNext();
				} else {
					liste = verwaltung.getAnfang();
				}
			}
			return liste;
		} else {
			return null;
		}
	}

	/**
	 * Gibt alle Vokabeln aus der Datenbank zurueck
	 * @return ArrayList<Vokabel>, die alle Vokabeln enthaelt
	 */
	@Override
	public ArrayList<Vokabel> getAllVokabeln() {
		ArrayList<Vokabel> vokabeln = new ArrayList<>();
		Vokabel vok = verwaltung.getAnfang();
		if (vok != null) {
			vokabeln.add(vok);
			while (vok.hasNext()) {
				vok = vok.getNext();
				vokabeln.add(vok);
			}
		}
		return vokabeln;
	}

}
