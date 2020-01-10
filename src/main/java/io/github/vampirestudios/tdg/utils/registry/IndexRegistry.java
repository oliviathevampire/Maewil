package io.github.vampirestudios.tdg.utils.registry;

import com.google.common.base.Preconditions;
import io.github.vampirestudios.tdg.utils.Identifier;

public class IndexRegistry<U> extends AbstractRegistry<Integer, U> {

    protected final int max;

    public IndexRegistry(Identifier name, int max, boolean canUnregister) {
        super(name, canUnregister);
        this.max = max;
    }

    @Override
    public void register(Integer key, U value) {
        Preconditions.checkArgument(key >= 0 && key <= this.max, "Tried registering " + value + " with id " + key + " which is less than 0 or greater than max " + this.max + " in registry " + this);
        super.register(key, value);
    }

    public void registerNextFree(U value) {
        this.register(this.getNextFreeId(), value);
    }

    @Override
    public Integer getId(U value) {
        Integer id = super.getId(value);
        return id == null ? -1 : id;
    }

    public int getNextFreeId() {
        for (int i = 0; i <= this.max; i++) {
            if (!this.map.containsKey(i)) {
                return i;
            }
        }
        return -1;
    }
}