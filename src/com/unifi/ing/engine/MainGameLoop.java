package com.unifi.ing.engine;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import com.unifi.ing.engine.entity.Camera;
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

		Terrain terrain = new Terrain(0,0,loader,texturePack,blendMap);
//		Terrain terrain = new Terrain(0,0,loader,texturePack, blendMap);

//		RawModel model = OBJLoader.loadObjModel("rock", loader);

//		TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("rock")));


//		List<Entity> entities = new ArrayList<Entity>();
//		Random random = new Random();
//		for(int i=0;i<400;i++){
//			float x = random.nextFloat()*800;
//			float z = random.nextFloat() * 800;	
//			float y = terrain.getHeightOfTerrain(x, z) - 1;
//			entities.add(new Entity(staticModel, new Vector3f(x,y,z),0,0,0,1));
//			entities.add(new Entity(staticModel, new Vector3f(x,y,z),0,0,0,1));
//
//		}

		Light light = new Light(new Vector3f(20000,20000,2000),new Vector3f(1,1,1));





		MasterRenderer renderer = new MasterRenderer(loader);

		RawModel roverModel = OBJLoader.loadObjModel("rover", loader);

		TexturedModel roverTexturedModel = new TexturedModel(roverModel, new ModelTexture(loader.loadTexture("roverTexture")));

		Rover rover = new Rover(roverTexturedModel, new Vector3f(600, 30, 550), 0, 0, 0, 2);
		

		Camera camera = new Camera(rover); 



		while(!Display.isCloseRequested()){
			camera.move();
			rover.move(terrain);
			
			renderer.processEntity(rover);
			
			renderer.processTerrain(terrain);


//			for(Entity entity:entities){
//				renderer.processEntity(entity);
//			}
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}

		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}
