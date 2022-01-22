package ru.gb.notesmanager.ui;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.widget.FrameLayout;
import ru.gb.notesmanager.App;
import ru.gb.notesmanager.domain.NoteEntity;
import ru.gb.notesmanager.R;
import ru.gb.notesmanager.ui.details.NoteDetailsFragment;
import ru.gb.notesmanager.ui.list.NotesListFragment;

public class MainActivity extends AppCompatActivity
        implements NotesListFragment.Controller, NoteDetailsFragment.Controller {

    private boolean addNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addNote = false;
        if (isTwoPaneMode()) {
            Fragment noteFragment = getSupportFragmentManager().findFragmentById(R.id.activity_main__list_fragment_container);
            if (noteFragment instanceof NoteDetailsFragment) moveFragment(noteFragment);
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
                .replace(R.id.activity_main__details_fragment_container
                        , newFragment
                        , NoteDetailsFragment.TAG_NOTE_DETAILS_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    private boolean isTwoPaneMode() {
        FrameLayout detailsFragmentContainer = findViewById(R.id.activity_main__details_fragment_container);
        return detailsFragmentContainer != null;
    }

    private void showListFragment() {
        Fragment notesListFragment = new NotesListFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main__list_fragment_container
                        , notesListFragment,
                        NotesListFragment.TAG_NOTES_LIST_FRAGMENT)
                .commit();
    }

    @Override
    public void addNote() {
        NoteEntity noteEntity = new NoteEntity(String.valueOf(App.get(this).getNoteRepository().getSize()), "", "");
        addNote = true;
        Fragment noteDetailsFragment = NoteDetailsFragment.newInstance(noteEntity);
        int contanerId = R.id.activity_main__list_fragment_container;
        if (isTwoPaneMode()) {
            contanerId = R.id.activity_main__details_fragment_container;
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(contanerId, noteDetailsFragment, NoteDetailsFragment.TAG_NOTE_DETAILS_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showNoteDetails(NoteEntity noteEntity) {
        if (isTwoPaneMode()) {
            NoteDetailsFragment noteDetailsFragment = (NoteDetailsFragment) getSupportFragmentManager()
                    .findFragmentByTag(NoteDetailsFragment.TAG_NOTE_DETAILS_FRAGMENT);
            if (noteDetailsFragment != null) {
                getSupportFragmentManager().popBackStack();
            }
        }
        Fragment noteDetailsFragment = NoteDetailsFragment.newInstance(noteEntity);
        int contanerId = R.id.activity_main__list_fragment_container;
        if (isTwoPaneMode()) {
            contanerId = R.id.activity_main__details_fragment_container;
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(contanerId, noteDetailsFragment, NoteDetailsFragment.TAG_NOTE_DETAILS_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDeleteButtonDetails(NoteEntity noteEntity) {
        getSupportFragmentManager().popBackStack();
        App.get(this).getNoteRepository().deleteNote(noteEntity);
        NotesListFragment notesListFragment = (NotesListFragment) getSupportFragmentManager()
                .findFragmentByTag(NotesListFragment.TAG_NOTES_LIST_FRAGMENT);
        if (notesListFragment == null) {
            throw new IllegalArgumentException("NotesListFragment not on screen");
        }
        notesListFragment.onDeleteEmployee(noteEntity);
    }

    @Override
    public void onOkButtonDetails(NoteEntity noteEntity) {
        getSupportFragmentManager().popBackStack();
        if (addNote) {
            App.get(this).getNoteRepository().addNote(noteEntity);
        } else {
            App.get(this).getNoteRepository().updateNote(noteEntity);
        }
        NotesListFragment notesListFragment = (NotesListFragment) getSupportFragmentManager()
                .findFragmentByTag(NotesListFragment.TAG_NOTES_LIST_FRAGMENT);
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