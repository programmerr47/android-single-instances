package com.github.programmerr47.singleinstances;

import com.github.programmerr47.singleinstances.DummyTestClassesContainer.SomeChild;
import com.github.programmerr47.singleinstances.DummyTestClassesContainer.SomeParent;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael Spitsin
 * @since 2016-08-15
 */
public class LazyInstanceTest {

    @Test
    public void smokeCheckSavingClass() {
        LazyInstance<SomeParent> lazyInstance = new TestableLazyInstance(SomeParent.class);
        Assert.assertEquals(lazyInstance.getTargetClass(), SomeParent.class);
    }

    private static final class TestableLazyInstance extends LazyInstance<SomeParent> {
        public TestableLazyInstance(Class<SomeParent> clazz) {
            super(clazz);
        }

        @Override
        public SomeParent init() {
            return new SomeChild();
        }
    }
}
