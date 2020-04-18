package io.github.vampirestudios.tdg.objs.tiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** A group of related tiles, used to create a larger sprite that is composed of multiple tiles */
public final class TileTypeGroup {

	// number of (32x32) tiles wide
	private final int width;

	// number of (32x32) tiles tall
	private final int height;
	
	// 1 dimensional array of tiles, in the (x, y) form of: [ (0, 0), (1, 0), (0, 1), (1, 1) ]
	private final TileType[] tileTypes;
	
	private final Boolean[] passable;
	
	public TileTypeGroup(int width, int height, TileType... tileTypes) {
		this.width = width;
		this.height = height;
		this.tileTypes = tileTypes;
		passable = new Boolean[tileTypes.length];
		
		// Default to passable of true, otherwise use the other constructor
		Arrays.fill(passable, true);
	}
	
	public TileTypeGroup(int width, int height, Object... tileTypes) {
		this.width = width;
		this.height = height;
		
		List<TileType> ttList = new ArrayList<>();
		List<Boolean> bList = new ArrayList<>();
		
		for(int x = 0; x < tileTypes.length; ) {
			TileType tt = (TileType) tileTypes[x];
			ttList.add(tt);
			Boolean b = (Boolean) tileTypes[x+1];
			bList.add(b);
			x += 2;
		}
		
		this.tileTypes = ttList.toArray(new TileType[0]);
		this.passable = bList.toArray(new Boolean[0]);
	}


	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public TileType[] getTileTypes() {
		return tileTypes;
	}
		
	
	public TileType getTypeAt0Coord(int x, int y) {
		int index = y*width+x;
		return tileTypes[index];
	}
	
	public boolean isPassableAtCoord(int x, int y) {
		int index = y*width+x;
		return passable[index];		
	}

	
	/** Create a new copy of this TileTypeGroup, but in the result replace the given tileType 
	 * with the new given type */
	public TileTypeGroup cloneAndReplace(TileType oldType, TileType newType) {
		TileType[] newArray = new TileType[this.tileTypes.length];
		
		for(int x = 0; x < newArray.length; x++) {
			newArray[x] = this.tileTypes[x];
			
			if(newArray[x].equals(oldType)) {
				newArray[x] = newType;
			}
		}
		
		return new TileTypeGroup(this.width, this.height, newArray);
	}
	
}