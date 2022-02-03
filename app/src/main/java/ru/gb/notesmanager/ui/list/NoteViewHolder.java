package ru.gb.notesmanager.ui.list;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import ru.gb.notesmanager.domain.NoteEntity;
import ru.gb.notesmanager.R;
import ru.gb.notesmanager.ui.OnNoteListener;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    private final TextView titleTextView = itemView.findViewById(R.id.title_text_view);
    private final TextView dataTextView = itemView.findViewById(R.id.data_text_view);
    private final ImageView deleteButton = itemView.findViewById(R.id.delete_image_view);
    private final ImageView updateButton = itemView.findViewById(R.id.update_image_view);
    private final LinearLayout mainLinearLayout = itemView.findViewById(R.id.main_linearlayout);
    private OnNoteListener onNoteListener;


    public NoteViewHolder(@NonNull LayoutInflater inflater,
                          @NonNull ViewGroup parent,
                          OnNoteListener onNoteListener) {
        super(inflater.inflate(R.layout.item_note, parent, false));
        this.onNoteListener = onNoteListener;
    }


    public void bind(NoteEntity note) {
        deleteButton.setOnClickListener(v -> onNoteListener.onDeleteEmployee(note));
        itemView.setOnClickListener(v -> onNoteListener.onClickEmployee(note));
        updateButton.setOnClickListener(v -> onNoteListener.onUpdateEmployee(note));
        mainLinearLayout.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(mainLinearLayout.getContext(), v);
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    return onNoteListener.onClickPopupMenu(note, item);
                }
            });
            popup.inflate(R.menu.notes_list_popup);
            popup.show();
        });
        titleTextView.setText(note.getTitle());
        dataTextView.setText(note.getDate());
    }

}
