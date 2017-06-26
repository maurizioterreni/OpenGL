package com.unifi.ing.engine.entity;


import org.lwjgl.util.vector.Vector3f;

import com.unifi.ing.engine.model.TexturedModel;
import com.unifi.ing.engine.terrains.Terrain;
import com.unifi.ing.engine.utils.Maths;
import com.unifi.ing.pattern.observer.Observer;

public class Cube extends Entity implements Observer{

	private float offsetX;
	private float offsetZ;

	public Cube(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, 
			float scale, float offsetX, float offsetZ) {
		super(model, position, rotX, rotY, rotZ, scale);
		this.offsetX = offsetX;
		this.offsetZ = offsetZ;
	}

	@Override
	public void update(Entity entity, Terrain terrain) {
		Rover rover = ((Rover) entity);
		Vector3f roverPosition = rover.getPosition();
		Vector3f position = rotateVector(new Vector3f(roverPosition.x + offsetX, 0, roverPosition.z + offsetZ),
				rover.getPosition(), Maths.toRadiants(rover.getRotY()));
		position.y = terrain.getHeightOfTerrain(position.x, position.z);
		this.setPosition(position);
	}

	private Vector3f rotateVector(Vector3f v , Vector3f center,float teta){
		teta = -teta;

		float v1 = (float) (center.x + (v.x - center.x) * Math.cos(teta) - (v.z - center.z) * Math.sin(teta));
		float v2 = (float) (center.z + (v.x - center.x) * Math.sin(teta) + (v.z - center.z) * Math.cos(teta));
		
		
		return new Vector3f(v1,v.y,v2);
	}
	
	public float getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(float offsetX) {
		this.offsetX = offsetX;
	}

	public float getOffsetZ() {
		return offsetZ;
	}

	public void setOffsetZ(float offsetZ) {
		this.offsetZ = offsetZ;
	}
}

