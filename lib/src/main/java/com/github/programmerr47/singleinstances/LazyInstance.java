package com.github.programmerr47.singleinstances;

/**
 * @author Michael Spitsin
 * @since 2016-07-29
 */
public abstract class LazyInstance<T> {
    private final Class<T> clazz;

    public LazyInstance(Class<T> clazz) {
        this.clazz = clazz;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public abstract T init();
}
