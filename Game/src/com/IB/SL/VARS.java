package com.IB.SL;

public class VARS {
	
	//DRAWING
	public static final String REND_LOCALLY = "relative";
	public static final String REND_WIREFRAME = "wireframe";

	//OBJECT PROPERTIES
	public static final String PHYS_CANSLEEP = "shouldSleep";
	public static final String PHYS_AWAKE = "isAwake";
	public static final String PHYS_NOGRAV = "noGravity";
	
	//PHYSICS
	public static final short TILE_BIT_SHIFT = 5;
	public static final float PPM = 32f; // PIXELS PER METER
	public static final float TPM = 1f;  // TILES PER METER
	
	public static final float Ag = PPM/9.8f; // Gravitational Acceleration
	public static final float Afr = PPM/100; //TODO: MOVE TO BE TILE PROPERTY!!
	
	//Collision bits
	public static final byte BIT_ENTITY = 2;
	public static final byte BIT_PLAYER = 4;
	public static final byte BIT_PROJECTILE = 8;
	public static final byte BIT_TILE = 16;
	public static final byte BIT_OVERLAY = 32;
	public static final byte BIT_UNUSED = 64;

	//0000 0000 0000 0001 1
	//0000 0000 0000 0010 2 
	//0000 0000 0000 0100 4
	//0000 0000 0000 1000 8
	//0000 0000 0001 0000 16
	//0000 0000 0010 0000 32
}
