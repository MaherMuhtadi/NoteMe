package com.example.noteme;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private final Context context;
    private final ArrayList<Note> notesArray;

    public NotesAdapter(Context context, ArrayList<Note> notesArray) {
        this.context = context;
        this.notesArray = notesArray;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.note_layout, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notesArray.get(position);
        holder.title.setText(note.getTitle());
        holder.subtitle.setText(note.getSubtitle());
        holder.description.setText(note.getDescription());
        holder.container.setBackgroundColor(Color.parseColor(note.getColor()));
        holder.deleteButton.setOnClickListener(view -> delete(note, holder));
    }

    private void delete(Note note, NoteViewHolder holder) {
            new AlertDialog.Builder(context)
                    .setTitle("Confirm Deletion")
                    .setMessage("Are you sure you want to delete this note?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        DatabaseHelper db = DatabaseHelper.getInstance(context);
                        if (db.delete(note.getId())) {
                            notesArray.remove(note);
                            notifyItemRemoved(holder.getAdapterPosition());
                            Toast.makeText(context, "Note deleted successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Failed to delete the note.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .show();
    }

    @Override
    public int getItemCount() {
        return notesArray.size();
    }

    public void insert(Note n) {
        notesArray.add(n);
        notifyItemInserted(notesArray.size()-1);
    }
}
