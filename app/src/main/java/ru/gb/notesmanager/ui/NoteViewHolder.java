package ru.gb.notesmanager.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.gb.notesmanager.domain.NotesEntity;
import ru.gb.notesmanager.R;

public class NoteViewHolder extends RecyclerView.ViewHolder{
    private final TextView titleTextView = itemView.findViewById(R.id.title_text_view);
    private final TextView dataTextView = itemView.findViewById(R.id.data_text_view);
    //private final Button deleteButton = itemView.findViewById(R.id.delete_button);
    private final ImageView deleteButton = itemView.findViewById(R.id.delete_image_view);
    private final ImageView updateButton = itemView.findViewById(R.id.update_image_view);
    private OnNoteListener onNoteListener;

    public NoteViewHolder(@NonNull LayoutInflater inflater,
                          @NonNull ViewGroup parent,
                          OnNoteListener onNoteListener) {
        super(inflater.inflate(R.layout.item_note, parent, false));
        this.onNoteListener = onNoteListener;
    }

    public void bind(NotesEntity note) {
        deleteButton.setOnClickListener(v -> onNoteListener.onDeleteEmployee(note));
        itemView.setOnClickListener(v -> onNoteListener.onClickEmployee(note));

        titleTextView.setText(note.getTitle());
        dataTextView.setText(note.getDate());

    }

}
