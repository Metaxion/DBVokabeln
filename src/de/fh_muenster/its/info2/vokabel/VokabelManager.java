package de.fh_muenster.its.info2.vokabel;

import java.util.ArrayList;

public interface VokabelManager {

	public boolean save(Vokabel v);
	
	public boolean delete(String deutsch);
	
	public Vokabel getRandomVokabel();
	
	public ArrayList<Vokabel> getAllVokabeln();
}
