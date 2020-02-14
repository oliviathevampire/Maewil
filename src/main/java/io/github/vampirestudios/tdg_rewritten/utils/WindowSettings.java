package io.github.vampirestudios.tdg_rewritten.utils;

/**
 * @author <a href="https://joezwet.me" target="_blank">Joe van der Zwet</a>
 */
public class WindowSettings {
    private String title;
    private int width;
    private int height;
    private boolean isFullscreen;

    public WindowSettings(String title, int width, int height, boolean isFullscreen) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.isFullscreen = isFullscreen;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isFullscreen() {
        return isFullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        isFullscreen = fullscreen;
    }
}