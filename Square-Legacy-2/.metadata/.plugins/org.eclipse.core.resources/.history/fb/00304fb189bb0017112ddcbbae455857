package com.IB.SL.entity.abilities;

import java.io.Serializable;
import java.util.HashMap;

import com.IB.SL.Game;
import com.IB.SL.Game.gameState;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.graphics.AnimatedSprite;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.graphics.font8x8;
import com.IB.SL.graphics.UI.GUI;
import com.IB.SL.input.Mouse;
import com.IB.SL.util.Sound;

public class EquippedAbilities implements Serializable{
	

	private transient font8x8 font8x8;
	transient public Ability[] abilities;
	public transient Ability selected = null;
	private int firstFree;
	public boolean tabOpen;
	private boolean abilityTabEnabled = false;
	private boolean abilityTabReleased;
	
	public HashMap<Ability, Integer> Cooldowns = new HashMap<Ability, Integer>();
	
	public EquippedAbilities(int size) {
		abilities = new Ability[size];
		firstFree = 0;
		font8x8 = new font8x8();
		tabOpen = false;
	}
	
	public boolean add(Ability Ability) {
		
		if (firstFree == abilities.length) {
			System.out.println("Could Not Add Ability: " + Ability.getName() + " No Ability Slots Remaining");
			return false;
		} 
		
		
		for (int i = firstFree; i < abilities.length; i++) {
			if (abilities[i] == null) {	
				firstFree = i;
				Ability.inSlot = i;
				System.out.println("Ability Added: " + Ability.getName() + " At Index: " + i);
				abilities[firstFree] = Ability;
				
				Cooldowns.put(Ability, 0);
				//Game.getGame().getPlayer().inventory.UI_Ab.addUI(new UI_Sprite(0, 0, abilities[i].sprite));
				return true;

			}
			
		}
		firstFree = abilities.length;
		return true;
	}
	

	
	public Ability get(int index) {
		return abilities[index];
	}
	
	public void removeByIndex(int index) {
		if (abilities[index] != null) {
			System.out.println("Ability Removed At Index " + index + "[" + abilities[index].getName() + "]");
		} else {
			System.out.println("The Slot At " + index + " Is Empty");
		}
		abilities[index] = null;
		if (index < firstFree) {
			firstFree = index;
		}
	}
	
	public void removeAbility(Ability Ability) {
		for(int i = 0; i < abilities.length; i++) {
			if(abilities[i] == Ability) {
				System.out.println("Ability Removed " + Ability.getName() + " At Index " + i);
				abilities[i] = null;
				if (i < firstFree) {
					firstFree = i;
				}
				return;
			}
		}
	}

	public void clear() {
		for (int i = 0; i < abilities.length; i++) {
			removeByIndex(i);
		}
	}
	
	public void renderATab(Screen screen) {
		int xOffset = 0;
		int yOffset = 0;
		for (int i = 0; i < abilities.length; i++) {
			if (abilities[i] != null) {
				

				if (abilities[i] == abilities[0]) {
					if (abilities[i].displaySprite != null) {
					screen.renderSprite(xOffset + 33, yOffset + 4, abilities[i].displaySprite, false);
					}else {
						abilities[i].basicInitialization();
						screen.renderSprite(xOffset + 33 + ((i * 23)), yOffset + 4, Sprite.VoidTile, false);
					}
				} else if (i <= 9){
					try {
						
						if (abilities[i].displaySprite != null) {
						screen.renderSprite(xOffset + 33 + ((i * 23)), yOffset + 4, abilities[i].displaySprite, false);
						} else {
							abilities[i].basicInitialization();
							screen.renderSprite(xOffset + 33 + ((i * 23)), yOffset + 4, Sprite.VoidTile, false);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if (Game.get().getPlayer().Lvl < abilities[i].unlock) {
					screen.renderAlphaSprite(Sprite.AB_LOCK, 31 + (i * 23), 2);
					//screen.drawRect(31 + (i * 23), 2, 19, 19, 0xffCC0000, false);						
				}
			}
		}
	}

	
	public void SlotChecking(int slot, Screen screen) {
		if (abilities[slot] != null) {
			//screen.drawFillRect(Mouse.getX() * 4 >> Game.TILE_BIT_SHIFT, Mouse.getY() * 4 >> Game.TILE_BIT_SHIFT, 60, 15, 0x00FF00, false);
			font8x8.render(Mouse.getX() * Game.scale >> Game.TILE_BIT_SHIFT, Mouse.getY() * Game.scale >> Game.TILE_BIT_SHIFT, -3, abilities[slot].name, screen, false, true);
		if (Mouse.getButton() == 3) {
			removeByIndex(slot);
		}
	if (Mouse.getButton() == 1) {
	if (abilities[slot].clickEvent()) {
	removeByIndex(slot);
	}
	}
	
	if (Mouse.getButton() == 2) {
		screen.renderSprite(Mouse.getX(), Mouse.getY(), abilities[slot].getSprite(), false);
	}
	}
	
	}
	
	
	private transient  AnimatedSprite animated_box = new AnimatedSprite(SpriteSheet.anim_abilityBox, 20, 20, 17);

	
	public void render(Screen screen) {
		if (abilityTabEnabled) {
			screen.renderSprite(0, 0, Sprite.AbilityBarOpen, false);
			for (int i = 0; i < abilities.length; i++) {
				if (i == 0) {
					screen.renderSprite(32, 3, Sprite.abilitybox,false);				
					} else if (i <= 9){
						screen.renderSprite(32 + ((i * 23)), 3, Sprite.abilitybox, false);
						}
					}
			renderATab(screen);
		} else {
		screen.renderSprite(0, 0, Sprite.AbilityBarClosed, false);
		
		if (Game.get().getPlayer().inventoryEnabled) {
		screen.drawFillRect(119, 5, 62, 9, 0xff302B23, false);
		font8x8.render(113, 5, -2, 0xffFFFFFF, "BETA BUILD", screen, false, false);
		}
		}
		
		
		if (selected != null) {
			screen.renderSprite(2, 2, Game.get().gui.renderBar(16, animated_box, selected.FIRE_RATE, selected.FIRE_RATE - this.Cooldowns.get(selected)), false);
			if (selected.displaySprite != null) {
			screen.renderSprite(4, 4, selected.displaySprite, false);
			}
		}
		
	keyEvents(screen);
	}
	
//screen.renderSprite(xp, yp, sprite, false);
//	}
	
	public boolean checkAbility(Screen screen, int slot, boolean scroll) {
		System.out.println("unlock: " + abilities[slot].unlock + " NAME: " + abilities[slot].name);
		if (Game.get().getPlayer().Lvl < abilities[slot].unlock && Game.get().gameState != gameState.INGAME_A) {
			if (!scroll) {
				Player.unlockWarning = true;
			}
			Mouse.notch = slot;
				System.out.println("Further Progression Required For: " + abilities[slot].getName());
				if (abilityTabEnabled) screen.drawRect(31 + (slot * 23), 2, 19, 19, 0xffff0000, false);						
				return false;
				} else {
					if (abilities[slot] != selected) {						
						selected = abilities[slot];
						Game.get().getPlayer().abilitySwitched(selected);
						Sound.Play(Sound.Click, false);
					}
				//	Game.getGame().getPlayer().setAbility(abilities[slot], abilities[slot].displaySprite);
					if (abilityTabEnabled) screen.drawRect(31 + (slot * 23), 2, 19, 19, 0xff19AD19, false);						
		//System.out.println("Ability " + abilities[slot].getName());
		Mouse.notch = slot;
		return true;
	}
		}


	private void keyEvents(Screen screen) {
		//Slot 1
		try {
	if (Game.get().getPlayer().input.a1) {
		checkAbility(screen,0, false);
	} if (Game.get().getPlayer().input.a2) {
		checkAbility(screen,1, false);
	} if (Game.get().getPlayer().input.a3) {
		checkAbility(screen,2, false);
	} if (Game.get().getPlayer().input.a4) {
		checkAbility(screen,3, false);
	} if (Game.get().getPlayer().input.a5) {
		checkAbility(screen,4, false);
	} if (Game.get().getPlayer().input.a6) {
		checkAbility(screen,5, false);
	} if (Game.get().getPlayer().input.a7) {
		checkAbility(screen,6, false);
	} if (Game.get().getPlayer().input.a8) {
		checkAbility(screen,7, false);
	} if (Game.get().getPlayer().input.a9) {
		checkAbility(screen,8, false);
	} if (Game.get().getPlayer().input.a0) {
		checkAbility(screen,9, false);

	}
		} catch (NullPointerException e) {
			System.out.println("No Ability Was Found In Requested Slot");
		}
	}	
	
	public void Update(GUI gui, Player player) {
		
		for (Ability ab : Cooldowns.keySet()) {
			if (Cooldowns.get(ab) < ab.FIRE_RATE) {
				Cooldowns.replace(ab, Cooldowns.get(ab) + 1);
			}
		}
		
		
		if (gui.checkBounds(0, 0, 12, 32, true, true)) {
			player.canShoot = false;
			try {
			if (Mouse.getButton() == 1) {
				tabOpen = true;
				
			} else if (!player.input.abTab){
				tabOpen = false;
			}
			} catch (Exception e) {
				
			}
	
			} else {
				player.canShoot = true;
			}
		
		//if (player.input.abTab && !tabOpen) tabOpen = true;
	
	if(tabOpen && !abilityTabEnabled && abilityTabReleased){
		Sound.Play(Sound.Click, false);
		abilityTabEnabled = true;
		abilityTabReleased = false;
	}
	
	if(!tabOpen) abilityTabReleased = true;
	
	if(tabOpen && abilityTabEnabled && abilityTabReleased){
		Sound.Play(Sound.Click, false);
		abilityTabEnabled = false;
		abilityTabReleased = false;
		
	}
	}

	public void disableAll() {
		for (int i = 0; i < abilities.length; i++) {
			if (abilities[i] != null) {
		}
	}
	}

	public int lastAbilitySlot() {
		int returned = 0;
		if (abilities[9] != null) {
			returned = 9;
		} else if (abilities[8] != null) {
			returned = 8;
		} else if (abilities[7] != null) {
			returned = 7;
		} else if (abilities[6] != null) {
			returned = 6;
		} else if (abilities[5] != null) {
			returned = 5;
		} else if (abilities[4] != null) {
			returned = 4;
		} else if (abilities[3] != null) {
			returned = 3;
		} else if (abilities[2] != null) {
			returned = 2;
		} else if (abilities[1] != null) {
			returned = 1;
		} else if (abilities[0] != null) {
			returned = 0;
		}
		return returned;
	}
	}
	

