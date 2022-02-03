package ru.gb.notesmanager.ui.list;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.gb.notesmanager.App;
import ru.gb.notesmanager.R;
import ru.gb.notesmanager.domain.NoteRepository;
import ru.gb.notesmanager.domain.NoteEntity;
import ru.gb.notesmanager.ui.OnNoteListener;

public class NotesListFragment extends Fragment implements OnNoteListener {
    public static final String TAG_NOTES_LIST_FRAGMENT = "TAG_NOTES_LIST_FRAGMENT";
    private Toolbar notesListFragmentToolbar;
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
        initMenu(view);
        initMenuListener(notesListFragmentToolbar.getMenu());
    }

    private void initMenuListener(@NonNull Menu menu) {
        menu.findItem(R.id.notes_list_menu__item_add).setOnMenuItemClickListener(this::onOptionsItemSelected);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notes_list_menu__item_add:
                controller.addNote();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.notes_list_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void initMenu(@NonNull View view) {
        notesListFragmentToolbar = view.findViewById(R.id.fragment_notes_list__toolbar);
        onCreateOptionsMenu(notesListFragmentToolbar.getMenu(), getActivity().getMenuInflater());
    }

    private void initButton(@NonNull View view) {
        addButton = view.findViewById(R.id.fragment_notes_list__add_button);
        addButton.setOnClickListener(v -> {
            controller.addNote();
        });
    }

    private void initRecycler(@NonNull View view) {
        recyclerView = view.findViewById(R.id.fragment_notes_list__recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NoteAdapter();
        updateNoteList();
        adapter.setOnNoteListener(this);
        recyclerView.setAdapter(adapter);
    }

    public void deleteEmployee(NoteEntity noteEntity) {
        noteRepository.deleteNote(noteEntity);
        updateNoteList();
    }

    @Override
    public void onDeleteEmployee(NoteEntity noteEntity) {
        new AlertDialog.Builder(getContext())
                .setTitle("Внимание!")
                .setMessage("Вы точно хотите удалить \"" + noteEntity.getTitle() + "\" ?")
                .setPositiveButton("Да", (dialog, id) -> {
                    deleteEmployee(noteEntity);
                })
                .setNegativeButton("Нет", (dialog, id) -> {
                    Toast.makeText(getContext(), "Удаление отменено", Toast.LENGTH_SHORT).show();
                })
                .show();

    }

    @Override
    public boolean onClickPopupMenu(NoteEntity noteEntity, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notes_list_popup__item_delete: {
                new AlertDialog.Builder(getContext())
                        .setTitle("Внимание!")
                        .setMessage("Вы точно хотите удалить \"" + noteEntity.getTitle() + "\" ?")
                        .setPositiveButton("Да", (dialog, id) -> {
                            deleteEmployee(noteEntity);
                        })
                        .setNegativeButton("Нет", (dialog, id) -> {
                            Toast.makeText(getContext(), "Удаление отменено", Toast.LENGTH_SHORT).show();
                        })
                        .show();
                return true;
            }
            case R.id.notes_list_popup__item_update: {
                controller.showNoteDetails(noteEntity);
            }
        }
        return false;
    }

    @Override
    public void onClickEmployee(NoteEntity noteEntity) {
        controller.showNoteDetails(noteEntity);
    }

    @Override
    public void onUpdateEmployee(NoteEntity noteEntity) {
        controller.showNoteDetails(noteEntity);
    }

    public void updateNoteList() {
        adapter.setNoteList(noteRepository.getNotes());
    }


    public interface Controller {
        void showNoteDetails(NoteEntity noteEntity);

        void addNote();
    }

}
