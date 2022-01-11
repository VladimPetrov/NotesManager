package ru.gb.notesmanager.domain;

import java.util.List;

public interface NoteRepository {
    List<NotesEntity> getNotes();

    void deleteNote(NotesEntity noteEntity);
    void updateNote(NotesEntity noteEntity);
    void addNote(NotesEntity noteEntity);
    int getSize();
}
