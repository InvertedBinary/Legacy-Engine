package com.IB.LE2.util;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import com.IB.LE2.Game;

public class WindowHandler implements WindowListener, FocusListener{
	
	private final Game game;
	
	public WindowHandler(Game game) {
		this.game = game;
		this.game.frame.addWindowListener(this);
		this.game.addFocusListener(this);
	}

	
	//TODO: Ping current menu upon state changes
	
	@Override
	public void windowActivated(WindowEvent event) {
	}

	@Override
	public void windowClosed(WindowEvent event) {
		//Boot.get().quit(); <= Should be called, but is being temporarily removed
						      // as it gets called when switching in/out of fullscreen mode
							  // causing it to end the process
	}

	@Override
	public void windowClosing(WindowEvent event) {
    
	}
	
	@Override
	public void focusLost(FocusEvent e) {
	//	if (game.gameState == game.gameState.INGAME || game.gameState == game.gameState.INGAME_A)
	//	game.switchState(game.gameState.PAUSE);	
	}

	@Override
	public void windowDeactivated(WindowEvent event) {
	}

	@Override
	public void windowDeiconified(WindowEvent event) {
	}

	@Override
	public void windowIconified(WindowEvent event) {
	}

	@Override
	public void windowOpened(WindowEvent event) {
	}

	@Override
	public void focusGained(FocusEvent e) {
		
	}

}
