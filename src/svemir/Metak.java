package svemir;

import java.awt.Color;
import java.awt.Graphics;

public class Metak extends Objekat {
	
	private boolean alive;

	public Metak(int xx, int yy) {
		super(xx, yy, Color.WHITE);
		alive = true;
	}

	@Override
	public void crtaj(Graphics g) {
		if(alive) {
			g.setColor(boja);
			g.drawLine(x, y, x, y + 12);
		}
	}
	
	public boolean getAlive() {
		return alive;
	}
	
	public void setAlive(boolean a) {
		alive = a;
	}

}
