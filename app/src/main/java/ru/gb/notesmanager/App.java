package ru.gb.notesmanager;

import android.app.Application;
import android.content.Context;

import ru.gb.notesmanager.data.CacheNoteRepositoryImpl;
import ru.gb.notesmanager.domain.NoteRepository;

public class App extends Application {
    private NoteRepository noteRepository = new CacheNoteRepositoryImpl();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    public NoteRepository getNoteRepository() {
        return noteRepository;
    }
}
