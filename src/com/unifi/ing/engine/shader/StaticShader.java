package com.unifi.ing.engine.shader;

import org.lwjgl.util.vector.Matrix4f;

import com.unifi.ing.engine.entity.Camera;
import com.unifi.ing.engine.entity.Light;
import com.unifi.ing.engine.utils.Maths;

public class StaticShader extends ShaderProgram{

	private static final String VERTEX_FILE = "src/com/unifi/ing/engine/shader/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/com/unifi/ing/engine/shader/fragmentShader.txt";
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition;
	private int location_lightColour;
	private int location_ShineDamper;
	private int location_Reflectivity;
	
	public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
 
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_lightPosition = super.getUniformLocation("lightPosition");
		location_lightColour = super.getUniformLocation("lightColour");
		location_ShineDamper = super.getUniformLocation("shineDamper");
		location_Reflectivity = super.getUniformLocation("reflectivity");
	}

	
	public void loadTransformationMatrix(Matrix4f matrix4f){
		
		super.loadMatrix(location_transformationMatrix, matrix4f);
	}
	
	public void loadProjectionMatrix(Matrix4f projection){
		super.loadMatrix(location_projectionMatrix, projection);
	}
	
	public void loadViewMatrix(Camera camera){
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
		
	}
	
	public void loadLight(Light light){
		super.loadVector(location_lightPosition, light.getPosition());
		super.loadVector(location_lightColour, light.getColor());
	}
	
	public void loadShineVariables(float damper, float reflectivity){
		super.loadFloat(location_Reflectivity, reflectivity);
		super.loadFloat(location_ShineDamper, damper);
	}
	
}
