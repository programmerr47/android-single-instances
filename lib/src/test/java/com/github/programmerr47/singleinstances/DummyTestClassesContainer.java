package com.github.programmerr47.singleinstances;

/**
 * @author Michael Spitsin
 * @since 2016-08-10
 */
public class DummyTestClassesContainer {
    private DummyTestClassesContainer() {}

    static final class SomeClass {}

    static class SomeGrandParent {}

    static class SomeParent extends SomeGrandParent {}

    static final class SomeChild extends SomeParent {}

    interface AwesomeInterface {}

    static final class OrdinaryClass implements AwesomeInterface {}
}
