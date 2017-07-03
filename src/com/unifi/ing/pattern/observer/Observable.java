package com.unifi.ing.pattern.observer;

import com.unifi.ing.engine.terrains.MultipleTerrain;

public interface Observable {
	public void notifyEntity(MultipleTerrain terrain);
}
