package de.fh_muenster.its.info2.vokabel;

import java.util.ArrayList;

public interface VokabelManager {

	/**
	 * Speichert eine Vokabel
	 * @param v, die zu speichernde Vokabel
	 * @return boolean, ob auch erfolgreich gespeichert wurde
	 */
	public boolean save(Vokabel v);
	
	/**
	 * Loescht eine Vokabel
	 * @param deutsch, der deutsche Teil der zu loeschenden Vokabel
	 * @return boolean, ob auch erfolgreich geloescht wurde
	 */
	public boolean delete(String deutsch);
	
	/**
	 * Gibt eine zufaellige Vokabel zurueck
	 * @return Vokabel, die zufaellige Vokabel
	 */
	public Vokabel getRandomVokabel();
	
	/**
	 * Gibt alle Vokabeln zurueck
	 * @return ArrayList<Vokabel>, die Liste aller Vokabeln
	 */
	public ArrayList<Vokabel> getAllVokabeln();
}
