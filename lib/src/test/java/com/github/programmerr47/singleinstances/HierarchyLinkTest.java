package com.github.programmerr47.singleinstances;

import com.github.programmerr47.singleinstances.DummyTestClassesContainer.AwesomeInterface;
import com.github.programmerr47.singleinstances.DummyTestClassesContainer.OrdinaryClass;
import com.github.programmerr47.singleinstances.DummyTestClassesContainer.SomeChild;
import com.github.programmerr47.singleinstances.DummyTestClassesContainer.SomeClass;
import com.github.programmerr47.singleinstances.DummyTestClassesContainer.SomeGrandParent;
import com.github.programmerr47.singleinstances.DummyTestClassesContainer.SomeParent;
import com.github.programmerr47.singleinstances.InstanceStorage.HierarchyLink;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael Spitsin
 * @since 2016-08-10
 */
public class HierarchyLinkTest {

    @Test
    public void checkObjectAndItsClass() {
        SomeClass obj = new SomeClass();
        HierarchyLink link = new HierarchyLink(obj, SomeClass.class);
        Assert.assertEquals(obj, link.instance);
        Assert.assertEquals(SomeClass.class, link.linkedClass);
    }

    @Test
    public void checkObjectAndItsParentClass() {
        SomeGrandParent parent = new SomeChild();
        HierarchyLink link = new HierarchyLink(parent, SomeParent.class);
        Assert.assertEquals(parent, link.instance);
        Assert.assertEquals(SomeParent.class, link.linkedClass);
    }

    @Test
    public void checkObjectAndItsInterface() {
        OrdinaryClass obj = new OrdinaryClass();
        HierarchyLink link = new HierarchyLink(obj, AwesomeInterface.class);
        Assert.assertEquals(obj, link.instance);
        Assert.assertEquals(AwesomeInterface.class, link.linkedClass);
    }

    @Test
    public void checkStaticCreator() {
        SomeClass obj = new SomeClass();
        HierarchyLink link = new HierarchyLink(obj, SomeClass.class);
        HierarchyLink anotherLink = HierarchyLink.hierarchy(obj, SomeClass.class);
        Assert.assertEquals(link.instance, anotherLink.instance);
        Assert.assertEquals(link.linkedClass, anotherLink.linkedClass);
    }

    @Test(expected = IllegalArgumentException.class)
    public void errorOnUnrelatedClassAndObject() {
        AwesomeInterface obj = new OrdinaryClass();
        new HierarchyLink(obj, SomeClass.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void errorOnParentObjectAndChildClass() {
        SomeGrandParent obj = new SomeParent();
        new HierarchyLink(obj, SomeChild.class);
    }
}
