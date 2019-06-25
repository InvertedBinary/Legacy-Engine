package com.IB.LE2.media.graphics;

public class AnimatedSprite extends Sprite {

	private Sprite sprite;

	private int frame = 0;
	private int rate = 12;
	private int time = 0;
	private int length = -1;
	
	
	public AnimatedSprite(SpriteSheet sheet, int width, int height, int length) {
		super(sheet, width, height);
		this.length = length;
		sprite = sheet.getSprites()[0];
		if (length > sheet.getSprites().length) System.err.println("ERR! Length > Sheet");
	}
	public void update() {
		time++;
		if (time % rate == 0) {
		if (frame >= length - 1) frame = 0;
		else frame++;
		sprite = sheet.getSprites()[frame];
		}
		//System.out.println(sprite + ", Frame: " + frame);
	}
	public Sprite getSprite() {
		return sprite;
		
	}

	public void setFrameRate(int frames) {
		rate = frames;
	}
	public void setFrame(int index) {
		if (index > sheet.getSprites().length - 1) {
			System.err.println("Index out of bounds in" + this);
		}
		sprite = sheet.getSprites()[index];
		this.frame = index;
	}
	public int getFrame() {
		return frame;
	}
	
	public int getLength() {
		return length;
	}
	
}
