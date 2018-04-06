package com.IB.SL.AlphaLWJGL.math;

public class Vec4
{
	private float x = 0f, y = 0f, z = 0f, w = 0f;
	
	public Vec4()
	{
			
	}
	
	public Vec4(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vec4(Vec4 vec)
	{
		setValues(vec);
	}
	
	public void setValues(Vec4 vec)
	{
		this.x = vec.x;
		this.y = vec.y;
		this.z = vec.z;
		this.w = vec.w;
	}
	
	public float sqrLen() 
	{
		return x * x + y * y + z * z;
	}
	
	public Vec4 negative()
	{
		return new Vec4(-x, -y, -z, -w);
	}
	
	public Vec4 add(Vec4 vec)
	{
		return new Vec4(this.x + vec.x, this.y + vec.y, this.z + vec.z, this.w + vec.w);
	}
	
	public Vec4 sub(Vec4 vec)
	{
		return new Vec4(this.x - vec.x, this.y - vec.y, this.z - vec.z, this.w - vec.w);
	}
	
	public Vec4 mult(float scalar)
	{
		return new Vec4(x * scalar, y * scalar, z * scalar, w * scalar);
	}
	
	public Vec4 scale(float scalar)
	{
		return mult(scalar);
	}
	
	public Vec4 scale(Vec4 vec)
	{
		return new Vec4(this.x * vec.x, this.y * vec.y, this.z * vec.z, this.w * vec.w);
	}
	
	public float dot(Vec4 vec)
	{
		return (this.x * vec.x + this.y * vec.y + this.z * vec.z + this.w * vec.w);
	}
	
	public float x()
	{
		return x;
	}
	
	public float y()
	{
		return y;
	}
	
	public float z()
	{
		return z;
	}
	
	public float w()
	{
		return w;
	}
	
	public boolean equals(Vec4 vec)
	{
		if (this == vec)
			return true;
		
		if (vec == null || (!(vec instanceof Vec4)))
			return false;
		
		if (!floatEquals(x, vec.x))
			return false;
		
		if (!floatEquals(y, vec.y))
			return false;
		
		if (!floatEquals(z, vec.z))
			return false;
		
		if (!floatEquals(w, vec.w))
			return false;
		
		return true;
	}
	
	public boolean floatEquals(float a, float b)
	{
		return (Float.floatToIntBits(a) == (Float.floatToIntBits(b)));
	}
	
	public String toString()
	{
		return "[x: " + x + 
			 " , y: " + y + 
			 " , z: " + z + 
			 " , w: " + w + "]";
	}
	
	
	
	
	
	
}
