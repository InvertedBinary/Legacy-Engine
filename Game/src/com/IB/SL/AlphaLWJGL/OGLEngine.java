package com.IB.SL.AlphaLWJGL;

import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetFramebufferSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL;

import com.IB.SL.AlphaLWJGL.util.IO;

public class OGLEngine
{
	String title = "";
	private long window;
	
	private IO io = new IO();

	public OGLEngine(String title)
		{
			System.out.println(this.title);
			this.title = title + " (OGL :: " + Version.getVersion() + ")";
			init();
		}

	public void init()
		{
			if (!GLFW.glfwInit()) throw new IllegalStateException("UNABLE TO INIT GLFW");

			glfwDefaultWindowHints();
			
			window = glfwCreateWindow(800, 600, title, NULL, NULL);

			if (window == NULL) {
				System.err.println("Creation of GLFW Window Failed!");
				glfwTerminate();
				return;
			}

			glfwMakeContextCurrent(window);
			glfwSetFramebufferSizeCallback(window, framebuffer_size_callback);
			
			GL.createCapabilities();
			
		    int vertShader = 0, fragShader = 0;
		    int shaderProgram;
	        try {
	            vertShader = io.createShader("/shaders/screen.vert", ARBVertexShader.GL_VERTEX_SHADER_ARB);
	            fragShader = io.createShader("/shaders/screen.frag", ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
	        }
	        catch(Exception exc) {
	            exc.printStackTrace();
	            return;
	        }
	        finally {
	            if(vertShader == 0 || fragShader == 0) {
	            	System.out.println("Failed to create shaders!");
	                return;
	            }
	        }
	        
	        shaderProgram = glCreateProgram();
	        glAttachShader(shaderProgram, vertShader);
	        glAttachShader(shaderProgram, fragShader);
	        glLinkProgram(shaderProgram);
	        
	        glDeleteShader(vertShader);
	        glDeleteShader(fragShader);
	        
	        float vertices[] = {
	                0.5f,  0.5f, 0.0f, 
	                0.5f, -0.5f, 0.0f,  
	               -0.5f, -0.5f, 0.0f,  
	               -0.5f,  0.5f, 0.0f   
	           };
	        
	         int indices[] = {
	               0, 1, 3, 
	               1, 2, 3 
	           };
		    
	        
	        int VBO, VAO, EBO;
	        VAO = glGenVertexArrays();
	        VBO = glGenBuffers();
	        EBO = glGenBuffers();
	        glBindVertexArray(VAO);

	        glBindBuffer(GL_ARRAY_BUFFER, VBO);
	        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

	        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
	        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

	        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * 4, 0);
	        glEnableVertexAttribArray(0);

	        glBindBuffer(GL_ARRAY_BUFFER, 0); 
		    glBindVertexArray(0);
		    
	        //glPolygonMode(GL_FRONT_AND_BACK, GL_LINE); //WIREFRAME
	        
			while (!glfwWindowShouldClose(window)) {
				glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
		        glClear(GL_COLOR_BUFFER_BIT);
		        
		        glUseProgram(shaderProgram);
		        glBindVertexArray(VAO);
		        
		        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);			    //glDrawArrays(GL_TRIANGLES, 0, 3);

				glfwSwapBuffers(window);
				glfwPollEvents();
			}
			
			glDeleteVertexArrays(VAO);
			glDeleteBuffers(VBO);

			glfwTerminate();
		}

	private static GLFWFramebufferSizeCallback framebuffer_size_callback = new GLFWFramebufferSizeCallback()
	{
		public void invoke(long window, int width, int height)
			{
				glViewport(0, 0, width, height);
			}
	};
	
	
	
	

	
}
