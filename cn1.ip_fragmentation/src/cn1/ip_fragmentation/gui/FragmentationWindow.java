package cn1.ip_fragmentation.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;

import cn1.ip_fragmentation.gui.Screen;

/**
 * Erstellt das eigentliche Fenster für die Visulaisierung.
 * Das Fenster wird mit einem BorderLayout aufgebaut. Im Layout.NORTH befindet sich ein Panel für die Eingabe der
 * Werte, im Layout.CENTER befindet sich ein weiteres Panel, welches durch die Klasse 'Screen' mit den Primitiven
 * für di Visualisierung gefüllt wird.
 * 
 * @author Sebastian
 *
 */
public class FragmentationWindow {
	
	private Integer packageLength;
	private Integer frameLength;
	private Integer headerLength;
	
	final private JFrame window;
	private Container cp;
	private JScrollPane scrollPane;
	
	private JPanel panelNorth = new JPanel();
	private Screen fragmentationPanel = new Screen();
	private JLabel headerLabel = new JLabel();
	private JLabel packageLabel = new JLabel();
	private JLabel frameLabel = new JLabel();
	private JTextField headerInput = new JTextField();
	private JTextField packageInput = new JTextField();
	private JTextField frameInput = new JTextField();
	private JButton fragmentButton = new JButton("Fragmentation");
	
	public FragmentationWindow(JFrame window) {
		this.window = window;
		this.cp = this.window.getContentPane();
			
		// Check if root window has been resized so we can adjust the size of our ScrollPane
		window.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				// Check if we already initialized our ScrollPane
				if (scrollPane != null) {
	                scrollPane.revalidate();
	                scrollPane.repaint();
                }
            }
		});
		
		this.buildFrame();
	}

	private void buildFrame() {
		
		// setze Layout und baue das Panel für die Eingabe auf.
		cp.setLayout(new BorderLayout());
		panelNorth.setBackground(Color.WHITE);
		this.panelNorth.setLayout(new GridLayout(1, 7));
		this.panelNorth.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		
		// Packet length label & input
		this.packageLabel.setText("Packet length:");
		this.panelNorth.add(this.packageLabel);
		this.panelNorth.add(this.packageInput);
		
		// Frame length label & input
		this.frameLabel.setText("Fragment length:");
		this.panelNorth.add(this.frameLabel);
		this.panelNorth.add(this.frameInput);
		
		// Header length label & input
		this.headerLabel.setText("Header length:");
		this.panelNorth.add(this.headerLabel);
		this.panelNorth.add(this.headerInput);
		this.panelNorth.add(this.fragmentButton);
		
		// Default values
		packageInput.setText("1024");
		frameInput.setText("128");
		headerInput.setText("20");
		
		// ActionListener für das Drücken des Buttons 'Fragmentation'.
		// Die Eingaben werden überprüft und in die Variablen geschrieben.
		this.fragmentButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if (packageInput.getText().equals("")) {
					if (frameInput.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "The package length and frame length was not initialized.\n"
								+ " Please put in a value for those two important values!");
					} else {
						JOptionPane.showMessageDialog(null, "The package length was not initialized.\n"
								+ " Please put in a value for this important value!");
					}
				} else {
					if (frameInput.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "The frame length was not initialized.\n"
								+ " Please put in a value for this important value!");
					} else {
						if (headerInput.getText().equals("")) {
							JOptionPane.showMessageDialog(null, "The header length was not initialized.\n"
									+ " The default value of 5 words = 20 byte will be taken for the computation!");
							packageLength = Integer.valueOf(packageInput.getText());
							frameLength = Integer.valueOf(frameInput.getText());
							headerLength = 20;
							frameUpdate();
						} else {
							if(Integer.parseInt(headerInput.getText()) < 20 || Integer.parseInt(headerInput.getText()) > 60) {
								JOptionPane.showMessageDialog(null, "The input value for \"Header length\" "
										+ "has to be bewteen 20 and 60 bytes!");
							} else {
								packageLength = Integer.valueOf(packageInput.getText());
								frameLength = Integer.valueOf(frameInput.getText());
								headerLength = (Integer.valueOf(headerInput.getText()));
								frameUpdate();
							}
						}
					}
				}
				
				if (frameLength != null && packageLength != null) {
					if (frameLength >= packageLength) {
						JOptionPane.showMessageDialog(null, "\"Frame length\" is greater or equal \"Package length\","
								+ " that does not make sense!\n Please put in some correct numbers!");
					}
				}

			}
		});
		
		this.cp.add(this.panelNorth, BorderLayout.NORTH);
		this.cp.validate();		
	}
	
	/**
	 * Diese Methode soll das Panel mit der Visulaisierung updaten, falls der 'Fragementation' Button erneut
	 * gedrückt wird.
	 */
	private void frameUpdate() {
		// if ScrollPane already exists, remove it and add a new one
		if (scrollPane != null) {
		     this.cp.remove(scrollPane);
		}
		
		// Create new packet visualization
		fragmentationPanel = new Screen(this.packageLength, this.frameLength, this.headerLength);
		
		// Add panel to ScrollPane
		this.scrollPane = new JScrollPane(this.fragmentationPanel);
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		// Needed to fix a bug that appears while scrolling
		scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		
		// Add ScrollPane to the main window
		this.cp.add(scrollPane);

		this.cp.validate();
		this.cp.repaint();
	}
}