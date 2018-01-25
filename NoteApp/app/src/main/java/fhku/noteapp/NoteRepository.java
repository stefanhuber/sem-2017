package fhku.noteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class NoteRepository {

    protected DBHelper dbHelper;

    public ContentValues toContentValues(Note note) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("title", note.getTitle());
        contentValues.put("note", note.getNote());
        contentValues.put("last_edit", note.getLastEdited());

        return contentValues;
    }

    public Note toNote(Cursor cursor) {
        Note note = new Note();
        note.setTitle(cursor.getString(cursor.getColumnIndex("title")));
        note.setNote(cursor.getString(cursor.getColumnIndex("note")));
        note.setId(cursor.getInt(cursor.getColumnIndex("id")));
        note.setLastEdited(cursor.getInt(cursor.getColumnIndex("last_edit")));
        return note;
    }

    public Note getNote(int id) {
        String selection = "id = ?";
        String[] selectionArgs = {id + ""};

        Cursor cursor = dbHelper.getReadableDatabase().query("note", null, selection, selectionArgs, null, null, "");
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            return toNote(cursor);
        } else {
            return null;
        }
    }

    public List<Note> getNotes() {
        List<Note> notes = new ArrayList<>();

        Cursor cursor = dbHelper.getReadableDatabase().query("note", null, null, null, null, null, "last_edit ASC");

        while (cursor.moveToNext()) {
            Note note = toNote(cursor);
            notes.add(note);
        }

        return notes;
    }

    public void addNote(Note note) {
        note.setLastEditedToNow();
        ContentValues contentValues = toContentValues(note);
        dbHelper.getWritableDatabase().insert("note", null, contentValues);
    }

    public static NoteRepository getNoteRepository(Context context) {
        NoteRepository noteRepository = new NoteRepository();
        noteRepository.dbHelper = new DBHelper(context);

        return noteRepository;
    }

}
