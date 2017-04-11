package com.unifi.ing.engine.entity;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private float distanceFromRover = 50;
	private float angleAroundRover = 0;
	
	private Vector3f position = new Vector3f(0,0,0);
	private float pitch = 20;
	private float yaw;
	private float roll;
	
	private Rover rover;
	
	public Camera(Rover rover){
		this.rover = rover;
	}
	
	public void move(){
		calculateZoom();
		calculatePitch();
		calculateAngleAroundRover();
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		calculateCameraPosition(horizontalDistance, verticalDistance);
		this.yaw = 180 - (rover.getRotY() + angleAroundRover);
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	private void calculateCameraPosition(float horizontalDistance, float verticalDistance){
		float theta = rover.getRotY() + angleAroundRover;
		float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
		position.x = rover.getPosition().x - offsetX;
		position.z = rover.getPosition().z - offsetZ;
 		position.y = rover.getPosition().y + verticalDistance;
 	}
	
	private float calculateHorizontalDistance(){
		return (float) (distanceFromRover * Math.cos(Math.toRadians(pitch)));
	}
	
	
	private float calculateVerticalDistance(){
		return (float) (distanceFromRover * Math.sin(Math.toRadians(pitch)));
	}
	
	
	
	private void calculateZoom(){
		float zoomLevel = Mouse.getDWheel() * 0.01f;
		distanceFromRover -= zoomLevel;
	}
	
	private void calculatePitch(){
		if(Mouse.isButtonDown(1)){
			float pitchChange = Mouse.getDY() * 0.01f;
			pitch -= pitchChange;
		}
	}
	
	private void calculateAngleAroundRover(){
		if(Mouse.isButtonDown(0)){
			float angleChange = Mouse.getDX() * 0.03f;
			angleAroundRover -= angleChange;
		}
	}

}
