package com.github.programmerr47.singleinstancessample;

import android.content.Context;

/**
 * @author Michael Spitsin
 * @since 2016-08-09
 */
public class CustomSomething {
    private final Context appContext;
    private final int someState;

    public CustomSomething(Context appContext, int someState) {
        this.appContext = appContext;
        this.someState = someState;
    }

    public Context getAppContext() {
        return appContext;
    }

    public int getSomeState() {
        return someState;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " { someState: " + someState + ", appContext: " + appContext + " }";
    }
}
