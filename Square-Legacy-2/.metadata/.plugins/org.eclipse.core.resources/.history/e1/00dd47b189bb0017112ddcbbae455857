package com.IB.SL.entity.abilities;

import com.IB.SL.Game;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.mob.hostile.WaterFamiliar;
import com.IB.SL.entity.projectile.Projectile;
import com.IB.SL.entity.spawner.WallParticleSpawner;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.input.Mouse;


public class SummonFamiliar extends Ability{
	
	
	public SummonFamiliar(double x, double y, double dir) {
		super(x, y, dir);
		basicInitialization();
	}
	
	public void basicInitialization() {
		this.name = "Summon Familiar";
		sprite = Sprite.WElemental_Display;
		unlock = 11;
		damage = 5;
		FIRE_RATE = 500;
		manaCost = 15;
		this.displaySprite = sprite;
		this.id = 4533;
	}
	

	
	public void update() {
	}
	
	public void render(Screen screen) {
	}
	
	public void renderAoE(Screen screen) {
		screen.drawCir(Mouse.getX() / Game.scale, Mouse.getY() / Game.scale , 10, 0xff00FFFF, false);
	//	Game.getGame().getLevel().drawAOE(screen, Mouse.getX() - (rad / 2), Mouse.getY() - (rad / 2), rad, 0xffFF00FF);	
		
	}
	
	public boolean use(Projectile p, Ability ability, Mob origin) {
		super.use(p, ability, origin);
		if(Game.get().getLevel().returnTileXY(Screen.xo, Screen.yo).solid() == false || Game.get().devModeOn) {			
		Game.get().getLevel().add(new WaterFamiliar((int) Screen.xo, (int) Screen.yo + 1, 1000));
		Game.get().getLevel().add(new WallParticleSpawner((int) (x + nx), (int) (y + ny), 30, 50, Game.get().getLevel()));
		return true;
		} else {
			return false;
		}
	}
}
