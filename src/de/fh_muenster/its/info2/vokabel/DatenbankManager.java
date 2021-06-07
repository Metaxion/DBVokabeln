package de.fh_muenster.its.info2.vokabel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Strategie die das VokabelManager Interface implementiert
 * @author Marcel Krups
 *
 */
public class DatenbankManager implements VokabelManager {
	
	private DatenbankVerwaltung data;
	
	/**
	 * Baut beim initialisieren die Datenbankverbindung mit auf
	 */
	public DatenbankManager() {
		try {
			data = new DatenbankVerwaltung();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	/**
	 * Speichert eine Vokabel in der Datenbank
	 * @param v Vokabel die gespeichert werden soll
	 * @return boolean, ob die Vokabel erfolgreich gespeichert wurde
	 */
	@Override
	public boolean save(Vokabel v) {
		try {
			if(data.findVoc(v.getEnglisch(), v.getDeutsch())) {				
				return false;
			} else {
				data.insert(v.getEnglisch(), v.getDeutsch());
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Loescht eine Vokabel in der Datenbank
	 * @param deutsch String der den deutschen Teil der zu loeschenden Vokabel enthaelt 
	 * @return boolean, ob die Vokabel erfolreich geloescht wurde
	 */
	@Override
	public boolean delete(String deutsch) {
		try {
			if(data.findVoc(deutsch)) {
				data.delete(deutsch);
				return true;				
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Gibt eine zufaellige Vokabel zurueck
	 * @return Vokabel, die zufaellige Vokabel
	 */
	@Override
	public Vokabel getRandomVokabel() {
		Random rand = new Random();
		int vokabelWahl = rand.nextInt(1000);
		ArrayList<Vokabel> vokabeln = getAllVokabeln();
		if(vokabeln.size() != 0) {
			return vokabeln.get(vokabelWahl % vokabeln.size());
		}
		return null;
	}

	/**
	 * Gibt alle Vokabeln aus der Datenbank zurueck
	 * @return ArrayList<Vokabel>, die alle Vokabeln enthaelt
	 */
	@Override
	public ArrayList<Vokabel> getAllVokabeln() {
		ArrayList<Vokabel> vokabeln = new ArrayList<>();
		ResultSet set;
		try {
			set = data.list();
			while(set.next()) {
				vokabeln.add(new Vokabel(set.getString("EN"), set.getString("DE")));
			}
			return vokabeln;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
