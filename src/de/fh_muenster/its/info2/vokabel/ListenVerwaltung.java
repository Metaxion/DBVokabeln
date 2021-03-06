package de.fh_muenster.its.info2.vokabel;

/**
 * Stellt die Verwaltung fuer eine doppelteverkettete Liste dar
 * @author Marcel Krups
 *
 */
public class ListenVerwaltung {

	private Vokabel anfang;
	private Vokabel ende;
	
	/**
	 * Fuellt den Anfang der Liste mit den Vokabeln aus der uebergebenen zeile
	 * @param vok, die Vokabel die angehaengt wird
	 */
	public void erstesElementAnhaengen(Vokabel vok) {
		anfang = vok;
		anfang.setNext(null);
		anfang.setPrev(null);
		ende = anfang;
	}
	
	/**
	 * Fuellt die naechste Stelle der Liste mit den Vokabeln aus der uebergebenen zeile
	 * @param vok, die Vokabel die angehaengt wird
	 */
	public void neuesElementAnhaengen(Vokabel vok) {
		ende.setNext(vok);
		ende.getNext().setPrev(ende);
		ende = ende.getNext();
	}
	
	public Vokabel getEnde() {
		return ende;
	}

	public void setEnde(Vokabel ende) {
		this.ende = ende;
	}

	public Vokabel getAnfang() {
		return anfang;
	}

	public void setAnfang(Vokabel anfang) {
		this.anfang = anfang;
	}

	/**
	 * Loescht das uebergebene Element vok aus der Liste
	 * @param vok, die zu loeschende Vokabel
	 */
	public void loescheElement(Vokabel vok) {
		//Erstes Element
		if(vok.getPrev() == null) {
			//Und kein weiteres Element
			if(vok.getNext() == null) {
				anfang = null;
				ende = anfang;
			} 
			//Erstes Element von mehreren
			else {
				anfang = anfang.getNext();
				anfang.setPrev(null);
			}
		}
		//Letztes Element
		else if(vok.getNext() == null) {
			ende = ende.getPrev();
			ende.setNext(null);
		}
		//Mitten drin
		else {
			Vokabel listeNext = vok.getNext();
			Vokabel listePrev = vok.getPrev();
			listePrev.setNext(listeNext);
			listeNext.setPrev(listePrev);
		}
	}

}
