package com.unifi.ing.engine.terrains;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import com.unifi.ing.engine.Loader;
import com.unifi.ing.engine.terrains.utils.MapTerrainIndex;
import com.unifi.ing.engine.texture.TerrainTexture;
import com.unifi.ing.engine.texture.TerrainTexturePack;

public class MultipleTerrain {
	//	Definisco il terreno e le sue multiTexture
	private TerrainTexture backgroundTexture;
	private TerrainTexture rTexture;
	private TerrainTexture gTexture;
	private TerrainTexture bTexture;

	private TerrainTexturePack texturePack;
	private TerrainTexture blendMap;


	private List<Terrain> terrains;  

	private MapTerrainIndex mapTerrainIndex;
	

	public MultipleTerrain(Loader loader){
		backgroundTexture = new TerrainTexture(loader.loadTexture("martian"));
		rTexture = new TerrainTexture(loader.loadTexture("dirt"));
		gTexture = new TerrainTexture(loader.loadTexture("martianDirt"));
		bTexture = new TerrainTexture(loader.loadTexture("path"));
		texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

		terrains = new ArrayList<Terrain>();

		for (int i = 0; i < 9; i++) {
			Terrain terrain = new Terrain(0,0,0,0,loader,texturePack,blendMap);
			terrains.add(terrain);
		}
		
		mapTerrainIndex = new MapTerrainIndex();
	}
	
	public void checkTerrain(Vector3f position) {
		int cPos = getCurrentTerrain(position.x, position.z);
		
		if(cPos == mapTerrainIndex.getCurrentTerrain())
			return;
		
		MapTerrainIndex newMap = null;
		
		if(cPos == mapTerrainIndex.getCurrentCTL()){
			newMap = MapTerrainIndex.builder().
					currentBottom(mapTerrainIndex.getCurrentLeft()).
					currentCBL(mapTerrainIndex.getCurrentBottom()).
					currentCBR(mapTerrainIndex.getCurrentTerrain()).
					currentCTL(mapTerrainIndex.getCurrentCBR()).
					currentCTR(mapTerrainIndex.getCurrentRight()).
					currentLeft(mapTerrainIndex.getCurrentCBL()).
					currentRight(mapTerrainIndex.getCurrentTop()).
					currentTerrain(mapTerrainIndex.getCurrentCTL()).
					currentTop(mapTerrainIndex.getCurrentCTR()).
					build();
		}else if(cPos == mapTerrainIndex.getCurrentTop()){
			newMap = MapTerrainIndex.builder().
					currentBottom(mapTerrainIndex.getCurrentTerrain()).
					currentCBL(mapTerrainIndex.getCurrentLeft()).
					currentCBR(mapTerrainIndex.getCurrentRight()).
					currentCTL(mapTerrainIndex.getCurrentCBL()).
					currentCTR(mapTerrainIndex.getCurrentCBR()).
					currentLeft(mapTerrainIndex.getCurrentCTL()).
					currentRight(mapTerrainIndex.getCurrentCTR()).
					currentTerrain(mapTerrainIndex.getCurrentTop()).
					currentTop(mapTerrainIndex.getCurrentBottom()).
					build();
		}
	}
	
	private void moveTerrain(){
		float x = terrains.get(mapTerrainIndex.getCurrentTerrain()).getX();
		float z = terrains.get(mapTerrainIndex.getCurrentTerrain()).getZ();
		
		terrains.get(mapTerrainIndex.getCurrentCTL()).setPosition(x + Terrain.SIZE, z + Terrain.SIZE);
		terrains.get(mapTerrainIndex.getCurrentTop()).setPosition(x, z + Terrain.SIZE);
		terrains.get(mapTerrainIndex.getCurrentCTR()).setPosition(x - Terrain.SIZE, z + Terrain.SIZE);
		terrains.get(mapTerrainIndex.getCurrentLeft()).setPosition(x + Terrain.SIZE, z);
		terrains.get(mapTerrainIndex.getCurrentRight()).setPosition(x - Terrain.SIZE, z);
		terrains.get(mapTerrainIndex.getCurrentCBL()).setPosition(x + Terrain.SIZE, z - Terrain.SIZE);
		terrains.get(mapTerrainIndex.getCurrentBottom()).setPosition(x, z - Terrain.SIZE);
		terrains.get(mapTerrainIndex.getCurrentCBR()).setPosition(x - Terrain.SIZE, z - Terrain.SIZE);
	}
	
	private int getCurrentTerrain(float roverX, float roverZ){
		for (int i = 1; i < terrains.size(); i++) {
			if(terrains.get(i).isInside(roverX,roverZ))
				return i;
		}
		
		return 0;
	}

}
