package com.IB.SL.AlphaLWJGL.util;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera
{

	public final static int FORWARD = 0;
	public final static int BACKWARD = 1;
	
	public final static int LEFT = 2;
	public final static int RIGHT = 3;
	
	public final static int UP = 4;
	public final static int DOWN = 5;
	
	public final static int ROT_RIGHT = 6;
	public final static int ROT_LEFT = 7;
	
	public final static int ROT_UP = 8;
	public final static int ROT_DOWN = 9;
	
	public final static float YAW = -90f;
	public final static float PITCH = 0f;
	public final static float SPEED = 2.5f;
	public final static float SENSITIVITY = 0.25f;
	public final static float FOV = 90f;
	
	public float Yaw = YAW;
	public float Pitch = PITCH;
	public float Speed = SPEED;
	public float Sensitivity = SENSITIVITY;
	public float Fov = FOV;
	public boolean leftControl = false;
	
	public Vector3f Position = new Vector3f(0f, 0f, 3f);
	public Vector3f Front = new Vector3f(0f, 0f, 0f);
	public Vector3f Up = new Vector3f(0f, 0f, 0f);
	public Vector3f Right = new Vector3f(0f, 0f, 0f);
	public Vector3f WorldUp = new Vector3f(0f, 0f, 0f);
	
	public Camera()
	{
		this.Position = new Vector3f(0f, 0f, 0f);
		this.WorldUp = new Vector3f(0f, 1f, 0f);
		updateCameraVectors();
	}
	
	
	public Camera(Vector3f position)
	{
		this.Position = position;
		this.WorldUp = new Vector3f(0f, 1f, 0f);
		updateCameraVectors();
	}
	
	public Camera(Vector3f position, Vector3f up)
	{
		this.Position = position;
		this.WorldUp = up;
		updateCameraVectors();
	}
	
	public Camera(Vector3f position, Vector3f up, float yaw, float pitch)
	{
		this.Position = position;
		this.WorldUp = up;
		this.Yaw = yaw;
		this.Pitch = pitch;
		updateCameraVectors();
	}
	
	public Camera(float posX, float posY, float posZ, float upX, float upY, float upZ, float yaw, float pitch)
	{
		this.Position = new Vector3f(posX, posY, posZ);
		this.WorldUp = new Vector3f(upX, upY, upZ);
		this.Yaw = yaw;
		this.Pitch = pitch;
		updateCameraVectors();
	}
	
	public Matrix4f getViewMatrix()
	{
		Matrix4f view = new Matrix4f();
        view = view.lookAt(Position, Position.add(Front, new Vector3f()), Up /*up*/);
        return view;
	}
	
	public void ProcessKeyboard(int dir, float dt)
	{
		float vel = Speed * dt;
		switch (dir)
		{
		case FORWARD:
			if (leftControl) ProcessMouseMovement(0, 4, true);
			else Position.add(Front.mul(vel, new Vector3f()));
			break;
		case BACKWARD:
			if (leftControl) ProcessMouseMovement(0, -4, true);
			else Position.sub(Front.mul(vel, new Vector3f()));
			break;
		case LEFT:
			if (leftControl) ProcessMouseMovement(-4, 0, true);
			else Position.sub(Right.mul(vel, new Vector3f()));
			break;
		case RIGHT:
			if (leftControl) ProcessMouseMovement(4, 0, true);
			else Position.add(Right.mul(vel, new Vector3f()));
			break;
		case UP:
			Position.add(Up.mul(vel, new Vector3f()));
			break;
		case DOWN:
			Position.sub(Up.mul(vel, new Vector3f()));
			break;
		case ROT_RIGHT:
			ProcessMouseMovement(4, 0, true);
			break;
		case ROT_LEFT:
			ProcessMouseMovement(-4, 0, true);
			break;
		case ROT_UP:
			ProcessMouseMovement(0, 4, true);
			break;
		case ROT_DOWN:
			ProcessMouseMovement(0, -4, true);
			break;
		}
		//Position.y = 0.0f;
		leftControl = false;
		updateCameraVectors();
	}
	
	public void ProcessMouseMovement(float xO, float yO, boolean constrainPitch)
	{
		xO *= Sensitivity;
		yO *= Sensitivity;

		Yaw += xO;
		Pitch += yO;

		if (constrainPitch)
		{
			if (Pitch > 89.0f) 
				Pitch = 89.0f;
			
			if (Pitch < -89.0f) 
				Pitch = -89.0f;
			
		}
		updateCameraVectors();
	}
	
	public void ProcessMouseScroll(float yO)
	{
		if (Fov >= 1f && Fov <= 115f)
			Fov -= yO;
		if (Fov <= 1f)
			Fov = 1f;
		if (Fov >= 115f)
			Fov = 115f;
	}
	
	private void updateCameraVectors()
	{
		Vector3f front = new Vector3f();
		front.x = (float) (Math.cos(Math.toRadians(Yaw)) * Math.cos(Math.toRadians(Pitch)));
		front.y = (float) (Math.sin(Math.toRadians(Pitch)));
		front.z = (float) (Math.sin(Math.toRadians(Yaw)) * Math.cos(Math.toRadians(Pitch)));
		Front = front.normalize(new Vector3f());
		
		Right = (front.cross(new Vector3f(0f, 1f, 0f), new Vector3f()));
		Up = (Right.cross(Front, new Vector3f()));
	}
}















