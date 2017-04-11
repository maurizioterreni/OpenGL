package com.unifi.ing.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import com.unifi.ing.engine.entity.Camera;
import com.unifi.ing.engine.entity.Entity;
import com.unifi.ing.engine.entity.Light;
import com.unifi.ing.engine.entity.Rover;
import com.unifi.ing.engine.model.RawModel;
import com.unifi.ing.engine.model.TexturedModel;
import com.unifi.ing.engine.renderer.MasterRenderer;
import com.unifi.ing.engine.terrains.Terrain;
import com.unifi.ing.engine.texture.ModelTexture;
import com.unifi.ing.engine.texture.TerrainTexture;
import com.unifi.ing.engine.texture.TerrainTexturePack;
import com.unifi.ing.engine.utils.DisplayManager;

public class MainGameLoop {
	public static void main(String[] args) {
		DisplayManager.createDisplay();
        Loader loader = new Loader();
         
        
        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("martian"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("martianDirt"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
        
        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

        RawModel model = OBJLoader.loadObjModel("rock", loader);
         
        TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("rock")));

        
        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();
        for(int i=0;i<1000;i++){
            entities.add(new Entity(staticModel, new Vector3f(random.nextFloat()*1600,0,random.nextFloat() * 800),0,0,0,1));
            entities.add(new Entity(staticModel,  new Vector3f(random.nextFloat()*1600,0,random.nextFloat() * 800),0,0,0, 0.7f));
            
            
        }
         
        Light light = new Light(new Vector3f(20000,20000,2000),new Vector3f(1,1,1));
         
        Terrain terrain = new Terrain(0,0,loader,texturePack,blendMap);
        Terrain terrain2 = new Terrain(1,0,loader,texturePack,blendMap);
         
          
        MasterRenderer renderer = new MasterRenderer();
        //10:01
        
        RawModel bunnyModel = OBJLoader.loadObjModel("rover", loader);
        
        TexturedModel bunny = new TexturedModel(bunnyModel, new ModelTexture(loader.loadTexture("roverTexture")));
        
        Rover rover = new Rover(bunny, new Vector3f(600, 30, 550), 0, 0, 0, 2);
        Camera camera = new Camera(rover); 
        while(!Display.isCloseRequested()){
            camera.move();
            rover.move();
  
            renderer.processEntity(rover);
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
