package ru.gb.notesmanager.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class NotesOption implements Parcelable {
    private int countStarts;
    private int countNotes;

    public NotesOption(int countStarts, int countNotes) {
        this.countStarts = countStarts;
        this.countNotes = countNotes;
    }

    public NotesOption(Parcel in) {
        this.countStarts = in.readInt();
        this.countNotes = in.readInt();
    }


    public static final Creator<NotesOption> CREATOR = new Creator<NotesOption>() {
        @Override
        public NotesOption createFromParcel(Parcel in) {
            return new NotesOption(in);
        }

        @Override
        public NotesOption[] newArray(int size) {
            return new NotesOption[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(countStarts);
        parcel.writeInt(countNotes);
    }

    public int getCountStarts() {
        return countStarts;
    }

    public int getCountNotes() {
        return countNotes;
    }

    public void setCountNotes(int countNotes) {
        this.countNotes = countNotes;
    }

}
