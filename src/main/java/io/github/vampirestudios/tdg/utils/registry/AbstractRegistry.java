package io.github.vampirestudios.tdg.utils.registry;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import io.github.vampirestudios.tdg.utils.Identifier;

import java.util.Map;
import java.util.Set;

public class AbstractRegistry<T, U> implements IRegistry<T, U> {

    protected final Identifier name;
    protected final boolean canUnregister;
    protected final BiMap<T, U> map = HashBiMap.create();
    protected final BiMap<T, U> unmodifiableMap;

    public AbstractRegistry(Identifier name, boolean canUnregister) {
        this.name = name;
        this.canUnregister = canUnregister;
        this.unmodifiableMap = Maps.unmodifiableBiMap(this.map);
    }


    @Override
    public void register(T key, U value) {
        Preconditions.checkArgument(key != null, "Tried registering " + value + " with name " + key + " which is invalid into registry " + this);
        Preconditions.checkArgument(!this.map.containsKey(key), "Cannot register " + value + " with name " + key + " twice into registry " + this);

        this.map.put(key, value);
//        RockBottomAPI.logger().info("Registered " + value + " with name " + key + " into registry " + this);
    }

    @Override
    public U get(T key) {
        if (key == null) {
//            RockBottomAPI.logger().warn("Tried getting value of " + key + " for registry " + this + " which is invalid");
            return null;
        } else {
            return this.map.get(key);
        }
    }

    @Override
    public T getId(U value) {
        return this.map.inverse().get(value);
    }

    @Override
    public int getSize() {
        return this.map.size();
    }

    @Override
    public void unregister(T key) {
        if (this.canUnregister) {
            this.map.remove(key);
//            RockBottomAPI.logger().info("Unregistered " + key + " from registry " + this);
        } else {
            throw new UnsupportedOperationException("Unregistering from registry " + this + " is disallowed");
        }
    }

    @Override
    public BiMap<T, U> getUnmodifiable() {
        return this.unmodifiableMap;
    }

    @Override
    public Set<T> keySet() {
        return this.unmodifiableMap.keySet();
    }

    @Override
    public Set<U> values() {
        return this.unmodifiableMap.values();
    }

    @Override
    public Set<Map.Entry<T, U>> entrySet() {
        return this.unmodifiableMap.entrySet();
    }

    @Override
    public Identifier getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name.toString();
    }
}