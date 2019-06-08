package coffeecatteam.theultimatetile.start;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.game.GameProvider;
import net.fabricmc.loader.launch.common.FabricLauncherBase;
import net.fabricmc.loader.util.Arguments;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TutGameProvider implements GameProvider {

    static class VersionData {
        public String id;
        public String name;
        public String release_target;
        public int world_version;
        public int protocol_version;
        public int pack_version;
        public String build_time;
        public boolean stable;
    }

    private static final Gson GSON = new Gson();

    private EnvType envType;
    private String entrypoint;
    private Arguments arguments;
    private Path gameJar, realmsJar;
    private VersionData versionData;
    private boolean hasModLoader = false;

    @Override
    public String getGameId() {
        if (versionData != null) {
            String id = versionData.id;
            if (id == null) {
                id = versionData.name;
            }

            if (id != null) {
                return "tut-" + id.replaceAll("[^a-zA-Z0-9.]+", "-");
            }
        }

        System.out.println("Testing");

        return "tut";
    }

    @Override
    public String getGameName() {
        if (versionData != null && versionData.name != null) {
            return "The Ultimate Tile " + versionData.name;
        }

        return "The Ultimate Tile";
    }

    @Override
    public String getEntrypoint() {
        return entrypoint;
    }

    @Override
    public Path getLaunchDirectory() {
        if (arguments == null) {
            return new File(".").toPath();
        }

        return FabricLauncherBase.getLaunchDirectory(arguments).toPath();
    }

    @Override
    public boolean isObfuscated() {
        return false;
    }

    @Override
    public boolean requiresUrlClassLoader() {
        return hasModLoader;
    }

    @Override
    public List<Path> getGameContextJars() {
        List<Path> list = new ArrayList<>();
        list.add(gameJar);
        return list;
    }

    @Override
    public boolean locateGame(EnvType envType, ClassLoader loader) {
        List<String> entrypointClasses = Lists.newArrayList("coffeecatteam.theultimatetile.start.TutLauncher");

        Optional<GameProviderHelper.EntrypointResult> entrypointResult = GameProviderHelper.findFirstClass(loader, entrypointClasses);
        if (!entrypointResult.isPresent()) {
            return false;
        }

        entrypoint = entrypointResult.get().entrypointName;
        gameJar = entrypointResult.get().entrypointPath;
        hasModLoader = GameProviderHelper.getSource(loader, "ModLoader.class").isPresent();

        return true;
    }

    @Override
    public void acceptArguments(String... args) {
        this.arguments = new Arguments();
        arguments.parse(args);

        FabricLauncherBase.processArgumentMap(arguments, envType);
    }

    @Override
    public void launch(ClassLoader loader) {
        String targetClass = entrypoint;

        try {
            Class<?> c = ((ClassLoader) loader).loadClass(targetClass);
            Method m = c.getMethod("main", String[].class);
            m.invoke(null, (Object) arguments.toArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}