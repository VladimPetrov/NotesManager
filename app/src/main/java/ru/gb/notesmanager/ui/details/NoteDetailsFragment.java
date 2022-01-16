package ru.gb.notesmanager.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.gb.notesmanager.R;
import ru.gb.notesmanager.domain.NotesEntity;

public class NoteDetailsFragment extends Fragment {
    private static final String NOTE_ARG_KEY = "NOTE_ARG_KEY";
    private EditText titleEditText;
    private TextView dateTextView;
    private EditText noteEditText;
    private Button deleteButton;
    private Button okButton;
    private Button cancelButton;
    private boolean addNote;

    public static NoteDetailsFragment newInstance (NotesEntity noteEntity) {
        NoteDetailsFragment noteDetailsFragment = new NoteDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(NOTE_ARG_KEY,noteEntity);
        noteDetailsFragment.setArguments(bundle);
        return noteDetailsFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_details, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        titleEditText = view.findViewById(R.id.fragment_note_details__title_note_edit_text);
        dateTextView = view.findViewById(R.id.fragment_note_details__date_note_text_view);
        noteEditText = view.findViewById(R.id.fragment_note_details__body_note_edit_text);
        deleteButton = view.findViewById(R.id.fragment_note_details__delete_button);
        okButton = view.findViewById(R.id.fragment_note_details__ok_button);
        cancelButton = view.findViewById(R.id.fragment_note_details__cancel_button);

    }
}
