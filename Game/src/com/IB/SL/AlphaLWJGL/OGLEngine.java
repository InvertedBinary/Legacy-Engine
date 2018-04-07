package com.IB.SL.AlphaLWJGL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryUtil.*;
import static com.IB.SL.AlphaLWJGL.util.ResourceManager.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.FloatBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GLDebugMessageCallback;
import org.lwjgl.opengl.GLUtil;

import com.IB.SL.AlphaLWJGL.util.ResourceManager;
import com.IB.SL.AlphaLWJGL.util.Shader;
import com.IB.SL.AlphaLWJGL.util.TextureHandler;
import org.joml.*;
import org.joml.Math;

public class OGLEngine
{
	String title = "";
	public static int WIDTH = 800;
	public static int HEIGHT = 600;
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
		window = glfwCreateWindow(WIDTH, HEIGHT, title, NULL, NULL);

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
		
		glEnable(GL_DEPTH_TEST);

		Shader shaders = new Shader("/shaders/screen.vert", "/shaders/screen.frag");
		shaders.getShaderProgram();

		float vertices[] = {
				// positions // colors // texture coords
				0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, // top right
				0.5f, -0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, // bottom right
				-0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, // bottom left
				-0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f // top left
		};
		
		float cubeVerts[] = {
			    -0.5f, -0.5f, -0.5f,  0.0f, 0.0f,
			     0.5f, -0.5f, -0.5f,  1.0f, 0.0f,
			     0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
			     0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
			    -0.5f,  0.5f, -0.5f,  0.0f, 1.0f,
			    -0.5f, -0.5f, -0.5f,  0.0f, 0.0f,

			    -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
			     0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
			     0.5f,  0.5f,  0.5f,  1.0f, 1.0f,
			     0.5f,  0.5f,  0.5f,  1.0f, 1.0f,
			    -0.5f,  0.5f,  0.5f,  0.0f, 1.0f,
			    -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,

			    -0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
			    -0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
			    -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
			    -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
			    -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
			    -0.5f,  0.5f,  0.5f,  1.0f, 0.0f,

			     0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
			     0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
			     0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
			     0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
			     0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
			     0.5f,  0.5f,  0.5f,  1.0f, 0.0f,

			    -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
			     0.5f, -0.5f, -0.5f,  1.0f, 1.0f,
			     0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
			     0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
			    -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
			    -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,

			    -0.5f,  0.5f, -0.5f,  0.0f, 1.0f,
			     0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
			     0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
			     0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
			    -0.5f,  0.5f,  0.5f,  0.0f, 0.0f,
			    -0.5f,  0.5f, -0.5f,  0.0f, 1.0f
			};
		
		Vector3f cubePositions[] = {
				  new Vector3f( 0.0f,  0.0f,  0.0f), 
				  new Vector3f( 2.0f,  5.0f, -15.0f), 
				  new Vector3f(-1.5f, -2.2f, -2.5f),  
				  new Vector3f(-3.8f, -2.0f, -12.3f),  
				  new Vector3f( 2.4f, -0.4f, -3.5f),  
				  new Vector3f(-1.7f,  3.0f, -7.5f),  
				  new Vector3f( 1.3f, -2.0f, -2.5f),  
				  new Vector3f( 1.5f,  2.0f, -2.5f), 
				  new Vector3f( 1.5f,  0.2f, -1.5f), 
				  new Vector3f(-1.3f,  1.0f, -1.5f)  
				};

		int indices[] = { 0, 1, 3, 1, 2, 3 };

		float texCoords[] = { 0.0f, 0.0f, 1.0f, 0.0f, 0.5f, 1.0f };

		TextureHandler.createTexture("GL_Textures/wall.jpg");

		int VBO, VAO, EBO;
		VAO = glGenVertexArrays();
		VBO = glGenBuffers();
		//EBO = glGenBuffers();
		glBindVertexArray(VAO);

		glBindBuffer(GL_ARRAY_BUFFER, VBO);
		glBufferData(GL_ARRAY_BUFFER, cubeVerts, GL_STATIC_DRAW);

		//glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
		//glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

	    // position attribute
	    glVertexAttribPointer(0, 3, GL_FLOAT, false, 5 * FLOAT_SIZE, 0);
	    glEnableVertexAttribArray(0);
	    // texture coord attribute
	    glVertexAttribPointer(1, 2, GL_FLOAT, false, 5 * FLOAT_SIZE, (3 * FLOAT_SIZE));
	    glEnableVertexAttribArray(1);

		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);

		//glPolygonMode(GL_FRONT_AND_BACK, GL_LINE); //WIREFRAME

		while (!glfwWindowShouldClose(window)) {
			processInput(window);

			glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);


			Matrix4f view = new Matrix4f();
			view = view.translate(new Vector3f(0.0f, 0.0f, -3.0f)); 
			
			Matrix4f projection = new Matrix4f();
			projection = projection.perspective((float) Math.toRadians(45.0f), WIDTH / HEIGHT, 0.1f, 100.0f);
			
			shaders.setMat4f("projection", projection);
			shaders.setMat4f("view", view);
			
			shaders.use();
			
			glBindVertexArray(VAO);

			for (int i = 0; i < 10; i++) {
				Matrix4f model = new Matrix4f();
				model = model.translate(cubePositions[i]);
				//model = model.rotate(-(float) Math.toRadians(-55.0f), new Vector3f(1.0f, 0.0f, 0.0f)); 
				model = model.rotate((float) (Math.toRadians(glfwGetTime() * (i - 5) * 100)), new Vector3f(1f, 0.3f, 0.5f));  
				shaders.setMat4f("model", model);
				glDrawArrays(GL_TRIANGLES, 0, 36);
			}
			//glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
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
		if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS) glfwSetWindowShouldClose(window, true);
	}

	private static GLFWFramebufferSizeCallback framebuffer_size_callback = new GLFWFramebufferSizeCallback()
	{
		public void invoke(long window, int width, int height)
		{
			OGLEngine.WIDTH = width;
			OGLEngine.HEIGHT = height;
			glViewport(0, 0, width, height);
		}
	};

}
