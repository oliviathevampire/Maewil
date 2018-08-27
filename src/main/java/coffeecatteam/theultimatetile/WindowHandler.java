package coffeecatteam.theultimatetile;

import coffeecatteam.theultimatetile.net.packet.Packet01Disconnect;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class WindowHandler implements WindowListener {

    private Game game;

    public WindowHandler(Game game) {
        this.game = game;
        this.game.getFrame().addWindowListener(this);
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        Packet01Disconnect packet = new Packet01Disconnect(this.game.getEntityManager().getPlayer().getUsername());
        packet.writeData(this.game.getClient());
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
