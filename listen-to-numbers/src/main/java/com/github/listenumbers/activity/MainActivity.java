package com.github.listenumbers.activity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import com.github.listenumbers.R;
import com.github.listenumbers.fragment.MainFragment;

public class MainActivity extends Activity {
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        currentFragment = new MainFragment();
        currentFragment.setArguments(getIntent().getExtras());
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, currentFragment)
                .commit();
    }
}
