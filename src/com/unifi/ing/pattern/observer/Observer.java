package com.unifi.ing.pattern.observer;

import com.unifi.ing.engine.entity.Entity;
import com.unifi.ing.engine.terrains.Terrain;

public interface Observer {
	public void update(Entity entity, Terrain terrain);
}
