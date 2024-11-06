package com.example.noteme;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private final Context context;
    private final ArrayList<Note> notesArray;
    private final TextView noNotesMessage;

    public NotesAdapter(Context context, ArrayList<Note> notesArray, TextView noNotesMessage) {
        this.context = context;
        this.notesArray = notesArray;
        this.noNotesMessage = noNotesMessage;
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
        holder.container.setOnClickListener(view -> launchNoteEditorActivity(note));
        toggleNoNotesMessage();
    }

    private void launchNoteEditorActivity(Note note) {
        Intent intent = new Intent(context, NoteEditor.class);
        intent.putExtra("is_new", false);
        intent.putExtra("note", note);
        ((MainActivity) context).getNoteEditorLauncher().launch(intent);
    }

    private void delete(Note note, NoteViewHolder holder) {
            new AlertDialog.Builder(context)
                    .setTitle("Confirm Deletion")
                    .setMessage("Are you sure you want to delete this note?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        try (DatabaseHelper db = DatabaseHelper.getInstance(context)) {
                            if (db.delete(note.getId())) {
                                notesArray.remove(note);
                                toggleNoNotesMessage();
                                notifyItemRemoved(holder.getAdapterPosition());
                                Toast.makeText(context, "Note deleted successfully!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Failed to delete the note.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(context, "Database error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void update(Note n) {
        for (int i = 0; i < notesArray.size(); i++) {
            if (notesArray.get(i).getId() == n.getId()) {
                notesArray.set(i, n);
                notifyItemChanged(i);
                break;
            }
        }
    }


    public void filter(String query) {
        ArrayList<Note> filteredList = new ArrayList<>();
        try (DatabaseHelper db = DatabaseHelper.getInstance(context)) {
            if (query.isEmpty()) {
                filteredList.addAll(db.getNotes());
            } else {
                for (Note note : db.getNotes()) {
                    if (note.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                            note.getSubtitle().toLowerCase().contains(query.toLowerCase()) ||
                            note.getDescription().toLowerCase().contains(query.toLowerCase())) {
                        filteredList.add(note);
                    }
                }
            }
        } catch (Exception e) {
            Toast.makeText(context, "Database error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        notesArray.clear();
        notesArray.addAll(filteredList);
        toggleNoNotesMessage();
        notifyDataSetChanged();
    }

    public void toggleNoNotesMessage() {
        if (notesArray.isEmpty()) {
            noNotesMessage.setVisibility(View.VISIBLE);
        } else {
            noNotesMessage.setVisibility(View.INVISIBLE);
        }
    }
}
