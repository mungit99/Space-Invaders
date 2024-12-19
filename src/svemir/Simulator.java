package svemir;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Simulator extends Frame {
	
	private Svemir svemir;
	private Generator generator;
	private Panel komande;

	public Simulator() {
		setBounds(700, 300, 200, 400);
		setResizable(false);
		populateWindow();
		setVisible(true);
	}

	private void populateWindow() {
		svemir = new Svemir();
		generator = new Generator(svemir);
		addCommands();
		addListeners();
		add(svemir, BorderLayout.CENTER);
		svemir.pokreni();
		generator.pokreni();
	}
	
	private void addListeners() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				svemir.prekini();
				generator.prekini();
				dispose();			
			}
		});	
		addKeyListener(new KeyAdapter() {	
			@Override
			public void keyPressed(KeyEvent e) {
				if(!svemir.getThread().isAlive()) return;
				switch(e.getKeyCode()) {
					case KeyEvent.VK_LEFT:
						if(svemir.getRadi()) {
							svemir.getIgrac().setX(-4);
							svemir.repaint();
						}
						break;
					case KeyEvent.VK_RIGHT:
						if(svemir.getRadi()) {
							svemir.getIgrac().setX(4);
							svemir.repaint();
						}
						break;
					case KeyEvent.VK_SPACE:
						if(svemir.getRadi()) {
							if(svemir.cur == 0) break;
							svemir.cur--;
							synchronized (svemir.getMeci()) {
								svemir.getMeci().offer(new Metak(svemir.getIgrac().getX(), 289));
								svemir.repaint();
							}
						}
						break;
					case KeyEvent.VK_P:
						if(svemir.getRadi()) {
							svemir.zaustavi();
							generator.zaustavi();
						}
						else {
							svemir.kreni();
							generator.kreni();
						}
						svemir.repaint();
						break;
				}
			}
		});
	}
	
	private void addCommands() {
		komande = new Panel();
		Button kreni = new Button("Pokreni!");
		kreni.addActionListener((ae) -> {
			svemir.kreni();
			generator.kreni();
			kreni.setEnabled(false);
			requestFocus();
		});
		komande.add(kreni);
		add(komande, BorderLayout.SOUTH);	
	}

	public static void main(String[] args) {
		new Simulator();
	}

}
