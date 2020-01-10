package io.github.vampirestudios.tdg.utils.registry;

import io.github.vampirestudios.tdg.utils.Identifier;

public class NameRegistry<U> extends AbstractRegistry<Identifier, U> {

    public NameRegistry(Identifier name, boolean canUnregister) {
        super(name, canUnregister);
    }
}