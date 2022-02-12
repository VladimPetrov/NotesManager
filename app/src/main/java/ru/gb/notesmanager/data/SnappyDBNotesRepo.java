package ru.gb.notesmanager.data;

import android.content.Context;

import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.gb.notesmanager.domain.NoteEntity;
import ru.gb.notesmanager.domain.NoteRepository;
import ru.gb.notesmanager.utils.Utils;

public class SnappyDBNotesRepo implements NoteRepository {
    private static final String NAME_DATABASE_SNAPPYDB = "NOTES_MANAGER";
    private final DB snappyDB;

    public SnappyDBNotesRepo(Context context) {
        snappyDB = initDatabase(context);
    }

    public void destroy() {
        try {
            snappyDB.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    private DB initDatabase(Context context) {
        try {
            return DBFactory.open(context, NAME_DATABASE_SNAPPYDB);
        } catch (SnappydbException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<NoteEntity> getNotes() {
        List<NoteEntity> data = new ArrayList<>();
        try {
            NoteEntity[] array = snappyDB.getArray(NAME_DATABASE_SNAPPYDB, NoteEntity.class);
            data.addAll(Arrays.asList(array));
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public void deleteNote(NoteEntity noteEntity) {
        final List<NoteEntity> data = getNotes();
        data.remove(Utils.findPosition(noteEntity, data));
        saveToSnappyDB(data);
    }

    @Override
    public void updateNote(NoteEntity noteEntity) {
        final List<NoteEntity> data = getNotes();
        data.set(Utils.findPosition(noteEntity, data), noteEntity);
        saveToSnappyDB(data);
    }

    @Override
    public void addNote(NoteEntity noteEntity) {
        final List<NoteEntity> data = getNotes();
        data.add(0, noteEntity);
        saveToSnappyDB(data);
    }

    @Override
    public int getSize() {
        return getNotes().size();
    }

    private void saveToSnappyDB(List<NoteEntity> data) {
        try {
            snappyDB.put(NAME_DATABASE_SNAPPYDB, data.toArray());
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

}
