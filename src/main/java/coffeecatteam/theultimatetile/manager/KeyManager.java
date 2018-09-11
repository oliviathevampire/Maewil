package coffeecatteam.theultimatetile.manager;

import coffeecatteam.theultimatetile.manager.iinterface.ITickableManager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener, ITickableManager {

    private boolean[] keys, justPressed, cantPress;
    public boolean up, down, left, right, sprint;
    public boolean attack, inventory, pause;

    public KeyManager() {
        keys = new boolean[256];
        justPressed = new boolean[keys.length];
        cantPress = new boolean[keys.length];
    }

    @Override
    public void tick() {
        for (int i = 0; i < keys.length; i++) {
            if (cantPress[i] && !keys[i])
                cantPress[i] = false;
            else if (justPressed[i]) {
                cantPress[i] = true;
                justPressed[i] = false;
            }
            if (!cantPress[i] && keys[i])
                justPressed[i] = true;
        }

        up = keys[KeybindsManager.W];
        down = keys[KeybindsManager.S];
        left = keys[KeybindsManager.A];
        right = keys[KeybindsManager.D];
        sprint = keys[KeybindsManager.CONTROL];

        attack = keys[KeybindsManager.SPACE];
        inventory = keys[KeybindsManager.E];
        pause = keys[KeybindsManager.ESCAPE];
    }

    public boolean keyJustPressed(int keyCode) {
        if (keyCode < 0 || keyCode >= keys.length)
            return false;
        return justPressed[keyCode];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() < 0 || e.getKeyCode() >= keys.length)
            return;
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() < 0 || e.getKeyCode() >= keys.length)
            return;
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
