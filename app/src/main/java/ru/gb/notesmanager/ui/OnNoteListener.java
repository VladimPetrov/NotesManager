package ru.gb.notesmanager.ui;

import ru.gb.notesmanager.domain.NotesEntity;

public interface OnNoteListener {
    void onDeleteEmployee(NotesEntity employeeEntity);
    void onClickEmployee(NotesEntity employeeEntity);
}
