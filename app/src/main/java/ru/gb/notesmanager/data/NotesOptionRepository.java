package ru.gb.notesmanager.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import ru.gb.notesmanager.domain.NotesOption;

public class NotesOptionRepository {
    private static final String SHARED_PREFS_NAME = "NotesManagerOptions";
    private static final String NOTES_MANAGER_COUNT_START_KEY = "NOTES_MANAGER_COUNT_START_KEY";
    private static final String NOTES_MANAGER_COUNT_NOTES_KEY = "NOTES_MANAGER_COUNT_NOTES_KEY";
    private NotesOption parameter;
    private SharedPreferences sharedPreferences;

    public NotesOptionRepository(int countStarts, int countNotes) {
        this.parameter = new NotesOption(countStarts, countNotes);
    }

    public NotesOptionRepository(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        final String countStarts = sharedPreferences.getString(NOTES_MANAGER_COUNT_START_KEY, "");
        final String countNotes = sharedPreferences.getString(NOTES_MANAGER_COUNT_NOTES_KEY, "");
        if ((!TextUtils.isEmpty(countNotes)) && (!TextUtils.isEmpty(countStarts))) {
            this.parameter = new NotesOption((Integer.parseInt(countStarts)) + 1, Integer.parseInt(countNotes));
        } else {
            this.parameter = new NotesOption(1, 0);
        }

    }

    private void saveParameter() {
        sharedPreferences
                .edit()
                .putString(NOTES_MANAGER_COUNT_START_KEY, String.valueOf(parameter.getCountStarts()))
                .putString(NOTES_MANAGER_COUNT_NOTES_KEY, String.valueOf(parameter.getCountNotes()))
                .apply();
    }

    public NotesOption getParameter() {
        return new NotesOption(parameter.getCountStarts(), parameter.getCountNotes());
    }

    public void setParameter(int countNotes) {
        this.parameter.setCountNotes(countNotes);
        saveParameter();
    }

}
