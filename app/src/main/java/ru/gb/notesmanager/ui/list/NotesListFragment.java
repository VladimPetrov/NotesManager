package ru.gb.notesmanager.ui.list;

import static ru.gb.notesmanager.R.layout.fragment_notes_list;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.gb.notesmanager.App;
import ru.gb.notesmanager.R;
import ru.gb.notesmanager.domain.NoteRepository;
import ru.gb.notesmanager.domain.NotesEntity;
import ru.gb.notesmanager.ui.NoteActivity;
import ru.gb.notesmanager.ui.OnNoteListener;

public class NotesListFragment extends Fragment implements OnNoteListener {
    private static final int NOTE_REQUEST_CODE = 10;
    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private NoteRepository noteRepository;
    private Button addButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        noteRepository = App.get(getContext()).getNoteRepository();
        Log.d("@@@", "noteRepository.size = " + noteRepository.getNotes().size());
        initRecycler(view);
        initButton(view);
    }

    private void initButton(@NonNull View view) {
        addButton = view.findViewById(R.id.fragment_notes_list__add_button);
        addButton.setOnClickListener(v -> {
            NotesEntity noteEntity = new NotesEntity(String.valueOf(noteRepository.getSize()),"","");
            Intent intent = new Intent(getContext(), NoteActivity.class);
            intent.putExtra(NoteActivity.NOTE_EXTRA_KEY,noteEntity);
            startActivityForResult(intent, NOTE_REQUEST_CODE);
        });
    }

    private void initRecycler(@NonNull View view) {
        recyclerView = view.findViewById(R.id.fragment_notes_list__recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NoteAdapter();
        adapter.setNoteList(noteRepository.getNotes());
        adapter.setOnNoteListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDeleteEmployee(NotesEntity noteEntity) {
        noteRepository.deleteNote(noteEntity);
        adapter.setNoteList(noteRepository.getNotes());
    }

    @Override
    public void onClickEmployee(NotesEntity noteEntity) {
        Intent intent = new Intent(getContext(),NoteActivity.class);
        intent.putExtra(NoteActivity.NOTE_EXTRA_KEY,noteEntity);
        //startActivity(intent);
        startActivityForResult(intent, NOTE_REQUEST_CODE);
    }

    @Override
    public void onUpdateEmployee(NotesEntity noteEntity) {
        Intent intent = new Intent(getContext(),NoteActivity.class);
        intent.putExtra(NoteActivity.NOTE_EXTRA_KEY,noteEntity);
        //startActivity(intent);
        startActivityForResult(intent, NOTE_REQUEST_CODE);
    }

}
