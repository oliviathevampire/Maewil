package io.github.vampirestudios.tdg.utils.registry;

import com.google.common.collect.BiMap;
import io.github.vampirestudios.tdg.utils.Identifier;

import java.util.Map;
import java.util.Set;

public interface IRegistry<T, U> {

    void register(T id, U value);

    U get(T id);

    T getId(U value);

    int getSize();

    void unregister(T id);

    BiMap<T, U> getUnmodifiable();

    Set<T> keySet();

    Set<U> values();

    Set<Map.Entry<T, U>> entrySet();

    Identifier getName();

    default <V extends IRegistry> V register() {
        Registries.REGISTRIES.register(this.getName(), this);
        return (V) this;
    }
}