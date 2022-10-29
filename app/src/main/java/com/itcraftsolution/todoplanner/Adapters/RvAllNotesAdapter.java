package com.itcraftsolution.todoplanner.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.itcraftsolution.todoplanner.Activities.AddNotesActivity;
import com.itcraftsolution.todoplanner.ViewModel.NotesViewModel;
import com.itcraftsolution.todoplanner.databinding.RvSampleNotesBinding;
import com.itcraftsolution.todoplanner.model.Notes;
import com.itcraftsolution.todoplanner.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RvAllNotesAdapter extends RecyclerView.Adapter<RvAllNotesAdapter.viewHolder> {

    Context context;
    List<Notes> list,allNotes;
    NotesViewModel notesViewModel;
    Timer timer;

    public RvAllNotesAdapter(Context context, List<Notes> list) {
        this.context = context;
        this.list = list;
        allNotes = new ArrayList<>(list);
        notesViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(NotesViewModel.class);

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
        holder.binding.cardviewLayout.setCardBackgroundColor(Color.parseColor(notes.getColor()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddNotesActivity.class);
                intent.putExtra("update", true);
                intent.putExtra("notes", notes.getNotes());
                intent.putExtra("title", notes.getNotesTitle());
                intent.putExtra("color", notes.getColor());
                intent.putExtra("id", notes.getId());
                intent.putExtra("pin", notes.isPin());
                context.startActivity(intent);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                BottomSheetDialog menuBottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetStyle);
                View menuSheetView = LayoutInflater.from(context).inflate(R.layout.note_option, view.findViewById(R.id.bottomSheetMenu));
                menuBottomSheetDialog.setContentView(menuSheetView);
                menuBottomSheetDialog.show();
                ImageView igEdit, igDelete, igFav, igShare;

                igEdit = menuSheetView.findViewById(R.id.btnEdit);
                igDelete = menuSheetView.findViewById(R.id.btnMenuDelete);
                igFav = menuSheetView.findViewById(R.id.btnFav);
                igShare = menuSheetView.findViewById(R.id.btnShare);

                boolean getFav = notesViewModel.getFavNotes(notes.getId());
                if(getFav)
                {
                    igFav.setImageResource(R.drawable.fillstar64);
                }

                igEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, AddNotesActivity.class);
                        intent.putExtra("update", true);
                        intent.putExtra("notes", notes.getNotes());
                        intent.putExtra("title", notes.getNotesTitle());
                        intent.putExtra("color", notes.getColor());
                        intent.putExtra("id", notes.getId());
                        context.startActivity(intent);
                        menuBottomSheetDialog.dismiss();
                    }
                });

                igDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetStyle);
                        View sheetView = LayoutInflater.from(context).inflate(R.layout.delete_box, view.findViewById(R.id.bottomsheet));
                        bottomSheetDialog.setContentView(sheetView);
                        bottomSheetDialog.show();
                        Button canclebtn, deletebtn;
                        canclebtn = sheetView.findViewById(R.id.btnCancle);
                        deletebtn = sheetView.findViewById(R.id.btnDelete);

                        deletebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                NotesViewModel notesViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(NotesViewModel.class);
                                notesViewModel.deleteNotes(notes.getId());
                                list.remove(holder.getAdapterPosition());
                                notifyDataSetChanged();
                                bottomSheetDialog.dismiss();
                            Snackbar.make(holder.binding.cardviewLayout, "Deleted Successfully!!", Snackbar.LENGTH_SHORT)
                                        .setBackgroundTint(context.getResources().getColor(R.color.red))
                                        .setTextColor(context.getResources().getColor(R.color.white))
                                        .show();
                            }
                        });
                        canclebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                bottomSheetDialog.dismiss();
                            }
                        });
                        menuBottomSheetDialog.dismiss();
                    }
                });

                igFav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(getFav)
                        {
                            igFav.setImageResource(R.drawable.emptystar64);
                            notesViewModel.favNotes(notes.getId(), false);
                            Snackbar.make(holder.binding.cardviewLayout, "Removed From Favourite", Snackbar.LENGTH_SHORT)
                                    .setBackgroundTint(context.getResources().getColor(R.color.red))
                                    .setTextColor(context.getResources().getColor(R.color.white))
                                    .show();
                        }else{
                            notesViewModel.favNotes(notes.getId(), true);
                            Snackbar.make(holder.binding.cardviewLayout, "Added into Favourite", Snackbar.LENGTH_SHORT)
                                    .setBackgroundTint(context.getResources().getColor(R.color.noteColor8))
                                    .setTextColor(context.getResources().getColor(R.color.white))
                                    .show();
                        }

                        menuBottomSheetDialog.dismiss();
                    }
                });

                igShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, notes.getNotesTitle());
                    intent.putExtra(Intent.EXTRA_TEXT, notes.getNotes());
                    context.startActivity(Intent.createChooser(intent, "Share Notes!!"));
                    menuBottomSheetDialog.dismiss();
                    }
                });

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
