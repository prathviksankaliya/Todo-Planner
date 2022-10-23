package com.itcraftsolution.todoplanner.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.itcraftsolution.todoplanner.Database.Notes;
import com.itcraftsolution.todoplanner.R;
import com.itcraftsolution.todoplanner.databinding.RvSampleNotesBinding;

import java.util.List;

public class RvAllNotesAdapter extends RecyclerView.Adapter<RvAllNotesAdapter.viewHolder> {

    Context context;
    List<Notes> list;

    public RvAllNotesAdapter(Context context, List<Notes> list) {
        this.context = context;
        this.list = list;
    }

    public void updateNotesList(List<Notes> notes)
    {
            list = notes;
            notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RvAllNotesAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_sample_notes, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvAllNotesAdapter.viewHolder holder, int position) {

        Notes notes = list.get(position);
        holder.binding.txNotes.setText(notes.getNotes());
        holder.binding.txTitle.setText(notes.getNotesTitle());
        holder.binding.txDate.setText(notes.getNotesDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder
    {
        RvSampleNotesBinding binding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RvSampleNotesBinding.bind(itemView);
        }
    }
}
