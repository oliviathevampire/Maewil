package coffeecatteam.theultimatetile.manager;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
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
        if (GameEngine.getGameEngine() != null)
            GameEngine.getGameEngine().setRunning(false);
        if (CreatorEngine.getCreatorEngine() != null)
            CreatorEngine.getCreatorEngine().setRunning(false);
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