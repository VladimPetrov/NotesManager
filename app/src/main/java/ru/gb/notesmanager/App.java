package ru.gb.notesmanager;

import android.app.Application;
import android.content.Context;

import ru.gb.notesmanager.data.CacheNoteRepositoryImpl;
import ru.gb.notesmanager.data.NotesOptionRepository;
import ru.gb.notesmanager.data.SharedPreferencesNotesRepo;
import ru.gb.notesmanager.data.SnappyDBNotesRepo;
import ru.gb.notesmanager.domain.NoteRepository;
import ru.gb.notesmanager.domain.NotesOption;

public class App extends Application {
    //private NoteRepository noteRepository = new CacheNoteRepositoryImpl();
    private NoteRepository noteRepository;
    private NotesOptionRepository options;

    @Override
    public void onCreate() {
        super.onCreate();
        //noteRepository = new SharedPreferencesNotesRepo(this);
        noteRepository = new SnappyDBNotesRepo(this);
        options = new NotesOptionRepository(20, noteRepository.getSize());
    }


    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    public NoteRepository getNoteRepository() {
        return noteRepository;
    }

    public NotesOption getOptions() {
        return options.getParameter();
    }
}
