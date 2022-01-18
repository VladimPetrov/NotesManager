package ru.gb.notesmanager.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import ru.gb.notesmanager.App;
import ru.gb.notesmanager.domain.NoteRepository;
import ru.gb.notesmanager.domain.NotesEntity;
import ru.gb.notesmanager.R;
import ru.gb.notesmanager.ui.details.NoteDetailsFragment;
import ru.gb.notesmanager.ui.list.NoteAdapter;
import ru.gb.notesmanager.ui.list.NotesListFragment;

public class MainActivity extends AppCompatActivity
        implements NotesListFragment.Controller, NoteDetailsFragment.Controller{
    private static final String TAG = "@@@";
    private static final String TAG_NOTES_LIST_FRAGMENT = "TAG_NOTES_LIST_FRAGMENT";
    private boolean addNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addNote = false;
        if (savedInstanceState == null) {
            Fragment notesListFragment = new NotesListFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_main__list_fragment_container, notesListFragment, TAG_NOTES_LIST_FRAGMENT)
                    .commit();
        }



    }

    @Override
    public void addNote() {
        NotesEntity noteEntity = new NotesEntity(String.valueOf(App.get(this).getNoteRepository().getSize()),"","");
        addNote = true;
        Fragment noteDetailsFragment = NoteDetailsFragment.newInstance(noteEntity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main__list_fragment_container, noteDetailsFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showNoteDetails(NotesEntity noteEntity) {
        Fragment noteDetailsFragment = NoteDetailsFragment.newInstance(noteEntity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main__list_fragment_container, noteDetailsFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDeleteButtonDetails(NotesEntity noteEntity) {
        getSupportFragmentManager().popBackStack();
        App.get(this).getNoteRepository().deleteNote(noteEntity);
        NotesListFragment notesListFragment = (NotesListFragment) getSupportFragmentManager().findFragmentByTag(TAG_NOTES_LIST_FRAGMENT);
        if(notesListFragment == null) {
            throw new IllegalArgumentException("NotesListFragment not on screen");
        }
        notesListFragment.onDeleteEmployee(noteEntity);
    }

    @Override
    public void onOkButtonDetails(NotesEntity noteEntity) {
        getSupportFragmentManager().popBackStack();
        if ( addNote ) { App.get(this).getNoteRepository().addNote(noteEntity); }
        else { App.get(this).getNoteRepository().updateNote(noteEntity); }
        NotesListFragment notesListFragment = (NotesListFragment) getSupportFragmentManager().findFragmentByTag(TAG_NOTES_LIST_FRAGMENT);
        if(notesListFragment == null) {
            throw new IllegalArgumentException("NotesListFragment not on screen");
        }
        notesListFragment.updateNoteList();
    }

    @Override
    public void onCancelButtonDetails() {
        getSupportFragmentManager().popBackStack();
    }
}