package ru.gb.notesmanager.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class NoteEntity implements Parcelable {
    private String id;
    private String title;
    private String textNote;
    private String date;
    private final String formatDate = "dd MMM yyyy HH:mm:ss";

    public NoteEntity(String title, String textNote) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.textNote = textNote;
        setDate();
    }

    protected NoteEntity(Parcel in) {
        id = in.readString();
        title = in.readString();
        textNote = in.readString();
        date = in.readString();

    }

    public static final Creator<NoteEntity> CREATOR = new Creator<NoteEntity>() {
        @Override
        public NoteEntity createFromParcel(Parcel in) {
            return new NoteEntity(in);
        }

        @Override
        public NoteEntity[] newArray(int size) {
            return new NoteEntity[size];
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

    public void setDate() { this.date = new SimpleDateFormat(formatDate).format(new Date()); }

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
