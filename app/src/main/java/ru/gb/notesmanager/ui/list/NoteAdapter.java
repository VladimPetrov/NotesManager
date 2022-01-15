package ru.gb.notesmanager.ui.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.gb.notesmanager.domain.NotesEntity;
import ru.gb.notesmanager.ui.OnNoteListener;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private List<NotesEntity> noteList = new ArrayList<>();
    private OnNoteListener onNoteListener;



    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new NoteViewHolder(inflater, parent, onNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    private NotesEntity getItem(int position) {
        return noteList.get(position);
    }

    public void setOnNoteListener(OnNoteListener onNoteListener) {
        this.onNoteListener = onNoteListener;
    }

    public void setNoteList(List<NotesEntity> noteList) {
        this.noteList = noteList;
        notifyDataSetChanged();
    }

}