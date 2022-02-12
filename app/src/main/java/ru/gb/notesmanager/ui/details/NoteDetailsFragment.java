package ru.gb.notesmanager.ui.details;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import ru.gb.notesmanager.R;
import ru.gb.notesmanager.domain.NoteEntity;


public class NoteDetailsFragment extends Fragment {
    private static final String NOTE_ARG_KEY = "NOTE_ARG_KEY";
    public static final String TAG_NOTE_DETAILS_FRAGMENT = "TAG_NOTE_DETAILS_FRAGMENT";
    private Toolbar noteDetailsFragmentToolbar;
    private EditText titleEditText;
    private TextView dateTextView;
    private EditText noteEditText;
    private Button deleteButton;
    private Button okButton;
    private Button cancelButton;
    private NoteEntity noteEntity;
    private Controller controller;

    public static NoteDetailsFragment newInstance(NoteEntity noteEntity) {
        NoteDetailsFragment noteDetailsFragment = new NoteDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(NOTE_ARG_KEY, noteEntity);
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
        return inflater.inflate(R.layout.fragment_note_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews(view);
        initListener();
        initMenu(view);
        initMenuListener(noteDetailsFragmentToolbar.getMenu());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.note_details_menu__item_delete:
                controller.onDeleteButtonDetails(noteEntity);
                return true;
            case R.id.note_details_menu__item_ok:
                noteEntity.setTitle(titleEditText.getText().toString());
                noteEntity.setTextNote(noteEditText.getText().toString());
                noteEntity.setDate();
                controller.onOkButtonDetails(noteEntity);
                return true;
            case R.id.note_details_menu__item_cancel:
                controller.onCancelButtonDetails();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.note_details_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void initMenu(@NonNull View view) {
        noteDetailsFragmentToolbar = view.findViewById(R.id.fragment_note_details__toolbar);
        onCreateOptionsMenu(noteDetailsFragmentToolbar.getMenu(), getActivity().getMenuInflater());
    }

    private void initMenuListener(@NonNull Menu menu) {
        menu.findItem(R.id.note_details_menu__item_delete).setOnMenuItemClickListener(this::onOptionsItemSelected);
        menu.findItem(R.id.note_details_menu__item_ok).setOnMenuItemClickListener(this::onOptionsItemSelected);
        menu.findItem(R.id.note_details_menu__item_cancel).setOnMenuItemClickListener(this::onOptionsItemSelected);
    }

    private void initViews(@NonNull View view) {
        titleEditText = view.findViewById(R.id.fragment_note_details__title_note_edit_text);
        dateTextView = view.findViewById(R.id.fragment_note_details__date_note_text_view);
        noteEditText = view.findViewById(R.id.fragment_note_details__body_note_edit_text);
        deleteButton = view.findViewById(R.id.fragment_note_details__delete_button);
        okButton = view.findViewById(R.id.fragment_note_details__ok_button);
        cancelButton = view.findViewById(R.id.fragment_note_details__cancel_button);
        noteEntity = getArguments().getParcelable(NOTE_ARG_KEY);
        titleEditText.setText(noteEntity.getTitle());
        dateTextView.setText(noteEntity.getDate());
        noteEditText.setText(noteEntity.getTextNote());
    }

    private void initListener() {
        deleteButton.setOnClickListener(view1 -> {
            controller.onDeleteButtonDetails(noteEntity);
        });
        cancelButton.setOnClickListener(view1 -> {
            controller.onCancelButtonDetails();
        });
        okButton.setOnClickListener(view1 -> {
            noteEntity.setTitle(titleEditText.getText().toString());
            noteEntity.setTextNote(noteEditText.getText().toString());
            noteEntity.setDate();
            controller.onOkButtonDetails(noteEntity);
        });
    }

    public interface Controller {
        void onDeleteButtonDetails(NoteEntity noteEntity);

        void onOkButtonDetails(NoteEntity noteEntity);

        void onCancelButtonDetails();
    }

}
