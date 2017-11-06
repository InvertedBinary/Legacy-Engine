package com.IB.SL.AlphaLWJGL;

public class Matrix4f {
	
	public static final int SIZE = 4 * 4;
	public float[] matrix = new float[SIZE];
	
	public Matrix4f() {
	}
	
	public static Matrix4f identity() {
		Matrix4f result = new Matrix4f();
		
		result.matrix[0 + 0 * 4] = 1.0f;
		result.matrix[1 + 1 * 4] = 1.0f;
		result.matrix[2 + 2 * 4] = 1.0f;
		result.matrix[3 + 3 * 4] = 1.0f;

		return result;
	}
	
	public static Matrix4f translate(Vector3f vector) {
		Matrix4f result = identity();
		result.matrix[0 + 3 * 4] = vector.x;
		result.matrix[1 + 3 * 4] = vector.y;
		result.matrix[2 + 3 * 4] = vector.z;
		
		return result;
	}
	
	public static Matrix4f rotate(float angle) {
		Matrix4f result = identity();
		float r = (float)Math.toRadians(angle);
		float cos = (float)Math.cos(r);
		float sin = (float)Math.sin(r);
		
		result.matrix[0 + 0 * 4] = cos;
		result.matrix[1 + 0 * 4] = sin;
		result.matrix[0 + 1 * 4] = -sin;
		result.matrix[1 + 1 * 4] = cos;
		
		return result;
	}
	
	public Matrix4f multiply(Matrix4f mat) {
		Matrix4f result = new Matrix4f();
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				float sum = 0.0f;
				for (int e = 0; e < 4; e++) {
					sum += this.matrix[x + e * 4] = mat.matrix[x + y * 4];
				}
				result.matrix[x + y * 4] = sum;
			}
		}
		return result;
	}

}
