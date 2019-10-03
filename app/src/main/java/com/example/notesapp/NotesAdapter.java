package com.example.notesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.notesapp.DataBase.Model.Note;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.viewHolder> {
    List <Note> notes;
    public NotesAdapter(List<Note> notes) {
        this.notes = notes;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Note note = notes.get(position);
        holder.title.setText(note.getTitle());
        holder.time.setText(note.getDateTime());
    }

    @Override
    public int getItemCount() {
        if(notes ==  null )return 0;
        return notes.size();
    }
    public void updateData(List<Note>notes){
        this.notes =notes;
        notifyDataSetChanged();
    }

    public Note getNote(int adapterPosition) {

        return notes.get(adapterPosition);
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView time;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            title =  itemView.findViewById(R.id.title);
            time =  itemView.findViewById(R.id.time);

        }
    }
}