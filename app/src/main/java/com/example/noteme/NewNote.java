package com.example.noteme;

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

public class NewNote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_note);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.new_note_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(getString(R.string.new_note_activity_title));

        new NoteColor(this,
                findViewById(R.id.note_container),
                findViewById(R.id.yellow_square),
                findViewById(R.id.pink_square),
                findViewById(R.id.green_square)
        );
    }

    public void saveNote(View v) {
        DatabaseHelper db = new DatabaseHelper(this);
        String title = ((EditText)findViewById(R.id.input_title)).getText().toString().trim();
        String subtitle = ((EditText)findViewById(R.id.input_subtitle)).getText().toString().trim();
        String note = ((EditText)findViewById(R.id.input_description)).getText().toString().trim();
        String color = NoteColor.color;
        if (title.isEmpty()) {
            Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
        }
        else{
            db.insertNote(title, subtitle, note, color);
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Handle the back arrow click event, e.g., finish the activity
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}