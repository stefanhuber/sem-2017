package fhku.noteapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected LinearLayout formNoteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        formNoteList = findViewById(R.id.form_note_list);

        loadList();
    }

    public void loadList() {
        List<Note> notes = NoteRepository.getNoteRepository(this).getNotes();

        for (Note note : notes) {
            Button button = new Button(this);
            button.setText(note.getTitle());
            button.setTag(note.getId());
            button.setOnClickListener(this);

            formNoteList.addView(button);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_item_new) {
            startActivity(new Intent(this, NoteActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        int id = (int) view.getTag();

        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra("note", id);
        startActivity(intent);
    }
}
