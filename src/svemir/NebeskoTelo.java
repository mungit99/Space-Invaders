package svemir;

import java.awt.Color;

public abstract class NebeskoTelo extends Objekat {
	
	protected int poluprecnik;
	protected boolean hitted;

	public NebeskoTelo(int xx, int yy, Color c, int r) {
		super(xx, yy, c);
		poluprecnik = r;
		hitted = false;
	}
	
	public int getPoluprecnik() {
		return poluprecnik;
	}

	public boolean getHitted() {
		return hitted;
	}
	
	public void setHitted(boolean h) {
		hitted = h;
	}
	
}
