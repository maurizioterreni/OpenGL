package com.unifi.ing.engine.skybox;

import org.lwjgl.util.vector.Matrix4f;

import com.unifi.ing.engine.entity.Camera;
import com.unifi.ing.engine.shader.ShaderProgram;
import com.unifi.ing.engine.utils.Maths;


public class SkyboxShader extends ShaderProgram{
 
    private static final String VERTEX_FILE = "src/com/unifi/ing/engine/skybox/skyboxVertexShader.txt";
    private static final String FRAGMENT_FILE = "src/com/unifi/ing/engine/skybox/skyboxFragmentShader.txt";
     
    private int location_projectionMatrix;
    private int location_viewMatrix;
     
    public SkyboxShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
     
    public void loadProjectionMatrix(Matrix4f matrix){
        super.loadMatrix(location_projectionMatrix, matrix);
    }
 
    public void loadViewMatrix(Camera camera){
        Matrix4f matrix = Maths.createViewMatrix(camera);
        matrix.m30 = 0;
        matrix.m31 = 0;
        matrix.m32 = 0;
        super.loadMatrix(location_viewMatrix, matrix);
    }
     
    @Override
    protected void getAllUniformLocations() {
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
    }
 
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }
 
}