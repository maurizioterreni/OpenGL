package com.unifi.ing.engine.shader;

import org.lwjgl.util.vector.Matrix4f;

import com.unifi.ing.engine.entity.Camera;
import com.unifi.ing.engine.utils.Maths;

public class StaticShader extends ShaderProgram{

	private static final String VERTEX_FILE = "src/com/unifi/ing/engine/shader/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/com/unifi/ing/engine/shader/fragmentShader.txt";
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	
	public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
 
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
    }

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
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
}
