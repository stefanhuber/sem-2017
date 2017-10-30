package fh_ku.taskmaster.repositories;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static int DB_VERSION = 1;
    public static String DB_NAME = "task_master";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTaskTable = "create table " + TaskRepository.TABLE_TASK + " (" +
                TaskRepository.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskRepository.COLUMN_NAME + " TEXT, " +
                TaskRepository.COLUMN_PRIORITY + " INTEGER, " +
                TaskRepository.COLUMN_DUE_DATE + " INTEGER, " +
                TaskRepository.COLUMN_CREATED + " INTEGER," +
                TaskRepository.COLUMN_CLOSED + " INTEGER" +
                ")";
        db.execSQL(createTaskTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}