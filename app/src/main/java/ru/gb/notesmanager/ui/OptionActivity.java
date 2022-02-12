package ru.gb.notesmanager.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ru.gb.notesmanager.R;
import ru.gb.notesmanager.domain.NotesOption;

public class OptionActivity extends Activity {
    public static final String NUMBER_KEY = "OPTION_NOTES_MANAGER";
    private TextView countStartsTextView;
    private TextView countNotesTextView;
    private Button cancelButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_options);
        initParameters();
    }

    private void initParameters() {
        NotesOption parameters;
        countStartsTextView = findViewById(R.id.activity_options__count_starts);
        countNotesTextView = findViewById(R.id.activity_options__count_notes);
        cancelButton = findViewById(R.id.activity_options__cancel);
        parameters = (NotesOption) getIntent().getExtras().getParcelable(NUMBER_KEY);
        countStartsTextView.setText("Колличество запусков: " + String.valueOf(parameters.getCountStarts()));
        countNotesTextView.setText("Колличество записей: " + String.valueOf(parameters.getCountNotes()));
        cancelButton.setOnClickListener(v -> {
            finish();
        });
    }

}
