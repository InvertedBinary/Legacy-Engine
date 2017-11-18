package com.IB.SL;

public class VARS {
	
	//OBJECT PROPERTIES
	public static final String PHYS_CANSLEEP = "shouldSleep";
	public static final String PHYS_AWAKE = "isAwake";
	public static final String PHYS_CANFLOAT = "canFloat";

	
	//PHYSICS
	public static final short TILE_BIT_SHIFT = 5;
	public static final float PPM = 32f; // PIXELS PER METER
	public static final float TPM = 1f;  // TILES PER METER
	
	public static final float Ag = PPM/9.8f; // Gravitational Acceleration
	public static final float Afr = PPM/100; //TODO: MOVE TO BE TILE PROPERTY!!
	
	//Collision bits
	public static final short BIT_ENTITY = 2;
	public static final short BIT_PLAYER = 4;
	public static final short BIT_PROJECTILE = 8;
	public static final short BIT_TILE = 16;
	public static final short BIT_OVERLAY = 32;
}
