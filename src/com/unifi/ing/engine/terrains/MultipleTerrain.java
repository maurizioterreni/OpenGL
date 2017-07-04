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
	private float offsetX = 0;
	private float offsetZ = 0;

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
	
	public void moveTerrainMap(Vector3f roverPosition){
		Integer cubePos = getMapValue(roverPosition.x, roverPosition.z);
		
		if(cubePos == null) return;
		
		if (cubePos == 5){
			offsetX = terrains.get(2).getX();
			offsetZ = terrains.get(2).getZ();
			moveTerrain(terrains.get(0), terrains.get(0).getX(), offsetZ + Terrain.SIZE);
			moveTerrain(terrains.get(3), terrains.get(3).getX(), offsetZ + Terrain.SIZE);
			moveTerrain(terrains.get(6), terrains.get(6).getX(), offsetZ + Terrain.SIZE);
		}else if (cubePos == 2){
			offsetX = terrains.get(0).getX() - 2800;
			offsetZ = terrains.get(0).getZ();
			moveTerrain(terrains.get(6), offsetX + Terrain.SIZE, terrains.get(6).getZ());
			moveTerrain(terrains.get(7), offsetX + Terrain.SIZE, terrains.get(7).getZ());
			moveTerrain(terrains.get(8), offsetX + Terrain.SIZE, terrains.get(8).getZ());
		}else if (cubePos == 7){
			offsetX = terrains.get(3).getX();
			offsetZ = terrains.get(3).getZ();
			moveTerrain(terrains.get(0), offsetX + Terrain.SIZE, terrains.get(0).getZ());
			moveTerrain(terrains.get(1), offsetX + Terrain.SIZE, terrains.get(1).getZ());
			moveTerrain(terrains.get(2), offsetX + Terrain.SIZE, terrains.get(2).getZ());
		}else if (cubePos == 3){
			offsetX = terrains.get(2).getX();
			offsetZ = terrains.get(2).getZ();
			moveTerrain(terrains.get(2), terrains.get(2).getX(), offsetZ + Terrain.SIZE);
			moveTerrain(terrains.get(5), terrains.get(5).getX(), offsetZ + Terrain.SIZE);
			moveTerrain(terrains.get(8), terrains.get(8).getX(), offsetZ + Terrain.SIZE);
		}
	}
	
	private void moveTerrain(Terrain t, float posX, float posZ){
		t.setX(posX);
		t.setZ(posZ);
	}

	private Integer getMapValue(float x, float z){
		float posX = x - offsetX;
		float posZ = z - offsetZ;
		
		int xCube = floatToInt(posX / Terrain.SIZE);
		int zCube = floatToInt(posZ / Terrain.SIZE);
		
		Integer index = center;
		
		if (xCube == 0 && zCube == 0){
			index = 0;
		}else if (xCube == 0 && zCube == 1){
			index = 1;
		}else if (xCube == 0 && zCube == 2){
			index = 2;
		}else if (xCube == 1 && zCube == 0){
			index = 3;
		}else if (xCube == 1 && zCube == 1){
			index = 4;
		}else if (xCube == 1 && zCube == 2){
			index = 5;
		}else if (xCube == 2 && zCube == 0){
			index = 6;
		}else if (xCube == 2 && zCube == 1){
			index = 7;
		}else if (xCube == 2 && zCube == 2){
			index = 8;
		}else{
			return null;
		}
		
		return index;
	}
	
	public Terrain getTerrain(Vector3f roverPosition){

		Integer cubePos = getMapValue(roverPosition.x, roverPosition.z);
		
		if(cubePos == null) return null;
		
		return terrains.get(cubePos);
	}

	public List<Terrain> getTerrains() {
		return terrains;
	}
	
	private int floatToInt(float value){
		if(value < 1){
			return 0;
		}else if(value >= 1 && value < 2){
			return 1;
		}else if(value >= 2 && value < 3){
			return 2;
		}else if(value >= 3 && value < 4){
			return 3;
		}else{
			return -1;
		}
	}

}
