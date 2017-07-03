package com.unifi.ing.engine.terrains;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import com.unifi.ing.engine.Loader;
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

	private int center = 4;

	public MultipleTerrain(Loader loader){
		backgroundTexture = new TerrainTexture(loader.loadTexture("martian"));
		rTexture = new TerrainTexture(loader.loadTexture("dirt"));
		gTexture = new TerrainTexture(loader.loadTexture("martianDirt"));
		bTexture = new TerrainTexture(loader.loadTexture("path"));
		texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

		terrains = new ArrayList<Terrain>();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Terrain terrain = new Terrain(0,0,i*Terrain.SIZE,j*Terrain.SIZE,loader,texturePack,blendMap);
				terrains.add(terrain);
			}

		}

	}

	public Terrain getTerrain(Vector3f roverPosition){

//		if()
		
		return terrains.get(center);
	}

	public List<Terrain> getTerrains() {
		return terrains;
	}

}
