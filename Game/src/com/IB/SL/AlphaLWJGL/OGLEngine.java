package com.IB.SL.AlphaLWJGL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;

import static com.IB.SL.AlphaLWJGL.util.Utils.*;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GLDebugMessageCallback;
import org.lwjgl.opengl.GLUtil;

import com.IB.SL.AlphaLWJGL.util.Utils;
import com.IB.SL.AlphaLWJGL.util.Shader;
import com.IB.SL.AlphaLWJGL.util.TextureHandler;

public class OGLEngine
{
	String title = "";
	private long window;
	
	public OGLEngine(String title)
		{
			System.out.println(this.title);
			this.title = title + " (OGL :: " + Version.getVersion() + ")";
			initGLFW();
			init();
		}

	public void initGLFW()
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
			GLUtil.setupDebugMessageCallback();
		}

	public void init()
		{
			Shader shaders = new Shader("/shaders/screen.vert", "/shaders/screen.frag");
	        shaders.getShaderProgram();
	        
	        float vertices[] = {
	        	    // positions          // colors           // texture coords
	        	     0.5f,  0.5f, 0.0f,   1.0f, 0.0f, 0.0f,   1.0f, 1.0f,   // top right
	        	     0.5f, -0.5f, 0.0f,   0.0f, 1.0f, 0.0f,   1.0f, 0.0f,   // bottom right
	        	    -0.5f, -0.5f, 0.0f,   0.0f, 0.0f, 1.0f,   0.0f, 0.0f,   // bottom left
	        	    -0.5f,  0.5f, 0.0f,   1.0f, 1.0f, 0.0f,   0.0f, 1.0f    // top left 
	        	};
	        
	        int indices[] = {
	              0, 1, 3, 
	              1, 2, 3 
	        };
	        
	        float texCoords[] = {
	        	0.0f, 0.0f,
	        	1.0f, 0.0f,
	        	0.5f, 1.0f
	        };
	        
	        BufferedImage texture = null;
				try {
					//texture = ImageIO.read(new File("C:/wall.jpg"));
					texture = ImageIO.read(Utils.getResourceAsFile("3D_Textures/wall.jpg"));
				} catch (IOException e) {
					e.printStackTrace();
				}
	        
	        TextureHandler.loadTexture(texture);
	        
	        int VBO, VAO, EBO;
	        VAO = glGenVertexArrays();
	        VBO = glGenBuffers();
	        EBO = glGenBuffers();
	        glBindVertexArray(VAO);

	        glBindBuffer(GL_ARRAY_BUFFER, VBO);
	        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

	        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
	        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

	        glVertexAttribPointer(0, 3, GL_FLOAT, false, 8 * FLOAT_SIZE, 0);
	        glEnableVertexAttribArray(0);
	        
	        glVertexAttribPointer(1, 3, GL_FLOAT, false, 8 * FLOAT_SIZE, 3 * FLOAT_SIZE);
	        glEnableVertexAttribArray(1);
	        
	        glVertexAttribPointer(2, 2, GL_FLOAT, false, 8 * FLOAT_SIZE, 6 * FLOAT_SIZE);
	        glEnableVertexAttribArray(2);

	        glBindBuffer(GL_ARRAY_BUFFER, 0); 
		    glBindVertexArray(0);
		    
	        //glPolygonMode(GL_FRONT_AND_BACK, GL_LINE); //WIREFRAME
	        
			while (!glfwWindowShouldClose(window)) {
				processInput(window);
				
				glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
		        glClear(GL_COLOR_BUFFER_BIT);
		        
		        shaders.use();
		        
		        glBindVertexArray(VAO);
		        
		        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
		        //glDrawArrays(GL_TRIANGLES, 0, 3);
		        
				glfwSwapBuffers(window);
				glfwPollEvents();
			}
			
			glDeleteVertexArrays(VAO);
			glDeleteBuffers(VBO);

			glfwTerminate();
		}
	
	private void processInput(long window)
		{
			if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS) 
				glfwSetWindowShouldClose(window, true);
		}

	private static GLFWFramebufferSizeCallback framebuffer_size_callback = new GLFWFramebufferSizeCallback()
	{
		public void invoke(long window, int width, int height)
			{
				glViewport(0, 0, width, height);
			}
	};
	
}
