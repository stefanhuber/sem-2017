package fhku.noteapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class NoteActivity extends AppCompatActivity {

    protected Note note;
    protected EditText formTitle;
    protected EditText formNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        formTitle = findViewById(R.id.form_title);
        formNote = findViewById(R.id.form_note);

        int id = getIntent().getIntExtra("note", -1);
        if (id > 0) {
            note = NoteRepository.getNoteRepository(this).getNote(id);
            formTitle.setText(note.getTitle());
            formNote.setText(note.getNote());
        } else {
            note = new Note();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_item_save) {
            save();
            startActivity(new Intent(this, MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public void save() {
        note.setTitle(formTitle.getText().toString());
        note.setNote(formNote.getText().toString());

        if (note.getId() > 0) {
            // editNote(note);
        } else {
            NoteRepository.getNoteRepository(this).addNote(note);
        }
    }

}
