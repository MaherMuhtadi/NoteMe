package com.example.noteme;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    TextView title;
    TextView subtitle;
    TextView description;
    ImageView deleteButton;
    RelativeLayout container;

    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.note_title);
        subtitle = itemView.findViewById(R.id.note_subtitle);
        description = itemView.findViewById(R.id.note_description);
        deleteButton = itemView.findViewById(R.id.delete_button);
        container = itemView.findViewById(R.id.note_container);
    }
}
