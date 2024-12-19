package svemir;

public class Generator extends Thread {
	
	private Svemir svemir;
	private boolean radi = false;

	public Generator(Svemir s) {
		svemir = s;
	}
	
	public synchronized void pokreni() {
		start();
	}
	
	public synchronized void kreni() {
		radi = true;
		notify();
	}
	
	public synchronized void zaustavi() {
		radi = false;
	}
	
	
	public void prekini() {
		interrupt();
	}
	
	@Override
	public void run() {
		try {
			while(!isInterrupted()) {
				synchronized(this) {
					if(!radi) wait();
				}
				sleep(1000);
				if(svemir.cur < 3) svemir.cur++; 
				svemir.dodaj(new Kometa((int)(Math.random() * 200), 0, (int)(Math.random() * 20) + 10));
			}
		} catch (InterruptedException e) {}
	}

}
