package svemir;

import java.awt.Color;
import java.awt.Graphics;

public class Kometa extends NebeskoTelo {
	
	private double orijentacija;
	private int[] xo;
	private int[] yo;

	public Kometa(int xx, int yy, int r) {
		super(xx, yy, Color.GRAY, r);
		orijentacija = Math.random() * 2 * Math.PI;
		xo = new int[5];
		yo = new int[5];
		for(int i = 0; i < 5; i++) {
			xo[i] = (int)(Math.cos(orijentacija + i * 2 * Math.PI / 5) * poluprecnik) + x;
			yo[i] = (int)(Math.sin(orijentacija + i * 2 * Math.PI / 5) * poluprecnik);
		}
	}

	@Override
	public void crtaj(Graphics g) {
		if(!hitted) {
			g.setColor(boja);
			int[] newY = new int[5]; 
			for(int i = 0; i < 5; i++) {
				newY[i] = yo[i] + y;
			}
			g.fillPolygon(xo, newY, 5);
		}
	}

}
