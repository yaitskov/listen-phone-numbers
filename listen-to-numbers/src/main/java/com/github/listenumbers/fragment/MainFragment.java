package com.github.listenumbers.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.listenumbers.R;
import com.github.listenumbers.inject.InjectingFragment;

import javax.inject.Inject;

/**
 * Daneel Yaitskov
 */
public class MainFragment extends InjectingFragment {
    @Inject
    SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }
}
