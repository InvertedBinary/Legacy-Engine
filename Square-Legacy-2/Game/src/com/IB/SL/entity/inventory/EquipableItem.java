package com.IB.SL.entity.inventory;

import java.io.Serializable;

import com.IB.SL.Game;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.inventory.item.Item;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.graphics.Screen;
import com.IB.SL.input.Mouse;

public abstract class EquipableItem extends Item implements Serializable{

	public static final int slot_WEAPON = 0;
	public static final int slot_SHEILD = 1;
	public static final int slot_HEAD = 2;
	public static final int slot_CHEST = 3;
	public static final int slot_BOOTS = 4;
	public static final int slot_UTILITY1 = 5;
	public static final int slot_UTILITY2 = 6;
	public static int slot_Default;
	
	public int mCost = 0;
	
	public int FIRE_RATE = 1;
	
	protected int slot;

	public int getSlot() {
		return slot;
	}
	
	public void RNGGen() {
		
	}
	
	public void attack() {
	}
	
	public void regenDesc() {
		
	}

	public double pX() {
		double dx = Mouse.getX();
		return dx;
	}
	
	public double pY() {
		double dy = Mouse.getY();
		return dy;
	}
	
	public double getDir() {
		double dir = Math.atan2(pY(), pX());
		return dir;
	}
	
	public void equipEvent() {
		Player p = Game.getGame().getPlayer();
		Game.getGame().getPlayer().calcStat(true);
		p.stat_item_Health += stat_Health;
		p.stat_item_Mana += stat_Mana;
		p.stat_item_Stam += stat_Stam;
		p.stat_item_ATC += ATC;
		p.stat_item_DEF += DEF;
		p.stat_item_VIT += VIT;
		p.stat_item_WIS += WIS;
		p.stat_item_EDR += EDR;
		p.stat_item_MAT += MAT;
		p.stat_item_MDF += MDF;
		p.stat_item_AGI += AGI;
		
		Game.getGame().getPlayer().calcStat(true);
	}
	
	public void dequipEvent() {
		Player p = Game.getGame().getPlayer();
		Game.getGame().getPlayer().calcStat(true);
		p.stat_item_Health -= stat_Health;
		p.stat_item_Mana -= stat_Mana;
		p.stat_item_Stam -= stat_Stam;
		p.stat_item_ATC -= ATC;
		p.stat_item_DEF -= DEF;
		p.stat_item_VIT -= VIT;
		p.stat_item_WIS -= WIS;
		p.stat_item_EDR -= EDR;
		p.stat_item_MAT -= MAT;
		p.stat_item_MDF -= MDF;
		p.stat_item_AGI -= AGI;
		
		Game.getGame().getPlayer().calcStat(true);
	}
	
	public void renderAoE(Screen screen) {
		
	}
	
	public void playAnim(Screen screen, int cooldown) {
		
	}

	public void attack(Entity origin) {
		// TODO Auto-generated method stub
		
	}
	
	
}
