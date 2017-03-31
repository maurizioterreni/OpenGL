package com.unifi.ing.engine;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;


import com.unifi.ing.engine.entity.Camera;
import com.unifi.ing.engine.entity.Entity;
import com.unifi.ing.engine.entity.Light;
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
		
		
        RawModel model = OBJLoader.loadObjModel("stall", loader);
         
        TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("stallTexture")));
         
        Entity entity = new Entity(staticModel, new Vector3f(0,-2,-30),0,0,0,1);
        Light light = new Light(new Vector3f(0, 0, -20), new Vector3f(1, 1, 1));
         
        Camera camera = new Camera();
         
        while(!Display.isCloseRequested()){
        	//Main loop
            entity.increaseRotation(0, 0.1f, 0);
            camera.move();
            renderer.prepare();
            shader.start();
            shader.loadLight(light);
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
