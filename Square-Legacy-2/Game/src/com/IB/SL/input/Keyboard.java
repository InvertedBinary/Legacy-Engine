package com.IB.SL.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
	
	public boolean suspended = false;
	
	private final int numOfKeys = 520;
	
	private boolean[] keys = new boolean[numOfKeys]; //65536
	private boolean[] toggles = new boolean[numOfKeys];
	private int[] toggle_helper = new int[numOfKeys];
	
	public boolean up, down, left, right, toggleDevModeInfo, DevMode, 
	Sprint, Pause, commandMode, jump, a1, a2, a3, a4, a5, a6, a7, a8,
	a9, a0, ab, inventory, gs1, gs2, gs3, gs4, space, map, capture,
	dropItem, toggleChatWindow, abTab, generalActivator, save, load, 
	inventory_Equipment, hk_manapot, hk_healthpot, hk_staminapot, buildMode,
	console;
	
	public boolean a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, enter, backspace, delete, tab, ctrl, arrow_Left, arrow_Right, exclamation; 
	
	public void suspendInput() {
		this.suspended = true;
		for (int i = 0; i < keys.length; i++) {
			keys[i] = false;
		}
		for (int i = 0; i < toggles.length; i++) {
			toggles[i] = false;
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

		Sprint = keys[KeyEvent.VK_SHIFT];
		save = keys[KeyEvent.VK_K];
		load = keys[KeyEvent.VK_N];

		ab = a1 || a2 || a3 || a4 || a5 || a6 || a7 || a8 || a9 || a0;
		abTab = keys[KeyEvent.VK_C];
		
		hk_healthpot = keys[KeyEvent.VK_V];
		hk_manapot = keys[KeyEvent.VK_C];
		hk_staminapot = keys[KeyEvent.VK_X];
		
		inventory = keys[KeyEvent.VK_E];
		inventory_Equipment = keys[KeyEvent.VK_Q];

		toggleChatWindow = keys[KeyEvent.VK_T];
		generalActivator = keys[KeyEvent.VK_F];
		
		buildMode = (true) ? toggles[KeyEvent.VK_B] : false;
		toggles[KeyEvent.VK_B] = (true) ? toggles[KeyEvent.VK_B] : false;
		
		}
		
		toggleDevModeInfo = keys[KeyEvent.VK_C];
		commandMode = keys[KeyEvent.VK_ALT];
		DevMode = keys[KeyEvent.VK_CONTROL];
		Pause = keys[KeyEvent.VK_ESCAPE];
	
		console = keys[KeyEvent.VK_BACK_QUOTE];
		
		/**
		 * gameState Keys:
		 */
		
		gs1 = keys[KeyEvent.VK_NUMPAD1] || keys[KeyEvent.VK_F1];
		gs2 = keys[KeyEvent.VK_NUMPAD2] || keys[KeyEvent.VK_F2];
		gs3 = keys[KeyEvent.VK_NUMPAD3] || keys[KeyEvent.VK_F3];
		gs4 = keys[KeyEvent.VK_ESCAPE];
		map = keys[KeyEvent.VK_M];
		capture = keys[KeyEvent.VK_F9];
		dropItem = keys[KeyEvent.VK_Q];
		
		
		
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

		
		
		a1 = keys[KeyEvent.VK_1];
		a2 = keys[KeyEvent.VK_2];
		a3 = keys[KeyEvent.VK_3];
		a4 = keys[KeyEvent.VK_4];
		a5 = keys[KeyEvent.VK_5];
		a6 = keys[KeyEvent.VK_6];
		a7 = keys[KeyEvent.VK_7];
		a8 = keys[KeyEvent.VK_8];
		a9 = keys[KeyEvent.VK_9];
		a0 = keys[KeyEvent.VK_0];
		
		
		
		exclamation = keys[KeyEvent.VK_EXCLAMATION_MARK];
		enter = keys[KeyEvent.VK_ENTER];
		space = keys[KeyEvent.VK_SPACE];
		backspace = keys[KeyEvent.VK_BACK_SPACE];
		delete = keys[KeyEvent.VK_DELETE];
		tab = keys[KeyEvent.VK_TAB];
		ctrl = keys[KeyEvent.VK_CONTROL];
		arrow_Left = keys[KeyEvent.VK_LEFT];
		arrow_Right = keys[KeyEvent.VK_RIGHT];
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
	}

	public void keyTyped(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		if(e.getKeyCode() == KeyEvent.VK_CONTROL) {		}
	}
	

}
