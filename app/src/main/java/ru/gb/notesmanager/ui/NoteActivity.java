package ru.gb.notesmanager.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ru.gb.notesmanager.App;
import ru.gb.notesmanager.R;
import ru.gb.notesmanager.domain.NotesEntity;

public class NoteActivity extends AppCompatActivity {
    public static final String NOTE_EXTRA_KEY = "NOTE_EXTRA_KEY";
    private EditText titleEditText;
    private TextView dateTextView;
    private EditText noteEditText;
    private Button deleteButton;
    private Button okButton;
    private Button cancelButton;
    private boolean addNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_activity);
        initView();
    }

    private void initView() {
        titleEditText = findViewById(R.id.title_note_edit_text);
        dateTextView = findViewById(R.id.date_note_text_view);
        noteEditText = findViewById(R.id.body_note_edit_text);
        deleteButton = findViewById(R.id.delete_button);
        okButton = findViewById(R.id.ok_button);
        cancelButton = findViewById(R.id.cancel_button);
        NotesEntity notesEntity = getIntent().getParcelableExtra(NOTE_EXTRA_KEY);
        Resources resources = getResources();
        Log.d("@@@", "Title = " + notesEntity.getTitle());
        if (notesEntity.getTitle().equals("")) {
            addNote = true;
            //titleEditText.setText("Title");
            //titleEditText.setTextColor(resources.getColor(R.color.text_color_gray,  null));
        } else {
            addNote = false;
            //titleEditText.setText(notesEntity.getTitle());
            //titleEditText.setTextColor(resources.getColor(R.color.text_color,  null));
        }
        Log.d("@@@", "addNote = " + addNote);
        dateTextView.setText(notesEntity.getDate());
        noteEditText.setText(notesEntity.getTextNote());

        deleteButton.setOnClickListener(v -> {
            App.get(this).getNoteRepository().deleteNote(notesEntity);
            setResult(RESULT_OK);
            finish();
        });
        okButton.setOnClickListener(v -> {
            notesEntity.setTitle(titleEditText.getText().toString());
            notesEntity.setTextNote(noteEditText.getText().toString());
            notesEntity.setDate();
            if ( addNote ) { App.get(this).getNoteRepository().addNote(notesEntity); }
               else { App.get(this).getNoteRepository().updateNote(notesEntity); }
            setResult(RESULT_OK);
            finish();
        });
        cancelButton.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }
}
