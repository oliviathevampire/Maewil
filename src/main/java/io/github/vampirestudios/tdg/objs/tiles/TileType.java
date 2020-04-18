package io.github.vampirestudios.tdg.objs.tiles;

import java.util.HashMap;
import java.util.Map;

/** For internal server use only: the graphical representation of an item or tile */
public final class TileType {
	
	/** The numerical name of the .png file that contains the tile data. */
	private final int number;
	
	/** Rotation of the tile: 0, 90, 180, 270. */
	private final int rotation;
	
	private static final HashMap<Integer, String> numberMap_synch = new HashMap<>();
	
    public TileType(int number) {
    	if(number <= 0) { throw new IllegalArgumentException(); }
    	
    	this.number = number;
    	this.rotation = 0;
    	synchronized(numberMap_synch) {
    		numberMap_synch.put(number, null);
    	}
    }	 

	
    public TileType(int number, int rotation) {
    	this.number = number;
    	this.rotation = rotation;
    	synchronized(numberMap_synch) {
    		numberMap_synch.put(number, null);
    	}
    }	 

    public TileType(int number, int rotation, String name) {
    	this.number = number;
    	this.rotation = rotation;
    	synchronized(numberMap_synch) {
    		numberMap_synch.put(number, name);
    	}
    }	 

    
    public int getNumber() {
		return number;
	}
    
    public int getRotation() {
		return rotation;
	}
    
    public static Map<Integer, String> getValues() {
    	synchronized(numberMap_synch) {
    		return new HashMap<>(numberMap_synch);
    	}
	}
	// TODO: LOW - Find a better way to handle this map.

    @Override
    public boolean equals(Object obj) {
    	if(!(obj instanceof TileType)) {
    		return false;
    	}
    	TileType other = (TileType)obj;
    	
    	// We intentionally do not compare name here.
    	return this.number == other.number && this.rotation == other.rotation;
    }
}