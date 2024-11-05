package com.example.noteme;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class NoteEditor extends AppCompatActivity {

    private boolean isNew;
    private Note oldNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_note_editor);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.new_note_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        isNew = getIntent().getBooleanExtra("is_new", true);
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        oldNote = (Note) getIntent().getSerializableExtra("note");

        if (!isNew) {
            toolbarTitle.setText(R.string.edit_note_activity_title);
            if (oldNote != null) {
                ((EditText)findViewById(R.id.input_title)).setText(oldNote.getTitle());
                ((EditText)findViewById(R.id.input_subtitle)).setText(oldNote.getSubtitle());
                ((EditText)findViewById(R.id.input_description)).setText(oldNote.getDescription());
                new NoteColorChanger(this,
                        findViewById(R.id.new_note_container),
                        findViewById(R.id.yellow_square),
                        findViewById(R.id.pink_square),
                        findViewById(R.id.green_square),
                        oldNote.getColor()
                );
            }
        } else {
            toolbarTitle.setText(getString(R.string.new_note_activity_title));
            new NoteColorChanger(this,
                    findViewById(R.id.new_note_container),
                    findViewById(R.id.yellow_square),
                    findViewById(R.id.pink_square),
                    findViewById(R.id.green_square)
            );
        }
    }

    public void saveNote(View v) {
        try (DatabaseHelper db = DatabaseHelper.getInstance(this)) {
            String title = ((EditText)findViewById(R.id.input_title)).getText().toString().trim();
            String subtitle = ((EditText)findViewById(R.id.input_subtitle)).getText().toString().trim();
            String note = ((EditText)findViewById(R.id.input_description)).getText().toString().trim();
            String color = NoteColorChanger.color;

            if (title.isEmpty()) {
                Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isNew) {
                if (db.insertNote(title, subtitle, note, color)) {
                    int id = db.getLastId();
                    Note newNote = new Note(id, title, subtitle, note, color);
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("new_note", newNote);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    Toast.makeText(this, "Could not save the note", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (oldNote != null) {
                    if (db.updateNote(oldNote.getId(), title, subtitle, note, color)) {
                        oldNote.setTitle(title);
                        oldNote.setSubtitle(subtitle);
                        oldNote.setDescription(note);
                        oldNote.setColor(color);
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("updated_note", oldNote);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    } else {
                        Toast.makeText(this, "Could not update the note", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Database error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}