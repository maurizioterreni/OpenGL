package com.unifi.ing.engine.entity;

import java.util.ArrayList;
import java.util.List;

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

	private float currentZrot = 0;
	private float currentXrot = 0;


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
//		rotateRover(terrain);

	}






	private void rotateRover(Terrain terrain) {

		int dist = 8;
		List<Vector3f> roverVec = new ArrayList<>();
		List<Float> heights = new ArrayList<>();

		roverVec.add(new Vector3f(getXP(this.getPosition().x - dist, this.getRotY()), this.getPosition().y, getZP(this.getPosition().z, this.getRotY())));
		roverVec.add(new Vector3f(getXP(this.getPosition().x + dist, this.getRotY()), this.getPosition().y, getZP(this.getPosition().z, this.getRotY())));

		roverVec.add(new Vector3f(getXP(this.getPosition().x, this.getRotY()), this.getPosition().y, getZP(this.getPosition().z + dist, this.getRotY())));
		roverVec.add(new Vector3f(getXP(this.getPosition().x, this.getRotY()), this.getPosition().y, getZP(this.getPosition().z - dist, this.getRotY())));

		for (Vector3f vector3f : roverVec) {
			heights.add(terrain.getHeightOfTerrain(vector3f.x, vector3f.z));
		}



		this.setRotZ(calcAng(currentXrot - Math.abs(heights.get(1) - heights.get(0)), 2 * dist));
		this.setRotX(calcAng(currentZrot - Math.abs(heights.get(3) - heights.get(2)), 2 * dist));




	}


	private float getXP(float coord, float alpha){
//		return (float) (coord * Math.sin(Math.toDegrees(alpha)));
		//		return coord;
		return coord * alpha;
	}

	private float getZP(float coord, float alpha){
//		return (float) (coord * Math.cos(Math.toDegrees(alpha)));
		//		return coord;
		return coord * alpha;
	}

	private float calcAng(float a, float b){
		float c = (float) Math.sqrt(Math.pow(a,2) + Math.pow(b, 2));
		return (float) Math.toDegrees(Math.asin(a/c));
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

		if(Keyboard.isKeyDown(Keyboard.KEY_P)){
			System.out.println("\tPx: " + this.getPosition().x + "\tPy: " + this.getPosition().y + "\tPz: " + this.getPosition().z);
			System.out.println("\tRx: " + this.getRotX() + "\tRy: " + this.getRotY() + "\tRz: " + this.getRotZ());
			System.out.println("\tAngolo: " + (this.getRotY() % 360));
			System.out.println("#################################################################################################");
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_R)){
			currentXrot = 0;
			currentZrot = 0;
			this.setRotX(0);
			this.setRotY(0);
			this.setRotZ(0);
			this.setPosition(new Vector3f(600, 30, 550));
		}

	}
}
