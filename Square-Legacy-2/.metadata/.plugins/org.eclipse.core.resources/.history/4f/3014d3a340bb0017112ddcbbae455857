package com.IB.SL;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class WindowHandler implements WindowListener, FocusListener{
	
	private final Game game;
	
	public WindowHandler(Game game) {
		this.game = game;
		this.game.frame.addWindowListener(this);
		this.game.addFocusListener(this);
	}

	@Override
	public void windowActivated(WindowEvent event) {
	}

	@Override
	public void windowClosed(WindowEvent event) {
		if (game.gameState == game.gameState.INGAME || game.gameState == game.gameState.INGAME_A) {
			Game.getGame().quit();
		}
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
		if (game.gameState == game.gameState.INGAME || game.gameState == game.gameState.INGAME_A)
		game.switchState(game.gameState.PAUSE);
	}

	@Override
	public void windowOpened(WindowEvent event) {
	}

	@Override
	public void focusGained(FocusEvent e) {
		
	}

}
