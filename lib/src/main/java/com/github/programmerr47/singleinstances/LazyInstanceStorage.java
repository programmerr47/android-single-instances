package com.github.programmerr47.singleinstances;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael Spitsin
 * @since 2016-07-29
 */
public class LazyInstanceStorage {
    private final Map<Class, Object> singleInstances = new HashMap<>();
    private final Map<Class, LazyInstance> initializers = new HashMap<>();

    public LazyInstanceStorage(LazyInstance... instances) {
        this(Arrays.asList(instances));
    }

    public LazyInstanceStorage(Collection<LazyInstance> instances) {
        for (LazyInstance lazyInstance : instances) {
            initializers.put(lazyInstance.getClazz(), lazyInstance);
        }
    }

    public <T> T get(Class<T> clazz) {
        if (!singleInstances.containsKey(clazz)) {
            singleInstances.put(clazz, initializers.get(clazz).init());
        }
        return clazz.cast(singleInstances.get(clazz));
    }
}
