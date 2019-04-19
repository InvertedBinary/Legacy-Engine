package com.IB.SL.graphics.UI.components.basic;

public interface UI_Clickable  {
	
	public boolean InBounds();
	
	public void Clicked();
	public void OnDownClick();
	public void Dragged();
	public void Hovered();
	public void UnsetHover();
	
}
