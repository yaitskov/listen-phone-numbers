package com.github.listenumbers.inject;

import android.app.Fragment;
import android.os.Bundle;

import com.github.listenumbers.ListenNumbersApplication;

public abstract class InjectingFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ListenNumbersApplication) getActivity().getApplication()).inject(this);
    }
}
