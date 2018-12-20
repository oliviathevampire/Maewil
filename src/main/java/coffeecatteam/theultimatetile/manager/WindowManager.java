package coffeecatteam.theultimatetile.manager;

import coffeecatteam.coffeecatutils.logger.CatLogger;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.state.State;
import coffeecatteam.theultimatetile.game.state.game.StateGame;
import coffeecatteam.theultimatetile.levelcreator.CreatorEngine;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class WindowManager implements WindowListener {

    private CatLogger logger;

    public WindowManager(Engine engine) {
        engine.getFrame().addWindowListener(this);
        logger = engine.getLogger();
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        if (GameEngine.getGameEngine() != null) {
            logger.print("Shutting down game engine!");
            GameEngine.getGameEngine().setRunning(false);
            if (State.getState() instanceof StateGame) {
                logger.print("Saving world!");
                ((StateGame) State.getState()).saveWorld();
                logger.print("World saved!");
            }
        }
        if (CreatorEngine.getCreatorEngine() != null) {
            logger.print("Shutting down creator engine!");
            CreatorEngine.getCreatorEngine().setRunning(false);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}