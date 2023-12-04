package com.devbyheart.whitepad;

import androidx.cardview.widget.CardView;

import com.devbyheart.whitepad.models.Notes;

public interface NoteItemListener {

    void onclick(Notes notes);

    void onLongClick(Notes notes, CardView cardView);
}