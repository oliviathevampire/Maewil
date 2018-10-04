package coffeecatteam.theultimatetile.manager;

import coffeecatteam.theultimatetile.TheUltimateTile;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class WindowManager implements WindowListener {

    private TheUltimateTile theUltimateTile;

    public WindowManager(TheUltimateTile theUltimateTile) {
        this.theUltimateTile = theUltimateTile;
        this.theUltimateTile.getFrame().addWindowListener(this);
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        theUltimateTile.setRunning(false);
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