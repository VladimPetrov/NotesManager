package ru.gb.notesmanager.ui;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.app.Notification;
import android.app.NotificationChannel;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import ru.gb.notesmanager.App;
import ru.gb.notesmanager.domain.NoteEntity;
import ru.gb.notesmanager.R;
import ru.gb.notesmanager.domain.NoteRepository;
import ru.gb.notesmanager.ui.details.NoteDetailsFragment;
import ru.gb.notesmanager.ui.list.NotesListFragment;

public class MainActivity extends AppCompatActivity
        implements NotesListFragment.Controller, NoteDetailsFragment.Controller {
    private static final String CHANNEL_ID = "channel for NoteManager";
    private static final int NOTIFICATION_ID = 2;
    private boolean addNote;
    private NoteRepository noteRepository;
    private NotificationChannelCompat notificationChannel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
        addNote = false;
        noteRepository = App.get(this).getNoteRepository();
        if (isTwoPaneMode()) {
            Fragment noteFragment = getSupportFragmentManager().findFragmentById(R.id.activity_main__list_fragment_container);
            if (noteFragment instanceof NoteDetailsFragment) moveFragment(noteFragment);
        }
        showListFragment();
    }

    private void createNotificationChannel() {
        notificationChannel = new NotificationChannelCompat.Builder(
        CHANNEL_ID,
                NotificationManagerCompat.IMPORTANCE_MAX)
                .setDescription("NoteManager")
                .setName("Глупые сообщения")
                .build();
        NotificationManagerCompat.from(this).createNotificationChannel(notificationChannel);
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
        NoteEntity noteEntity = new NoteEntity(String.valueOf(noteRepository.getSize()), "", "");
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
        new AlertDialog.Builder(this)
                .setTitle("Внимание!")
                .setMessage("Вы точно хотите удалить \"" + noteEntity.getTitle() + "\" ?")
                .setPositiveButton("Да", (dialog, id) -> {
                    getSupportFragmentManager().popBackStack();
                    noteRepository.deleteNote(noteEntity);
                    NotesListFragment notesListFragment = (NotesListFragment) getSupportFragmentManager()
                            .findFragmentByTag(NotesListFragment.TAG_NOTES_LIST_FRAGMENT);
                    if (notesListFragment == null) {
                        throw new IllegalArgumentException("NotesListFragment not on screen");
                    }
                    notesListFragment.deleteEmployee(noteEntity);
                })
                .setNegativeButton("Нет", (dialog, id) -> {
                    Toast.makeText(this, "Удаление отменено", Toast.LENGTH_LONG).show();
                })
                .show();
    }

    @Override
    public void onOkButtonDetails(NoteEntity noteEntity) {
        getSupportFragmentManager().popBackStack();
        if (addNote) {
            noteRepository.addNote(noteEntity);
            final Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Добавлена заметка")
                    .setContentText("\"" + noteEntity.getTitle() + "\"")
                    .setColorized(true)
                    .setColor(Color.GREEN)
                    .setSmallIcon(R.drawable.ic_baseline_library_books_24)
                    .build();
            NotificationManagerCompat.from(this).notify(NOTIFICATION_ID, notification);
        } else {
            noteRepository.updateNote(noteEntity);
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