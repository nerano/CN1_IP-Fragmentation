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
	private int headerLength = 20;
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
	}
	
	/**
	 * Diese Methode berechnet den "Offset" und teilt das package in frames auf.
	 * Zur Verdeutlichung habe ich noch ein Bild ins repository gestellt, das zeigt was genau hier passiert.
	 */
	private void fragmentation() {
		int packNum = (this.packageLength/this.frameLength) + 1;
		Integer offset = 0;
				
		for (int i = 0; i < packNum; i++) {
			map = new HashMap<String, String>();
			map.put("ID", this.identifier);
			map.put("length", this.frameLength.toString());
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
		}
		
		// Ich war mir nicht sicher, ob wir die ID immer brauchen, diese ist nämlich nur sinnvoll, wenn mehr als
		// eine fragmentation durchgeführt wird, daher habe ich mir hier keine Mühe gemacht eine neue ID zu 
		// berechnen.
		this.identifier = "0001";
	}
	
	private void output() {
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
