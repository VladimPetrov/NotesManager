package ru.gb.notesmanager.domain;

import java.util.List;

public interface NoteRepository {
    List<NotesEntity> getNotes();

    void deleteEmployee(NotesEntity noteEntity);
}
