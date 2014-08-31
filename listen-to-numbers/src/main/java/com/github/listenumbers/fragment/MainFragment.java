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
import com.github.listenumbers.generator.PatternParser;
import com.github.listenumbers.generator.TextGenerator;
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
    public static final String CURRENT_PATTERN = "current-pattern";
    public static final String DEFAULT_PATTERN = "ddd dd ddd";
    @InjectView(R.id.btnListen)
    Button btnListen;

    @InjectView(R.id.txtNumber)
    EditText txtNumber;

    @Inject
    SharedPreferences preferences;

    TextToSpeech textToSpeech;

    private TextGenerator generator;
    private String currentNumber;

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
            generate();
            speak();
        } else {
            Toast.makeText(getActivity(), "Speech synsethis is not available",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void updatePattern() {
        generator = new PatternParser().parse(
                preferences.getString(CURRENT_PATTERN, DEFAULT_PATTERN));

    }

    private void generate() {
        if (generator == null) {
            updatePattern();
        }
        currentNumber = generator.generate();
    }

    private void speak() {
        textToSpeech.speak(currentNumber, TextToSpeech.QUEUE_ADD, null);
    }

    @OnClick(R.id.btnListen)
    public void listen() {
        String answer = txtNumber.getText().toString();
        if (answer.equals(currentNumber)) {
            generate();
            Toast.makeText(getActivity(), "Ok. Listen to the next.",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Error. Try again.",
                    Toast.LENGTH_SHORT).show();
        }
        txtNumber.getText().clear();
        speak();
    }
}
