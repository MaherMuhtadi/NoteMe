package com.example.noteme;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "NoteMe_DB";
    private static final String TABLE_NAME = "notes";
    private static final String ID_COL = "id";
    private static final String TITLE_COL = "title";
    private static final String SUBTITLE_COL = "subtitle";
    private static final String NOTE_COL = "note";
    private static final String COLOR_COL = "color";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + "(" +
                ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                TITLE_COL + " TEXT, " +
                SUBTITLE_COL + " TEXT, " +
                NOTE_COL + " TEXT, " +
                COLOR_COL + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Boolean insertNote(String title, String subtitle, String note, String color) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE_COL, title);
        contentValues.put(SUBTITLE_COL, subtitle);
        contentValues.put(NOTE_COL, note);
        contentValues.put(COLOR_COL, color);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }
}
