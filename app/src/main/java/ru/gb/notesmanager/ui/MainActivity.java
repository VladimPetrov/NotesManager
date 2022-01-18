package ru.gb.notesmanager.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import ru.gb.notesmanager.App;
import ru.gb.notesmanager.domain.NoteRepository;
import ru.gb.notesmanager.domain.NotesEntity;
import ru.gb.notesmanager.R;
import ru.gb.notesmanager.ui.details.NoteDetailsFragment;
import ru.gb.notesmanager.ui.list.NoteAdapter;
import ru.gb.notesmanager.ui.list.NotesListFragment;

public class MainActivity extends AppCompatActivity
        implements NotesListFragment.Controller, NoteDetailsFragment.Controller {
    private static final String TAG = "@@@";
    private static final String TAG_NOTES_LIST_FRAGMENT = "TAG_NOTES_LIST_FRAGMENT";
    private static final String TAG_NOTE_DETAILS_FRAGMENT = "TAG_NOTE_DETAILS_FRAGMENT";
    private boolean addNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addNote = false;
        if (isTwoPaneMode()) {
            Fragment noteFragment = getSupportFragmentManager().findFragmentById(R.id.activity_main__list_fragment_container);
            if (noteFragment instanceof NoteDetailsFragment) {
                moveFragment(noteFragment);
                //showListFragment();
            }
            // showListFragment();
        }
        showListFragment();


    }

    private void moveFragment(Fragment oldFragment) {
        final Bundle args = oldFragment.getArguments();
        final Bundle savedState = new Bundle();
        oldFragment.onSaveInstanceState(savedState);

        Fragment newFragment = null;
        try {
            newFragment = oldFragment.getClass().newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        newFragment.setArguments(args);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main__details_fragment_container, newFragment, TAG_NOTE_DETAILS_FRAGMENT)
                .addToBackStack(null)
                .commit();
        //newFragment.onAttach((Context) this);
        //FrameLayout container = findViewById(R.id.activity_main__details_fragment_container);
        //final View fragmentView = newFragment.onCreateView(LayoutInflater.from(this), container, savedState);
        //container.addView(fragmentView);
        //newFragment.onViewCreated(fragmentView, savedState);
    }

    private boolean isTwoPaneMode() {
        FrameLayout detailsFragmentContainer = findViewById(R.id.activity_main__details_fragment_container);
        return detailsFragmentContainer != null;
    }

    private void showListFragment() {
        Fragment notesListFragment = new NotesListFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main__list_fragment_container, notesListFragment, TAG_NOTES_LIST_FRAGMENT)
                .commit();
    }

    @Override
    public void addNote() {
        NotesEntity noteEntity = new NotesEntity(String.valueOf(App.get(this).getNoteRepository().getSize()), "", "");
        addNote = true;
        Fragment noteDetailsFragment = NoteDetailsFragment.newInstance(noteEntity);
        int contanerId = R.id.activity_main__list_fragment_container;
        if (isTwoPaneMode()) {
            contanerId = R.id.activity_main__details_fragment_container;
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(contanerId, noteDetailsFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showNoteDetails(NotesEntity noteEntity) {
        Fragment noteDetailsFragment = NoteDetailsFragment.newInstance(noteEntity);
        int contanerId = R.id.activity_main__list_fragment_container;
        if (isTwoPaneMode()) {
            contanerId = R.id.activity_main__details_fragment_container;
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(contanerId, noteDetailsFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDeleteButtonDetails(NotesEntity noteEntity) {
        getSupportFragmentManager().popBackStack();
        App.get(this).getNoteRepository().deleteNote(noteEntity);
        NotesListFragment notesListFragment = (NotesListFragment) getSupportFragmentManager().findFragmentByTag(TAG_NOTES_LIST_FRAGMENT);
        if (notesListFragment == null) {
            throw new IllegalArgumentException("NotesListFragment not on screen");
        }
        notesListFragment.onDeleteEmployee(noteEntity);
    }

    @Override
    public void onOkButtonDetails(NotesEntity noteEntity) {
        getSupportFragmentManager().popBackStack();
        if (addNote) {
            App.get(this).getNoteRepository().addNote(noteEntity);
        } else {
            App.get(this).getNoteRepository().updateNote(noteEntity);
        }
        NotesListFragment notesListFragment = (NotesListFragment) getSupportFragmentManager().findFragmentByTag(TAG_NOTES_LIST_FRAGMENT);
        if (notesListFragment == null) {
            throw new IllegalArgumentException("NotesListFragment not on screen");
        }
        notesListFragment.updateNoteList();
    }

    @Override
    public void onCancelButtonDetails() {
        getSupportFragmentManager().popBackStack();

    }
}