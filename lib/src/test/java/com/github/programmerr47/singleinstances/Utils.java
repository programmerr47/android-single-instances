package com.github.programmerr47.singleinstances;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author Michael Spitsin
 * @since 2016-08-10
 */
public class Utils {
    static Map<Class, Object> getStorageMap(InstanceStorage instanceStorage) {
        Map<Class, Object> storageMap = null;

        try {
            Class instanceStorageClass = instanceStorage.getClass();
            Field containerField = instanceStorageClass.getDeclaredField("container");
            containerField.setAccessible(true);
            storageMap = (Map<Class, Object>) containerField.get(instanceStorage);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return storageMap;
    }

    static Map<Class, Object> getLazyStorageMap(LazyInstanceStorage instanceStorage) {
        Map<Class, Object> storageMap = null;

        try {
            Class instanceStorageClass = instanceStorage.getClass();
            Field containerField = instanceStorageClass.getDeclaredField("singleInstances");
            containerField.setAccessible(true);
            storageMap = (Map<Class, Object>) containerField.get(instanceStorage);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return storageMap;
    }

    static Map<Class, LazyInstance> getLazyInitializers(LazyInstanceStorage instanceStorage) {
        Map<Class, LazyInstance> storageMap = null;

        try {
            Class instanceStorageClass = instanceStorage.getClass();
            Field containerField = instanceStorageClass.getDeclaredField("initializers");
            containerField.setAccessible(true);
            storageMap = (Map<Class, LazyInstance>) containerField.get(instanceStorage);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return storageMap;
    }
}
