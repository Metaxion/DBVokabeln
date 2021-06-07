package de.fh_muenster.its.info2.vokabel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import de.fh_muenster.its.info2.Exception.KeinSemicolonException;
import de.fh_muenster.its.info2.Exception.LeereVokabelException;

public class Vokabeltrainer {

	protected VokabelManager manager;

	/**
	 * Hauptprogramm des Vokabeltrainers <br>
	 * Verwaltete das Hauptmenue und gibt ueber eine Menuesteuerung Zugriff zu den
	 * einzelnen Funktionalitäten <br>
	 * Die uebergebene Datei (dateiName) wird als Vokabel-Datenbank verwendet, falls
	 * diese Datei nicht exisiteren sollte, wir sie neu erstellt. <br>
	 * Menue-Eingaben: <br>
	 * - einlesen <br>
	 * - speichern <br>
	 * - hinzufuegen <br>
	 * - loeschen <br>
	 * - abfragen <br>
	 * - beenden <br>
	 * (- debug) <br>
	 * TODO Tests schreiben
	 * 
	 * @param dateiName
	 */
	public void start(String dateiName) {
		VokabelManager[] managers = new VokabelManager[2];
		managers[0] = new VerketteteListeManager();
		managers[1] = new DatenbankManager();
		manager = managers[0];

		System.out.println("-- Vokabeltrainer --\n");
		int laeuft = 1;
		Scanner eingabeScanner = new Scanner(System.in);
		while (laeuft == 1) {
			System.out.println(
					"\n-- Hauptmenue --\nEingabemoeglichkeiten:\neinlesen\nspeichern\nhinzufuegen\nloeschen\nabfragen\nnextmanager\nbeenden\nEingabe:\n");
			String eingabe = eingabeScanner.nextLine();
			switch (eingabe) {
			case "einlesen":
				try {
					vokabelDateiEinlesen();
				} catch (IOException e) {
					System.err.println("Datei oder Reader konnte beim Einlesen nicht richtig geoeffnet werden");
				}
				break;
			case "speichern":
				if (manager instanceof VerketteteListeManager) {
					try {
						vokabelDateiSpeichern(dateiName);
					} catch (IOException e) {
						System.err.println("Datei oder Writer konnte beim Speichern nicht richtig geoeffnet werden");
					}
				} else {
					System.out.println("Sie arbeiten mit einer Datenbank...");
				}
				break;
			case "hinzufuegen":
				vokabelHinzufuegen();
				break;
			case "loeschen":
				vokabelLoeschen();
				break;
			case "abfragen":
				vokabelAbfragen();
				break;
			case "debug":
				vokabelnAusgeben();
				break;
			case "nextmanager":
				if (manager instanceof VerketteteListeManager) {
					manager = managers[1];
					System.out.println("Der VokabelManager ist nun die Datenbank");
				} else {
					manager = managers[0];
					System.out.println("Der VokabelManager ist nun die VerketteteListe");
				}
				break;
			case "beenden":
				System.out.println("\n-- Vokabeltrainer beendet --");
				return;
			default:
				System.out.println("Eingabe ist ungueltig, bitte wiederholen. \n\n");
				break;
			}
		}
		eingabeScanner.close();
	}

	/**
	 * Fragt eine zufaellige Vokabel in einer zufaelligen Uebersetzungsrichtung ab.
	 */
	@SuppressWarnings("resource")
	protected void vokabelAbfragen() {
		Scanner vokabelScanner = new Scanner(System.in);
		String eingabe;

		// Zufaellige Vokabel und Uebersetzungsrichtung bestimmen
		Vokabel vok = manager.getRandomVokabel();
		Random rand = new Random();
		int uebersetzungsRichtung = rand.nextInt(1);

		// Ueberpruefung
		if (vok != null) {

			// Abfrage
			if (uebersetzungsRichtung == 0) {
				// Englisch zu Deutsch
				System.out.println("Geben sie die deutsche Uebersetzung an: " + vok.getEnglisch());
				eingabe = vokabelScanner.nextLine();
				if (eingabe.equals(vok.getDeutsch())) {
					System.out.println("\nRichtig!");
				} else {
					System.out.println("\nFalsch! " + vok.getDeutsch() + " waere richtig gewesen!");
				}
			} else {
				// Deutsch zu Englisch
				System.out.println("Geben sie die englische Uebersetzung an: " + vok.getDeutsch());
				eingabe = vokabelScanner.nextLine();
				if (eingabe.equals(vok.getEnglisch())) {
					System.out.println("\nRichtig!");
				} else {
					System.out.println("\nFalsch! " + vok.getEnglisch() + " waere richtig gewesen!");
				}
			}
		} else {
			System.out.println("Es gibt keine Vokabeln zum abfragen!");
		}
	}

	/**
	 * Laesst den Nutzer eine Vokabel auf deutsch angeben und falls
	 * sie vorhanden ist wird sie geloescht. 
	 */
	@SuppressWarnings("resource")
	protected void vokabelLoeschen() {
		Scanner vokabelScanner = new Scanner(System.in);
		System.out.println("\nGeben sie die zu loeschende Variabel in deutsch an:");
		String eingabe = vokabelScanner.nextLine();
		eingabe = eingabe.trim();
		boolean ergebnis = manager.delete(eingabe);
		if (ergebnis) {
			System.out.println("Die Vokabel wurde erfolgreich geloescht!");
		} else {
			System.out.println("Die Vokabel konnte nicht geloescht werden!");
		}
	}

	/**
	 * Fordert die Eingabe einer Vokabel und speichert sie in der Liste.
	 */
	@SuppressWarnings("resource")
	protected void vokabelHinzufuegen() {
		Scanner vokabelScanner = new Scanner(System.in);
		System.out.println("Geben sie die englisch Vokabel ein:");
		String eingabeEnglisch = vokabelScanner.nextLine();
		if (eingabeEnglisch == "") {
			System.out.println("Die Eingabe enthaelt keine Vokabel");
			return;
		}
		System.out.println("\nGeben sie die deutsche Vokabel ein:");
		String eingabeDeutsch = vokabelScanner.nextLine();
		if (eingabeDeutsch == "") {
			System.out.println("Die Eingabe enthaelt keine Vokabel");
			return;
		}

		try {
			if (validiereZeile(eingabeEnglisch + ";" + eingabeDeutsch)) {
				if(manager.save(new Vokabel(eingabeEnglisch, eingabeDeutsch))) {					
					System.out.println("\nVokabel hinzugefuegt");
				} else {
					System.out.println("\nVokabel konnte nicht hinzugefuegt werden");
				}
			}
		} catch (KeinSemicolonException e) {
			e.printStackTrace();
		} catch (LeereVokabelException e) {
			System.out.println("Die Eingabe enthaelt keine Vokabel");
		}
	}

	/**
	 * Liest die einzelnen Vokabeln aus der Liste aus und speichert sie Zeile fuer
	 * Zeile in der Vokabel Datei (dateiName).
	 * 
	 * @param dateiName
	 * @throws IOException
	 */
	protected void vokabelDateiSpeichern(String dateiName) throws IOException {
		int counter = 0;
		ArrayList<Vokabel> vokabeln = manager.getAllVokabeln();
		if (!vokabeln.isEmpty()) {
			File datei = new File(dateiName);
			BufferedWriter writer = new BufferedWriter(new FileWriter(datei));
			for (Vokabel vok : vokabeln) {
				writer.append(vok.getEnglisch() + ";" + vok.getDeutsch());
				if (counter > 0) {
					writer.append("\n");
				}
				counter++;
			}
			writer.close();
			System.out.println("\n" + counter + " Vokabeln wurden erfolgreich gespeichert!");
		} else {
			System.out.println("\nEs gibt keine Vokabeln zum speichern!");
		}
	}

	/**
	 * Gibt alle Vokabeln aus der Liste auf der Konsole aus
	 */
	protected void vokabelnAusgeben() {
		for (Vokabel vok : manager.getAllVokabeln()) {
			System.out.println(vok.getEnglisch() + ";" + vok.getDeutsch());
		}
	}

	/**
	 * Liest alle Vokabel aus der Vokabel Datei (dateiName) aus und fuellt mit den
	 * einzelnen Vokabeln die Liste.
	 * 
	 * @param dateiName
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	protected void vokabelDateiEinlesen() throws IOException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Geben sie die Vokabel Datei an:");
		String dateiName = scanner.nextLine();
		File datei = new File(dateiName);
		if (!datei.exists()) {
			datei.createNewFile();// TODO: was genau passiert da
		}
		BufferedReader reader = new BufferedReader(new FileReader(datei));
		String zeile = null;
		int zeilenNummer = 0;
		int vokabelAnzahl = 0;
		while ((zeile = reader.readLine()) != null) {
			zeilenNummer++;
			try {
				if (validiereZeile(zeile) == true) {
					String[] vokabeln = zeile.split(";");
					boolean ergebnis = manager.save(new Vokabel(vokabeln[0].trim(), vokabeln[1].trim()));
					if (ergebnis) {
						vokabelAnzahl++;
					}
				}
			} catch (KeinSemicolonException e) {
				System.out.println("Zeile " + zeilenNummer + " enthaelt kein Semicolon");
			} catch (LeereVokabelException e) {
				System.out.println("Zeile " + zeilenNummer + " enthaelt keine Vokabel");
			}
		}
		reader.close();
		System.out.println("\n" + vokabelAnzahl + " Vokabeln wurden erfolgreich eingelesen!");
	}

	/**
	 * Ueberprueft ob eine Zeile zwei Vokabel getrennt von einem Semikolon enthaelt.
	 * 
	 * @param zeile
	 * @return true, wenn die Zeile valide ist ; false, wenn die Zeile invalide ist
	 * @throws KeinSemicolonException
	 * @throws LeereVokabelException
	 */
	protected boolean validiereZeile(String zeile) throws KeinSemicolonException, LeereVokabelException {
		if (zeile.contains(";")) {
			String[] woerter = zeile.split(";");
			if (!((woerter[0].isBlank() || woerter[0].isEmpty()) || (woerter[1].isBlank() || woerter[1].isEmpty()))) {
				return true;
			} else {
				throw new LeereVokabelException();
			}
		} else {
			throw new KeinSemicolonException();
		}
	}

}
