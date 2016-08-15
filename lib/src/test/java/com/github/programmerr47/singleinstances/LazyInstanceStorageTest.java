package com.github.programmerr47.singleinstances;

import com.github.programmerr47.singleinstances.DummyTestClassesContainer.SomeChild;
import com.github.programmerr47.singleinstances.DummyTestClassesContainer.SomeClass;
import com.github.programmerr47.singleinstances.DummyTestClassesContainer.SomeParent;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static com.github.programmerr47.singleinstances.Utils.getLazyInitializers;
import static com.github.programmerr47.singleinstances.Utils.getLazyStorageMap;

/**
 * @author Michael Spitsin
 * @since 2016-08-15
 */
public class LazyInstanceStorageTest {
    @Test
    public void checkNoObjects() {
        LazyInstanceStorage instanceStorage = new LazyInstanceStorage();
        Map<Class, Object> innerStorageMap = getLazyStorageMap(instanceStorage);
        Map<Class, LazyInstance> innerInitializers = getLazyInitializers(instanceStorage);
        Assert.assertTrue(innerStorageMap.isEmpty());
        Assert.assertTrue(innerInitializers.isEmpty());
    }

    @Test
    public void checkEmptyListOfObjects() {
        LazyInstanceStorage instanceStorage = new LazyInstanceStorage(new ArrayList<LazyInstance>());
        Map<Class, Object> innerStorageMap = getLazyStorageMap(instanceStorage);
        Map<Class, LazyInstance> innerInitializers = getLazyInitializers(instanceStorage);
        Assert.assertTrue(innerStorageMap.isEmpty());
        Assert.assertTrue(innerInitializers.isEmpty());
    }

    @Test
    public void checkLazyPlacing() {
        SomeLazyInstance someLazyInstance = new SomeLazyInstance(SomeClass.class);
        SomeParentLazyInstance someParentLazyInstance = new SomeParentLazyInstance(SomeParent.class);
        LazyInstanceStorage instanceStorage = new LazyInstanceStorage(someLazyInstance, someParentLazyInstance);
        Map<Class, Object> innerStorageMap = getLazyStorageMap(instanceStorage);
        Map<Class, LazyInstance> innerInitializers = getLazyInitializers(instanceStorage);
        Assert.assertEquals(innerStorageMap.size(), 0);
        Assert.assertEquals(innerInitializers.size(), 2);
        Assert.assertNotNull(instanceStorage.get(SomeParent.class));
        Assert.assertNotNull(instanceStorage.get(SomeClass.class));
        Assert.assertEquals(innerStorageMap.size(), 2);
    }

    @Test
    public void checkSameLazyPlacing() {
        SomeParentLazyInstance someParentLazyInstance = new SomeParentLazyInstance(SomeParent.class);
        SomeParentChildLazyInstance someParentChildLazyInstance = new SomeParentChildLazyInstance(SomeParent.class);
        LazyInstanceStorage instanceStorage = new LazyInstanceStorage(someParentLazyInstance, someParentChildLazyInstance);
        Map<Class, Object> innerStorageMap = getLazyStorageMap(instanceStorage);
        Map<Class, LazyInstance> innerInitializers = getLazyInitializers(instanceStorage);
        Assert.assertEquals(innerStorageMap.size(), 0);
        Assert.assertEquals(innerInitializers.size(), 1);
        Assert.assertNotNull(instanceStorage.get(SomeParent.class));
        Assert.assertEquals(innerStorageMap.size(), 1);
        Assert.assertEquals(innerStorageMap.get(SomeParent.class).getClass(), SomeChild.class);
    }

    private static final class SomeLazyInstance extends LazyInstance<SomeClass> {
        public SomeLazyInstance(Class<SomeClass> clazz) {
            super(clazz);
        }

        @Override
        public SomeClass init() {
            return new SomeClass();
        }
    }

    private static final class SomeParentLazyInstance extends LazyInstance<SomeParent> {
        public SomeParentLazyInstance(Class<SomeParent> clazz) {
            super(clazz);
        }

        @Override
        public SomeParent init() {
            return new SomeParent();
        }
    }

    private static final class SomeParentChildLazyInstance extends LazyInstance<SomeParent> {
        public SomeParentChildLazyInstance(Class<SomeParent> clazz) {
            super(clazz);
        }

        @Override
        public SomeParent init() {
            return new SomeChild();
        }
    }
}
