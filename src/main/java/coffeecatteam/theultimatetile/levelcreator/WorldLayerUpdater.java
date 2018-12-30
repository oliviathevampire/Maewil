package coffeecatteam.theultimatetile.levelcreator;

import coffeecatteam.theultimatetile.levelcreator.grid.GridWorldEditor;

/**
 * @author CoffeeCatRailway
 * Created: 27/12/2018
 */
public class WorldLayerUpdater implements Runnable {

    private CreatorEngine creatorEngine;
    private GridWorldEditor gridWorldEditor;
    private int xWorldSize, yWorldSize;

    public WorldLayerUpdater(CreatorEngine creatorEngine, GridWorldEditor gridWorldEditor) {
        this.creatorEngine = creatorEngine;
        this.gridWorldEditor = gridWorldEditor;
        this.xWorldSize = gridWorldEditor.getxWorldSize();
        this.yWorldSize = gridWorldEditor.getyWorldSize();
    }

    @Override
    public void run() {
        int fps = 60;
        double timePerTick = 1000000000d / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();

        while (creatorEngine.isRunning()) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            lastTime = now;

            if (delta >= 1) {
                for (int x = 0; x < xWorldSize; x++) {
                    for (int y = 0; y < yWorldSize; y++) {
                        gridWorldEditor.getGrid()[x][y].setWorldLayer(gridWorldEditor.getGrid(), xWorldSize, yWorldSize);
                    }
                }

                delta--;
            }
        }
    }

    public void setxWorldSize(int xWorldSize) {
        this.xWorldSize = xWorldSize;
    }

    public void setyWorldSize(int yWorldSize) {
        this.yWorldSize = yWorldSize;
    }
}
