package com.IB.SL.AlphaLWJGL.math;

public class Vec3
{
	private float x = 0f, y = 0f, z = 0f;
	
	public Vec3()
	{
			
	}
	
	public Vec3(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vec3(Vec3 vec)
	{
		setValues(vec);
	}
	
	public void setValues(Vec3 vec)
	{
		this.x = vec.x;
		this.y = vec.y;
		this.z = vec.z;
	}
	
	public float sqrLen() 
	{
		return x * x + y * y + z * z;
	}
	
	public Vec3 negative()
	{
		return new Vec3(-x, -y, -z);
	}
	
	public Vec3 add(Vec3 vec)
	{
		return new Vec3(this.x + vec.x, this.y + vec.y, this.z + vec.z);
	}
	
	public Vec3 sub(Vec3 vec)
	{
		return new Vec3(this.x - vec.x, this.y - vec.y, this.z - vec.z);
	}
	
	public Vec3 mult(float scalar)
	{
		return new Vec3(x * scalar, y * scalar, z * scalar);
	}
	
	public Vec3 scale(float scalar)
	{
		return mult(scalar);
	}
	
	public Vec3 scale(Vec3 vec)
	{
		return new Vec3(this.x * vec.x, this.y * vec.y, this.z * vec.z);
	}
	
	public float dot(Vec3 vec)
	{
		return (this.x * vec.x + this.y * vec.y + this.z * vec.z);
	}
	
	public Vec3 cross(Vec3 vec) 
	{
		return new Vec3(this.y * vec.z - vec.y * this.z, this.z * vec.x - vec.z * this.x, this.x * vec.y - vec.x * this.y);
	}
	
	public float angle(Vec3 vec) 
	{
		float dot = dot(vec);
		float sqrLen = (float) Math.sqrt(sqrLen() * vec.sqrLen());
		
		return (float) Math.acos(dot / sqrLen);
	}
	
	public Vec4 toDir()
	{
		return new Vec4(x, y, z, 0f);
	}
	
	public Vec4 toPoint()
	{
		return new Vec4(x, y, z, 1f);
	}
	
	public float[] toArray()
	{
		float[] array = {x, y, z};
		return array;
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
	
	public boolean equals(Vec3 vec)
	{
		if (this == vec)
			return true;
		
		if (vec == null || (!(vec instanceof Vec3)))
			return false;
		
		if (!floatEquals(x, vec.x))
			return false;
		
		if (!floatEquals(y, vec.y))
			return false;
		
		if (!floatEquals(z, vec.z))
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
			 " , z: " + z + "]";
	}
	
	
	
	
	
	
}
