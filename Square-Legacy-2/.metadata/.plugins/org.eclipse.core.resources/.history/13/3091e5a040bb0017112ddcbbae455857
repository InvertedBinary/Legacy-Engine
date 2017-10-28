package com.IB.SL.entity.mob.bosses;

import java.util.List;

import com.IB.SL.Game;
import com.IB.SL.entity.inventory.ActiveEffects;
import com.IB.SL.entity.inventory.EquipableItem;
import com.IB.SL.entity.inventory.item.equipables.head.BerserkersHelm;
import com.IB.SL.entity.inventory.item.equipables.mace.mace_Frostbitten;
import com.IB.SL.entity.inventory.item.equipables.rings.ShardOfTrueIce;
import com.IB.SL.entity.inventory.item.equipables.secondary.FrostCandle;
import com.IB.SL.entity.inventory.item.equipables.secondary.MagicMirror;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.graphics.AnimatedSprite;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.graphics.UI.GUI;
import com.IB.SL.level.Node;
import com.IB.SL.level.TileCoord;
import com.IB.SL.level.worlds.Dungeon03;
import com.IB.SL.util.Sound;
import com.IB.SL.util.Vector2i;

public class FrozenKing extends Mob {

	transient private AnimatedSprite FrozenKing = new AnimatedSprite(SpriteSheet.FrozenKing_anim, 64, 64, 16);

	transient private AnimatedSprite animSprite = FrozenKing;
	transient double xa = 0;
	transient double ya = 0;
	transient int xx = 0;
	transient int yy = 0;
	transient double time = 0;
	transient public int fireRate = 0;
	transient public int mineRate = 450;
	transient public int stormRate = 1201;
	transient public int stunRate = 40;
	transient public int fanRate = 80;

	transient int fireRateTime = 0;
	transient int index = 0;
	transient private GUI gui;
	transient int pullTime = 0;

	transient public static boolean justspawned = false;
	transient private List<Node> path = null;

	transient private double speed = 0.25;

	public FrozenKing(int x, int y) {
		this.mobhealth = 7500;
		this.maxhealth = mobhealth;
		this.id = 11712192;
		this.name = "The Frozen King";
		this.hostility = hostility.BOSS;
		this.x = x << 4;
		this.y = y << 4;
		sprite = animSprite.getSprite();
		this.gui = new GUI();
		this.effects = new ActiveEffects(7, this);
		Sound.switchMusic(Sound.TheMightyWillCrumble, 1f);

	}

	private void move() {
		List<Player> players = level.getPlayers(this, 200);
		if (players.size() > 0) {
			xa = 0;
			ya = 0;
			double px = level.getPlayerAt(0).getX();
			double py = (int) level.getPlayerAt(0).getY();
			Vector2i start = new Vector2i((int) getX() >> Game.TILE_BIT_SHIFT, (int) getY() >> Game.TILE_BIT_SHIFT);
			Vector2i destination = new Vector2i(px / TileCoord.TILE_SIZE, py / TileCoord.TILE_SIZE);
			if (time % 1 == 0)
				path = level.findPath(start, destination);
			if (path != null) {
				if (path.size() > 0) {
					Vector2i vec = path.get(path.size() - 1).tile;
					if (x < vec.getX() << 4)
						xa++;
					if (x > vec.getX() << 4)
						xa--;
					if (y < vec.getY() << 4)
						ya++;
					if (y > vec.getY() << 4)
						ya--;
				}
			}
		} else {
			
		}
		System.out.println(this.mobhealth);
		if (xa != 0 || ya != 0) {
			if (this.mobhealth < hh) {
			move(xa * speed, ya * speed);
			}
			walking = true;
		} else {
			walking = false;
		}
	}

	boolean scaled = false;
	double hh = mobhealth;
	public void update() {
		if (!scaled) {
			hh = maxhealth * 0.99;
			scaled = true;
		}
		
		if (time % 2 == 0) {
			this.hurt = false;
		}
		// updatePull();
		// updateShockwave();
		if (fireRate > 0) {
			fireRate--;
		}

		if (mineRate > 0) {
			mineRate--;
		}
		
		if (stunRate > 0) {
			stunRate--;
		}
		
		if (fanRate > 0) {
			fanRate--;
		}
		
		if (stormRate > 0) {
			stormRate--;
		}
		
		time++;
			move();
		animSprite.setFrameRate(6);
		animSprite.update();
		if (ya < 0) {
			dir = DIRECTION.UP;
		} else if (ya > 0) {
			dir = DIRECTION.DOWN;
		}
		if (xa < 0) {
			dir = DIRECTION.LEFT;
		} else if (xa > 0) {
			dir = DIRECTION.RIGHT;
		}
		this.updateFrostBomb();
		this.updateFrostBreath();
		this.updateFrostFan();
		this.updateFrostShard();
		this.updateIceStorm();
		
	}

	private void updateFrostShard() {
		List<Player> players = level.getPlayers(this, 200);
		if ((players.size() > 0) && fireRate <= 0 ) {
			double px = level.getClientPlayer().getX();
			double py = level.getClientPlayer().getY();
			double sx = this.getX();
			double sy = this.getY();
			double dx = px - sx;
			double dy = py - sy;
			double dir = Math.atan2(dy, dx);
		frostShard(x, y - 2, dir);
		fireRate = 50;
		}
	}

	private void updateFrostBomb() {
		List<Player> players = level.getPlayers(this, 200);
		if ((players.size() > 0) && mineRate <= 0 ) {
			double px = level.getClientPlayer().getX();
			double py = level.getClientPlayer().getY();
			double sx = this.getX();
			double sy = this.getY();
			double dx = px - sx;
			double dy = py - sy;
			double dir = Math.atan2(dy, dx);
		frostBomb(x+ Math.random() * 160 - 80, y - 2 + Math.random() * 160 - 80 , dir);
		frostBomb(x+ Math.random() * 160 - 80, y - 2 + Math.random() * 160 - 80 , dir);
		mineRate = 225;
		}
	}
	
	private void updateFrostBreath() {
		List<Player> players = level.getPlayers(this, 200);
		if ((players.size() > 0) && stunRate <= 100 ) {
			double px = level.getClientPlayer().getX();
			double py = level.getClientPlayer().getY();
			double sx = this.getX();
			double sy = this.getY();
			double dx = px - sx;
			double dy = py - sy;
			double dir = Math.atan2(dy, dx);
		frostBreath(x, y - 2, dir + (Math.PI / 3) * Math.random() - (Math.PI / 6));
		}
		if (stunRate <= 0) {
		stunRate = 300;
		}
	}
	
	private void updateIceStorm() {
		List<Player> players = level.getPlayers(this, 200);
		if ((players.size() > 0) && stormRate <= 50) {
			double px = level.getClientPlayer().getX();
			double py = level.getClientPlayer().getY();
			double sx = this.getX();
			double sy = this.getY();
			double dx = px - sx;
			double dy = py - sy;
			double dir = Math.atan2(dy, dx);
		frostShard(x, y - 2, dir + (Math.PI / 6) * Math.random() - (Math.PI / 12));
		}
		if (stormRate <= 0) {
		stormRate = 275;
		}
	}
	
	private void updateFrostFan() {
		List<Player> players = level.getPlayers(this, 200);
		if ((players.size() > 0) && fanRate <= 0) {
			double px = level.getClientPlayer().getX();
			double py = level.getClientPlayer().getY();
			double sx = this.getX();
			double sy = this.getY();
			double dx = px - sx;
			double dy = py - sy;
			double dir = Math.atan2(dy, dx);
			frostShard(x, y - 2, dir);
			frostShard(x, y - 2, dir + Math.PI / 8);
			frostShard(x, y - 2, dir + Math.PI / 16);
			frostShard(x, y - 2, dir + Math.PI / 32);
			frostShard(x, y - 2, dir - Math.PI / 8);
			frostShard(x, y - 2, dir - Math.PI / 16);
			frostShard(x, y - 2, dir - Math.PI / 32);

			fanRate = 200;
		}
	}

	public void death() {
		if (!removed) {
			Game.getGame().getLevel().add(new mace_Frostbitten((int) x, (int) y, 2000, 1, EquipableItem.slot_WEAPON));
			Game.getGame().getLevel().add(new ShardOfTrueIce((int) x, (int) y, 2000, 1, EquipableItem.slot_UTILITY1));
  			Game.getGame().getLevel().add(new BerserkersHelm((int) x, (int) y, 2000, 1, EquipableItem.slot_HEAD));
  			Game.getGame().getLevel().add(new MagicMirror((int) x, (int) y, 2000, 1, EquipableItem.slot_SHEILD));
 			Game.getGame().getLevel().add(new FrostCandle((int) x, (int) y, 2000, 1, EquipableItem.slot_SHEILD));
			
		}
	}



	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		this.xOffset = -35;
		this.yOffset = -44;
		screen.renderMobSpriteUniversal((int) (x + xOffset), (int) (y + yOffset), sprite);
	}

	public boolean switchedMusic = false;
	public void renderGUI(Screen screen) {
		gui.font.render((int) (x - 105) + 1, (int) (y - 64) + 1, -5, 0, this.name, screen, true, false);
		gui.font.render((int) (x - 105), (int) (y - 64), -5, 0xffFFFFFF, this.name, screen, true, false);

		if (level.getPlayers(this, 300).size() > 0) {
			screen.renderSprite((Game.width / 2) - 54, 2, Sprite.resize(gui.renderBar(60, gui.healthbar, maxhealth, mobhealth), 1.5), false); // 130
			Game.getGame().getLevel().SpawnTime_MOD = -1;
			if (!switchedMusic) {
			Sound.switchMusic(Sound.CallOfTheVoid, 1f);
			switchedMusic = true;
			}
		} else {
			Game.getGame().getLevel().SpawnTime_MOD = Dungeon03.SpawnTime_Default;
			if (switchedMusic) {
			Sound.switchMusic(Sound.TheMightyWillCrumble, 1f);
			switchedMusic = false;
			}
		}
		
	}

}
