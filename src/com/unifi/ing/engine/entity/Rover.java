package com.unifi.ing.engine.entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import com.unifi.ing.engine.model.TexturedModel;
import com.unifi.ing.engine.terrains.Terrain;
import com.unifi.ing.engine.utils.DisplayManager;

public class Rover extends Entity{
	
	private static final float RUN_SPEED = 20;
	private static final float TURN_SPEED = 160;
	private static final float GRAVITY = -20;

	
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardsSpeed = 0;

	public Rover(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}

	
	public void move(Terrain terrain){
		checkInputs();
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
	}
	
	private void checkInputs(){
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
	}
}
