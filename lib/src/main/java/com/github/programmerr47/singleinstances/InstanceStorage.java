package com.github.programmerr47.singleinstances;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michael Spitsin
 * @since 2016-07-29
 */
public class InstanceStorage {
    private final Map<Class, Object> container = new HashMap<>();

    public InstanceStorage(Object... instances) {
        this(Arrays.asList(instances));
    }

    public InstanceStorage(List<Object> instances) {
        for (Object instance : instances) {
            container.put(instance.getClass(), instance);
        }
    }

    public <T> T get(Class<T> clazz) {
        return clazz.cast(container.get(clazz));
    }
}
