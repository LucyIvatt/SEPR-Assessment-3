package com.dicycat.kroy.gamemap;


import java.util.HashMap;

/**
* @author Martha Cartwright
*/
public enum TileType {
	

	NONCOLLIDABLE(422,false),
	COLLIDABLE(421,true);

	
	private int id;
	private boolean collidable;
	
	public static final int TILE_SIZE = 16;

	TileType(int id, boolean collidable) {
		this.id = id;
		this.collidable = collidable;
	}

	public int getId() {
		return id;
	}

	public boolean isCollidable() {
		return collidable;
	}

	
	/**
	 * HashMap stores all the ID's of the TileType is the key and the value is the TileType itself.
	 * This allows for the TileType to be retreived by the ID in getTileTypeByID
	 */
	private static HashMap<Integer, TileType> tileMap;
	
	static {
		tileMap = new HashMap<Integer, TileType>();
		for (TileType tileType: TileType.values()) {
			tileMap.put(tileType.getId(), tileType);
		}
	}
	
	/**
	 * @param id ID of TileType
	 * @return TileType
	 */
	public static TileType getTileTypeByID(int id) {
		return tileMap.get(id);
	}
}
