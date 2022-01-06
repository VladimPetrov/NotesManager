package ru.gb.notesmanager.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotesEntity implements Parcelable {
    private String id;
    private String title;
    private String textNote;
    private String date;
    private final String formatDate = "dd MMM yyyy HH:mm:ss";

    public NotesEntity(String id, String title, String textNote) {
        this.id = id;
        this.title = title;
        this.textNote = textNote;
        setDate(new Date());
    }

    protected NotesEntity(Parcel in) {
        id = in.readString()
        title = in.readString();
        textNote = in.readString();
        date = in.readString();

    }

    public static final Creator<NotesEntity> CREATOR = new Creator<NotesEntity>() {
        @Override
        public NotesEntity createFromParcel(Parcel in) {
            return new NotesEntity(in);
        }

        @Override
        public NotesEntity[] newArray(int size) {
            return new NotesEntity[size];
        }
    };

    public String getId() { return id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTextNote() {
        return textNote;
    }

    public void setTextNote(String textNote) {
        this.textNote = textNote;
    }

    public String getDate() {
        return date;
    }

    private void setDate(Date date) {
        this.date = new SimpleDateFormat(formatDate).format(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(textNote);
        parcel.writeString(date);
    }
}
