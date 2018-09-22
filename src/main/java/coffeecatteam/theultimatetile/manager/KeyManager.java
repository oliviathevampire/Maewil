package coffeecatteam.theultimatetile.manager;

import coffeecatteam.theultimatetile.manager.iinterface.ITickableManager;
import coffeecatteam.theultimatetile.state.StateOptions;
import coffeecatteam.theultimatetile.state.options.Keybind;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener, ITickableManager {

    private boolean[] keys, justPressed, cantPress;
    public boolean moveUp, moveDown, moveLeft, moveRight, useSprint, useAttack;

    private int currentKeyPressedCode;
    private char currentKeyPressedChar = '~';

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

        moveUp = keys[StateOptions.OPTIONS.controls().get(Keybind.W).getKeyCode()];
        moveDown = keys[StateOptions.OPTIONS.controls().get(Keybind.S).getKeyCode()];
        moveLeft = keys[StateOptions.OPTIONS.controls().get(Keybind.A).getKeyCode()];
        moveRight = keys[StateOptions.OPTIONS.controls().get(Keybind.D).getKeyCode()];
        useSprint = keys[StateOptions.OPTIONS.controls().get(Keybind.CONTROL).getKeyCode()];
        useAttack = keys[StateOptions.OPTIONS.controls().get(Keybind.SPACE).getKeyCode()];
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

        if (e.getKeyCode() != KeyEvent.VK_ENTER) {
            currentKeyPressedCode = e.getKeyCode();
            currentKeyPressedChar = e.getKeyChar();
        }
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

    public int getCurrentKeyPressedCode() {
        return currentKeyPressedCode;
    }

    public void setCurrentKeyPressedCode(int currentKeyPressedCode) {
        this.currentKeyPressedCode = currentKeyPressedCode;
    }

    public char getCurrentKeyPressedChar() {
        return currentKeyPressedChar;
    }

    public void setCurrentKeyPressedChar(char currentKeyPressedChar) {
        this.currentKeyPressedChar = currentKeyPressedChar;
    }
}
