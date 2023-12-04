package com.devbyheart.whitepad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.devbyheart.whitepad.NoteItemListener;
import com.devbyheart.whitepad.R;
import com.devbyheart.whitepad.models.Notes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class NotesAdapter extends RecyclerView.Adapter<NotesViewHolder> {

    Context context;
    List<Notes> list;
    NoteItemListener listener;

    public NotesAdapter(Context context, List<Notes> list, NoteItemListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.notes_list
                , parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.note_title.setText(list.get(position).getTitle());
        holder.note_title.setSelected(true);

        holder.note_data.setText(list.get(position).getNotes());
        holder.note_date.setText(list.get(position).getDate());

        holder.note_date.setSelected(true);

        if (list.get(position).isPinned()) {
            holder.note_pin.setImageResource(R.drawable.star_info);
        } else {
            holder.note_pin.setImageResource(0);
        }
        int colorcode = getRandomColor();
        holder.notes_container.setCardBackgroundColor(holder.itemView.getResources().
                getColor(colorcode, null));

        holder.notes_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onclick(list.get(holder.getAdapterPosition()));
            }
        });
        holder.notes_container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onLongClick(list.get(holder.getAdapterPosition()),
                        holder.notes_container);
                return true;
            }
        });
    }

    private int getRandomColor() {
        List<Integer> colorcode = new ArrayList<>();

        colorcode.add(R.color.note_col1);
        colorcode.add(R.color.note_col2);
        colorcode.add(R.color.note_col3);
        colorcode.add(R.color.note_col4);
        colorcode.add(R.color.note_col5);
        colorcode.add(R.color.note_col6);

        Random random = new Random();
        int color_combo = random.nextInt(colorcode.size());
        return colorcode.get(color_combo);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterlist(List<Notes> filteritem) {
        list = filteritem;
        notifyDataSetChanged();
    }
}


class NotesViewHolder extends RecyclerView.ViewHolder {
    CardView notes_container;
    TextView note_title, note_data, note_date;
    ImageView note_pin;

    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);

        notes_container = itemView.findViewById(R.id.notes_container);
        note_title = itemView.findViewById(R.id.note_title);
        note_data = itemView.findViewById(R.id.note_data);
        note_date = itemView.findViewById(R.id.note_date);
        note_pin = itemView.findViewById(R.id.note_pin);


    }
}