package de.fh_muenster.its.info2.vokabel;

/**
 * Ein Element einer doppelt verketteten Liste
 * @author Marcel Krups
 *
 */
public class Vokabel {

	private String englisch;
	private String deutsch;
	private Vokabel next, prev;
	
	public Vokabel(String englisch, String deutsch) {
		this.englisch = englisch;
		this.deutsch = deutsch;
		next = null;
		prev = null;
	}
	
	/**
	 * @return boolean, ob das Element einen Nachfolger hat
	 */
	public boolean hasNext() {
		if(next != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getEnglisch() {
		return englisch;
	}
	public void setEnglisch(String englisch) {
		this.englisch = englisch;
	}
	public String getDeutsch() {
		return deutsch;
	}
	public void setDeutsch(String deutsch) {
		this.deutsch = deutsch;
	}

	public Vokabel getNext() {
		return next;
	}

	public void setNext(Vokabel next) {
		this.next = next;
	}

	public Vokabel getPrev() {
		return prev;
	}

	public void setPrev(Vokabel prev) {
		this.prev = prev;
	}
	
	
}
