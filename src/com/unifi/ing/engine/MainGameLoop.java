package com.unifi.ing.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import com.unifi.ing.engine.entity.Camera;
import com.unifi.ing.engine.entity.Entity;
import com.unifi.ing.engine.entity.Light;
import com.unifi.ing.engine.renderer.MasterRenderer;
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
        TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader), new ModelTexture(loader.loadTexture("grassTexture")));
        grass.getTexture().setHasTransparency(true);
        grass.getTexture().setUseFakeLighting(true);
        
        TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader), new ModelTexture(loader.loadTexture("fern")));
        fern.getTexture().setHasTransparency(true);
        
        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();
        for(int i=0;i<1000;i++){
            entities.add(new Entity(staticModel, new Vector3f(random.nextFloat()*1600,0,random.nextFloat() * 800),0,0,0,1));
            entities.add(new Entity(grass,  new Vector3f(random.nextFloat()*1600,0,random.nextFloat() * 800),0,0,0, 1));
            entities.add(new Entity(fern,  new Vector3f(random.nextFloat()*1600,0,random.nextFloat() * 800),0,0,0, 0.6f));
            
        }
         
        Light light = new Light(new Vector3f(20000,20000,2000),new Vector3f(1,1,1));
         
        Terrain terrain = new Terrain(0,0,loader,new ModelTexture(loader.loadTexture("mrtBk")));
        Terrain terrain2 = new Terrain(1,0,loader,new ModelTexture(loader.loadTexture("mrtBk")));
         
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
