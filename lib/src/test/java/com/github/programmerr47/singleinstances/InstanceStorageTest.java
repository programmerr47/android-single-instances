package com.github.programmerr47.singleinstances;

import com.github.programmerr47.singleinstances.DummyTestClassesContainer.SomeChild;
import com.github.programmerr47.singleinstances.DummyTestClassesContainer.SomeClass;
import com.github.programmerr47.singleinstances.DummyTestClassesContainer.SomeGrandParent;
import com.github.programmerr47.singleinstances.DummyTestClassesContainer.SomeParent;
import com.github.programmerr47.singleinstances.InstanceStorage.HierarchyLink;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static com.github.programmerr47.singleinstances.InstanceStorage.HierarchyLink.hierarchy;
import static com.github.programmerr47.singleinstances.Utils.getStorageMap;

/**
 * @author Michael Spitsin
 * @since 2016-08-10
 */
public class InstanceStorageTest {

    @Test
    public void checkNoObjects() {
        InstanceStorage instanceStorage = new InstanceStorage();
        Map<Class, Object> innerStorageMap = getStorageMap(instanceStorage);
        Assert.assertTrue(innerStorageMap.isEmpty());
    }

    @Test
    public void checkEmptyListOfObjects() {
        InstanceStorage instanceStorage = new InstanceStorage(new ArrayList<>());
        Map<Class, Object> innerStorageMap = getStorageMap(instanceStorage);
        Assert.assertTrue(innerStorageMap.isEmpty());
    }

    @Test
    public void checkOrdinaryObjectPlacing() {
        SomeClass one = new SomeClass();
        SomeChild two = new SomeChild();
        InstanceStorage instanceStorage = new InstanceStorage(one, two);
        Map<Class, Object> innerStorageMap = getStorageMap(instanceStorage);
        Assert.assertEquals(innerStorageMap.size(), 2);
        Assert.assertNotNull(instanceStorage.get(SomeChild.class));
        Assert.assertNotNull(instanceStorage.get(SomeClass.class));
    }

    @Test
    public void checkPolymorphicObjectPlacing() {
        SomeGrandParent one = new SomeGrandParent();
        SomeGrandParent two = new SomeParent();
        SomeGrandParent three = new SomeChild();
        InstanceStorage instanceStorage = new InstanceStorage(one, two, three);
        Map<Class, Object> innerStorageMap = getStorageMap(instanceStorage);
        Assert.assertEquals(innerStorageMap.size(), 3);
        Assert.assertNotNull(instanceStorage.get(SomeChild.class));
        Assert.assertNotNull(instanceStorage.get(SomeParent.class));
        Assert.assertNotNull(instanceStorage.get(SomeGrandParent.class));
        Assert.assertEquals(one, instanceStorage.get(SomeGrandParent.class));
    }

    @Test
    public void checkOverridingForSameObjects() {
        SomeGrandParent one = new SomeParent();
        SomeGrandParent two = new SomeChild();
        SomeGrandParent three = new SomeParent();
        InstanceStorage instanceStorage = new InstanceStorage(one, two, three);
        Map<Class, Object> innerStorageMap = getStorageMap(instanceStorage);
        Assert.assertEquals(innerStorageMap.size(), 2);
        Assert.assertNotNull(instanceStorage.get(SomeChild.class));
        Assert.assertNotNull(instanceStorage.get(SomeParent.class));
        Assert.assertEquals(three, instanceStorage.get(SomeParent.class));
        Assert.assertNotEquals(one, instanceStorage.get(SomeParent.class));
    }

    @Test
    public void checkWorkingWithConcreteClasses() {
        SomeGrandParent one = new SomeChild();
        InstanceStorage instanceStorage = new InstanceStorage(one);
        Assert.assertNull(instanceStorage.get(SomeGrandParent.class));
        Assert.assertNotNull(instanceStorage.get(SomeChild.class));
    }

    @Test
    public void checkHierarchyLinkParsing() {
        SomeGrandParent one = new SomeChild();
        HierarchyLink hierarchy = hierarchy(one, SomeGrandParent.class);
        InstanceStorage instanceStorage = new InstanceStorage(one, hierarchy);
        Map<Class, Object> innerStorageMap = getStorageMap(instanceStorage);
        Assert.assertEquals(innerStorageMap.size(), 2);
        Assert.assertNotNull(instanceStorage.get(SomeChild.class));
        Assert.assertNotNull(instanceStorage.get(SomeGrandParent.class));
        Assert.assertNull(innerStorageMap.get(HierarchyLink.class));
        Assert.assertEquals(one, instanceStorage.get(SomeGrandParent.class));
    }
}
