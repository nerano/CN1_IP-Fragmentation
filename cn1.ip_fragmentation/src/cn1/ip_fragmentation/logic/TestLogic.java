package cn1.ip_fragmentation.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Das Programm ist nur ein einfacher Test für die Fragmentierung.
 * Es ist sicherlich möglich noch einiges zu verbessern, allerdings tut das Programm, was es soll und führt eine
 * richtige Framgentierung durch.
 * 
 * @author Sebastian Kropp
 *
 */
public class TestLogic {
	
	private int packageLength;
	private Integer frameLength;
	private Integer headerLength;
	private String identifier = "0000";
	
	private ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
	private HashMap<String, String> map;
	
	private void userInput() throws IOException {
		System.out.println("Please put in the package length (in byte): ");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String length = reader.readLine();
		this.packageLength = Integer.parseInt(length);
		
		System.out.println("Please put in the length for each Frame (also in byte): ");
		length = reader.readLine();
		this.frameLength = Integer.parseInt(length);
		/**
		 * Einlesen der Header Länge. Ich lasse den Nutzer hier nur words eingeben, da die Header Länge durch 8
		 * teilbar bleiben muss. Wenn wir hier ebenfalls bytes eingeben lassen, kann etwas komisches wie 25 oder
		 * so ohne Probleme eingelesen werden. Durch das einlesen und umrechnen der Wörter kann dies nicht passieren.
		 */
		System.out.println("Please put in the header length (length between 5 words = 20 bytes"
				+ " and 15 words = 60 bytes. The input should be in words!): ");
		length = reader.readLine();
		if(Integer.parseInt(length) < 5 || Integer.parseInt(length) > 15) {
			throw new IllegalArgumentException("Wrong input! The input value has to be between 5 and 15!");
		} else {
			this.headerLength = Integer.parseInt(length)*32/8;
		}
	}
	
	/**
	 * Diese Methode berechnet den "Offset" und teilt das package in frames auf.
	 * Zur Verdeutlichung habe ich noch ein Bild ins repository gestellt, das zeigt was genau hier passiert.
	 * Update: Ich habe nun auch die Header Länge eingebaut und einen Fehler in der Berechnung der frame length
	 * 		   für das letzte Frame behoben. Der Programmierstiel ist mittlerweile natürlich ziemlich hässlich,
	 * 		   aber die Logik passt.
	 */
	private void fragmentation() {
		int packNum = (this.packageLength/this.frameLength) + 1;
		int totalProcessed = 0;
		Integer frameLength = 0;
		Integer offset = 0;
				
		for (int i = 0; i < packNum; i++) {
			map = new HashMap<String, String>();
			
			map.put("ID", this.identifier);
			
			if (totalProcessed > (this.packageLength-this.frameLength-this.headerLength)) {
				frameLength = this.packageLength-totalProcessed;
				map.put("length", frameLength.toString());
			} else {
				map.put("length", this.frameLength.toString());
			}
			
			if (i < packNum-1) {
				map.put("MF", "1");
			} else {
				map.put("MF", "0");
			}
			
			if (i != 0) {
				offset = i * ((this.frameLength-this.headerLength)/8);
				map.put("offset", offset.toString());
			} else {
				map.put("offset", "0");
			}
			this.list.add(map);
			totalProcessed += this.frameLength-this.headerLength;
		}
		
		// Ich war mir nicht sicher, ob wir die ID immer brauchen, diese ist nämlich nur sinnvoll, wenn mehr als
		// eine fragmentation durchgeführt wird, daher habe ich mir hier keine Mühe gemacht eine neue ID zu 
		// berechnen.
		this.identifier = "0001";
	}
	
	private void output() {
		System.out.println("Header length: " + this.headerLength + " bytes.");
		System.out.println(list.size()+" frames were created.\n");
		System.out.println("ID\tlength\tMF\toffset");
		
		for(int i = 0; i < this.list.size(); i++) {
			System.out.print(list.get(i).get("ID")+"\t");
			System.out.print(list.get(i).get("length")+"\t");
			System.out.print(list.get(i).get("MF")+"\t");
			System.out.print(list.get(i).get("offset")+"\n");
		}
	}
	
	public static void main(String[] args) {
		TestLogic ft = new TestLogic();
		try {
			ft.userInput();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ft.fragmentation();
		ft.output();
	}
}
