package com.unifi.ing.engine;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import com.unifi.ing.engine.entity.Camera;
import com.unifi.ing.engine.entity.Entity;
import com.unifi.ing.engine.shader.StaticShader;
import com.unifi.ing.engine.texture.ModelTexture;
import com.unifi.ing.engine.utils.DisplayManager;

import model.RawModel;
import model.TexturedModel;

public class MainGameLoop {
	public static void main(String[] args) {
		DisplayManager.createDisplay();
		Loader loader = new Loader();

		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);
		
		float[] vertices = {            
                -0.5f,0.5f,0,   
                -0.5f,-0.5f,0,  
                0.5f,-0.5f,0,   
                0.5f,0.5f,0,        
                 
                -0.5f,0.5f,1,   
                -0.5f,-0.5f,1,  
                0.5f,-0.5f,1,   
                0.5f,0.5f,1,
                 
                0.5f,0.5f,0,    
                0.5f,-0.5f,0,   
                0.5f,-0.5f,1,   
                0.5f,0.5f,1,
                 
                -0.5f,0.5f,0,   
                -0.5f,-0.5f,0,  
                -0.5f,-0.5f,1,  
                -0.5f,0.5f,1,
                 
                -0.5f,0.5f,1,
                -0.5f,0.5f,0,
                0.5f,0.5f,0,
                0.5f,0.5f,1,
                 
                -0.5f,-0.5f,1,
                -0.5f,-0.5f,0,
                0.5f,-0.5f,0,
                0.5f,-0.5f,1
                 
        };
         
        float[] textureCoords = {
                 
                0,0,
                0,1,
                1,1,
                1,0,            
                0,0,
                0,1,
                1,1,
                1,0,            
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0
 
                 
        };
         
        int[] indices = {
                0,1,3,  
                3,1,2,  
                4,5,7,
                7,5,6,
                8,9,11,
                11,9,10,
                12,13,15,
                15,13,14,   
                16,17,19,
                19,17,18,
                20,21,23,
                23,21,22
 
        };
         
        RawModel model = loader.loadToVAO(vertices,textureCoords,indices);
         
        TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("image")));
         
        Entity entity = new Entity(staticModel, new Vector3f(0,0,-5),0,0,0,1);
         
        Camera camera = new Camera();
         
        while(!Display.isCloseRequested()){
        	//Main loop
            entity.increaseRotation(1, 1, 0);
            camera.move();
            renderer.prepare();
            shader.start();
            shader.loadViewMatrix(camera);
            renderer.render(entity,shader);
            shader.stop();
            DisplayManager.updateDisplay();         
        }
 
        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
	}
}
