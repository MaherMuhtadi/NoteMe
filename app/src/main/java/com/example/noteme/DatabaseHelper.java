package com.example.noteme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper instance;
    private static final String DB_NAME = "NoteMe_DB";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "notes";
    private static final String ID_COL = "id";
    private static final String TITLE_COL = "title";
    private static final String SUBTITLE_COL = "subtitle";
    private static final String NOTE_COL = "note";
    private static final String COLOR_COL = "color";

    private DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
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

    public ArrayList<Note> searchNotes(String selection) {
        ArrayList<Note> notesArray = new ArrayList<>();
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            Cursor result = db.rawQuery("SELECT " + selection + " FROM " + TABLE_NAME, null);
            if (result.getCount() > 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(0);
                    String title = result.getString(1);
                    String subtitle = result.getString(2);
                    String description = result.getString(3);
                    String color = result.getString(4);
                    Note note = new Note(id, title, subtitle, description, color);
                    notesArray.add(note);
                }
            }
            result.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notesArray;
    }

    public boolean insertNote(String title, String subtitle, String note, String color) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(TITLE_COL, title);
            contentValues.put(SUBTITLE_COL, subtitle);
            contentValues.put(NOTE_COL, note);
            contentValues.put(COLOR_COL, color);
            long result = db.insert(TABLE_NAME, null, contentValues);
            return result != -1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
