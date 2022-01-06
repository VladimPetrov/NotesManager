package ru.gb.notesmanager.data;

import java.util.ArrayList;
import java.util.List;

import ru.gb.notesmanager.domain.NoteRepository;
import ru.gb.notesmanager.domain.NotesEntity;

public class CacheNoteRepositoryImpl implements NoteRepository {
    private final ArrayList<NotesEntity> cache = new ArrayList<>();

    public CacheNoteRepositoryImpl() {
        cache.addAll(createDummyNotesData());
    }

    private static ArrayList<NotesEntity> createDummyNotesData() {
        final ArrayList<NotesEntity> noteEntities = new ArrayList<>();
        noteEntities.add(new NotesEntity(
                "0",
                "Заголовок 1",
                "id = 0 ; Title = Заголовок 1"
        ));
        noteEntities.add(new NotesEntity(
                "1",
                "Заголовок 2",
                "id = 1 ; Title = Заголовок 2"
        ));
        noteEntities.add(new NotesEntity(
                "2",
                "Заголовок 3",
                "id = 2 ; Title = Заголовок 3"
        ));
        noteEntities.add(new NotesEntity(
                "3",
                "Заголовок 4",
                "id = 3 ; Title = Заголовок 4"
        ));
        noteEntities.add(new NotesEntity(
                "4",
                "Заголовок 5",
                "id = 4 ; Title = Заголовок 5"
        ));
        noteEntities.add(new NotesEntity(
                "5",
                "Заголовок 6",
                "id = 5 ; Title = Заголовок 6"
        ));
        return noteEntities;
    }



    @Override
    public List<NotesEntity> getNotes() {
        return new ArrayList<>(cache);
    }

    @Override
    public void deleteEmployee(NotesEntity noteEntity) {
        try {
            cache.remove(findPosition(noteEntity));
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
        }
    }
    private int findPosition(NotesEntity noteEntity) {
        for (int i = 0; i < cache.size(); i++) {
            if (noteEntity.getId().equals(cache.get(i).getId())) {
                return i;
            }
        }
        throw new IllegalArgumentException("Нет такого элемента");
    }
}
