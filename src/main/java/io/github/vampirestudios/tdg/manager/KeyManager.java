package io.github.vampirestudios.tdg.manager;

import com.badlogic.gdx.Gdx;
import io.github.vampirestudios.tdg.screen.options.OptionsScreen;
import io.github.vampirestudios.tdg.screen.options.controls.Keybind;
import org.mini2Dx.core.game.GameContainer;

import java.awt.event.KeyEvent;

public class KeyManager {

    private boolean[] keys, justPressed, cantPress;
    public boolean moveUp, moveDown, moveLeft, moveRight, useSprint, useAttack, screenshot, fullscreen;

    private int currentKeyPressedCode;
    private char currentKeyPressedChar = '~';

    public KeyManager() {
        keys = new boolean[256];
        justPressed = new boolean[keys.length];
        cantPress = new boolean[keys.length];
    }

    public void update(GameContainer container, float delta) {
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

        moveUp = Gdx.input.isKeyPressed(OptionsScreen.OPTIONS.controls().get(Keybind.W).getKeyCode());
        moveDown = Gdx.input.isKeyPressed(OptionsScreen.OPTIONS.controls().get(Keybind.S).getKeyCode());
        moveLeft = Gdx.input.isKeyPressed(OptionsScreen.OPTIONS.controls().get(Keybind.A).getKeyCode());
        moveRight = Gdx.input.isKeyPressed(OptionsScreen.OPTIONS.controls().get(Keybind.D).getKeyCode());
        useSprint = Gdx.input.isKeyPressed(OptionsScreen.OPTIONS.controls().get(Keybind.CONTROL).getKeyCode());
        useAttack = Gdx.input.isKeyPressed(OptionsScreen.OPTIONS.controls().get(Keybind.SPACE).getKeyCode());
        screenshot = Gdx.input.isKeyPressed(OptionsScreen.OPTIONS.controls().get(Keybind.F2).getKeyCode());
        fullscreen = Gdx.input.isKeyPressed(OptionsScreen.OPTIONS.controls().get(Keybind.F11).getKeyCode());
    }

    public boolean keyJustPressed(int key) {
        if (key < 0 || key >= keys.length)
            return false;
        return justPressed[key];
    }

    public void keyPressed(int key, char c) {
        if (key < 0 || key >= keys.length)
            return;
        keys[key] = true;

        if (key != KeyEvent.VK_ENTER) {
            currentKeyPressedCode = key;
            currentKeyPressedChar = c;
        }
    }

    public void keyReleased(int key, char c) {
        if (key < 0 || key >= keys.length)
            return;
        keys[key] = false;
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
