package coffeecatteam.theultimatetile.start;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.game.GameProvider;

import java.nio.file.Path;
import java.util.List;

public class TutGameProvider implements GameProvider {
    @Override
    public String getGameId() {
        return "tut";
    }

    @Override
    public String getGameName() {
        return "The Ultimate Tile";
    }

    @Override
    public String getEntrypoint() {
        return null;
    }

    @Override
    public Path getLaunchDirectory() {
        return null;
    }

    @Override
    public boolean isObfuscated() {
        return false;
    }

    @Override
    public boolean requiresUrlClassLoader() {
        return false;
    }

    @Override
    public List<Path> getGameContextJars() {
        return null;
    }

    @Override
    public boolean locateGame(EnvType envType, ClassLoader loader) {
        return false;
    }

    @Override
    public void acceptArguments(String... arguments) {

    }

    @Override
    public void launch(ClassLoader loader) {
        
    }

}