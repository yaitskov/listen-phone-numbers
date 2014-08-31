package com.github.listenumbers.inject;

import android.content.Context;
import android.content.SharedPreferences;

import com.github.listenumbers.PrefFiles;
import com.github.listenumbers.activity.MainActivity;
import com.github.listenumbers.fragment.MainFragment;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Daneel Yaitskov
 */
@Module(injects = { MainFragment.class })
public class CommonModule {
    private final Context context;

    public CommonModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public SharedPreferences getUserPrefs() {
        return context.getSharedPreferences(PrefFiles.USER,
                Context.MODE_PRIVATE);
    }
}
