package com.itcraftsolution.todoplanner.Adapters;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.itcraftsolution.todoplanner.AddNotesActivity;
import com.itcraftsolution.todoplanner.Database.Notes;
import com.itcraftsolution.todoplanner.R;
import com.itcraftsolution.todoplanner.ViewModel.NotesViewModel;
import com.itcraftsolution.todoplanner.databinding.RvSampleNotesBinding;

import java.util.ArrayList;
import java.util.List;

public class RvAllNotesAdapter extends RecyclerView.Adapter<RvAllNotesAdapter.viewHolder> {

    Context context;
    List<Notes> list,allNotes;

    public RvAllNotesAdapter(Context context, List<Notes> list) {
        this.context = context;
        this.list = list;
        allNotes = new ArrayList<>(list);
    }

    public void searchNotes(List<Notes> filterNotes)
    {
        this.list = filterNotes;
        notifyDataSetChanged();
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddNotesActivity.class);
                intent.putExtra("update", true);
                intent.putExtra("notes", notes.getNotes());
                intent.putExtra("title", notes.getNotesTitle());
                intent.putExtra("id", notes.getId());
                context.startActivity(intent);

            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
//                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetStyle);
//                View sheetView = LayoutInflater.from(context).inflate(R.layout.custom_delete_box, view.findViewById(R.id.bottomsheet));
//                bottomSheetDialog.setContentView(sheetView);
//                bottomSheetDialog.show();
//                Button canclebtn, deletebtn;
//                canclebtn = sheetView.findViewById(R.id.btnCancle);
//                deletebtn = sheetView.findViewById(R.id.btnDelete);
//
//                deletebtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        NotesViewModel notesViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(NotesViewModel.class);
//                        notesViewModel.deleteNotes(notes.getId());
//                        list.remove(holder.getAdapterPosition());
//                        notifyDataSetChanged();
//                        bottomSheetDialog.dismiss();
//                        Snackbar.make(holder.binding.cardviewLayout, "Note Deleted Successfully!!", Snackbar.LENGTH_SHORT)
//                                .setBackgroundTint(context.getResources().getColor(R.color.red))
//                                .setTextColor(context.getResources().getColor(R.color.white))
//                                .show();
//                    }
//                });
//                canclebtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        bottomSheetDialog.dismiss();
//                    }
//                });

                return true;
            }
        });
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
