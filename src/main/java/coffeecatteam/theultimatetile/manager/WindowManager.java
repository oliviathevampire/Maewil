package coffeecatteam.theultimatetile.manager;

import coffeecatteam.coffeecatutils.Logger;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.state.State;
import coffeecatteam.theultimatetile.game.state.game.StateGame;
import coffeecatteam.theultimatetile.levelcreator.CreatorEngine;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class WindowManager implements WindowListener {

    public WindowManager(Engine gameEngine) {
        gameEngine.getFrame().addWindowListener(this);
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        if (GameEngine.getGameEngine() != null) {
            Logger.print("Shutting down game engine!");
            GameEngine.getGameEngine().setRunning(false);
            if (State.getState() instanceof StateGame) {
                Logger.print("Saving world!");
                ((StateGame) State.getState()).saveWorld();
                Logger.print("World saved!");
            }
        }
        if (CreatorEngine.getCreatorEngine() != null) {
            Logger.print("Shutting down creator engine!");
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