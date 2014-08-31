package com.github.listenumbers.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.listenumbers.R;
import com.github.listenumbers.SpacelessEqualizer;
import com.github.listenumbers.generator.PatternParser;
import com.github.listenumbers.generator.TextGenerator;
import com.github.listenumbers.inject.InjectingFragment;
import com.google.common.base.Equivalence;

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

    private Equivalence<String> answerChecker = new SpacelessEqualizer();
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
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
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
            updatePattern();
        } else {
            Toast.makeText(getActivity(), "Speech synsethis is not available",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnu_add_pattern:
                editPattern("");
                return true;
            case R.id.mnu_edit_pattern:
                editPattern(preferences.getString(CURRENT_PATTERN, DEFAULT_PATTERN));
                return true;
            case R.id.mnu_remove_pattern:
                return true;
            default:
                return false;
        }
    }

    private void editPattern(String defaultText) {
        final EditText input = new EditText(getActivity());
        input.setText(defaultText);
        new AlertDialog.Builder(getActivity())
                .setTitle("Pattern")
                .setMessage(defaultText.isEmpty() ? "New pattern" : "Pattern modification")
                .setView(input)
                .setPositiveButton(defaultText.isEmpty() ? "Add" : "Update",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String newPattern = input.getText().toString();
                                preferences.edit()
                                        .putString(CURRENT_PATTERN, newPattern.isEmpty()
                                                ? DEFAULT_PATTERN
                                                : newPattern)
                                        .commit();
                                updatePattern();
                            }
                        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    private void updatePattern() {
        generator = new PatternParser().parse(
                preferences.getString(CURRENT_PATTERN, DEFAULT_PATTERN));
        generate();
    }

    private void generate() {
        currentNumber = generator.generate();
        speak();
    }

    private void speak() {
        textToSpeech.speak(currentNumber, TextToSpeech.QUEUE_ADD, null);
    }

    @OnClick(R.id.btnListen)
    public void listen() {
        String answer = txtNumber.getText().toString();
        if (answerChecker.equivalent(currentNumber, answer)) {
            generate();
            Toast.makeText(getActivity(), "Ok. Listen to the next.",
                    Toast.LENGTH_SHORT).show();
        } else {
            speak();
            Toast.makeText(getActivity(), "Error. Try again.",
                    Toast.LENGTH_SHORT).show();
        }
        txtNumber.getText().clear();
    }

    @OnClick(R.id.btnShowNumber)
    public void showNumber() {
        if (currentNumber != null) {
            Toast.makeText(getActivity(), currentNumber, Toast.LENGTH_LONG)
                    .show();
        }
    }
}
