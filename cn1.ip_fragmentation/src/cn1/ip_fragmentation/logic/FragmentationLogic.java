package cn1.ip_fragmentation.logic;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Enth�lt die Logik aus der Klasse 'TestLogic', wurde lediglich f�r die packages angepasst.
 * 
 * @author Sebastian
 *
 */
public class FragmentationLogic {

	private static int packageLength;
	private static Integer frameLength;
	private static Integer headerLength;
	private static String identifier;
	
	private static HashMap<String, String> map;
	private static ArrayList<HashMap<String, String>> list;
	
	public FragmentationLogic(int pack, int frame, int head, String id) {
		
	}
	
	public static ArrayList<HashMap<String, String>> calculate(int packLength, int frameInput, int header, String id) {
		packageLength = packLength;
		frameLength = frameInput;
		headerLength = header;
		identifier = id;
	
		list = new ArrayList<HashMap<String, String>>();
		
		int packNum = (packageLength/frameLength) + 1;
		int totalProcessed = 0;
		Integer frame = 0;
		Integer offset = 0;
		// paddingBytes pr�ft ob headerLength ein vielfaches von 4 ist.
		Integer paddingBytes = headerLength % 4;
		
		/**
		 * Falls headerLength kein vielfaches von 4 ist, wird der Header per padding aufgef�llt.
		 * Dies wird �ber eine switch-Anweisung �ber die headerLength ausgef�hrt. Danach enth�lt die Variable
		 * 'paddingBytes' die Anzahl der aufgef�llten Bytes und kann in der Visualisierung ausgegeben werden.
		 */
		if(paddingBytes != 0) {
			switch(paddingBytes) {
			case 1: headerLength += 3;
					paddingBytes = 3;
					break;
			case 2: headerLength += 2;
					paddingBytes = 2;
					break;
			case 3: headerLength += 1;
					paddingBytes = 1;
					break;
			default: headerLength += 0;
					break;
			}
		}
		
		for (int i = 0; i < packNum; i++) {
			map = new HashMap<String, String>();
			
			map.put("header", headerLength.toString());
			map.put("ID", identifier);
			
			if (totalProcessed > (packageLength-frameLength-headerLength)) {
				frame = packageLength-totalProcessed;
				map.put("length", frame.toString());
			} else {
				map.put("length", frameLength.toString());
			}
			
			if (i < packNum-1) {
				map.put("MF", "1");
			} else {
				map.put("MF", "0");
			}
			
			if (i != 0) {
				offset = i * ((frameLength-headerLength)/8);
				map.put("offset", offset.toString());
			} else {
				map.put("offset", "0");
			}
			map.put("padding", paddingBytes.toString());
			
			list.add(map);
			totalProcessed += frameLength-headerLength;
		}
		
		return list;
	}
}
