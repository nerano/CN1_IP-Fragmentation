package cn1.ip_fragmentation.main;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import cn1.ip_fragmentation.gui.FragmentationWindow;
/**
 * Erstellt das Fenster und füllt es durch aufruf der Klasse 'FragmentationWindow'.
 * 
 * @author Sebastian
 *
 */
public class Main {

	public static void main(String[] args) {
		final JFrame frame = new JFrame("IP Fragmentation");
		frame.setResizable(true);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		new FragmentationWindow(frame);
		frame.setVisible(true);
		frame.setSize(850, 400);
	}
}
