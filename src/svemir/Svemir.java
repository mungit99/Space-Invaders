package svemir;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Svemir extends Canvas implements Runnable {

	private Thread nit = new Thread(this);
	private ConcurrentLinkedQueue<NebeskoTelo> tela;
	private ConcurrentLinkedQueue<Metak> meci;
	private boolean radi = false;
	private Igrac igrac;
	private int score;
	private boolean firstRender = true;
	int cur = 3;
	
	public Svemir() {
		setBackground(Color.BLACK);
		tela = new ConcurrentLinkedQueue<>();
		meci = new ConcurrentLinkedQueue<>();
		igrac = new Igrac(100, 325);
		score = 0;
	}
	
	public synchronized void dodaj(NebeskoTelo n) {
        synchronized (tela) {
            tela.offer(n);
        }
	}
	
	public Thread getThread() {
		return nit;
	}
	
	public boolean getRadi() {
		return radi;
	}
	
	public synchronized void zaustavi() {
		radi = false;
	}
	
	public synchronized void kreni() {
		radi = true;
		notify();
	}
	
	public synchronized void pokreni() {
		nit.start();
	}
	
	public void prekini() {
		if(nit != null) nit.interrupt();
	}
	
	public Igrac getIgrac() {
		return igrac;
	}
	
	public ConcurrentLinkedQueue<Metak> getMeci() {
		return meci;
	}
	
	public boolean collision(NebeskoTelo n, Metak m) {
		double dx = m.getX() - n.getX();
		double dy = m.getY() - n.getY();
	    return dx * dx + dy * dy <= n.getPoluprecnik() * n.getPoluprecnik();
	}
	
	public void moveObjects() {
		//move comets down and check if some went over south border to end the game
		synchronized(tela) {
			Iterator<NebeskoTelo> iter = tela.iterator();
			while(iter.hasNext()) {
				NebeskoTelo n = iter.next();
				n.setY(5);
				if(n.getY() > 325) {
					repaint();
					prekini();
				}
			}
		}
		
		//move bullets up and remove ones that went over north border 
		synchronized (meci) {
			Iterator<Metak> iter2 = meci.iterator();
			while(iter2.hasNext()) {
				Metak m = iter2.next();
				m.setY(-8);
				if(m.getY() < 0) iter2.remove();
			}
		}
	}
	
	public void checkCollision() {
		Iterator<Metak> iterm = meci.iterator();
		synchronized (meci) {
			while(iterm.hasNext()) {
				Metak m = iterm.next();
				Iterator<NebeskoTelo> itern = tela.iterator();
				synchronized(tela) {
					while(itern.hasNext()) {
						NebeskoTelo n = itern.next();
						
						//first comets that have lover y-axis wont get hit
						if(n.getY() > m.getY()) continue;
						
						if(collision(n, m)) {
							itern.remove();
							iterm.remove();
							score++;
							repaint();
							break;
						}
						
						//radius of comet is maximum 30 pixel so we can end loop here 
						if(n.getY() < m.getY() + 30) break;
					}
				}
			}
		}
	}

	@Override
	public void run() {
		try {
			while(!Thread.interrupted()) {
				synchronized(this) {
					if(!radi) wait();
				}
				Thread.sleep(100);
				moveObjects();
				checkCollision();
				repaint();
			}
		} catch (InterruptedException e) {}	
	}
	
	@Override
	public synchronized void paint(Graphics g) {  
		//draw player
        igrac.crtaj(g);
        
        //draw comets
        synchronized (tela) {  
            for (NebeskoTelo n : tela) {
                n.crtaj(g);
            }
        }
        
        //draw bullets
        synchronized (meci) {
        	for(Metak m: meci) m.crtaj(g);
        }
        
        //draw score
        g.setFont(new Font("Arial", Font.BOLD, 20));
		g.setColor(Color.GREEN);
		g.drawString("Score: " + score, 5, 18);
		
		//draw available bullets that can be shoot 
		int temp = 0;
		for(int i = 0; i < cur; i++) {
			g.fillOval(120 + temp, 6, 15, 15);
			temp += 20;
		}
		g.drawOval(120, 6, 15, 15);
		g.drawOval(140, 6, 15, 15);
		g.drawOval(160, 6, 15, 15);
		g.setColor(Color.RED);
		
		//paused game
		if(!radi && !firstRender) {
			g.drawString("PAUSE", 50, 165);	
		}
		
		//end game
		if(!nit.isAlive()) {
			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.drawString("Your score is " + score, 10, 165);
		}
		firstRender = false;
	}

}
