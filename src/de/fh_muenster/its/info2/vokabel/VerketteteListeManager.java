package de.fh_muenster.its.info2.vokabel;

import java.util.ArrayList;
import java.util.Random;

public class VerketteteListeManager implements VokabelManager {

	ListenVerwaltung verwaltung;

	public VerketteteListeManager() {
		verwaltung = new ListenVerwaltung();
	}

	@Override
	public boolean save(Vokabel v) {
		if (verwaltung.getAnfang() != null) {
			verwaltung.neuesElementAnhaengen(v);
		} else {
			verwaltung.erstesElementAnhaengen(v);
		}
		return true;
	}

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

	@Override
	public Vokabel getRandomVokabel() {
		Random rand = new Random();
		int vokabelWahl = rand.nextInt(1000);
		Vokabel liste = verwaltung.getAnfang();
		for (int i = 0; i < vokabelWahl; i++) {
			if (liste.hasNext()) {
				liste = liste.getNext();
			} else {
				liste = verwaltung.getAnfang();
			}
		}
		return liste;
	}

	@Override
	public ArrayList<Vokabel> getAllVokabeln() {
		ArrayList<Vokabel> vokabeln = new ArrayList<>();
		Vokabel vok = verwaltung.getAnfang();
		vokabeln.add(vok);
		while(vok.hasNext()) {
			vok = vok.getNext();
			vokabeln.add(vok);
		}
		return vokabeln;
	}

}
