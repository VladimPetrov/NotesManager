package ru.gb.notesmanager.data;

import ru.gb.notesmanager.domain.NotesOption;

public class NotesOptionRepository {
    private NotesOption parameter;

    public NotesOptionRepository(int countStarts, int countNotes) {
        this.parameter = new NotesOption(countStarts, countNotes);
    }

    public NotesOption getParameter() {
        return new NotesOption(parameter.getCountStarts(),parameter.getCountNotes());
    }

}
