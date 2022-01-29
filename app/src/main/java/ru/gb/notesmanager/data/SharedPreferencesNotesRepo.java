package ru.gb.notesmanager.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ru.gb.notesmanager.domain.NoteEntity;
import ru.gb.notesmanager.domain.NoteRepository;
import ru.gb.notesmanager.utils.Utils;

public class SharedPreferencesNotesRepo implements NoteRepository {
    private static final String SHARED_PREFS_NAME = "SharedPreferencesNotesRepo";
    private static final String SHARED_PREFS_NOTES_KEY = "SHARED_PREFS_NOTES_KEY";
    private final SharedPreferences sharedPreferences;
    private final Gson gson = new Gson();

    public SharedPreferencesNotesRepo(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }


    @Override
    public List<NoteEntity> getNotes() {
        final String notesJson = sharedPreferences.getString(SHARED_PREFS_NOTES_KEY, "");
        if (!TextUtils.isEmpty(notesJson)) {
            Type type = new TypeToken<ArrayList<NoteEntity>>() {
            }.getType();
            return gson.fromJson(notesJson, type);
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public void deleteNote(NoteEntity noteEntity) {
        final List<NoteEntity> data = getNotes();
        data.remove(Utils.findPosition(noteEntity,data));
        saveToSharedPreferences(data);
    }

    @Override
    public void updateNote(NoteEntity noteEntity) {
        final List<NoteEntity> data = getNotes();
        data.set(Utils.findPosition(noteEntity,data),noteEntity);
        saveToSharedPreferences(data);
    }

    @Override
    public void addNote(NoteEntity noteEntity) {
        final List<NoteEntity> data = getNotes();
        data.add(0,noteEntity);
        saveToSharedPreferences(data);
    }

    @Override
    public int getSize() {
        return getNotes().size();
    }

    private void saveToSharedPreferences (List<NoteEntity> data) {
        final String gsonString = gson.toJson(data);
        sharedPreferences
                .edit()
                .putString(SHARED_PREFS_NOTES_KEY, gsonString)
                .apply();
    }

}
