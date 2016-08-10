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
            if (instance instanceof HierarchyLink) {
                HierarchyLink link = (HierarchyLink) instance;
                container.put(link.linkedClass, link.instance);
            } else {
                container.put(instance.getClass(), instance);
            }
        }
    }

    public <T> T get(Class<T> clazz) {
        return clazz.cast(container.get(clazz));
    }

    public static final class HierarchyLink {
        final Object instance;
        final Class linkedClass;

        public HierarchyLink(Object instance, Class linkedClass) {
            if (!linkedClass.isInstance(instance)) {
                throw new IllegalArgumentException("Allow to put only parent classes for that object.\n" +
                        "Obj.clazz: " + instance.getClass() + ", but linkedClass: " + linkedClass);
            }

            this.instance = instance;
            this.linkedClass = linkedClass;
        }

        public static HierarchyLink hierarchy(Object instance, Class linkedClass) {
            return new HierarchyLink(instance, linkedClass);
        }
    }
}
