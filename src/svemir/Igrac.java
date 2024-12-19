package svemir;

import java.awt.Color;
import java.awt.Graphics;

public class Igrac extends Objekat {
	
	private int[] xo;
	private int[] yo;

	public Igrac(int xx, int yy) {
		super(xx, yy, Color.RED);
		xo = new int[4];
		yo = new int[4];
	}

	@Override
	public synchronized void crtaj(Graphics g) {
		xo[0] = x - 10;
		xo[1] = x;
		xo[2] = x + 10;
		xo[3] = x;
		yo[0] = y;
		yo[1] = y - 22;
		yo[2] = y;
		yo[3] = y - 4;
		g.setColor(boja);
		g.fillPolygon(xo, yo, 4);
	}
	
}
