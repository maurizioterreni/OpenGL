package com.unifi.ing.engine.utils;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.unifi.ing.engine.entity.Camera;

public class Maths {
	
	static public final float FLOAT_ROUNDING_ERROR = 0.000001f; // 32 bits
	static public final float PI = 3.1415927f;
	static public final float PI2 = PI * 2;
	static public final float radiansToDegrees = 180f / PI;
	static public final float degreesToRadians = PI / 180;
	

	
	public static Matrix4f createTransformationMatrix(Vector3f translation , float rx, float ry, float rz , float scale){
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation,matrix,matrix);
		Matrix4f.rotate((float) Math.toRadians(rx),new Vector3f(1, 0, 0) , matrix,matrix);
		Matrix4f.rotate((float) Math.toRadians(ry),new Vector3f(0, 1, 0) , matrix,matrix);
		Matrix4f.rotate((float) Math.toRadians(rz),new Vector3f(0, 0, 1) , matrix,matrix);
		Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);
		return matrix;
	}
	
	public static Matrix4f createViewMatrix(Camera camera){
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0,0) , viewMatrix, viewMatrix);
		Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1,0) , viewMatrix, viewMatrix);
		Vector3f cameraPos = camera.getPosition();
		Vector3f negativeCameraPos  = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
		Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
		return viewMatrix;
	}
	
	public static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos) {
		float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
		float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
		float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
		float l3 = 1.0f - l1 - l2;
		return l1 * p1.y + l2 * p2.y + l3 * p3.y;
	}
	
	static public float atan2 (float y, float x) {
		if (x == 0f) {
			if (y > 0f) return PI / 2;
			if (y == 0f) return 0f;
			return -PI / 2;
		}
		final float atan, z = y / x;
		if (Math.abs(z) < 1f) {
			atan = z / (1f + 0.28f * z * z);
			if (x < 0f) return atan + (y < 0f ? -PI : PI);
			return atan;
		}
		atan = PI / 2 - z / (z * z + 0.28f);
		return y < 0f ? atan - PI : atan;
	}
	

	static public short clamp (short value, short min, short max) {
		if (value < min) return min;
		if (value > max) return max;
		return value;
	}

	static public int clamp (int value, int min, int max) {
		if (value < min) return min;
		if (value > max) return max;
		return value;
	}

	static public long clamp (long value, long min, long max) {
		if (value < min) return min;
		if (value > max) return max;
		return value;
	}

	static public float clamp (float value, float min, float max) {
		if (value < min) return min;
		if (value > max) return max;
		return value;
	}

	static public double clamp (double value, double min, double max) {
		if (value < min) return min;
		if (value > max) return max;
		return value;
	}
	
	static public boolean isEqual (float a, float b, float tolerance) {
		return Math.abs(a - b) <= tolerance;
	}
	
	static public boolean isEqual (float a, float b) {
		return Math.abs(a - b) <= FLOAT_ROUNDING_ERROR;
	}
	static public float len(float a, float b, float c){
		return (float) Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2) + Math.pow(c, 2));
	}
	
	static public boolean isZero (float value) {
		return Math.abs(value) <= FLOAT_ROUNDING_ERROR;
	}

	/** Returns true if the value is zero.
	 * @param tolerance represent an upper bound below which the value is considered zero. */
	static public boolean isZero (float value, float tolerance) {
		return Math.abs(value) <= tolerance;
	}
	public static float dot (float x1, float y1, float z1, float x2, float y2, float z2) {
		return x1 * x2 + y1 * y2 + z1 * z2;
	}
	
}
