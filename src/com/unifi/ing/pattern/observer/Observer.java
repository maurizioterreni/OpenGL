package com.unifi.ing.pattern.observer;

import com.unifi.ing.engine.entity.Entity;
import com.unifi.ing.engine.terrains.MultipleTerrain;

public interface Observer {
	public void update(Entity entity, MultipleTerrain terrain);
}
