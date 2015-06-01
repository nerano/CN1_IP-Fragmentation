package cn1.ip_fragmentation.logic;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Enthält die Logik aus der Klasse 'TestLogic', wurde lediglich für die packages angepasst.
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
		
		for (int i = 0; i < packNum; i++) {
			map = new HashMap<String, String>();
			
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
			list.add(map);
			totalProcessed += frameLength-headerLength;
		}
		
		return list;
	}
}
