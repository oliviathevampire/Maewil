package coffeecatteam.theultimatetile.game.tile.tiles;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.inventory.items.Item;
import coffeecatteam.theultimatetile.game.inventory.items.ItemStack;
import coffeecatteam.theultimatetile.game.tile.Tile;
import coffeecatteam.theultimatetile.game.tile.Tiles;
import coffeecatteam.theultimatetile.gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TileBreakable extends Tile implements IDamageableTile {

    private Item drop;
    private int health, maxHealth = 300;
    private boolean isMineable = true;

    public TileBreakable(Engine engine, BufferedImage texture, String id, Item drop, TileType tileType) {
        super(engine, texture, id, true, tileType);
        this.drop = drop;
        this.health = this.maxHealth;
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);

        if (isMineable) {
            int index = (int) NumberUtils.map(this.health, 0, this.maxHealth, 0, Assets.TILE_CRACKING.length - 1);
            if (index < 0)
                index = 0;
            BufferedImage currentFrame = Assets.TILE_CRACKING[index];
            g.drawImage(currentFrame, (int) (position.getX() * Tile.TILE_WIDTH - ((GameEngine) engine).getCamera().getxOffset()), (int) (position.getY() * Tile.TILE_HEIGHT - ((GameEngine) engine).getCamera().getyOffset()), TILE_WIDTH, TILE_HEIGHT, null);
        }
    }

    @Override
    public void damage(int damage) {
        if (isMineable) {
            this.health -= damage;
            if (this.health <= 0) {
                if (position.getX() == 0 || position.getX() == ((GameEngine) engine).getWorld().getWidth() || position.getY() == 0 || position.getY() == ((GameEngine) engine).getWorld().getHeight())
                    return;
                ((GameEngine) engine).getWorld().setFGTile((int) position.getX(), (int) position.getY(), Tiles.AIR);
                ((GameEngine) engine).getItemManager().addItem(new ItemStack(drop), (int) position.getX() * Tile.TILE_WIDTH, (int) (position.getY() * Tile.TILE_HEIGHT));
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
