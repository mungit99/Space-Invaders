package svemir;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Objekat {
	
	protected int x, y;
	protected Color boja;
	
	public Objekat(int xx, int yy, Color c) {
		x = xx;
		y = yy;
		boja = c;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		int temp = this.x + x;
		if(temp >= 0 && temp <= 186) this.x = temp;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y += y;
	}

	public abstract void crtaj(Graphics g);
	
}
