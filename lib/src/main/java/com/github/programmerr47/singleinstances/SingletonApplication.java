package com.github.programmerr47.singleinstances;

import android.app.Application;

import java.util.List;

/**
 * @author Michael Spitsin
 * @since 2016-07-29
 */
public abstract class SingletonApplication extends Application {
    private static InstanceStorage storage;

    @Override
    public void onCreate() {
        super.onCreate();
        List<Object> singleInstances = initAllSingleInstances();
        storage = new InstanceStorage(singleInstances);
    }

    protected abstract List<Object> initAllSingleInstances();

    public static <T> T getGlobal(Class<T> clazz) {
        return storage.get(clazz);
    }
}
