package com.unifi.ing.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import com.unifi.ing.engine.entity.Camera;
import com.unifi.ing.engine.entity.Entity;
import com.unifi.ing.engine.entity.Light;
import com.unifi.ing.engine.terrains.Terrain;
import com.unifi.ing.engine.texture.ModelTexture;
import com.unifi.ing.engine.utils.DisplayManager;

import model.RawModel;
import model.TexturedModel;

public class MainGameLoop {
	public static void main(String[] args) {
		DisplayManager.createDisplay();
        Loader loader = new Loader();
         
         
        RawModel model = OBJLoader.loadObjModel("rock", loader);
         
        TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("rock")));
        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();
        for(int i=0;i<1000;i++){
            entities.add(new Entity(staticModel, new Vector3f(random.nextFloat()*1600,0,random.nextFloat() * 800),0,0,0,1));
        }
         
        Light light = new Light(new Vector3f(20000,20000,2000),new Vector3f(1,1,1));
         
        Terrain terrain = new Terrain(0,0,loader,new ModelTexture(loader.loadTexture("groundMartian")));
        Terrain terrain2 = new Terrain(1,0,loader,new ModelTexture(loader.loadTexture("groundMartian")));
         
        Camera camera = new Camera();   
        MasterRenderer renderer = new MasterRenderer();
        
        
         
        while(!Display.isCloseRequested()){
            camera.move();
             
            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);
            for(Entity entity:entities){
                renderer.processEntity(entity);
            }
            renderer.render(light, camera);
            DisplayManager.updateDisplay();
        }
 
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
	}
}
