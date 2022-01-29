package ru.gb.notesmanager.data;

import java.util.ArrayList;
import java.util.List;

import ru.gb.notesmanager.domain.NoteRepository;
import ru.gb.notesmanager.domain.NoteEntity;
import ru.gb.notesmanager.utils.Utils;

public class CacheNoteRepositoryImpl implements NoteRepository {
    private final ArrayList<NoteEntity> cache = new ArrayList<>();

    public CacheNoteRepositoryImpl() {
        cache.addAll(createDummyNotesData());
    }

    private static ArrayList<NoteEntity> createDummyNotesData() {
        final ArrayList<NoteEntity> noteEntities = new ArrayList<>();
        noteEntities.add(new NoteEntity(
                "Заголовок 1",
                "id = 0 ; Title = Заголовок 1"
        ));
        noteEntities.add(new NoteEntity(
                "Заголовок 2",
                "id = 1 ; Title = Заголовок 2"
        ));
        noteEntities.add(new NoteEntity(
                "Заголовок 3",
                "id = 2 ; Title = Заголовок 3"
        ));
        noteEntities.add(new NoteEntity(
                "Заголовок 4",
                "id = 3 ; Title = Заголовок 4"
        ));
        noteEntities.add(new NoteEntity(
                "Заголовок 5",
                "id = 4 ; Title = Заголовок 5"
        ));
        noteEntities.add(new NoteEntity(
                "Заголовок 6",
                "id = 5 ; Title = Заголовок 6"
        ));
        return noteEntities;
    }

    public int getSize() { return cache.size(); }

    @Override
    public List<NoteEntity> getNotes() {
        return new ArrayList<>(cache);
    }

    @Override
    public void deleteNote(NoteEntity noteEntity) {
        try {
            cache.remove(Utils.findPosition(noteEntity, cache));
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
        }
    }
    @Override
    public void updateNote(NoteEntity noteEntity) {
        try {
            cache.set(Utils.findPosition(noteEntity, cache),noteEntity);
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
        }
    }
    @Override
    public void addNote(NoteEntity noteEntity) {
        cache.add(noteEntity);
    }

}
