package com.github.listenumbers.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.listenumbers.R;
import com.github.listenumbers.inject.InjectingFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Daneel Yaitskov
 */
public class MainFragment extends InjectingFragment
        implements TextToSpeech.OnInitListener {
    @InjectView(R.id.btnListen)
    Button btnListen;

    @InjectView(R.id.txtNumber)
    EditText txtNumber;

    @Inject
    SharedPreferences preferences;

    TextToSpeech textToSpeech;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        textToSpeech = new TextToSpeech(activity, this);
    }

    @Override
    public void onDetach() {
        textToSpeech.shutdown();
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        ButterKnife.inject(this, view);
        txtNumber.requestFocus();
        return view;
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            btnListen.setEnabled(true);
        } else {
            Toast.makeText(getActivity(), "Speech synsethis is not available",
                    Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.btnListen)
    public void listen() {
        textToSpeech.speak(txtNumber.getText().toString(), TextToSpeech.QUEUE_ADD, null);
        txtNumber.getText().clear();
    }
}
