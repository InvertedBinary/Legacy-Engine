package com.IB.LE2._GL.GL_Real;

import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_DISABLED;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_DEBUG_CONTEXT;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLUtil;

public class GL_Main
	{
		//vars
		public static String title;
		
		public static int WIDTH = 1280;
		public static int HEIGHT = 720;
		public static float ASPECT = (float)WIDTH / (float)HEIGHT;
		
		private long window;
		
		//construct
		public GL_Main()
		{
			this.init(title);
		}
	
		public GL_Main(String title)
		{
			this.init(title);
		}
		
		public void init(String title)
		{
			GL_Main.title = title;
			GL_Main.title = title + " (OGL :: " + Version.getVersion() + ")";
			System.out.println("Initializing Game: " + title);
			
			initGLFW();
			
			glEnable(GL_DEPTH_TEST);
			
			run();
		}
		
		public void initGLFW()
		{
			if (!GLFW.glfwInit()) throw new IllegalStateException("UNABLE TO INIT GLFW");

			glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GL_TRUE);

			glfwDefaultWindowHints();
			window = glfwCreateWindow(WIDTH, HEIGHT, title, NULL, NULL);

			
			if (window == NULL) {
				System.err.println("Creation of GLFW Window Failed!");
				glfwTerminate();
				return;
			}

			glfwMakeContextCurrent(window);
			
			//TODO: Setup Callbacks
			/*glfwSetFramebufferSizeCallback(window, framebuffer_size_callback);
			glfwSetCursorPosCallback(window, mouse_callback);
			glfwSetScrollCallback(window, scroll_callback);*/
			
		    glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
		    
		    //glfwSetWindowIcon(window, null);
			
			GL.createCapabilities();
			GLUtil.setupDebugMessageCallback();
		}
		
		//body
		
		public void run() {
			while (!glfwWindowShouldClose(window)) {
				
			}
		}
	
	}
