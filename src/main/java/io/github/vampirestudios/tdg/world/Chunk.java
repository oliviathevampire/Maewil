package io.github.vampirestudios.tdg.world;

import io.github.vampirestudios.tdg.objs.tiles.Tile;

import java.util.HashMap;
import java.util.Map;

public class Chunk {

    public static final Tile[][] tiles = new Tile[32][32];
    Map<Integer , Map<Integer,Chunk>> chunks = new HashMap<>();

    public void getChunk(int x, int y){
        Chunk chunk = chunks.get(4).get(-2);
    }

}
