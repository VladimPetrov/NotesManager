package ru.gb.notesmanager.domain;

import java.util.List;

public interface NoteRepository {
    List<NoteEntity> getNotes();

    void deleteNote(NoteEntity noteEntity);
    void updateNote(NoteEntity noteEntity);
    void addNote(NoteEntity noteEntity);
    int getSize();
}
