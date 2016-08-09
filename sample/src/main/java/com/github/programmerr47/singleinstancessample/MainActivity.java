package com.github.programmerr47.singleinstancessample;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.programmerr47.singleinstances.LazyInstanceStorage;
import com.github.programmerr47.singleinstances.LazySingletonApplication;
import com.github.programmerr47.singleinstances.SingletonApplication;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView uiHandlerLabel;
    private TextView appContextLabel;
    private TextView customInstanceLabel;
    private TextView libraryInstanceLabel;

    private TextView lazyUiHandlerLabel;
    private TextView lazyAppContextLabel;
    private TextView lazyCustomInstanceLabel;
    private TextView lazyLibraryInstanceLabel;

    private View actionGetUiHandler;
    private View actionGetAppContext;
    private View actionGetCustomInstance;
    private View actionGetLibraryInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View view = getWindow().getDecorView();

        uiHandlerLabel = (TextView) findViewById(R.id.uiHandlerValue);
        appContextLabel = (TextView) findViewById(R.id.appContextValue);
        customInstanceLabel = (TextView) findViewById(R.id.customInstanceValue);
        libraryInstanceLabel = (TextView) findViewById(R.id.libraryInstanceValue);

        lazyUiHandlerLabel = (TextView) findViewById(R.id.lazyUiHandlerValue);
        lazyAppContextLabel = (TextView) findViewById(R.id.lazyAppContextValue);
        lazyCustomInstanceLabel = (TextView) findViewById(R.id.lazyCustomInstanceValue);
        lazyLibraryInstanceLabel = (TextView) findViewById(R.id.lazyLibraryInstanceValue);

        actionGetUiHandler =  findViewById(R.id.actionGetUiHandler);
        actionGetAppContext =  findViewById(R.id.actionGetAppContext);
        actionGetCustomInstance =  findViewById(R.id.actionGetCustomInstance);
        actionGetLibraryInstance =  findViewById(R.id.actionGetLibraryInstance);

        uiHandlerLabel.setText(SampleApplication.getNotLazyGlobal(Handler.class).toString());
        appContextLabel.setText(SampleApplication.getNotLazyGlobal(Context.class).toString());
        customInstanceLabel.setText(SampleApplication.getNotLazyGlobal(CustomSomething.class).toString());
        libraryInstanceLabel.setText(SampleApplication.getNotLazyGlobal(Picasso.class).toString());

        updateLazySingletonLables();

        actionGetUiHandler.setOnClickListener(this);
        actionGetAppContext.setOnClickListener(this);
        actionGetCustomInstance.setOnClickListener(this);
        actionGetLibraryInstance.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Class clazz = getSingleInstanceClassByButton(view);
        SampleApplication.getGlobal(clazz);
        updateLazySingletonLables();
    }

    private Class getSingleInstanceClassByButton(View button) {
        switch (button.getId()) {
            case R.id.actionGetUiHandler:
                return Handler.class;
            case R.id.actionGetAppContext:
                return Context.class;
            case R.id.actionGetCustomInstance:
                return CustomSomething.class;
            case R.id.actionGetLibraryInstance:
                return Picasso.class;
            default:
                throw new IllegalArgumentException("there is no class for " + button + " button");
        }
    }

    private void updateLazySingletonLables() {
        Map<Class, Object> internalSingletonStorageMap = getLazyStorageInternals();
        updateLazySingletonLabel(lazyUiHandlerLabel, internalSingletonStorageMap, Handler.class);
        updateLazySingletonLabel(lazyAppContextLabel, internalSingletonStorageMap, Context.class);
        updateLazySingletonLabel(lazyCustomInstanceLabel, internalSingletonStorageMap, CustomSomething.class);
        updateLazySingletonLabel(lazyLibraryInstanceLabel, internalSingletonStorageMap, Picasso.class);
    }

    private void updateLazySingletonLabel(TextView label, Map<Class, Object> internalMap, Class clazz) {
        if (internalMap.containsKey(clazz)) {
            label.setText(internalMap.get(clazz).toString());
        } else {
            label.setText("No instance");
        }
    }

    private Map<Class, Object> getLazyStorageInternals() {
        Map<Class, Object> result = null;
        try {
            Field lazyStorageField = LazySingletonApplication.class.getDeclaredField("storage");
            lazyStorageField.setAccessible(true);
            Class lazyStorageClass = lazyStorageField.getType();
            LazyInstanceStorage lazyStorage = (LazyInstanceStorage) lazyStorageField.get(null);
            Field singleInstancesField = lazyStorageClass.getDeclaredField("singleInstances");
            singleInstancesField.setAccessible(true);
            result = (HashMap<Class, Object>) singleInstancesField.get(lazyStorage);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return result;
    }
}
