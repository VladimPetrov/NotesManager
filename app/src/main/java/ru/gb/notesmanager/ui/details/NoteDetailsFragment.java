package ru.gb.notesmanager.ui.details;

import android.content.Context;
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
import ru.gb.notesmanager.ui.list.NotesListFragment;

public class NoteDetailsFragment extends Fragment {
    private static final String NOTE_ARG_KEY = "NOTE_ARG_KEY";
    private EditText titleEditText;
    private TextView dateTextView;
    private EditText noteEditText;
    private Button deleteButton;
    private Button okButton;
    private Button cancelButton;
    private NotesEntity notesEntity;
    private Controller controller;

    public static NoteDetailsFragment newInstance (NotesEntity noteEntity) {
        NoteDetailsFragment noteDetailsFragment = new NoteDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(NOTE_ARG_KEY,noteEntity);
        noteDetailsFragment.setArguments(bundle);
        return noteDetailsFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof NoteDetailsFragment.Controller) {
            controller = (NoteDetailsFragment.Controller) context;
        } else {
            throw new IllegalStateException("Activity must implement NotesListFragment.Controller");
        }
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
        notesEntity = getArguments().getParcelable(NOTE_ARG_KEY);
        titleEditText.setText(notesEntity.getTitle());
        dateTextView.setText(notesEntity.getDate());
        noteEditText.setText(notesEntity.getTextNote());

        deleteButton.setOnClickListener(view1 -> {
            controller.onDeleteButtonDetails(notesEntity);
        });
        cancelButton.setOnClickListener(view1 -> {
            controller.onCancelButtonDetails();
        });
        okButton.setOnClickListener(view1 -> {
            notesEntity.setTitle(titleEditText.getText().toString());
            notesEntity.setTextNote(noteEditText.getText().toString());
            notesEntity.setDate();
            controller.onOkButtonDetails(notesEntity);
        });

    }

    public interface Controller {
        void onDeleteButtonDetails(NotesEntity noteEntity);
        void onOkButtonDetails(NotesEntity noteEntity);
        void onCancelButtonDetails();
    }

}
