package ru.gb.notesmanager.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import ru.gb.notesmanager.domain.NotesEntity;
import ru.gb.notesmanager.R;

public class MainActivity extends AppCompatActivity implements OnNoteListener {

    private RecyclerView recyclerView;
    private NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecycler();
    }

    private void initRecycler() {
        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new NoteAdapter();
        //adapter.setData(employeeRepository.getEmployees());
        //adapter.setOnDeleteClickListener(this);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDeleteEmployee(NotesEntity employeeEntity) {

    }

    @Override
    public void onClickEmployee(NotesEntity employeeEntity) {

    }
}