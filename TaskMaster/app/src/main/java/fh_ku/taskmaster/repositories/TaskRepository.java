package fh_ku.taskmaster.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.Calendar;

import fh_ku.taskmaster.models.Task;

public class TaskRepository {

    public static final String TABLE_TASK = "task";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PRIORITY = "priority";
    public static final String COLUMN_CLOSED = "closed";
    public static final String COLUMN_DUE_DATE = "due_date";
    public static final String COLUMN_CREATED = "created";

    private DatabaseHelper dbHelper;

    public TaskRepository(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void close() {
        this.dbHelper.close();
    }

    public Task taskAtCursorPosition(Cursor cursor, int position) {
        if (cursor != null && cursor.moveToPosition(position)) {
            Log.i("TASK REPOSITORY","Task found at position: " + position);
            return new Task()
                .setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)))
                .setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)))
                .setPriority(cursor.getInt(cursor.getColumnIndex(COLUMN_PRIORITY)))
                .setDueDate(Task.TimestampToDate(cursor.getLong(cursor.getColumnIndex(COLUMN_DUE_DATE))))
                .setCreated(Task.TimestampToDate(cursor.getLong(cursor.getColumnIndex(COLUMN_CREATED))))
                .setClosed(cursor.getInt(cursor.getColumnIndex(COLUMN_CLOSED)));
        }

        Log.i("TASK REPOSITORY","No Task at position " + position + " found.");
        return null;
    }

    public Cursor queryAllTasks() {
        return this.dbHelper.getReadableDatabase().query(TABLE_TASK,null,null,null,null,null,null);
    }

    public Task getTask(int id) {
        Cursor cursor = dbHelper.getReadableDatabase().query(TABLE_TASK,null,COLUMN_ID+"=?",new String[]{String.valueOf(id)},null,null,null);
        return this.taskAtCursorPosition(cursor, 0);
    }

    public ContentValues taskToContentValues(Task task) {
        ContentValues cvs = new ContentValues();
        cvs.put(COLUMN_NAME, task.getName());
        cvs.put(COLUMN_PRIORITY,task.getPriority());
        cvs.put(COLUMN_DUE_DATE,task.getDueDate().getTime());
        cvs.put(COLUMN_CREATED,task.getCreated() == null ? Calendar.getInstance().getTimeInMillis() : task.getCreated().getTime());
        cvs.put(COLUMN_CLOSED,task.isClosed() ? 1 : 0);
        return cvs;
    }

    public Task addTask(Task task) {
        task.setId((int) dbHelper.getWritableDatabase().insert(TABLE_TASK,null,taskToContentValues(task)));
        return task;
    }

    public Task updateTask(Task task) {
        dbHelper.getWritableDatabase().update(TABLE_TASK,taskToContentValues(task),COLUMN_ID+"=?",new String[]{String.valueOf(task.getId())});
        return task;
    }

}
