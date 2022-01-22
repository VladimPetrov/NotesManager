package ru.gb.notesmanager.data;

import java.util.ArrayList;
import java.util.List;

import ru.gb.notesmanager.domain.NoteRepository;
import ru.gb.notesmanager.domain.NoteEntity;

public class CacheNoteRepositoryImpl implements NoteRepository {
    private final ArrayList<NoteEntity> cache = new ArrayList<>();

    public CacheNoteRepositoryImpl() {
        cache.addAll(createDummyNotesData());
    }

    private static ArrayList<NoteEntity> createDummyNotesData() {
        final ArrayList<NoteEntity> noteEntities = new ArrayList<>();
        noteEntities.add(new NoteEntity(
                "0",
                "Заголовок 1",
                "id = 0 ; Title = Заголовок 1"
        ));
        noteEntities.add(new NoteEntity(
                "1",
                "Заголовок 2",
                "id = 1 ; Title = Заголовок 2"
        ));
        noteEntities.add(new NoteEntity(
                "2",
                "Заголовок 3",
                "id = 2 ; Title = Заголовок 3"
        ));
        noteEntities.add(new NoteEntity(
                "3",
                "Заголовок 4",
                "id = 3 ; Title = Заголовок 4"
        ));
        noteEntities.add(new NoteEntity(
                "4",
                "Заголовок 5",
                "id = 4 ; Title = Заголовок 5"
        ));
        noteEntities.add(new NoteEntity(
                "5",
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
            cache.remove(findPosition(noteEntity));
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
        }
    }
    @Override
    public void updateNote(NoteEntity noteEntity) {
        try {
            cache.set(findPosition(noteEntity),noteEntity);
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
        }
    }
    @Override
    public void addNote(NoteEntity noteEntity) {
        cache.add(noteEntity);
    }
    private int findPosition(NoteEntity noteEntity) {
        for (int i = 0; i < cache.size(); i++) {
            if (noteEntity.getId().equals(cache.get(i).getId())) {
                return i;
            }
        }
        throw new IllegalArgumentException("Нет такого элемента");
    }
}
