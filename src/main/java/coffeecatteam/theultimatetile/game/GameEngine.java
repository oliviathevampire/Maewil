package coffeecatteam.theultimatetile.game;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.game.state.State;
import coffeecatteam.theultimatetile.game.state.StateCredits;
import coffeecatteam.theultimatetile.game.state.StateMenu;
import coffeecatteam.theultimatetile.game.state.StateOptions;
import coffeecatteam.theultimatetile.game.state.game.StateSelectGame;
import coffeecatteam.theultimatetile.game.state.options.OptionsSounds;
import coffeecatteam.theultimatetile.game.state.options.controls.OptionsControls;
import coffeecatteam.theultimatetile.game.world.World;
import coffeecatteam.theultimatetile.gfx.Camera;
import coffeecatteam.theultimatetile.manager.EntityManager;
import coffeecatteam.theultimatetile.manager.InventoryManager;
import coffeecatteam.theultimatetile.manager.ItemManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class GameEngine extends Engine {

    private static GameEngine gameEngine;

    // States
    public StateMenu stateMenu;
    public StateSelectGame stateSelectGame;
    public StateCredits stateCredits;

    // Option states
    public OptionsControls optionsControls;
    public OptionsSounds optionsSpounds;

    private EntityManager entityManager;
    private ItemManager itemManager;
    private InventoryManager inventoryManager;

    private Camera camera;
    private World world;

    public GameEngine(String[] args, String title, int width, int height) {
        super(args, title, width, height);
        gameEngine = this;
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        super.init(container);
        camera = new Camera(this, 0, 0);

        stateMenu = new StateMenu(this);
        stateSelectGame = new StateSelectGame(this);
        stateCredits = new StateCredits(this);

        optionsControls = new OptionsControls(this);
        optionsSpounds = new OptionsSounds(this);
        State.setState(stateMenu);

        entityManager = new EntityManager(this, new EntityPlayer(this, ""));

        itemManager = new ItemManager(this);

        inventoryManager = new InventoryManager(this);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
//        TagBase.TEST();
        super.update(container, delta);

        if (State.getState() != null)
            State.getState().update(container, delta);
    }

    @Override
    public void rendera(GameContainer container, Graphics g) throws SlickException {
        if (State.getState() != null)
            State.getState().render(g);
        if (StateOptions.OPTIONS.fpsCounter())
            renderFPSCounter(g);
    }

    public Camera getCamera() {
        return camera;
    }

    public String getUsername() {
        return this.entityManager.getPlayer().getUsername();
    }

    public void setUsername(String username) {
        this.entityManager.getPlayer().setUsername(username);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public EntityPlayer getPlayer() {
        return entityManager.getPlayer();
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    /*
     * Gets an instance of the game.
     */
    public static GameEngine getGameEngine() {
        return gameEngine;
    }
}
