package com.IB.SL.entity.mob.bosses;

import java.util.List;

import com.IB.SL.Game;
import com.IB.SL.entity.inventory.ActiveEffects;
import com.IB.SL.entity.inventory.EquipableItem;
import com.IB.SL.entity.inventory.item.equipables.head.SandblastedTurban;
import com.IB.SL.entity.inventory.item.equipables.staves.wand_MendersStaff;
import com.IB.SL.entity.inventory.item.equipables.staves.wand_Swarm;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.mob.hostile.DesertFly;
import com.IB.SL.graphics.AnimatedSprite;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.graphics.UI.GUI;
import com.IB.SL.level.Node;
import com.IB.SL.level.TileCoord;
import com.IB.SL.util.Vector2i;

public class SwarmBoss extends Mob {

	transient private AnimatedSprite swarm = new AnimatedSprite(SpriteSheet.Swarm_anim, 64, 64, 2);

	transient private AnimatedSprite animSprite = swarm;
	transient double xa = 0;
	transient double ya = 0;
	transient int xx = 0;
	transient int yy = 0;
	transient double time = 0;
	transient public int fireRate = 0;
	transient public int mineRate = 450;
	transient public int desertFlyRate = 1201;
	transient public int stunRate = 40;
	transient public int fanRate = 80;

	transient int fireRateTime = 0;
	transient int index = 0;
	transient private GUI gui;
	transient int pullTime = 0;

	transient public static boolean justspawned = false;
	transient private List<Node> path = null;

	transient private double speed = 0.5;

	public SwarmBoss(int x, int y) {
		this.mobhealth = 3200;
		this.maxhealth = mobhealth;
		this.id = 11711171;
		this.name = "Swarm of Ryzan-Koh";
		this.hostility = hostility.BOSS;
		this.x = x << 4;
		this.y = y << 4;
		sprite = animSprite.getSprite();
		this.gui = new GUI();
		this.effects = new ActiveEffects(7, this);
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
		if (xa != 0 || ya != 0) {
			move(xa * speed, ya * speed);
			walking = true;
		} else {
			walking = false;
		}
	}

	public void update() {


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
		
		if (desertFlyRate > 0) {
			desertFlyRate--;
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
		
		updateSwarmShoot();
		updateSwarmStun();
		updateSwarmMine();
		updateSwarmFan();
		updateSpawnDesertFly();

	}

	private void updateSwarmShoot() {
		List<Player> players = level.getPlayers(this, 200);
		if ((players.size() > 0) && fireRate <= 0 ) {
			double px = level.getClientPlayer().getX();
			double py = level.getClientPlayer().getY();
			double sx = this.getX();
			double sy = this.getY();
			double dx = px - sx;
			double dy = py - sy;
			double dir = Math.atan2(dy, dx);
		swarmShoot(x, y - 2, dir);
		fireRate = 25;
		}
	}

	private void updateSwarmMine() {
		List<Player> players = level.getPlayers(this, 200);
		if ((players.size() > 0) && mineRate <= 0 ) {
			double px = level.getClientPlayer().getX();
			double py = level.getClientPlayer().getY();
			double sx = this.getX();
			double sy = this.getY();
			double dx = px - sx;
			double dy = py - sy;
			double dir = Math.atan2(dy, dx);
		swarmMine(x+ Math.random() * 160 - 80, y - 2 + Math.random() * 160 - 80 , dir);
		swarmMine(x+ Math.random() * 160 - 80, y - 2 + Math.random() * 160 - 80 , dir);
		swarmMine(x+ Math.random() * 160 - 80, y - 2 + Math.random() * 160 - 80 , dir);
		swarmMine(x+ Math.random() * 160 - 80, y - 2 + Math.random() * 160 - 80 , dir);
		swarmMine(x+ Math.random() * 160 - 80, y - 2 + Math.random() * 160 - 80 , dir);
		swarmMine(x+ Math.random() * 160 - 80, y - 2 + Math.random() * 160 - 80 , dir);
		swarmMine(x+ Math.random() * 160 - 80, y - 2 + Math.random() * 160 - 80 , dir);
		swarmMine(x+ Math.random() * 160 - 80, y - 2 + Math.random() * 160 - 80 , dir);
		
		mineRate = 400;
		}
	}
	
	
	private void updateSpawnDesertFly() {
		List<Player> players = level.getPlayers(this, 200);
		if ((players.size() > 0) && desertFlyRate <= 0 ) {
		
        level.add(new DesertFly((int)(x+ Math.random() * 160 -80) / 16, (int)(y - 2 + Math.random() * 160 -80) / 16));
        level.add(new DesertFly((int)(x+ Math.random() * 160 -80) / 16, (int)(y - 2 + Math.random() * 160 -80) / 16));
        level.add(new DesertFly((int)(x+ Math.random() * 160 -80) / 16, (int)(y - 2 + Math.random() * 160 -80) / 16));
        level.add(new DesertFly((int)(x+ Math.random() * 160 -80) / 16, (int)(y - 2 + Math.random() * 160 -80) / 16));
        level.add(new DesertFly((int)(x+ Math.random() * 160 -80) / 16, (int)(y - 2 + Math.random() * 160 -80) / 16));
        level.add(new DesertFly((int)(x+ Math.random() * 160 -80) / 16, (int)(y - 2 + Math.random() * 160 -80) / 16));
        level.add(new DesertFly((int)(x+ Math.random() * 160 -80) / 16, (int)(y - 2 + Math.random() * 160 -80) / 16));
        level.add(new DesertFly((int)(x+ Math.random() * 160 -80) / 16, (int)(y - 2 + Math.random() * 160 -80) / 16));
		
        desertFlyRate = 1100;
		}
	}
	
	private void updateSwarmStun() {
		List<Player> players = level.getPlayers(this, 200);
		if ((players.size() > 0) && stunRate <= 0 ) {
			double px = level.getClientPlayer().getX();
			double py = level.getClientPlayer().getY();
			double sx = this.getX();
			double sy = this.getY();
			double dx = px - sx;
			double dy = py - sy;
			double dir = Math.atan2(dy, dx);
		swarmStun(x, y - 2, dir);
		stunRate = 140;
		}
	}
	
	private void updateSwarmFan() {
		List<Player> players = level.getPlayers(this, 200);
		if ((players.size() > 0) && fanRate <= 0) {
			double px = level.getClientPlayer().getX();
			double py = level.getClientPlayer().getY();
			double sx = this.getX();
			double sy = this.getY();
			double dx = px - sx;
			double dy = py - sy;
			double dir = Math.atan2(dy, dx);
			swarmShoot(x, y - 2, dir);
			swarmShoot(x, y - 2, dir + Math.PI / 8);
			swarmShoot(x, y - 2, dir + Math.PI / 16);
			swarmShoot(x, y - 2, dir + Math.PI / 32);
			swarmShoot(x, y - 2, dir - Math.PI / 8);
			swarmShoot(x, y - 2, dir - Math.PI / 16);
			swarmShoot(x, y - 2, dir - Math.PI / 32);

			fanRate = 50;
		}
	}

	public void death() {
		if (!removed) {
			Game.getGame().getLevel().add(new wand_MendersStaff((int) x, (int) y, 2000, 1, EquipableItem.slot_WEAPON));
			Game.getGame().getLevel().add(new wand_Swarm((int) x, (int) y, 2000, 1, EquipableItem.slot_WEAPON));
			Game.getGame().getLevel().add(new SandblastedTurban((int) x, (int) y, 2000, 1, EquipableItem.slot_HEAD));
		}
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		this.xOffset = -35;
		this.yOffset = -44;
		screen.renderMobSpriteUniversal((int) (x + xOffset), (int) (y + yOffset), sprite);
	}

	public void renderGUI(Screen screen) {
		gui.font.render((int) (x - 113) + 1, (int) (y - 60) + 1, -5, 0, this.name, screen, true, false);
		gui.font.render((int) (x - 113), (int) (y - 60), -5, 0xffFFFFFF, this.name, screen, true, false);

		if (level.getPlayers(this, 300).size() > 0)
			screen.renderSprite((Game.width / 2) - 54, 2,
					Sprite.resize(gui.renderBar(60, gui.healthbar, maxhealth, mobhealth), 1.5), false); // 130
	}

}
