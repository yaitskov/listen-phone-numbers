package com.github.listenumbers;

import android.app.Application;
import android.os.StrictMode;

import com.github.listenumbers.inject.CommonModule;
import com.github.listenumbers.inject.Injector;

import dagger.ObjectGraph;

public class ListenNumbersApplication extends Application implements Injector {
    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        threadPolicy();
        super.onCreate();
        objectGraph = ObjectGraph.create(new CommonModule(getApplicationContext()));
    }

    private void threadPolicy() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyDialog()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDropBox()
                    .build());
        }
    }

    @Override
    public void inject(Object object) {
        objectGraph.inject(object);
    }

    @Override
    public ObjectGraph getObjectGraph() {
        return objectGraph;
    }
}
