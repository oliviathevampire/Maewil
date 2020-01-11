package io.github.vampirestudios.tdg.utils;

import io.github.vampirestudios.tdg.gfx.IInputHandler;
import io.github.vampirestudios.tdg.utils.registry.Registries;

public final class Keybind {

    private final Identifier name;
    private int key;

    public Keybind(Identifier name, int defKey) {
        this.name = name;
        this.key = defKey;
    }

    public static boolean isMouse(int key) {
        return /*key <= GLFW.GLFW_MOUSE_BUTTON_8*/false;
    }

    public void setBind(int key) {
        this.key = key;
    }

    public boolean isDown() {
        IInputHandler input = /*RockBottomAPI.getGame().getInput()*/null;

        if (this.isMouse()) {
            return input.isMouseDown(this.key);
        } else {
            return input.isKeyDown(this.key);
        }
    }

    public boolean isPressed() {
        IInputHandler input = /*RockBottomAPI.getGame().getInput()*/null;

        if (this.isMouse()) {
            return input.wasMousePressed(this.key);
        } else {
            return input.wasKeyPressed(this.key);
        }
    }

    public String getDisplayName() {
        return /*RockBottomAPI.getInternalHooks().getKeyOrMouseName(this.key)*/"";
    }

    public boolean isKey(int key) {
        return this.key == key;
    }

    public Identifier getName() {
        return this.name;
    }

    public int getKey() {
        return this.key;
    }

    public boolean isMouse() {
        return isMouse(this.key);
    }

    public Keybind register() {
        Registries.KEYBIND_REGISTRY.register(this.getName(), this);
        return this;
    }
}