package com.IB.SL.AlphaLWJGL;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glGetString;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class LWJGL implements Runnable {

	//TODO: Delete this class when done
	String title;
	private boolean running = true;
	private Thread thread;
	
	private long win;
	
	private GLFWKeyCallback keyCallBack;
	   
	public LWJGL(String title) {
		this.title = title;
        System.out.println("LWJGL Version " + Version.getVersion() + " is working.");
	}

	public void start() {
		running = true;
		thread = new Thread(this, "SL2");
		thread.start();
	}
	
	public void init() {
		if (!glfwInit()) {
			System.err.println("GLFW failed to initalize..");
			System.exit(1);
		}
		
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		win = glfwCreateWindow(1280, 720, title, NULL, NULL);
		
		if (win == NULL) {
			System.err.println("Window failed to initialize");
		}
		
		glfwSetKeyCallback(win, keyCallBack = new Input());
		
		GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(win, 100, 100);
		
		glfwMakeContextCurrent(win);
		glfwShowWindow(win);
		
		GL.createCapabilities();
		//Create textures here!
		
		
		//glClearColor(0.56f, 0.25f, 0.425f, 1.0f);
		//glEnable(GL_DEPTH_TEST);
		
		System.out.println("OpenGL: " + glGetString(GL_VERSION));
	}
	
	
	public void update() {
		glfwPollEvents();
		
		if(Input.keys[GLFW_KEY_SPACE]) {
			System.out.println("SPACE PRESSED");
		}
	}

	public void render() {
		glfwSwapBuffers(win);
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	@Override
	public void run() {
		init();
		while(running) {
			update();
			render();
			
			if(glfwWindowShouldClose(win)) {
				running = false;
			}
		}
		
	}
}