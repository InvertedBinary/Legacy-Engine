package com.IB.SL.graphics.UI.part;

public interface UI_Clickable  {
	
	public boolean InBounds();
	public void Clicked();
	public void Dragged();
	public void Hovered();
	public void UnsetHover();
}
