package com.IB.SL.graphics.UI;

import java.util.ArrayList;

import com.IB.SL.Game;
import com.IB.SL.Game.gameState;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.graphics.AnimatedSprite;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.graphics.font;
import com.IB.SL.graphics.font8x8;
import com.IB.SL.input.Mouse;
import com.IB.SL.util.SaveGame;
import com.IB.SL.util.Sound;
import com.IB.SL.util.TextBox;

public class GUI extends CheckBounds {
	private static final long serialVersionUID = 1L;

	transient public font font;
	transient public font8x8 font8x8;
	boolean options = false;
	public boolean charMenu = false;

	transient int colortime = 0;
	transient int colorMod = 0;
	// TODO: Red outline on screen -- lowhealth
	public transient int displayTime = 0;
	transient public int displayTimeM = 0;
	transient public int displayTimeS = 0;

	private transient Sprite MobHealthSprite = Sprite.MobHealthBar20;

	private transient Sprite HealthSprite = Sprite.HealthBar20;
	private transient Sprite ManaSprite = Sprite.manabar20;
	private transient Sprite StaminaSprite = Sprite.manabar20;
	public transient int fadeTime;
	public transient int fadeTime2;
	public transient int fadeTimeS;
	public transient int fadeTimeS2;

	Player tempLoadInfo = null;

	transient public AnimatedSprite healthbar = new AnimatedSprite(SpriteSheet.anim_hb, 72, 32, 61);
	transient public AnimatedSprite manabar = new AnimatedSprite(SpriteSheet.anim_mb, 72, 32, 61);
	transient public AnimatedSprite staminabar = new AnimatedSprite(SpriteSheet.anim_sb, 72, 32, 61);
	transient public AnimatedSprite expbar = new AnimatedSprite(SpriteSheet.anim_eb, 156, 32, 151);

	public boolean displayH = true, displayM = true, displayS = true;
	public int yOffM = 130, yOffH = 143, yOffS = 156;

	public GUI() {
		font = new font();
		font8x8 = new font8x8();
	}

	public void update() {
		if (name == null) {
			name = new TextBox(90, 38, 204, 20, Game.get().key, 12, false);
		}
		if (Game.get().gameState == Game.get().gameState.MENU && newCharMenu) {
			name.update(); // LAG with using numpad
		} else {
			name.reset(false);
		}

		if (cmd == null) {
			cmd = new TextBox(5, 5, 266, 19, Game.get().key, -1, false);
			cmd.desc = "Command:";
			cmd.useCmds = true;
			cmd.acceptable.add("!");
			cmd.acceptable.add(",");
			cmd.acceptable.add(".");
		}
		if (Game.get().gameState == Game.get().gameState.PAUSE) {
			cmd.update();
		} else {
			cmd.reset(false);
		}
		// expBar.update();
	}

	public void render(Screen screen) {

	}

	public void renderInventory(Screen screen, Player player) {
		player.getInventory().render(screen);
		player.equipment.render(screen);
		player.quests.render(screen);
		player.effects.render(screen);
		renderName(screen, player.name, -13, 152, -4, false, false, false);
		font8x8.render(50, 141, -2, 0xff000000, "" + player.Lvl, screen, false, true, 1);
		font8x8.render(233, 141, -2, 0xff000000, "" + (player.Lvl + 1), screen, false, true, 1);
		screen.renderSprite(73, 140, renderBar(150, expbar, player.maxExp(), player.currentExp()), false); // 130
	}

	public void renderInventory(Screen screen, Player player, int x, int y) {
		renderName(screen, player.name, -13, 152, -4, false, false, false);
		player.getInventory().render(screen);
		player.getInventory().move(x, y);
		player.equipment.render(screen);
		player.quests.render(screen);
	}

	public void renderInventory(Screen screen, Entity e, Player player) {
		e.getChestInventory().render(screen, player);
	}

	public void renderBench(Screen screen, Entity e, Player player) {
		e.getChestInventory().renderBench(screen, player);
	}

	public void renderName(Screen screen, String PersonNameGetter, int x, int y, int spacing, boolean fixed,
			boolean background, boolean font8) {
		int color = 0xFFFFFF;
		if (PersonNameGetter.equalsIgnoreCase("Nate") || PersonNameGetter.equalsIgnoreCase("coinreturn1")) {
			colorMod++;
			if ((colorMod % 60) == 0) {
				colortime++;
				colorMod = 0;
			}
			if (colortime == 0)
				color = 0x990000;

			if (colortime == 1)
				color = 0x8899f7;

			if (colortime == 2)
				color = 0x2d1fbd;

			if (colortime == 3)
				color = 0x990000;

			if (colortime == 4)
				color = 0xCC9900;

			if (colortime == 5)
				color = 0xFFFFFF;

			if (colortime == 6)
				colortime = 0;

		} else if (PersonNameGetter.equalsIgnoreCase("Robbie") || PersonNameGetter.equalsIgnoreCase("R0881E")
				|| PersonNameGetter.equalsIgnoreCase("GeekSquad2")) {
			color = 0x8899f7;
		} else if (PersonNameGetter.equalsIgnoreCase("Pascal") || PersonNameGetter.equalsIgnoreCase("Nickavia")) {
			color = 0x0066FF;
		} else if (PersonNameGetter.equalsIgnoreCase("*[A]7381")
				|| PersonNameGetter.equalsIgnoreCase("AggressiveChairlift")) {
			color = 0x990000;
		} else if (PersonNameGetter.equalsIgnoreCase("Meg") || PersonNameGetter.equalsIgnoreCase("Megan")) {
			color = 0xF0C4E5;
		} else if (PersonNameGetter.equalsIgnoreCase("Garrett")) {
			color = 0x0066ff;
		} else if (PersonNameGetter.equalsIgnoreCase("Vaxonus")) {
			color = 0x990000;
		} else {
			color = 0xFFFFFF;
		}

		if (!font8) {
			font.render(x, y, spacing, color, PersonNameGetter, screen, fixed, background);
		} else {
			font8x8.render(x, y, spacing, color, PersonNameGetter, screen, fixed, background);
		}
	}

	public void renderString(Screen screen, String PersonNameGetter, int x, int y, int spacing, int color,
			boolean fixed, boolean background, boolean font8) {
		if (!font8) {
			font.render(x, y, spacing, color, PersonNameGetter, screen, fixed, background);
		} else {
			font8x8.render(x, y, spacing, color, PersonNameGetter, screen, fixed, background);
		}
	}

	public void renderAbilities(Screen screen, Player player) {
		if (!player.getInventory().overMoveLock && !player.getInventory().moveLock) {
			player.abilities.render(screen);
		}
	}
	
	public void renderBuild(Screen screen, Player player) {
		
		
	}
	

	// 263, 42, 55[mana]

	public void renderMainMenu(Screen screen) {
		if (!charMenu) {

			fadeTime2++;
			screen.clear();
			if (overContinue == false && overChars == false) {
				screen.renderSprite(0, 0, Sprite.Title, false);
			}
			if (overContinue == true) {
				screen.renderSprite(0, 0, Sprite.title_overContinue, false);
			}

			if (overChars == true) {
				screen.renderSprite(0, 0, Sprite.title_overChars, false);
			}
			if (Game.loading && Game.get().gameState == gameState.MENU) {
				// screen.renderSheet(0, 0, SpriteSheet.loading, false);
				if (fadeTime2 % 3 == 0) {
					fadeTime--;
					fadeTime2 = 0;
				}
				screen.fade(fadeTime, fadeTime, fadeTime);
			}
		} else if (charMenu && newCharMenu == false) {
			screen.renderSprite(0, 0, Sprite.title_Chars, false);
			Game.get().font.render(30, 9, save1, screen, false, false);
			Game.get().font.render(30, 41, save2, screen, false, false);
			Game.get().font.render(30, 73, save3, screen, false, false);
			Game.get().font.render(30, 105, save4, screen, false, false);

			if (saveSelected.equals(save1)) {
				Game.get().font.render(-6, 9, "X", Game.get().getScreen(), false, false);
			}
			if (saveSelected.equals(save2)) {
				Game.get().font.render(-6, 41, "X", Game.get().getScreen(), false, false);
			}
			if (saveSelected.equals(save3)) {
				Game.get().font.render(-6, 73, "X", Game.get().getScreen(), false, false);
			}
			if (saveSelected.equals(save4)) {
				Game.get().font.render(-6, 105, "X", Game.get().getScreen(), false, false);
			}

			if (overTrash == 1) {
				screen.renderSprite(272, 8, Sprite.title_overTrash, false);
			}
			if (overTrash == 2) {
				screen.renderSprite(272, 40, Sprite.title_overTrash, false);
			}
			if (overTrash == 3) {
				screen.renderSprite(272, 72, Sprite.title_overTrash, false);
			}
			if (overTrash == 4) {
				screen.renderSprite(272, 104, Sprite.title_overTrash, false);
			}

			try {

				if (overSave == 1) {
					if (tempLoadInfo == null) {
						tempLoadInfo = SaveGame.load(save1);
					}
					font8x8.render(Mouse.getX() * Game.scale >> Game.TILE_BIT_SHIFT,
							Mouse.getY() * Game.scale >> Game.TILE_BIT_SHIFT, -2, 0xffffffff,
							"Level: " + tempLoadInfo.Lvl, screen, false, true, 7);
				} else if (overSave == 2) {
					if (tempLoadInfo == null) {
						tempLoadInfo = SaveGame.load(save2);

					}
					font8x8.render(Mouse.getX() * Game.scale >> Game.TILE_BIT_SHIFT,
							Mouse.getY() * Game.scale >> Game.TILE_BIT_SHIFT, -2, 0xffffffff,
							"Level: " + tempLoadInfo.Lvl, screen, false, true, 7);
				} else if (overSave == 3) {
					if (tempLoadInfo == null) {
						tempLoadInfo = SaveGame.load(save3);

					}
					font8x8.render(Mouse.getX() * Game.scale >> Game.TILE_BIT_SHIFT,
							Mouse.getY() * Game.scale >> Game.TILE_BIT_SHIFT, -2, 0xffffffff,
							"Level: " + tempLoadInfo.Lvl, screen, false, true, 7);
				} else if (overSave == 4) {
					if (tempLoadInfo == null) {
						tempLoadInfo = SaveGame.load(save4);

					}
					font8x8.render(Mouse.getX() * Game.scale >> Game.TILE_BIT_SHIFT,
							Mouse.getY() * Game.scale >> Game.TILE_BIT_SHIFT, -2, 0xffffffff,
							"Level: " + tempLoadInfo.Lvl, screen, false, true, 7);
				} else {
					tempLoadInfo = null;
				}
			} catch (Exception e) {

			}

		} else if (newCharMenu) {
			screen.renderSprite(0, 0, Sprite.title_NewChar, false);
			if (Game.get().runTut) {
				Game.get().font.render(116, 70, "X", Game.get().getScreen(), false, false);
			}

			if (overTutorial) {
				font8x8.render(Mouse.getX() * Game.scale >> Game.TILE_BIT_SHIFT,
						Mouse.getY() * Game.scale >> Game.TILE_BIT_SHIFT, -2, 0xffffffff, "Run The Tutorial", screen,
						false, true, 7);
			}

			// R = 0;
			// G = 0;
			// B = 0;

			Game.get().font8x8.render(160, 119, "" + R, Game.get().getScreen(), false, false);
			Game.get().font8x8.render(160, 135, "" + G, Game.get().getScreen(), false, false);
			Game.get().font8x8.render(160, 151, "" + B, Game.get().getScreen(), false, false);

			System.out.println("B: " + B);
			Sprite sprite = Sprite.resize(playersprite, 4);
			screen.renderMobSpriteUniversal(30, 80, sprite, R, G, B);
			// screen.renderMobSpriteUniversal(30, 80, sprite);
			// if (Game.getGame().perma) {
			// Game.getGame().font.render(-6, 139, "X",
			// Game.getGame().getScreen(), false, false);
			// }
			//
			// if (overPerma) {
			// font8x8.render(Mouse.getX() * Game.scale >> Game.TILE_BIT_SHIFT,
			// Mouse.getY() * Game.scale >> Game.TILE_BIT_SHIFT, -2, 0xffffffff,
			// "On Death You Lose Everything", screen, false, true, 7);
			// }

			name.render(screen);
		}
	}

	public void renderSplash(Screen screen) {
		fadeTimeS2++;
		if (fadeTimeS2 % 3 == 0) {
			fadeTimeS++;
			fadeTimeS2 = 0;
		}
		if (Game.loadGame) {
			screen.renderSheet(0, 0, SpriteSheet.ibLOGO, false);
		}
		screen.fade(fadeTimeS, fadeTimeS, fadeTimeS);
	}

	public void renderDeath(Screen screen) {
		screen.clear();
		if (overMenu == false && overQuit == false) {
			screen.renderAlphaSprite(0, 0, Sprite.Death, false);
		}
		if (overMenu) {
			screen.renderAlphaSprite(0, 0, Sprite.Death_Menu, false);
		}
		if (overQuit) {
			screen.renderAlphaSprite(0, 0, Sprite.Death_Quit, false);
		}

	}

	public void renderPause(Screen screen) {

		if (!options) {
			if (overResume == false && overMenuPause == false) {
				screen.renderSprite(0, 0, Sprite.pauseMenu, false);
			}
			if (overResume) {
				screen.renderSprite(0, 0, Sprite.pauseMenuResume, false);
			}
			if (overMenuPause) {
				screen.renderSprite(0, 0, Sprite.pauseMenuMenu, false);
			}
			if (overQuitPause) {
				screen.renderSprite(0, 0, Sprite.pauseMenuQuit, false);
			}

			cmd.render(screen);

		} else if (options) {

			screen.renderSprite(0, 0, Sprite.pauseOptions, false);

			Game.get().font8x8.render(5, 126, -2, desc, Game.get().getScreen(), false, false);

			if (Game.get().autoSave) {
				Game.get().font.render(-6, 37, "X", Game.get().getScreen(), false, false);
			}

			if (overDelFiles) {
				Game.get().font.render(-6, 67, "X", Game.get().getScreen(), false, false);
			}

		}
	}

	public Sprite renderBar(int size, AnimatedSprite sprite, double max, double current) {
		double currentHealth = current;
		double maxHealth = max;
		double special = maxHealth - 1;
		double barSpace = size;
		double slope = maxHealth / barSpace;
		double hb = barSpace;
		double s1 = 0;

		ArrayList<Double> variables = new ArrayList<Double>();

		for (int i = 0; i < barSpace - 2; i++) {
			if (i == 0) {
				if (maxHealth > barSpace) {
					s1 = special - slope;// 38 OR 4.75
				} else if (maxHealth <= barSpace) {
					s1 = maxHealth - slope;// 38 OR 4.75
				}
				variables.add(s1);
			} else {
				variables.add(variables.get(i - 1) - slope);
			}
		}

		if (currentHealth < maxHealth) {
			if (currentHealth <= 0) {
				hb = 0;
			} else {
				hb = 1;
				for (int i = 0; i < barSpace - 2; i++) {
					if (currentHealth >= variables.get(i)) {
						hb = barSpace - i - 1;
						break;
					}
				}
			}
		}
		sprite.setFrame((int) size - (int) hb);
		return sprite.getSprite();
	}

	public Sprite renderHealthExperiment(Screen screen, Mob mob, int size) {
		double CurrentHealth = mob.mobhealth;
		double MaxHealth = mob.maxhealth;
		double Special = MaxHealth - 1;
		double BarSpace = size;
		double Slope = MaxHealth / BarSpace;
		// double hb = 20;
		double s1 = 0;
		if (MaxHealth > BarSpace) {
			s1 = Special - Slope;// 38 OR 4.75
		} else if (MaxHealth <= BarSpace) {
			s1 = MaxHealth - Slope;// 38 OR 4.75
		}
		double s2 = s1 - Slope;// 36 OR 4.5
		double s3 = s2 - Slope;// 34 OR 4.25
		double s4 = s3 - Slope;// 32 OR 4.0
		double s5 = s4 - Slope;// 30 OR 3.75
		double s6 = s5 - Slope;// 28 OR 3.5
		double s7 = s6 - Slope;// 26 OR 3.25
		double s8 = s7 - Slope;// 24 OR 3.0
		double s9 = s8 - Slope;// 22 OR 2.75
		double s10 = s9 - Slope;// 20 OR 2.5
		double s11 = s10 - Slope;// 18 OR 2.25
		double s12 = s11 - Slope;// 16 OR 2.0
		double s13 = s12 - Slope;// 14 OR 1.75
		double s14 = s13 - Slope;// 12 OR 1.5
		double s15 = s14 - Slope;// 10 OR 1.25
		double s16 = s15 - Slope;// 8 OR 1.0
		double s17 = s16 - Slope;// 6 OR 0.75
		double s18 = s17 - Slope;// 4 OR 0.5
		double s19 = s18 - Slope;// 2 OR 0.25
		if (mob.incombat || mob.mobhealth < mob.maxhealth) {
			displayTime = 0;
		} else if (displayTime < 151) {
			displayTime++;
			displayH = false;
		}
		if (displayTime <= 150) {
			displayH = true;
			if (CurrentHealth == MaxHealth || CurrentHealth > MaxHealth) {
				HealthSprite = Sprite.HealthBar20;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s1)) {
				HealthSprite = Sprite.HealthBar19;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s2) && (CurrentHealth < s1)) {
				HealthSprite = Sprite.HealthBar18;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s3) && (CurrentHealth < s2)) {
				HealthSprite = Sprite.HealthBar17;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s4) && (CurrentHealth < s3)) {
				HealthSprite = Sprite.HealthBar16;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s5) && (CurrentHealth < s4)) {
				HealthSprite = Sprite.HealthBar15;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s6) && (CurrentHealth < s5)) {
				HealthSprite = Sprite.HealthBar14;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s7) && (CurrentHealth < s6)) {
				HealthSprite = Sprite.HealthBar13;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s8) && (CurrentHealth < s7)) {
				HealthSprite = Sprite.HealthBar12;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s9) && (CurrentHealth < s8)) {
				HealthSprite = Sprite.HealthBar11;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s10) && (CurrentHealth < s9)) {
				HealthSprite = Sprite.HealthBar10;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s11) && (CurrentHealth < s10)) {
				HealthSprite = Sprite.HealthBar9;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s12) && (CurrentHealth < s11)) {
				HealthSprite = Sprite.HealthBar8;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s13) && (CurrentHealth < s12)) {
				HealthSprite = Sprite.HealthBar7;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s14) && (CurrentHealth < s13)) {
				HealthSprite = Sprite.HealthBar6;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s15) && (CurrentHealth < s14)) {
				HealthSprite = Sprite.HealthBar5;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s16) && (CurrentHealth < s15)) {
				HealthSprite = Sprite.HealthBar4;
				screen.fade(20, 0, 0);
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s17) && (CurrentHealth < s16)) {
				HealthSprite = Sprite.HealthBar3;
				screen.fade(40, 0, 0);
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s18) && (CurrentHealth < s17)) {
				HealthSprite = Sprite.HealthBar2;
				screen.fade(60, 0, 0);
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s19) && (CurrentHealth < s18)) {
				HealthSprite = Sprite.HealthBar1;
				screen.fade(80, 0, 0);
			}
			if (CurrentHealth <= 0) {
				HealthSprite = Sprite.HealthBar0;
			}
		} else {
			HealthSprite = new Sprite(0, 0xff000000);
		}
		return HealthSprite;
	}

	public Sprite renderMobHealthExperiment(Mob mob, int size) {
		double CurrentHealth = mob.mobhealth;
		double MaxHealth = mob.maxhealth;
		double Special = MaxHealth - 1;
		double BarSpace = size;
		double Slope = MaxHealth / BarSpace;
		// double hb = 20;
		double s1 = 0;
		if (MaxHealth > BarSpace) {
			s1 = Special - Slope;// 38 OR 4.75
		} else if (MaxHealth <= BarSpace) {
			s1 = MaxHealth - Slope;// 38 OR 4.75
		}
		double s2 = s1 - Slope;// 36 OR 4.5
		double s3 = s2 - Slope;// 34 OR 4.25
		double s4 = s3 - Slope;// 32 OR 4.0
		double s5 = s4 - Slope;// 30 OR 3.75
		double s6 = s5 - Slope;// 28 OR 3.5
		double s7 = s6 - Slope;// 26 OR 3.25
		double s8 = s7 - Slope;// 24 OR 3.0
		double s9 = s8 - Slope;// 22 OR 2.75
		double s10 = s9 - Slope;// 20 OR 2.5
		double s11 = s10 - Slope;// 18 OR 2.25
		double s12 = s11 - Slope;// 16 OR 2.0
		double s13 = s12 - Slope;// 14 OR 1.75
		double s14 = s13 - Slope;// 12 OR 1.5
		double s15 = s14 - Slope;// 10 OR 1.25
		double s16 = s15 - Slope;// 8 OR 1.0
		double s17 = s16 - Slope;// 6 OR 0.75
		double s18 = s17 - Slope;// 4 OR 0.5
		double s19 = s18 - Slope;// 2 OR 0.25

		if (mob.mobhealth < mob.maxhealth || mob.incombat) {
			displayTime = 0;
		} else if (displayTime < 151) {
			displayTime++;
		}
		if (displayTime < 150) {
			if (CurrentHealth == MaxHealth || CurrentHealth > MaxHealth) {
				MobHealthSprite = Sprite.MobHealthBar20;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s1)) {
				MobHealthSprite = Sprite.MobHealthBar19;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s2) && (CurrentHealth < s1)) {
				MobHealthSprite = Sprite.MobHealthBar18;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s3) && (CurrentHealth < s2)) {
				MobHealthSprite = Sprite.MobHealthBar17;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s4) && (CurrentHealth < s3)) {
				MobHealthSprite = Sprite.MobHealthBar16;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s5) && (CurrentHealth < s4)) {
				MobHealthSprite = Sprite.MobHealthBar15;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s6) && (CurrentHealth < s5)) {
				MobHealthSprite = Sprite.MobHealthBar14;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s7) && (CurrentHealth < s6)) {
				MobHealthSprite = Sprite.MobHealthBar13;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s8) && (CurrentHealth < s7)) {
				MobHealthSprite = Sprite.MobHealthBar12;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s9) && (CurrentHealth < s8)) {
				MobHealthSprite = Sprite.MobHealthBar11;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s10) && (CurrentHealth < s9)) {
				MobHealthSprite = Sprite.MobHealthBar10;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s11) && (CurrentHealth < s10)) {
				MobHealthSprite = Sprite.MobHealthBar9;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s12) && (CurrentHealth < s11)) {
				MobHealthSprite = Sprite.MobHealthBar8;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s13) && (CurrentHealth < s12)) {
				MobHealthSprite = Sprite.MobHealthBar7;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s14) && (CurrentHealth < s13)) {
				MobHealthSprite = Sprite.MobHealthBar6;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s15) && (CurrentHealth < s14)) {
				MobHealthSprite = Sprite.MobHealthBar5;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s16) && (CurrentHealth < s15)) {
				MobHealthSprite = Sprite.MobHealthBar4;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s17) && (CurrentHealth < s16)) {
				MobHealthSprite = Sprite.MobHealthBar3;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s18) && (CurrentHealth < s17)) {
				MobHealthSprite = Sprite.MobHealthBar2;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s19) && (CurrentHealth < s18)) {
				MobHealthSprite = Sprite.MobHealthBar1;
			}
			if (CurrentHealth <= 0) {
				MobHealthSprite = Sprite.MobHealthBar0;
			}
		}
		return MobHealthSprite;
	}

	public Sprite renderManaExperiment(Mob mob, int size) {
		double CurrentHealth = mob.mana;
		double MaxHealth = mob.maxmana;
		double Special = MaxHealth - 1;
		double BarSpace = size;
		double Slope = MaxHealth / BarSpace;
		// double hb = 20;
		double s1 = 0;
		if (MaxHealth > BarSpace) {
			s1 = Special - Slope;// 38 OR 4.75
		} else if (MaxHealth <= BarSpace) {
			s1 = MaxHealth - Slope;// 38 OR 4.75
		}
		double s2 = s1 - Slope;// 36 OR 4.5
		double s3 = s2 - Slope;// 34 OR 4.25
		double s4 = s3 - Slope;// 32 OR 4.0
		double s5 = s4 - Slope;// 30 OR 3.75
		double s6 = s5 - Slope;// 28 OR 3.5
		double s7 = s6 - Slope;// 26 OR 3.25
		double s8 = s7 - Slope;// 24 OR 3.0
		double s9 = s8 - Slope;// 22 OR 2.75
		double s10 = s9 - Slope;// 20 OR 2.5
		double s11 = s10 - Slope;// 18 OR 2.25
		double s12 = s11 - Slope;// 16 OR 2.0
		double s13 = s12 - Slope;// 14 OR 1.75
		double s14 = s13 - Slope;// 12 OR 1.5
		double s15 = s14 - Slope;// 10 OR 1.25
		double s16 = s15 - Slope;// 8 OR 1.0
		double s17 = s16 - Slope;// 6 OR 0.75
		double s18 = s17 - Slope;// 4 OR 0.5
		double s19 = s18 - Slope;// 2 OR 0.25

		if (mob.incombat || mob.mana < mob.maxmana) {
			displayTimeM = 0;
		} else if (displayTimeM < 151) {
			displayTimeM++;
			displayM = false;
		}
		if (displayTimeM <= 150) {
			displayM = true;
			if (CurrentHealth == MaxHealth || CurrentHealth > MaxHealth) {
				ManaSprite = Sprite.manabar20;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s1)) {
				ManaSprite = Sprite.manabar19;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s2) && (CurrentHealth < s1)) {
				ManaSprite = Sprite.manabar18;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s3) && (CurrentHealth < s2)) {
				ManaSprite = Sprite.manabar17;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s4) && (CurrentHealth < s3)) {
				ManaSprite = Sprite.manabar16;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s5) && (CurrentHealth < s4)) {
				ManaSprite = Sprite.manabar15;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s6) && (CurrentHealth < s5)) {
				ManaSprite = Sprite.manabar14;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s7) && (CurrentHealth < s6)) {
				ManaSprite = Sprite.manabar13;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s8) && (CurrentHealth < s7)) {
				ManaSprite = Sprite.manabar12;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s9) && (CurrentHealth < s8)) {
				ManaSprite = Sprite.manabar11;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s10) && (CurrentHealth < s9)) {
				ManaSprite = Sprite.manabar10;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s11) && (CurrentHealth < s10)) {
				ManaSprite = Sprite.manabar9;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s12) && (CurrentHealth < s11)) {
				ManaSprite = Sprite.manabar8;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s13) && (CurrentHealth < s12)) {
				ManaSprite = Sprite.manabar7;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s14) && (CurrentHealth < s13)) {
				ManaSprite = Sprite.manabar6;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s15) && (CurrentHealth < s14)) {
				ManaSprite = Sprite.manabar5;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s16) && (CurrentHealth < s15)) {
				ManaSprite = Sprite.manabar4;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s17) && (CurrentHealth < s16)) {
				ManaSprite = Sprite.manabar3;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s18) && (CurrentHealth < s17)) {
				ManaSprite = Sprite.manabar2;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s19) && (CurrentHealth < s18)) {
				ManaSprite = Sprite.manabar1;
			}
			if (CurrentHealth <= 0) {
				ManaSprite = Sprite.manabar0;
			}
		} else {
			ManaSprite = new Sprite(0, 0xff000000);
		}
		return ManaSprite;
	}

	public Sprite renderStaminaExperiment(Mob mob, int size) {
		double CurrentHealth = mob.stamina;
		double MaxHealth = mob.maxstamina;
		double Special = MaxHealth - 1;
		double BarSpace = size;
		double Slope = MaxHealth / BarSpace;
		// double hb = 20;
		double s1 = 0;
		if (MaxHealth > BarSpace) {
			s1 = Special - Slope;// 38 OR 4.75
		} else if (MaxHealth <= BarSpace) {
			s1 = MaxHealth - Slope;// 38 OR 4.75
		}
		double s2 = s1 - Slope;// 36 OR 4.5
		double s3 = s2 - Slope;// 34 OR 4.25
		double s4 = s3 - Slope;// 32 OR 4.0
		double s5 = s4 - Slope;// 30 OR 3.75
		double s6 = s5 - Slope;// 28 OR 3.5
		double s7 = s6 - Slope;// 26 OR 3.25
		double s8 = s7 - Slope;// 24 OR 3.0
		double s9 = s8 - Slope;// 22 OR 2.75
		double s10 = s9 - Slope;// 20 OR 2.5
		double s11 = s10 - Slope;// 18 OR 2.25
		double s12 = s11 - Slope;// 16 OR 2.0
		double s13 = s12 - Slope;// 14 OR 1.75
		double s14 = s13 - Slope;// 12 OR 1.5
		double s15 = s14 - Slope;// 10 OR 1.25
		double s16 = s15 - Slope;// 8 OR 1.0
		double s17 = s16 - Slope;// 6 OR 0.75
		double s18 = s17 - Slope;// 4 OR 0.5
		double s19 = s18 - Slope;// 2 OR 0.25

		/*
		 * if (mob.stamina < mob.maxstamina|| mob.incombat) { displayTimeS = 0;
		 * } else if (displayTimeS < 151){ displayTimeS++; } if (displayTime <
		 * 150) {
		 */
		if (mob.stamina < mob.maxstamina) {
			displayTimeS = 0;
			displayS = true;
		} else if (displayTimeS < 151) {
			displayS = false;
			displayTimeS++;
		}
		if (displayS) {
			if (CurrentHealth == MaxHealth || CurrentHealth > MaxHealth) {
				StaminaSprite = Sprite.StaminaBar20;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s1)) {
				StaminaSprite = Sprite.StaminaBar19;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s2) && (CurrentHealth < s1)) {
				StaminaSprite = Sprite.StaminaBar18;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s3) && (CurrentHealth < s2)) {
				StaminaSprite = Sprite.StaminaBar17;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s4) && (CurrentHealth < s3)) {
				StaminaSprite = Sprite.StaminaBar16;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s5) && (CurrentHealth < s4)) {
				StaminaSprite = Sprite.StaminaBar15;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s6) && (CurrentHealth < s5)) {
				StaminaSprite = Sprite.StaminaBar14;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s7) && (CurrentHealth < s6)) {
				StaminaSprite = Sprite.StaminaBar13;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s8) && (CurrentHealth < s7)) {
				StaminaSprite = Sprite.StaminaBar12;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s9) && (CurrentHealth < s8)) {
				StaminaSprite = Sprite.StaminaBar11;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s10) && (CurrentHealth < s9)) {
				StaminaSprite = Sprite.StaminaBar10;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s11) && (CurrentHealth < s10)) {
				StaminaSprite = Sprite.StaminaBar9;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s12) && (CurrentHealth < s11)) {
				StaminaSprite = Sprite.StaminaBar8;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s13) && (CurrentHealth < s12)) {
				StaminaSprite = Sprite.StaminaBar7;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s14) && (CurrentHealth < s13)) {
				StaminaSprite = Sprite.StaminaBar6;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s15) && (CurrentHealth < s14)) {
				StaminaSprite = Sprite.StaminaBar5;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s16) && (CurrentHealth < s15)) {
				StaminaSprite = Sprite.StaminaBar4;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s17) && (CurrentHealth < s16)) {
				StaminaSprite = Sprite.StaminaBar3;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s18) && (CurrentHealth < s17)) {
				StaminaSprite = Sprite.StaminaBar2;
			}
			if ((!(CurrentHealth == MaxHealth)) && (CurrentHealth >= s19) && (CurrentHealth < s18)) {
				StaminaSprite = Sprite.StaminaBar1;
			}
			if (CurrentHealth <= 0) {
				StaminaSprite = Sprite.StaminaBar0;
			}
		} else {
			StaminaSprite = new Sprite(0, 0xff000000);
		}
		return StaminaSprite;
	}

	public void renderManaExperiment(Screen screen, Mob mob, int x, int y, boolean fixed, int size) {
		int CurrentMana = (int) mob.mana;
		int MaxMana = (int) mob.maxmana;
		int Special = MaxMana - 1;
		int BarSpace = size;
		int Slope = MaxMana / BarSpace;
		// int hb= 20;
		int s1 = Special - Slope;// 38
		int s2 = s1 - Slope;// 36
		int s3 = s2 - Slope;// 34
		int s4 = s3 - Slope;// 32
		int s5 = s4 - Slope;// 30
		int s6 = s5 - Slope;// 28
		int s7 = s6 - Slope;// 26
		int s8 = s7 - Slope;// 24
		int s9 = s8 - Slope;// 22
		int s10 = s9 - Slope;// 20
		int s11 = s10 - Slope;// 18
		int s12 = s11 - Slope;// 16
		int s13 = s12 - Slope;// 14
		int s14 = s13 - Slope;// 12
		int s15 = s14 - Slope;// 10
		int s16 = s15 - Slope;// 8
		int s17 = s16 - Slope;// 6
		int s18 = s17 - Slope;// 4
		int s19 = s18 - Slope;// 2

		if (mob.mana < mob.maxmana || mob.incombat) {
			displayTimeM = 0;
		} else if (displayTimeM < 151) {
			displayTimeM++;
		}
		if (displayTimeM < 150) {
			if (CurrentMana == MaxMana || CurrentMana > MaxMana) {
				screen.renderSprite(x, y, Sprite.manabar20, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s1)) {
				screen.renderSprite(x, y, Sprite.manabar19, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s2) && (CurrentMana < s1)) {
				screen.renderSprite(x, y, Sprite.manabar18, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s3) && (CurrentMana < s2)) {
				screen.renderSprite(x, y, Sprite.manabar17, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s4) && (CurrentMana < s3)) {
				screen.renderSprite(x, y, Sprite.manabar16, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s5) && (CurrentMana < s4)) {
				screen.renderSprite(x, y, Sprite.manabar15, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s6) && (CurrentMana < s5)) {
				screen.renderSprite(x, y, Sprite.manabar14, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s7) && (CurrentMana < s6)) {
				screen.renderSprite(x, y, Sprite.manabar13, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s8) && (CurrentMana < s7)) {
				screen.renderSprite(x, y, Sprite.manabar12, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s9) && (CurrentMana < s8)) {
				screen.renderSprite(x, y, Sprite.manabar11, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s10) && (CurrentMana < s9)) {
				screen.renderSprite(x, y, Sprite.manabar10, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s11) && (CurrentMana < s10)) {
				screen.renderSprite(x, y, Sprite.manabar9, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s12) && (CurrentMana < s11)) {
				screen.renderSprite(x, y, Sprite.manabar8, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s13) && (CurrentMana < s12)) {
				screen.renderSprite(x, y, Sprite.manabar7, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s14) && (CurrentMana < s13)) {
				screen.renderSprite(x, y, Sprite.manabar6, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s15) && (CurrentMana < s14)) {
				screen.renderSprite(x, y, Sprite.manabar5, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s16) && (CurrentMana < s15)) {
				screen.renderSprite(x, y, Sprite.manabar4, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s17) && (CurrentMana < s16)) {
				screen.renderSprite(x, y, Sprite.manabar3, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s18) && (CurrentMana < s17)) {
				screen.renderSprite(x, y, Sprite.manabar2, fixed);
			}
			if ((!(CurrentMana == MaxMana)) && (CurrentMana >= s19) && (CurrentMana < s18)) {
				screen.renderSprite(x, y, Sprite.manabar1, fixed);
			}
			if (CurrentMana <= 0) {
				screen.renderSprite(x, y, Sprite.manabar0, fixed);
			}
		}
	}

	public void renderStaminaExperiment(Screen screen, Mob mob, int x, int y, boolean fixed, int size) {
		int CurrentStamina = (int) mob.stamina;
		int MaxStamina = (int) mob.maxstamina;
		int Special = MaxStamina - 1;
		int BarSpace = size;
		int Slope = MaxStamina / BarSpace;
		// int hb= 20;
		int s1 = Special - Slope;// 38
		int s2 = s1 - Slope;// 36
		int s3 = s2 - Slope;// 34
		int s4 = s3 - Slope;// 32
		int s5 = s4 - Slope;// 30
		int s6 = s5 - Slope;// 28
		int s7 = s6 - Slope;// 26
		int s8 = s7 - Slope;// 24
		int s9 = s8 - Slope;// 22
		int s10 = s9 - Slope;// 20
		int s11 = s10 - Slope;// 18
		int s12 = s11 - Slope;// 16
		int s13 = s12 - Slope;// 14
		int s14 = s13 - Slope;// 12
		int s15 = s14 - Slope;// 10
		int s16 = s15 - Slope;// 8
		int s17 = s16 - Slope;// 6
		int s18 = s17 - Slope;// 4
		int s19 = s18 - Slope;// 2

		if (CurrentStamina == MaxStamina || CurrentStamina > MaxStamina) {
			screen.renderSprite(x, y, Sprite.StaminaBar20, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s1)) {
			screen.renderSprite(x, y, Sprite.StaminaBar19, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s2) && (CurrentStamina < s1)) {
			screen.renderSprite(x, y, Sprite.StaminaBar18, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s3) && (CurrentStamina < s2)) {
			screen.renderSprite(x, y, Sprite.StaminaBar17, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s4) && (CurrentStamina < s3)) {
			screen.renderSprite(x, y, Sprite.StaminaBar16, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s5) && (CurrentStamina < s4)) {
			screen.renderSprite(x, y, Sprite.StaminaBar15, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s6) && (CurrentStamina < s5)) {
			screen.renderSprite(x, y, Sprite.StaminaBar14, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s7) && (CurrentStamina < s6)) {
			screen.renderSprite(x, y, Sprite.StaminaBar13, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s8) && (CurrentStamina < s7)) {
			screen.renderSprite(x, y, Sprite.StaminaBar12, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s9) && (CurrentStamina < s8)) {
			screen.renderSprite(x, y, Sprite.StaminaBar11, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s10) && (CurrentStamina < s9)) {
			screen.renderSprite(x, y, Sprite.StaminaBar10, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s11) && (CurrentStamina < s10)) {
			screen.renderSprite(x, y, Sprite.StaminaBar9, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s12) && (CurrentStamina < s11)) {
			screen.renderSprite(x, y, Sprite.StaminaBar8, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s13) && (CurrentStamina < s12)) {
			screen.renderSprite(x, y, Sprite.StaminaBar7, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s14) && (CurrentStamina < s13)) {
			screen.renderSprite(x, y, Sprite.StaminaBar6, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s15) && (CurrentStamina < s14)) {
			screen.renderSprite(x, y, Sprite.StaminaBar5, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s16) && (CurrentStamina < s15)) {
			screen.renderSprite(x, y, Sprite.StaminaBar4, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s17) && (CurrentStamina < s16)) {
			screen.renderSprite(x, y, Sprite.StaminaBar3, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s18) && (CurrentStamina < s17)) {
			screen.renderSprite(x, y, Sprite.StaminaBar2, fixed);
		}
		if ((!(CurrentStamina == MaxStamina)) && (CurrentStamina >= s19) && (CurrentStamina < s18)) {
			screen.renderSprite(x, y, Sprite.StaminaBar1, fixed);
		}
		if (CurrentStamina <= 0) {
			screen.renderSprite(x, y, Sprite.StaminaBar0, fixed);
		}
	}

	public void renderPoints(Screen screen, Player player) {
		if (!player.getInventory().moveLock) {
			if (player.skillPoints != 0 || player.inventoryEnabled || player.inPointMenu) {

				if (this.checkBounds(146, 152, 13, 13, true, true)) {
					screen.renderSprite(144, 150, Sprite.button_Over_newPoints, false);
					// screen.drawFillRect(Mouse.getX() * Game.scale >>
					// Game.TILE_BIT_SHIFT, Mouse.getY() * Game.scale >>
					// Game.TILE_BIT_SHIFT, 80, 8, 0xff302B23, false);
					font8x8.render(Mouse.getX() * Game.scale >> Game.TILE_BIT_SHIFT,
							Mouse.getY() * Game.scale >> Game.TILE_BIT_SHIFT, -2, 0, "Points: " + player.skillPoints,
							screen, false, true, 1);
					if (Mouse.getButton() == 1 && player.inventoryEnabled) {
						Mouse.setMouseB(0);
						player.inPointMenu = !player.inPointMenu;
						openeding = false;
					}
				} else {
					screen.renderSprite(144, 150, Sprite.button_newPoints, false);
				}
			}
		}
	}

	public int pointVal(Player player) {
		int points = 1;
		if (tmpPoints >= 2) {
			if (player.input.Sprint) {
				points = 2;
			}
		}
		if (Game.get().gameState == Game.get().gameState.INGAME_A) {
			Game.get().getPlayer().skillPoints += points;
		}

		return points;
	}

	String selected = "none";
	int stat, stat_wep, tmpPoints;
	private double pts;
	boolean openeding = false;
	
	public void fillPoints(Player p, int pts) {
		if (pts <= p.skillPoints) {
			switch (selected) {
			case "Vitality":
				p.stat_base_VIT += pts;
				break;
			case "Wisdom":
				p.stat_base_WIS += pts;
				break;
			case "Endurance":
				p.stat_base_EDR += pts;
				break;
			case "Intelligence":
				p.stat_base_MAT += pts;
				break;
			case "Strength":
				p.stat_base_ATC += pts;
				break;
			case "Faith":
				p.stat_base_MDF += pts;
				break;
			case "Agility":
				p.stat_base_AGI += pts;
				break;
			case "Resistance":
				p.stat_base_DEF += pts;
				break;
			}
			p.skillPoints -= pts;
		}
	}
	
	public void renderPointMenu(Screen screen, Player player) {
		if (openeding == false) {
			tmpPoints = player.skillPoints;
			openeding = true;
		}
		screen.renderSheet(0, 0, SpriteSheet.statPoints, false);

		// screen.renderAlphaSprite(0, 0, new Sprite(300, 168, 0, 0,
		// SpriteSheet.statPoints));
		// font8x8.render(83, 30, "Points: "+(int)player.skillPoints, screen,
		// false, false);
		// font8x8.render(0, 0, -2, 0, "" + selected, screen, false, false);

		switch (selected) {
		case "none":
			stat = -1;
			stat_wep = -1;
			break;
		case "Vitality":
			stat = (int) player.stat_base_VIT;
			stat_wep = (int) player.stat_item_VIT;
			break;
		case "Wisdom":
			stat = (int) player.stat_base_WIS;
			stat_wep = (int) player.stat_item_WIS;
			break;
		case "Endurance":
			stat = (int) player.stat_base_EDR;
			stat_wep = (int) player.stat_item_EDR;
			break;
		case "Intelligence":
			stat = (int) player.stat_base_MAT;
			stat_wep = (int) player.stat_item_MAT;
			break;
		case "Strength":
			stat = (int) player.stat_base_ATC;
			stat_wep = (int) player.stat_item_ATC;
			break;
		case "Faith":
			stat = (int) player.stat_base_MDF;
			stat_wep = (int) player.stat_item_MDF;
			break;
		case "Agility":
			stat = (int) player.stat_base_AGI;
			stat_wep = (int) player.stat_item_AGI;
			break;
		case "Resistance":
			stat = (int) player.stat_base_DEF;
			stat_wep = (int) player.stat_item_DEF;
			break;
		}
		
		String existingPt = "" + (int) stat + "(" + (int) stat_wep + ")";
		if (!selected.equals("none")) {
			font.render(150 - (existingPt.length() * 16) / 2 - 10, 52, -3, existingPt, screen, false, false);
			font8x8.render(150 - (selected.length() * 7) / 2 - 4, 80, -2, 0, selected, screen, false, false);
			font8x8.render(150 - (("" + (int)pts +  "(" + (int) stat_wep + ")").length() * 8) / 2 - 8, 95, 0, 0xff00FF00, ("" + (int)pts) +  "(" + (int) stat_wep + ")", screen, false, false);

		
		} else {
			screen.renderSprite(128, 118, new Sprite(7, 7, 0xffFFFFFF), false);
			screen.renderSprite(163, 118, new Sprite(7, 7, 0xffFFFFFF), false);
			font8x8.render(150 - (("Skill Points:").length() * 8) / 2 + 6, 59, -2, 0xff0, "Skill Points:", screen, false, false);
			if (player.skillPoints < 10) {
			font.render(150 - (("" + player.skillPoints).length() * 48) / 2 - 16, 84 - 14, -3, 0xff0, ("" + player.skillPoints), screen, false, false, 3);
			} else if (player.skillPoints < 100) {
				font.render(150 - (("" + player.skillPoints).length() * 48) / 2 - 26, 84 - 14, 16, 0xff0, ("" + player.skillPoints), screen, false, false, 3);				
			} else {
				font.render(150 - (("99+").length() * 48) / 2 - 3, 84 - 14, 9, 0xff0, ("99+"), screen, false, false, 3);				
			}



		}
		
		if (!selected.equals("none") && pts != 0) {
		if (!selected.equals("Vitality")) {
			screen.renderAlphaSprite(133, 6, new Sprite(32, 32, 0, 0, SpriteSheet.statPtFade2));
		}
		if (!selected.equals("Wisdom")) {
			screen.renderAlphaSprite(83, 18, new Sprite(32, 32, 0, 0, SpriteSheet.statPtFade2));
		}
		if (!selected.equals("Endurance")) {
			screen.renderAlphaSprite(183, 18, new Sprite(32, 32, 0, 0, SpriteSheet.statPtFade2));
		}
		if (!selected.equals("Intelligence")) {
			screen.renderAlphaSprite(71, 68, new Sprite(32, 32, 0, 0, SpriteSheet.statPtFade2));
		}
		if (!selected.equals("Strength")) {
			screen.renderAlphaSprite(195, 68, new Sprite(32, 32, 0, 0, SpriteSheet.statPtFade2));
		}
		if (!selected.equals("Faith")) {
			screen.renderAlphaSprite(83, 118, new Sprite(32, 32, 0, 0, SpriteSheet.statPtFade2));
		}
		if (!selected.equals("Agility")) {
			screen.renderAlphaSprite(183, 118, new Sprite(32, 32, 0, 0, SpriteSheet.statPtFade2));
		}
		if (!selected.equals("Resistance")) {
			screen.renderAlphaSprite(133, 130, new Sprite(32, 32, 0, 0, SpriteSheet.statPtFade2));
		}
		} else if (!selected.equals("none")) {

			if (selected.equals("Vitality")) {
				screen.renderAlphaSprite(133, 6, new Sprite(32, 32, 0, 0, SpriteSheet.statPtFade3));
			}
			if (selected.equals("Wisdom")) {
				screen.renderAlphaSprite(83, 18, new Sprite(32, 32, 0, 0, SpriteSheet.statPtFade3));
			}
			if (selected.equals("Endurance")) {
				screen.renderAlphaSprite(183, 18, new Sprite(32, 32, 0, 0, SpriteSheet.statPtFade3));
			}
			if (selected.equals("Intelligence")) {
				screen.renderAlphaSprite(71, 68, new Sprite(32, 32, 0, 0, SpriteSheet.statPtFade3));
			}
			if (selected.equals("Strength")) {
				screen.renderAlphaSprite(195, 68, new Sprite(32, 32, 0, 0, SpriteSheet.statPtFade3));
			}
			if (selected.equals("Faith")) {
				screen.renderAlphaSprite(83, 118, new Sprite(32, 32, 0, 0, SpriteSheet.statPtFade3));
			}
			if (selected.equals("Agility")) {
				screen.renderAlphaSprite(183, 118, new Sprite(32, 32, 0, 0, SpriteSheet.statPtFade3));
			}
			if (selected.equals("Resistance")) {
				screen.renderAlphaSprite(133, 130, new Sprite(32, 32, 0, 0, SpriteSheet.statPtFade3));
			}
		}
		
		
		if (selected.equals("none") || pts == 0) {
		if (this.checkBounds(133, 6, 32, 32, true, true)) {
			if (!selected.equals("Vitality"))
			screen.renderAlphaSprite(133, 6, new Sprite(32, 32, 0, 0, SpriteSheet.statPtFade));
			if (Mouse.getButton() == 1) {
				switchSelected("Vitality");
				buttonActivated();
			}
		}

		if (this.checkBounds(83, 18, 32, 32, true, true)) {
			if (!selected.equals("Wisdom"))
			screen.renderAlphaSprite(83, 18, new Sprite(32, 32, 0, 0, SpriteSheet.statPtFade));
			if (Mouse.getButton() == 1) {
				switchSelected("Wisdom");
				buttonActivated();
			}
		}

		if (this.checkBounds(183, 18, 32, 32, true, true)) {
			if (!selected.equals("Endurance"))
			screen.renderAlphaSprite(183, 18, new Sprite(32, 32, 0, 0, SpriteSheet.statPtFade));
			if (Mouse.getButton() == 1) {
				switchSelected("Endurance");
				buttonActivated();
			}
		}

		if (this.checkBounds(71, 68, 32, 32, true, true)) {
			if (!selected.equals("Intelligence"))
			screen.renderAlphaSprite(71, 68, new Sprite(32, 32, 0, 0, SpriteSheet.statPtFade));
			if (Mouse.getButton() == 1) {
				switchSelected("Intelligence");
				buttonActivated();
			}
		}

		if (this.checkBounds(195, 68, 32, 32, true, true)) {
			if (!selected.equals("Strength"))
				screen.renderAlphaSprite(195, 68, new Sprite(32, 32, 0, 0, SpriteSheet.statPtFade));
			if (Mouse.getButton() == 1) {
				switchSelected("Strength");
				buttonActivated();
			}
		}

		if (this.checkBounds(83, 118, 32, 32, true, true)) {
			if (!selected.equals("Faith"))
				screen.renderAlphaSprite(83, 118, new Sprite(32, 32, 0, 0, SpriteSheet.statPtFade));
			if (Mouse.getButton() == 1) {
				switchSelected("Faith");
				buttonActivated();
			}
		}

		if (this.checkBounds(183, 118, 32, 32, true, true)) {
			if (!selected.equals("Agility"))
				screen.renderAlphaSprite(183, 118, new Sprite(32, 32, 0, 0, SpriteSheet.statPtFade));
			if (Mouse.getButton() == 1) {
				switchSelected("Agility");
				buttonActivated();
			}
		}

		if (this.checkBounds(133, 130, 32, 32, true, true)) {
			if (!selected.equals("Resistance"))
				screen.renderAlphaSprite(133, 130, new Sprite(32, 32, 0, 0, SpriteSheet.statPtFade));
			if (Mouse.getButton() == 1) {
				switchSelected("Resistance");
				buttonActivated();
			}
			}
		}
		
		
		if (this.checkBounds(137, 117, 24, 9, true, true)) {
			if (!selected.equals("none")) {
				font8x8.render(Mouse.getX() * Game.scale >> Game.TILE_BIT_SHIFT, Mouse.getY() * Game.scale >> Game.TILE_BIT_SHIFT, -3, 0xFFFFFF, "Add " + (int)pts + " to " + selected, screen, false, true);
			}
			if (Mouse.getButton() == 1) {
				if (!selected.equals("none")) {
					fillPoints(player, (int)pts);
					tmpPoints = player.skillPoints;
					openeding = false;
					pts = 0;
					player.calcStat(false);
					selected = ("none");
				} else {
					player.inPointMenu = false; 					
				}
				buttonActivated();
			}
		}

		
		if (!selected.equals("none")) {
		if (this.checkBounds(128, 118, 7, 7, true, true)) {
				font8x8.render(Mouse.getX() * Game.scale >> Game.TILE_BIT_SHIFT, Mouse.getY() * Game.scale >> Game.TILE_BIT_SHIFT, -3, 0xFFFFFF, "" + tmpPoints + " left", screen, false, true);
			if (Mouse.getMouseB() == 1) {
				if (pts > 0) {
				int sub = pointVal(player);
				pts -= sub;
				tmpPoints += sub;
				buttonActivated();
				}
			}
		}
		

		if (this.checkBounds(163, 118, 7, 7, true, true)) {
				font8x8.render(Mouse.getX() * Game.scale >> Game.TILE_BIT_SHIFT, Mouse.getY() * Game.scale >> Game.TILE_BIT_SHIFT, -3, 0xFFFFFF, "" + tmpPoints + " left", screen, false, true);
			if (Mouse.getButton() == 1) {
				if (tmpPoints > 0) {
				int add = pointVal(player);
				pts += add;
				tmpPoints -= add;
				
				buttonActivated();
				}
			}
		}
		}

		// Done Button
		/*
		 * if (this.checkBounds(182, 108, 23, 9, true, true)) { if
		 * (Mouse.getButton() == 1) { Sound.Play(Sound.Click, false);
		 * player.inPointMenu = false; Mouse.setMouseB(0); } }
		 * 
		 * //VIT font8x8.render(85, 52, -3, ""+(int)player.stat_base_VIT +
		 * "("+(int)player.stat_item_VIT+")", screen, false, false); if
		 * (this.checkBounds(108, 41, 15, 7, true, true)) { if
		 * (Mouse.getButton() == 1) { if(player.skillPoints > 0) {
		 * player.stat_base_VIT += pointVal(player); player.skillPoints -=
		 * pointVal(player); player.calcStat(false); buttonActivated(); } } }
		 * 
		 * //WIS font8x8.render(128, 52, -3, ""+(int)player.stat_base_WIS +
		 * "("+(int)player.stat_item_WIS+")", screen, false, false); if
		 * (this.checkBounds(151, 41, 15, 7, true, true)) { if
		 * (Mouse.getButton() == 1) { if(player.skillPoints > 0) {
		 * player.stat_base_WIS += pointVal(player); player.skillPoints -=
		 * pointVal(player); player.calcStat(false); buttonActivated(); } } }
		 * 
		 * //EDR font8x8.render(171, 52, -3, ""+(int)player.stat_base_EDR +
		 * "("+(int)player.stat_item_EDR+")", screen, false, false); if
		 * (this.checkBounds(193, 41, 15, 7, true, true)) { if
		 * (Mouse.getButton() == 1) { if(player.skillPoints > 0) {
		 * player.stat_base_EDR += pointVal(player); player.skillPoints -=
		 * pointVal(player); player.calcStat(false); buttonActivated(); } } }
		 * 
		 * //ATC //screen.drawRect(108, 72, 15, 7, 0xff00FF00, false);
		 * font8x8.render(85, 82, -3, ""+(int)player.stat_base_ATC +
		 * "("+(int)player.stat_item_ATC+")", screen, false, false); if
		 * (this.checkBounds(108, 72, 15, 7, true, true)) { if
		 * (Mouse.getButton() == 1) { if(player.skillPoints > 0) {
		 * player.stat_base_ATC += pointVal(player); player.skillPoints -=
		 * pointVal(player); player.calcStat(false); buttonActivated(); } } }
		 * 
		 * //DEF //screen.drawRect(151, 72, 15, 7, 0xff00FF00, false);
		 * font8x8.render(129, 82, -3, ""+(int)player.stat_base_DEF +
		 * "("+(int)player.stat_item_DEF+")", screen, false, false); if
		 * (this.checkBounds(151, 72, 15, 7, true, true)) { if
		 * (Mouse.getButton() == 1) { if(player.skillPoints > 0) {
		 * player.stat_base_DEF += pointVal(player); player.skillPoints -=
		 * pointVal(player); player.calcStat(false); buttonActivated(); } } }
		 * 
		 * //MAT font8x8.render(171, 82, -3, ""+(int)player.stat_base_MAT +
		 * "("+(int)player.stat_item_MAT+")", screen, false, false); if
		 * (this.checkBounds(193, 72, 15, 7, true, true)) { if
		 * (Mouse.getButton() == 1) { if(player.skillPoints > 0) {
		 * player.stat_base_MAT += pointVal(player); player.skillPoints -=
		 * pointVal(player); player.calcStat(false); buttonActivated(); } } }
		 * 
		 * //MDF //screen.drawRect(108, 103, 15, 7, 0xff00FF00, false);
		 * font8x8.render(85, 113, -3, ""+(int)player.stat_base_MDF +
		 * "("+(int)player.stat_item_MDF+")", screen, false, false); if
		 * (this.checkBounds(108, 103, 15, 7, true, true)) { if
		 * (Mouse.getButton() == 1) { if(player.skillPoints > 0) {
		 * player.stat_base_MDF += pointVal(player); player.skillPoints -=
		 * pointVal(player); player.calcStat(false); buttonActivated(); } } }
		 * 
		 * font8x8.render(129, 113, -3, ""+(int)player.stat_base_AGI +
		 * "("+(int)player.stat_item_AGI+")", screen, false, false); if
		 * (this.checkBounds(151, 103, 15, 7, true, true)) { if
		 * (Mouse.getButton() == 1) { if(player.skillPoints > 0) {
		 * player.stat_base_AGI += pointVal(player); player.skillPoints -=
		 * pointVal(player); player.calcStat(false); buttonActivated(); } } }
		 */

	}
	
	public void switchSelected(String st) {
		if (this.selected == "none" || pts == 0) {
			selected = st;
		}
	}

	public void buttonActivated() {
		Sound.Play(Sound.Click, false);
		Mouse.setMouseB(0);
	}

	/*
	 * if (Game.Mana >= 20) { screen.renderSprite(x, y, Sprite.manabar20,
	 * false);
	 * 
	 * }else if (Game.Mana == 19) { screen.renderSprite(x, y, Sprite.manabar19,
	 * false);
	 * 
	 * }else if (Game.Mana == 18) { screen.renderSprite(x, y, Sprite.manabar18,
	 * false);
	 * 
	 * }else if (Game.Mana == 17) { screen.renderSprite(x, y, Sprite.manabar17,
	 * false);
	 * 
	 * }else if (Game.Mana == 16) { screen.renderSprite(x, y, Sprite.manabar16,
	 * false);
	 * 
	 * }else if (Game.Mana == 15) { screen.renderSprite(x, y, Sprite.manabar15,
	 * false);
	 * 
	 * }else if (Game.Mana == 14) { screen.renderSprite(x, y, Sprite.manabar14,
	 * false);
	 * 
	 * }else if (Game.Mana == 13) { screen.renderSprite(x, y, Sprite.manabar13,
	 * false);
	 * 
	 * }else if (Game.Mana == 12) { screen.renderSprite(x, y, Sprite.manabar12,
	 * false);
	 * 
	 * }else if (Game.Mana == 11) { screen.renderSprite(x, y, Sprite.manabar11,
	 * false);
	 * 
	 * }else if (Game.Mana == 10) { screen.renderSprite(x, y, Sprite.manabar10,
	 * false);
	 * 
	 * }else if (Game.Mana == 9) { screen.renderSprite(x, y, Sprite.manabar9,
	 * false);
	 * 
	 * }else if (Game.Mana == 8) { screen.renderSprite(x, y, Sprite.manabar8,
	 * false);
	 * 
	 * }else if (Game.Mana == 7) { screen.renderSprite(x, y, Sprite.manabar7,
	 * false);
	 * 
	 * }else if (Game.Mana == 6) { screen.renderSprite(x, y, Sprite.manabar6,
	 * false);
	 * 
	 * }else if (Game.Mana == 5) { screen.renderSprite(x, y, Sprite.manabar5,
	 * false);
	 * 
	 * }else if (Game.Mana == 4) { screen.renderSprite(x, y, Sprite.manabar4,
	 * false);
	 * 
	 * }else if (Game.Mana == 3) { screen.renderSprite(x, y, Sprite.manabar3,
	 * false);
	 * 
	 * }else if (Game.Mana == 2) { screen.renderSprite(x, y, Sprite.manabar2,
	 * false);
	 * 
	 * }else if (Game.Mana == 1) { screen.renderSprite(x, y, Sprite.manabar1,
	 * false);
	 * 
	 * }else if (Game.Mana <= 0) { screen.renderSprite(x, y, Sprite.manabar0,
	 * false); }
	 */

}
