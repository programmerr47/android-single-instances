package com.github.programmerr47.singleinstancessample;

import android.content.Context;
import android.os.Handler;

import com.github.programmerr47.singleinstances.InstanceStorage;
import com.github.programmerr47.singleinstances.LazyInstance;
import com.github.programmerr47.singleinstances.LazySingletonApplication;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

import static com.github.programmerr47.singleinstances.InstanceStorage.HierarchyLink.hierarchy;

/**
 * @author Michael Spitsin
 * @since 2016-08-09
 */
public class SampleApplication extends LazySingletonApplication {

    private static InstanceStorage storage;

    @Override
    public void onCreate() {
        super.onCreate();

        storage = new InstanceStorage(
                hierarchy(getApplicationContext(), Context.class),
                new Handler(),
                new CustomSomething(getApplicationContext(), 5),
                Picasso.with(getApplicationContext()));
    }

    @Override
    protected List<? extends LazyInstance> initAllLazyInstances() {
        return Arrays.asList(
                new AppContextLazyInstance(Context.class),
                new UiHandlerLazyInstance(Handler.class),
                new CustomSomethingLazyInstance(CustomSomething.class),
                new PicassoLazyInstance(Picasso.class));
    }

    public static <T> T getNotLazyGlobal(Class<T> clazz) {
        return storage.get(clazz);
    }

    private final class AppContextLazyInstance extends LazyInstance<Context> {
        public AppContextLazyInstance(Class<Context> clazz) {
            super(clazz);
        }

        @Override
        public Context init() {
            return getApplicationContext();
        }
    }

    private final class UiHandlerLazyInstance extends LazyInstance<Handler> {
        public UiHandlerLazyInstance(Class<Handler> clazz) {
            super(clazz);
        }

        @Override
        public Handler init() {
            return new Handler();
        }
    }

    private final class CustomSomethingLazyInstance extends LazyInstance<CustomSomething> {
        public CustomSomethingLazyInstance(Class<CustomSomething> clazz) {
            super(clazz);
        }

        @Override
        public CustomSomething init() {
            return new CustomSomething(getApplicationContext(), 10);
        }
    }

    private final class PicassoLazyInstance extends LazyInstance<Picasso> {
        public PicassoLazyInstance(Class<Picasso> clazz) {
            super(clazz);
        }

        @Override
        public Picasso init() {
            return Picasso.with(getApplicationContext());
        }
    }
}
