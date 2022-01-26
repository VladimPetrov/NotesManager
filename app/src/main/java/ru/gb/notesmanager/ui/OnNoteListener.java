package ru.gb.notesmanager.ui;

import android.view.MenuItem;

import ru.gb.notesmanager.domain.NoteEntity;

public interface OnNoteListener {
    void onDeleteEmployee(NoteEntity employeeEntity);
    void onClickEmployee(NoteEntity employeeEntity);
    void onUpdateEmployee(NoteEntity employeeEntity);
    boolean onClickPopupMenu(NoteEntity employeeEntity, MenuItem item);
}
