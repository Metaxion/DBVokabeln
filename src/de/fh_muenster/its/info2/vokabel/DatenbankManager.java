package de.fh_muenster.its.info2.vokabel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class DatenbankManager implements VokabelManager {
	
	DatenbankVerwaltung data;
	
	public DatenbankManager() {
		try {
			data = new DatenbankVerwaltung();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	@Override
	public boolean save(Vokabel v) {
		try {
			data.insert(v.getEnglisch(), v.getDeutsch());
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(String deutsch) {
		try {
			data.delete(deutsch);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

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
