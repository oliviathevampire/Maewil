package coffeecatteam.theultimatetile.tiles;

import coffeecatteam.theultimatetile.GameEngine;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.inventory.items.Item;
import coffeecatteam.theultimatetile.inventory.items.ItemStack;
import coffeecatteam.utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TileBreakable extends Tile implements IDamageableTile {

    private Item drop;
    private int health, maxHealth = 300;
    private boolean isMineable = true;

    public TileBreakable(GameEngine gameEngine, BufferedImage texture, String id, Item drop, TileType tileType) {
        super(gameEngine, texture, id, true, tileType);
        this.drop = drop;
        this.health = this.maxHealth;
    }

    @Override
    public void render(Graphics g) {
        super.render(g);

        if (isMineable) {
            int index = (int) Utils.map(this.health, 0, this.maxHealth, 0, Assets.TILE_CRACKING.length - 1);
            if (index < 0)
                index = 0;
            BufferedImage currentFrame = Assets.TILE_CRACKING[index];
            g.drawImage(currentFrame, (int) (x * Tile.TILE_WIDTH - gameEngine.getCamera().getxOffset()), (int) (y * Tile.TILE_HEIGHT - gameEngine.getCamera().getyOffset()), TILE_WIDTH, TILE_HEIGHT, null);
        }
    }

    @Override
    public void damage(int damage) {
        if (isMineable) {
            this.health -= damage;
            if (this.health <= 0) {
                if (x == 0 || x == gameEngine.getWorld().getWidth() || y == 0 || y == gameEngine.getWorld().getHeight())
                    return;
                gameEngine.getWorld().setFGTile(x, y, Tiles.AIR);
                gameEngine.getItemManager().addItem(new ItemStack(drop), x * Tile.TILE_WIDTH, y * Tile.TILE_HEIGHT);
                this.health = this.maxHealth;
            }
        }
    }

    public boolean isMineable() {
        return isMineable;
    }

    public void setMineable(boolean mineable) {
        isMineable = mineable;
    }
}
