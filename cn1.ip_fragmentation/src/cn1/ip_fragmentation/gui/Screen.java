package cn1.ip_fragmentation.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

import cn1.ip_fragmentation.logic.FragmentationLogic;

/**
 * Zeichnet die Rechtecke, inklusive Beschriftung, für die Visualisierung der Fragmentation.
 * 
 * @author Sebastian
 *
 */
public class Screen extends JPanel {

	private static final long serialVersionUID = 1L;
	private Integer packageLength;
	private Integer headerLength;
	private Integer identifier = 0;
	
	ArrayList<HashMap<String, String>> fragmentation;

	public Screen() {
		
	}
	
	public Screen(int packageLength, int frameLength, int headerLength) {
		this.setLayout(new BorderLayout());
		this.packageLength = packageLength;
		this.headerLength = headerLength;
		this.fragmentation = 
				FragmentationLogic.calculate(packageLength, frameLength, headerLength, this.identifier.toString());
		repaint();
	}

	/** 
	 * Methode wird in einer Endlosscheife immer wieder aufgerufen und zeichnet die Rechtecke für die,
	 * durch die Logik errechnete, Visualisierung. Dies geschieht per 'drawRect()', die Beschriftung wird per
	 * 'drawString()' eingefügt.
	 */
	public void paint(Graphics g) {
		FontMetrics metrics;
		
		int xPosition = 50;
		int yPosition = 25;
		int stringPosition = 50;
		int packageWidth = 500;
		int packageHeight = 40;
		
		String [] fields = {"Length = ", "Header = ", "ID = ", "MF = ", "Offset = ", "Data"};
		Integer [] initialize = {this.packageLength, this.headerLength, this.identifier, 0, 0};
		
		Graphics2D g2 = (Graphics2D) g;		
		g2.setColor(Color.BLACK);
		g2.drawRect(xPosition, yPosition, packageWidth, packageHeight);
		for (int i = 0; i < fields.length; i++) {
			if (i == fields.length-1) {
				xPosition += 3;
				g2.drawString(fields[i], xPosition, stringPosition);
			} else {
				xPosition += 3;
				g2.drawString(fields[i] + initialize[i], xPosition, stringPosition);
				metrics = g2.getFontMetrics();
				xPosition += metrics.stringWidth(fields[i] + initialize[i]) + 3;
				g2.drawLine(xPosition, yPosition, xPosition, yPosition+40);
			}
		}
		
		for (int i = 0; i < this.fragmentation.size(); i++) {
			String [] values = {this.fragmentation.get(i).get("length"), this.headerLength.toString(),
					this.fragmentation.get(i).get("ID"), this.fragmentation.get(i).get("MF"),
					this.fragmentation.get(i).get("offset")};
			xPosition = 50;
			if (i == 0) {
				yPosition += 125;
				stringPosition = yPosition + 25;
			} else {
				yPosition += 65;
				stringPosition = yPosition + 25;
			}
			g2.drawRect(xPosition, yPosition, packageWidth, packageHeight);
			for (int j = 0; j < values.length; j++) {
				//System.out.println(values[j]);
				xPosition += 3;
				g2.drawString(fields[j] + values[j], xPosition, stringPosition);
				metrics = g2.getFontMetrics();
				xPosition += metrics.stringWidth(fields[j] + values[j]) + 3;
				g2.drawLine(xPosition, yPosition, xPosition, yPosition+40);
			}
			xPosition += 3;
			g2.drawString(fields[5], xPosition, stringPosition);
			
			this.validate();
		}
	}
}

