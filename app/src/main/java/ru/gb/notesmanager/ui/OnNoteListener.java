package ru.gb.notesmanager.ui;

import ru.gb.notesmanager.domain.NoteEntity;

public interface OnNoteListener {
    void onDeleteEmployee(NoteEntity employeeEntity);
    void onClickEmployee(NoteEntity employeeEntity);
    void onUpdateEmployee(NoteEntity employeeEntity);
}
