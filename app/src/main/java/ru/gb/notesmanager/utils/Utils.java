package ru.gb.notesmanager.utils;

import java.util.ArrayList;
import java.util.List;

import ru.gb.notesmanager.domain.NoteEntity;

public class Utils {

    public static int findPosition(NoteEntity noteEntity, List<NoteEntity> data) {
        for (int i = 0; i < data.size(); i++) {
            if (noteEntity.getId().equals(data.get(i).getId())) {
                return i;
            }
        }
        throw new IllegalArgumentException("Нет такого элемента");
    }

}
