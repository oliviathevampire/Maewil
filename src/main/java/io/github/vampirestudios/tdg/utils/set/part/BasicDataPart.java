package io.github.vampirestudios.tdg.utils.set.part;

import java.util.Objects;

public abstract class BasicDataPart<T> extends DataPart<T> {

    protected final T data;

    public BasicDataPart(T data) {
        this.data = data;
    }

    @Override
    public T get() {
        return this.data;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "@" + this.data.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        return Objects.equals(this.data, ((BasicDataPart) o).data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.data);
    }
}