package io.github.vampirestudios.tdg.utils.registry;

import io.github.vampirestudios.tdg.content.IContentLoader;
import io.github.vampirestudios.tdg.objs.items.Item;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.utils.Identifier;
import io.github.vampirestudios.tdg.utils.Keybind;
import io.github.vampirestudios.tdg.utils.set.part.IPartFactory;

public final class Registries {

    public static final NameRegistry<IRegistry> REGISTRIES = new NameRegistry<>(new Identifier("registries"), false);
    public static final NameRegistry<Tile> TILES = new NameRegistry<>(new Identifier("tiles"), false).register();
    public static final NameRegistry<Item> ITEMS = new NameRegistry<>(new Identifier("items"), false).register();
    public static final NameRegistry<Keybind> KEYBIND_REGISTRY = new NameRegistry<>(new Identifier("keybind_registry"), true).register();
    public static final NameRegistry<IContentLoader> CONTENT_LOADER_REGISTRY = new NameRegistry<>(new Identifier("content_loader_registry"), false).register();
    public static final IndexRegistry<IPartFactory> PART_REGISTRY = new IndexRegistry<>(new Identifier("part_registry"), Byte.MAX_VALUE, false).register();

}
