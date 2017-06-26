package com.unifi.ing.pattern.observer;

import com.unifi.ing.engine.terrains.Terrain;

public interface Observable {
	public void notifyEntity(Terrain terrain);
}
