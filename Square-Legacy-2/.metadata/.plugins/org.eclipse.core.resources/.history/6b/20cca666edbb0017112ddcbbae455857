package com.IB.SL.graphics.UI;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import com.IB.SL.Boot;
import com.IB.SL.Game;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.input.Mouse;
import com.IB.SL.util.SaveGame;
import com.IB.SL.util.Sound;
import com.IB.SL.util.TextBox;

@SuppressWarnings("static-access")
public class CheckBounds extends UI implements Serializable{
	
	
	public static boolean overContinue = false;
	public static boolean overChars = false;
	public static boolean overMenu = false;
	public static boolean overQuit = false;
	public static boolean overResume = false;
	public static boolean overMenuPause = false;
	public static boolean overQuitPause = false;
	public static boolean newCharMenu = false;
	public String desc = "";
	public boolean overDelFiles = false;
	public boolean overTutorial = false;

	public String save1 = "(Open)";
	public String save2 = "(Open)";
	public String save3 = "(Open)";
	public String save4 = "(Open)";
	transient public TextBox name;
	transient public TextBox cmd;

	
	public String saveSelected = "";
	public int overTrash = 0;
	public int overSave = 0;
	transient Sprite playersprite = Sprite.playerback;
	
	
	public int R = 0;
	public int G = 0;
	public int B = 0;
	
	
	
	
	
	public void checkMenu() {
		if(!Boot.get().gui.charMenu) {
		if (Boot.get().gameState == Boot.get().gameState.MENU) {
				if (checkBounds(29, 96, 243, 24, true, true)) {
				overContinue = true;
				if (Mouse.getButton() == 1 && !Game.loading) {
					Game.loading = true;
					Sound.Play(Sound.Click, false);
					Mouse.setMouseB(-1);
				}
			} else {
				overContinue = false;
			}
				
				if (checkBounds(29, 132, 243, 24, true, true)) {
					overChars = true;
					if (Mouse.getButton() == 1) {
						Boot.get().gui.charMenu = true;
						Sound.Play(Sound.Click, false);
						Mouse.setMouseB(-1);
					}
				} else {
					overChars = false;
				}
		
					}
		} else if (Boot.get().gui.charMenu && newCharMenu == false) {
			
			if (checkBounds(229, 140, 16, 16, true, true)) {
				if (Mouse.getButton() == 1) {
				Boot.get().gui.charMenu = false;
				Sound.Play(Sound.Click, false);
				Mouse.setMouseB(-1);
				}
			}
			
			if (checkBounds(252, 140, 37, 16, true, true)) {
				if (Mouse.getButton() == 1) {
					Boot.get().switchCharacter(saveSelected);
					/*if (Game.getGame().runTut) {
					Game.getGame().getPlayer().setPosition(71, 12, Maps.tutWorldId, true);
					}*/
				Sound.Play(Sound.Click, false);
				Mouse.setMouseB(-1);
				Boot.get().gui.charMenu = false;
				}
			}
			
			if (checkBounds(10, 10, 16, 16, true, true)) {
					if (!save1.equals("(Open)")) {
						overSave = 1;
						if (Mouse.getButton() == 1) {
					saveSelected = save1;
				Sound.Play(Sound.Click, false);
				Mouse.setMouseB(-1);
					}
				}
			} else {
				if (overSave == 1) {
					overSave = 0;
				}
			}
			
			if (checkBounds(10, 42, 16, 16, true, true)) {
					if (!save2.equals("(Open)")) {
						overSave = 2;
						if (Mouse.getButton() == 1) {
					saveSelected = save2;
				Sound.Play(Sound.Click, false);
				Mouse.setMouseB(-1);
					}
				}
			} else {
				if (overSave == 2) {
					overSave = 0;
				}
			}
			
			if (checkBounds(10, 74, 16, 16, true, true)) {
					if (!save3.equals("(Open)")) {
						overSave = 3;
						if (Mouse.getButton() == 1) {
					saveSelected = save3;
				Sound.Play(Sound.Click, false);
				Mouse.setMouseB(-1);
					}
				}
			} else {
				if (overSave == 3) {
					overSave = 0;
				}
			}
			
			if (checkBounds(10, 106, 16, 16, true, true)) {
					if (!save4.equals("(Open)")) {
							overSave = 4;
						if (Mouse.getButton() == 1) {
					saveSelected = save4;
				Sound.Play(Sound.Click, false);
				Mouse.setMouseB(-1);
					}
				}
			} else {
				if (overSave == 4) {
					overSave = 0;
				}
			}
			
			if (checkBounds(206, 140, 16, 16, true, true)) {
				if (Mouse.getButton() == 1) {
					newCharMenu = true;
				Sound.Play(Sound.Click, false);
				Mouse.setMouseB(-1);
					}
				}
			
			if (checkBounds(278, 10, 14, 16, true, true)) {
					if (!save1.equals("(Open)")) {
						overTrash = 1;
						if (Mouse.getButton() == 1) {
					SaveGame.deleteCharacter(save1);
					save1 = save2;
					save2 = save3;
					save4 = "(Open)";
				if (Game.PersonNameGetter == save1) {
					Boot.get().switchCharacter(saveSelected);
				}
				Sound.Play(Sound.Click, false);
				Mouse.setMouseB(-1);
					}
				}
			} else {
				if (overTrash == 1) {
					overTrash = 0;
				}
			}
			
			if (checkBounds(278, 42, 14, 16, true, true)) {
					if (!save2.equals("(Open)")) {
						overTrash = 2;
						if (Mouse.getButton() == 1) {
					SaveGame.deleteCharacter(save2);
					save2 = save3;
					save3 = save4;
					save4 = "(Open)";
					if (Game.PersonNameGetter == save2) {
						Boot.get().switchCharacter(saveSelected);
					}
	
				Sound.Play(Sound.Click, false);
				Mouse.setMouseB(-1);
					}
				}
			} else {
				if (overTrash == 2) {
					overTrash = 0;
				}
			}
			
			if (checkBounds(278, 74, 14, 16, true, true)) {
					if (!save3.equals("(Open)")) {
						overTrash = 3;
						if (Mouse.getButton() == 1) {
					SaveGame.deleteCharacter(save3);
					save3 = save4;
					save4 = "(Open)";
					if (Game.PersonNameGetter == save3) {
						Boot.get().switchCharacter(saveSelected);
					}
				Sound.Play(Sound.Click, false);
				Mouse.setMouseB(-1);
					}
				}
			} else {
				if (overTrash == 3) {
					overTrash = 0;
				}
			}
			
			if (checkBounds(278, 106, 14, 16, true, true)) {
					if (!save4.equals("(Open)")) {
						overTrash = 4;
						if (Mouse.getButton() == 1) {
					SaveGame.deleteCharacter(save4);
					save4 = "(Open)";
					if (Game.PersonNameGetter == save4) {
						Boot.get().switchCharacter(saveSelected);
					}
				Sound.Play(Sound.Click, false);
				Mouse.setMouseB(-1);
					}
				}
			} else {
				if (overTrash == 4) {
					overTrash = 0;
				}
			}
			
			try {
				
			if (Boot.get().getCharDirs().get(0) != null) {
				save1 = Boot.get().getCharDirs().get(0);
			} else {
				save1 = "(Open)";
			}
			if (Boot.get().getCharDirs().get(1) != null) {
				save2 = Boot.get().getCharDirs().get(1);
			} else {
				save2 = "(Open)";
			}
			if (Boot.get().getCharDirs().get(2) != null) {
				save3 = Boot.get().getCharDirs().get(2);
			} else {
				save3 = "(Open)";
			}
			if (Boot.get().getCharDirs().get(3) != null) {
				save4 = Boot.get().getCharDirs().get(3);
			} else {
				save4 = "(Open)";
			}
		} catch (Exception e) {
		}
				
			
			
		} else if (newCharMenu) {
			
			if (checkBounds(129, 68, 104, 22, true, true)) {
				overTutorial = true;
				} else {
					overTutorial = false;
			}
			
			if (checkBounds(132, 71, 16, 16, true, true)) {
				if (Mouse.getButton() == 1) {
					Boot.get().runTut = !Boot.get().runTut;
					name.focused = false;
					}
				}
			
			//Character Sprite Preview
			
			if (checkBounds(104, 135, 8, 9, true, true)) {
				if (Mouse.getButton() == 1) {
					playersprite = Sprite.playerup;
					}
				}
			
			if (checkBounds(104, 148, 8, 9, true, true)) {
				if (Mouse.getButton() == 1) {
					playersprite = Sprite.playerback;
					}
				}
			
			if (checkBounds(97, 142, 9, 8, true, true)) {
				if (Mouse.getButton() == 1) {
					playersprite = Sprite.playerleft;
					}
				}
			
			if (checkBounds(110, 142, 9, 8, true, true)) {
				if (Mouse.getButton() == 1) {
					playersprite = Sprite.playerright;
					}
				}
			
			//END CHARACTER SPRITE PREVIEW
			
			//RGB SETTERS
			
			if (checkBounds(142, 117, 12, 12, true, true)) {
				if (Mouse.getButton() == 1) {
					if (R < 255) {
						R++;
					}
					Mouse.setMouseB(-1);	
				}
				}
			
			if (checkBounds(142, 133, 12, 12, true, true)) {
				if (Mouse.getButton() == 1) {
					if (G < 255) {
						G++;
					}
					Mouse.setMouseB(-1);
					}
				}
			
			if (checkBounds(142, 149, 12, 12, true, true)) {
				if (Mouse.getButton() == 1) {
					if (B < 255) {
						B++;
					}
					Mouse.setMouseB(-1);
					}
				}
			
			
			if (checkBounds(155, 117, 12, 12, true, true)) {
				if (Mouse.getButton() == 1) {
					if (R > 0) {
						R--;
					}
					Mouse.setMouseB(-1);	
				}
				}
			
			if (checkBounds(155, 133, 12, 12, true, true)) {
				if (Mouse.getButton() == 1) {
					if (G > 0) {
					G--;
					}
					Mouse.setMouseB(-1);
					}
				}
			
			if (checkBounds(155, 149, 12, 12, true, true)) {
				if (Mouse.getButton() == 1) {
					if (B > 0) {
						B--;
					}
					Mouse.setMouseB(-1);
					}
				}
			
			
			
			
			
			
			//END RGB SETTERS
			
			
			
//			if (checkBounds(132, 92, 124, 22, true, true)) {
//				overPerma = true;
//				name.focused = false;
//				} else {
//					overPerma = false;
//			}
//			
//			if (checkBounds(135, 69, 16, 16, true, true)) {
//				if (Mouse.getButton() == 1) {
//					Game.getGame().perma = !Game.getGame().perma;
//				Sound.Play(Sound.Click, false);
//				Mouse.setMouseB(-1);
//					}
//				}
			
			

			if (checkBounds(254, 145, 16, 16, true, true)) {
				if (Mouse.getButton() == 1) {
					newCharMenu = false;
			
					name.reset(false);
				Sound.Play(Sound.Click, false);
				Mouse.setMouseB(-1);
					}
				}
			
			if (checkBounds(277, 145, 16, 16, true, true)) {
				if (Mouse.getButton() == 1) {
			Boot.get().switchCharacter(name.getText(true));
			saveSelected = (name.getText(true));
			newCharMenu = false;
			name.reset(false);
			
			Boot.get().save(true);
				Sound.Play(Sound.Click, false);
				Mouse.setMouseB(-1);
					}
				}
			
		}
	}
	
	public boolean checkBounds(int x, int y, int width, int height, boolean toScale, boolean temp) {
		if (toScale) {
		x *= Boot.get().scale;
		y *= Boot.get().scale;
		width *= Boot.get().scale;
		height *= Boot.get().scale;
		}
		
		if (Mouse.getX() < x + width && Mouse.getX() > x && Mouse.getY() < y + height && Mouse.getY() > y ) {
			return true;
		} else {
		return false;
		}
	}	

	public boolean checkBounds(int x, int y, int width, int height, boolean toScale) {
		if (toScale) {
		x *= Boot.get().scale;
		y *= Boot.get().scale;
		width *= Boot.get().scale;
		height *= Boot.get().scale;
		}
		
		System.out.println("X: " + Screen.xo + "," + x);
		if (Screen.xo < x + width && Screen.xo > x && Screen.yo < y + height && Screen.yo > y ) {
			return true;
		} else {
		return false;
		}
	}	
	
		public void checkDeath() {
		if (Boot.get().gameState == Boot.get().gameState.DEATH) {
				/*if (Mouse.getX() < 85 + 300 && Mouse.getX() > 85 - 5
						&& Mouse.getY() < 270 + 105 && Mouse.getY() > 270 - 10) {*/
					if (checkBounds(19, 65, 76, 28, true, true)) {
					overMenu = true;
					if (Mouse.getButton() == 1) {
						Sound.Play(Sound.Click, false);
						Game.switchState(Boot.get().gameState.MENU);
						Boot.get().deathTimeTicks = 0;
						Boot.get().deathTimeSec = 0;

					}
					} else {
					overMenu = false;
				}
				
				
					/*if (Mouse.getX() < 808 + 237 && Mouse.getX() > 808 - 4
							&& Mouse.getY() < 268 + 107 && Mouse.getY() > 268 - 8) {*/
						if (checkBounds(200, 65, 60, 28, true, true)) {
						overQuit = true;
						if (Mouse.getButton() == 1) {
							Sound.Play(Sound.Click, false);
							Boot.get().quit();
						}
					} else {
						overQuit = false;
					}
					}
		
		}

	public void checkPause() {
		
		if (Boot.get().gameState == Boot.get().gameState.PAUSE) {
			if (Boot.get().getPlayer().input.exclamation) {
				System.out.println("true");
				this.cmd.focused = true;
			}	
			if (checkBounds(274, 5, 20, 20, true, true)) {
				desc = "Returns To Previous Menu";
				if (Mouse.getButton() == 1) {
					Sound.Play(Sound.Click, false);
					Boot.get().gui.options = !Boot.get().gui.options;
					Mouse.setMouseB(-1);
				}
			}
			
			if (Boot.get().gui.options == true) {
				if (checkBounds(249, 5, 20, 20, true, true)) {
					desc = "Opens The Help PDF\n(Must have a PDF reader installed!)";
					if (Mouse.getButton() == 1) {
						if (Desktop.isDesktopSupported()) {
							try {
								File myFile = new File(SaveGame.createDataFolder() + "/Help File.pdf");
								Desktop.getDesktop().open(myFile);
							} catch (IOException ex) {
								System.err.println("Cannot Open PDF, No Application Registered For PDFs");
							}
						}
						Sound.Play(Sound.Click, false);
						Mouse.setMouseB(-1);
					}
				}
				
				if (checkBounds(8, 36, 20, 20, true, true)) {
						desc = "Toggle Autosave";
					if (Mouse.getButton() == 1) {
						Boot.get().loadProp.savePrefs(Boot.get());
						Sound.Play(Sound.Click, false);
						Boot.get().autoSave = !Boot.get().autoSave;
						Mouse.setMouseB(-1);
					}
				} else {
				}
				
					if (checkBounds(8, 66, 20, 20, true, true)) {
						desc = "Delete All Save Files Associated\nWith This Square Legacy Profile";
						overDelFiles = true;
						if (Mouse.getButton() == 1) {
							Sound.Play(Sound.Click, false);
							Player p = Boot.get().getPlayer();
						
							SaveGame.deleteCharacter(p.name);
							//p.reset(p);
							p.invokeLoad(p);
							Mouse.setMouseB(-1);
						}
					} else {
						overDelFiles = false;
					}
					
						if (checkBounds(8, 96, 20, 20, true, true)) {
							desc = "Toggle Multiplayer (Beta) Features\nFor Developers Only!";
							if (Mouse.getButton() == 1) {
								Boot.get().loadProp.savePrefs(Boot.get());
								//Game.getGame().multiplayerEnabled = !Game.getGame().multiplayerEnabled;
								Sound.Play(Sound.Click, false);
								Mouse.setMouseB(-1);
							}
						} else {
						}
						
						
				
			} else {
				
				
				if (checkBounds(25, 93, 243, 24, true, true)) {
				overResume = true;
				if (Mouse.getButton() == 1) {
					Sound.Play(Sound.Click, false);
					Game.switchState(Boot.get().gameState.INGAME);
				}
			} else {
				overResume = false;
			}
				if (checkBounds(30, 133, 114, 24, true, true)) {
				overMenuPause = true;
				if (Mouse.getButton() == 1) {
					Sound.Play(Sound.Click, false);
					Game.switchState(Boot.get().gameState.MENU);
				}
			} else {
				overMenuPause = false;
			}
				if (checkBounds(162, 133, 110, 24, true, true)) {
				overQuitPause = true;
				if (Mouse.getButton() == 1) {
						Sound.Play(Sound.Click, false);
						Boot.get().quit();
					}
			} else {
				overQuitPause = false;
			}
			}
		}
	}
}
