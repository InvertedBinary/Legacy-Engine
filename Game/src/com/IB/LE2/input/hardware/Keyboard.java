package com.IB.LE2.input.hardware;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
	
	public boolean suspended = false;
	
	private final int numOfKeys = 520;
	
	private boolean[] keys = new boolean[numOfKeys]; //65536
	
	private boolean[] ticks = new boolean[numOfKeys];

	private boolean[] toggles = new boolean[numOfKeys];
	public int[] toggle_helper = new int[numOfKeys];
	
	private boolean up, down, left, right, 
	sprint, crouch, pan;
	
	private boolean a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, enter, backspace, delete, tab, ctrl, arrow_Left, arrow_Right, exclamation; 
	
	public boolean getKeyState(String name) {
		if (suspended) return false;
		switch (name.toLowerCase()) {
		case "up":
			return up;
		case "down":
			return down;
		case "left":
			return left;
		case "right":
			return right;
		case "sprint":
			return sprint;
		case "crouch":
			return crouch;
		case "ctrl":
			return ctrl;
		case "r":
			return r;
		case "pan":
			return pan;
		default:
			return false;
		}
	}
	
	public void suspendInput() {
		this.suspended = true;
		for (int i = 0; i < numOfKeys; i++) {
			keys[i] = false;
			ticks[i] = false;
			toggles[i] = false;
			toggle_helper[i] = 0;
		}
	}
	
	public void resumeInput() {
		this.suspended = false;
	}
	
	public void update() {
		if (!suspended) {
		up = toggles[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		crouch = keys[KeyEvent.VK_CONTROL] || keys[KeyEvent.VK_D];
		
		pan = keys[KeyEvent.VK_C];
		
		/**
		 * gameState Keys:
		 */	
		
		/**
		 * Alphabet & Numerals
		 */
		a = keys[KeyEvent.VK_A];
		b = keys[KeyEvent.VK_B];
		c = keys[KeyEvent.VK_C];
		d = keys[KeyEvent.VK_D];
		e = keys[KeyEvent.VK_E];
		f = keys[KeyEvent.VK_F];
		g = keys[KeyEvent.VK_G];
		h = keys[KeyEvent.VK_H];
		i = keys[KeyEvent.VK_I];
		j = keys[KeyEvent.VK_J];
		k = keys[KeyEvent.VK_K];
		l = keys[KeyEvent.VK_L];
		m = keys[KeyEvent.VK_M];
		n = keys[KeyEvent.VK_N];
		o = keys[KeyEvent.VK_O];
		p = keys[KeyEvent.VK_P];
		q = keys[KeyEvent.VK_Q];
		r = keys[KeyEvent.VK_R];
		s = keys[KeyEvent.VK_S];
		t = keys[KeyEvent.VK_T];
		u = keys[KeyEvent.VK_U];
		v = keys[KeyEvent.VK_V];
		w = keys[KeyEvent.VK_W];
		x = keys[KeyEvent.VK_X];
		y = keys[KeyEvent.VK_Y];
		z = keys[KeyEvent.VK_Z];

		sprint = keys[KeyEvent.VK_SHIFT];
		
		exclamation = keys[KeyEvent.VK_EXCLAMATION_MARK];
		enter = keys[KeyEvent.VK_ENTER];
		backspace = keys[KeyEvent.VK_BACK_SPACE];
		delete = keys[KeyEvent.VK_DELETE];
		tab = keys[KeyEvent.VK_TAB];
		ctrl = keys[KeyEvent.VK_CONTROL];
		arrow_Left = keys[KeyEvent.VK_LEFT];
		arrow_Right = keys[KeyEvent.VK_RIGHT];
		
		
		
		for (int i = 0; i < ticks.length; i++) {
			ticks[i] = false;
		}
		}
	}

	
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		
		if (keys[e.getKeyCode()] && toggles[e.getKeyCode()] == false) {
			toggles[e.getKeyCode()] = true;
		}
		
		if (toggle_helper[e.getKeyCode()] > 0) {
			toggles[e.getKeyCode()] = false;
			toggle_helper[e.getKeyCode()] = 0;
		}
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		
		if (toggles[e.getKeyCode()] == true) {
			toggle_helper[e.getKeyCode()]++;
		}
		
		ticks[e.getKeyCode()] = true;
	}

	public void keyTyped(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		if(e.getKeyCode() == KeyEvent.VK_CONTROL) {		}
	}
	

}
