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
//		Creazione del display
		DisplayManager.createDisplay();
		
//		Inizializzazione dell'oggetto loader utilizzato per caricare modelli 3D
		Loader loader = new Loader();


//		Definisco il terreno e le sue multiTexture
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("martian"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("martianDirt"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

		Terrain terrain = new Terrain(0,0,loader,texturePack,blendMap);
		

//		RawModel model = OBJLoader.loadObjModel("rock", loader);

//		TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("rock")));
//
//
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

//		Creo l'oggetto luce e lo posiziono ad una determinata altezza
		Light light = new Light(new Vector3f(20000,20000,2000),new Vector3f(1,1,1));


		MasterRenderer renderer = new MasterRenderer(loader);
		
		
//		Carico il modello del Rover
		RawModel roverModel = OBJLoader.loadObjModel("rover", loader);

		TexturedModel roverTexturedModel = new TexturedModel(roverModel, new ModelTexture(loader.loadTexture("roverTexture")));

//		Setto la posizione del rover in una determinata area
		Rover rover = new Rover(roverTexturedModel, new Vector3f(600, 30, 550), 0, 0, 0, 1);
		
//		Inizializzo la camera fornendogli come parametro l'oggetto da seguire
		
		Camera camera = new Camera(rover); 


//		Rimaniamo all'interno del ciclo finchè non vi è una richiesta di chiusura 
		while(!Display.isCloseRequested()){
//			Chiamo il metodo move del rover fornendogli come parametro il terreno su cui si deve muovere
//			Il rover si muoverà in base all'input fornito dall'utente gestito all'interno del metodo stesso
			rover.move(terrain);
//			Ricalcolo la posizione della camera in base alla nuova posizione del Rover
			camera.move();
			
//			Eseguo il renderer sul rover
			renderer.processEntity(rover);
//			Eseguo il renderer sul terreno
			renderer.processTerrain(terrain);


//			for(Entity entity:entities){
//				renderer.processEntity(entity);
//			}
			
//			Eseguo il renderer della luce passandogli la camera 
//			in modo tale da poter calcolare la luce secondo il 
//			nostro punto di vista
			renderer.render(light, camera);
			
//			Aggiorno il display
			DisplayManager.updateDisplay();
		}
		
//		Pulisco la memoria prima di terminare il programma
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}
