package com.unifi.ing.engine.entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import com.unifi.ing.engine.model.TexturedModel;
import com.unifi.ing.engine.terrains.Terrain;
import com.unifi.ing.engine.utils.DisplayManager;
import com.unifi.ing.engine.utils.Quaternion;

public class Rover extends Entity{

	private static final float RUN_SPEED = 20;
	private static final float TURN_SPEED = 160;
	private static final float GRAVITY = -20;


	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardsSpeed = 0;
	float offset = 0.5f;



	public Rover(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}


	public void move(Terrain terrain){
		checkInputs(terrain);
		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, 0, dz);
		upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
		super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds() , 0);
		float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		if(super.getPosition().y < terrainHeight){
			upwardsSpeed = 0;
			super.getPosition().y = terrainHeight;
		}
		
		Vector3f normal = calculateNormal(this.getPosition().x, this.getPosition().z, terrain);
		
		rotateRover(normal);
		
	}
	
	private void rotateRover(Vector3f normal){
		Quaternion quaternion = new Quaternion(normal.x, normal.y, normal.z, normal.y).nor();
		this.setRotX(quaternion.getPitch());
		this.setRotZ(quaternion.getRoll());
	}
	
	private Vector3f calculateNormal(float x, float z, Terrain terrain){
		
		float heightL = terrain.getHeightOfTerrain(x - offset, z);//getHeight((int)(x-1f), (int)z, generator);
		float heightR = terrain.getHeightOfTerrain(x + offset, z);//getHeight((int)(x+1f), (int)z, generator);
		float heightD = terrain.getHeightOfTerrain(x, z - offset);//getHeight((int)x, (int)(z-1f), generator);
		float heightU = terrain.getHeightOfTerrain(x, z + offset);//getHeight((int)x, (int)(z+1f), generator);
		Vector3f normal = new Vector3f(heightL - heightR, 2f, heightD - heightU);
		
		normal.normalise();
		return normal; 
	}
	

	private void checkInputs(Terrain terrain){
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			this.currentSpeed = +RUN_SPEED;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			this.currentSpeed = -RUN_SPEED;
		}else{
			this.currentSpeed = 0;
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			this.currentTurnSpeed = -TURN_SPEED;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			this.currentTurnSpeed = TURN_SPEED;
		}else{
			this.currentTurnSpeed = 0;
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_B)){
			this.setRotX(this.getRotX() + 0.1f);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_N)){
			this.setRotY(this.getRotY() + 0.1f);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_M)){
			this.setRotZ(this.getRotZ() + 0.1f);
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_H)){
			this.setRotX(this.getRotX() - 0.1f);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_J)){
			this.setRotY(this.getRotY() - 0.1f);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_K)){
			this.setRotZ(this.getRotZ() - 0.1f);
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_L)){
			offset = offset + 0.01f;
			System.out.println(offset);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_P)){
			offset = offset - 0.01f;
			System.out.println(offset);
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_R)){
			this.setRotX(0);
			this.setRotY(0);
			this.setRotZ(0);
			this.setPosition(new Vector3f(600, 10030, 550));
		}

	}
}
