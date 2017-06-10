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
		
//		rotateRover(terrain);
		
	}
	
//	private void rotateRover(Terrain terrain) {
//		float offset = 2f;
//		
//		float x = this.getPosition().x;
//		float y = this.getPosition().y;
//		float z = this.getPosition().z;
//		
//		Vector3f vX = rotateVector(pointTwoVector(new Vector3f(x,y, z + offset), 
//				new Vector3f(x,y, z - offset)),this.getRotY());
//		
//		Vector3f v1 = rotateVector(pointTwoVector(new Vector3f(x + offset,terrain.getHeightOfTerrain(x + offset, z + offset), z + offset), 
//				new Vector3f(x + offset,terrain.getHeightOfTerrain(x + offset, z - offset), z - offset)),this.getRotY());
//		vX.normalise();
//		v1.normalise();
//		
//		
////		Vector3f vZ = rotateVector(pointTwoVector(new Vector3f(x + offset,y, z), 
////				new Vector3f(x - offset,y, z)),this.getRotY());
////		
////		Vector3f v2 = rotateVector(pointTwoVector(new Vector3f(x + offset,terrain.getHeightOfTerrain(x + offset, z + offset), z + offset), 
////				new Vector3f(x + offset,terrain.getHeightOfTerrain(x + offset, z - offset), z - offset)),this.getRotY());
//		
//		
//		
//		
//		//float angleZ = (float) Math.acos( Maths.mul(v2, vZ) / (Maths.len(v2) * Maths.len(vZ)));
//		float angleX = (float) Math.acos( Maths.mul(v1, vX) / (Maths.len(v1) * Maths.len(vX)) );
//		
//		
//		Quaternion quaternion = new Quaternion(vX)
//		
//		this.setRotX(50);
//		
//		
////		this.setRotX(angleX * Maths.radiansToDegrees);
////		this.setRotZ(angleZ * Maths.radiansToDegrees); 
//		
//	}
//	
//	private Vector3f rotateVector(Vector3f v , float teta){
//		float v1 = (float) (v.x * Math.cos(teta) - v.z * Math.sin(teta));
//		float v2 = (float) (v.x * Math.sin(teta) + v.z * Math.cos(teta));
//		
//		return new Vector3f(v1,v.y,v2);
//	}
//
//
//	private Vector3f pointTwoVector(Vector3f p1, Vector3f p2){
//		if(p1.y > p2.y){
//			return new Vector3f(p2.x - p1.x, p2.y - p1.y, p2.z - p1.z);
//		}else{
//			return new Vector3f(p1.x - p2.x, p1.y - p2.y, p1.z - p2.z);
//		}
//	}

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

		

		if(Keyboard.isKeyDown(Keyboard.KEY_R)){
			this.setRotX(0);
			this.setRotY(0);
			this.setRotZ(0);
			this.setPosition(new Vector3f(600, 30, 550));
		}

	}
}
