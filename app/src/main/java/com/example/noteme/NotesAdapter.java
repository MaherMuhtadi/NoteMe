package com.example.noteme;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private Context context;
    private ArrayList<Note> notesArray;

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
    }

    @Override
    public int getItemCount() {
        return notesArray.size();
    }
}
