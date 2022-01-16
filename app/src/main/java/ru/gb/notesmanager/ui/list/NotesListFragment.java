package ru.gb.notesmanager.ui.list;

import android.content.Context;
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
import ru.gb.notesmanager.ui.OnNoteListener;

public class NotesListFragment extends Fragment implements OnNoteListener {
    private static final int NOTE_REQUEST_CODE = 10;
    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private NoteRepository noteRepository;
    private Button addButton;
    private Controller controller;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Controller) {
            controller = (Controller) context;
        } else {
            throw new IllegalStateException("Activity must implement NotesListFragment.Controller");
        }
    }


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
        controller.showNoteDetails(noteEntity);
    }

    @Override
    public void onUpdateEmployee(NotesEntity noteEntity) {
        controller.showNoteDetails(noteEntity);
    }

    public interface Controller {
        void showNoteDetails (NotesEntity noteEntity);
    }

}
