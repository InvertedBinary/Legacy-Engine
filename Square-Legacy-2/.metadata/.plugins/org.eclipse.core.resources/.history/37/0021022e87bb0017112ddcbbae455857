package com.IB.SL.entity.inventory.item;

import java.util.ArrayList;
import java.util.List;

import com.IB.SL.entity.Entity;
import com.IB.SL.entity.inventory.ChestInventory;
import com.IB.SL.entity.inventory.Inventory;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.graphics.Screen;
import com.IB.SL.level.Level;

public class Item extends Entity {
	
	//private Particle particle;
	public transient int life;
	transient protected int time = 0;
	transient public double xx, yy, zz;
	public transient double xa;
	public transient double ya;
	public transient double za;
	transient public int dropchance;
	transient public int rarity;
	transient public int HealthPotDropchance = 5;
	transient public int ManaPotDropchance = 3;
	transient public int StaminaPotDropchance = 2;
	public String name;
	public String displayname;
	transient public String price;
	transient protected String desc = "";
	transient public int stackSize = -1;
	transient public boolean nearShop = false;

	transient public int	type_STAFF = 0;
	transient public int	type_ARMOR = 1;
	transient public int	type_FABRIC = 2; 
	transient public int	type_ARTIFICE = 3; 
	transient public int	type_MATERIAL = 4; 

	public double ATC = 0;
	public double DEF = 0;
	public double VIT = 0;
	public double WIS = 0;
	public double EDR = 0;
	public double MAT = 0;
	public double MDF = 0;
	public double AGI = 0;
	
	public double stat_Health;
	public double stat_Mana;
	public double stat_Stam;
	
	 public ArrayList<String> recipe = new ArrayList<String>();
	
	transient public int item_TYPE = -1;
	//public int id = -1;
	public void basicInitialization() {
	}
	
	public void basicInitialization(int slot) {
	}

	public void basicInitialization(String city) {
	}
	
	public void basicInitialization(com.IB.SL.entity.inventory.item.consumables.CoinBag.Type type) {
	}
	
	public void basicInitialization(com.IB.SL.entity.inventory.item.consumables.AbstractMatter.Tier tier) {
	}
	
	public void RNGGen() {
		
	}
	
	protected boolean add(Inventory inventory) {
		boolean added;
		if (inventory.add(this)) {
			added = true;
		} else {
			added = false;
		}
		return added;
	}
	
	public boolean add(ChestInventory inventory) {
		boolean added;
		if (inventory.add(this)) {
			added = true;
		} else {
			added = false;
		}
		return added;
	}

	public boolean clickEvent() {
		return false;
	}
	
	protected void move(double x, double y) {
		/*List<Player> players = level.getPlayers(this, 28);
		List<Player> players1 = level.players;	*/
			if (collision(x, y)) {
				this.xa *= -0.5;
				this.ya *= -0.5;
				this.za *= -0.5;
			}
			this.xx += xa;
			this.yy += ya;
			this.zz += za;

		
			}
	
	public boolean collision(double x, double y) {
		boolean solid = false;
		for(int c = 0; c < 4; c++) {
			double xt = (x - c % 2 * 16) / 16;
			double yt = (y - c / 2 * 16) / 16 + 10;
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if (c % 2 == 0) ix = (int)Math.floor(xt);
			if (c / 2 == 0) iy = (int)Math.floor(yt);
			if (level.getTile(ix, iy).solidtwo()) solid = true;
		}
		return solid;
	}
	
	public String getDesc() {
		return desc;
	}
	
	
	public boolean pickup(double xi, double yi, Level level, List<Player> players) {
			
		boolean remove = false;
		if (!nearShop) {
			
		if (Inventory.pickUpTime > 5) {
			Inventory.pickUpTime = 0;
		int xp = 0, yp = 0;
		for (int i = 0; i < players.size(); i++) {
			xp = (int) players.get(i).getX();
			yp = (int) players.get(i).getY();
				if (xp < (int) xx + sprite.getWidth() + 3
	            && xp > (int) xx - 3
	            && yp < (int) (yy - (int)zz) + sprite.getHeight() + 3
	            && yp > (((int)yy - (int)zz)) - 3
	           
	            ) {
			remove = true;
				}
		}
		}
		}
		return remove;
	}
	
	/*public boolean pickup(double xi, double yi, Level level, List<Player> players) {
		
		boolean remove = false;
		if (!nearShop) {
			
		if (Inventory.pickUpTime > 8) {
			Inventory.pickUpTime = 0;
		int xp = 0, yp = 0;
		for (int i = 0; i < players.size(); i++) {
	    if (players.size() > 0) {
			remove = true;
				}
			}
			}
		}
		return remove;
	}
*/
	public void setX(int x) {
		this.x = x;
		this.xx = x;
	}
	
	public void setY(int y) {
		this.y = y;
		this.yy = y;
	}
	
	public void render(Screen screen)	{ 
	
	}
	
	public String getName() {
		return name;
	}



	
}
