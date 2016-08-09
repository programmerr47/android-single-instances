package com.github.programmerr47.singleinstances;

import android.app.Application;

import java.util.List;

/**
 * @author Michael Spitsin
 * @since 2016-07-29
 */
public abstract class LazySingletonApplication extends Application {
    private static LazyInstanceStorage storage;

    @Override
    public void onCreate() {
        super.onCreate();
        List<? extends LazyInstance> lazyInstances = initAllLazyInstances();
        storage = new LazyInstanceStorage(lazyInstances);
    }

    protected abstract List<? extends LazyInstance> initAllLazyInstances();

    public static <T> T getGlobal(Class<T> clazz) {
        return storage.get(clazz);
    }
}
