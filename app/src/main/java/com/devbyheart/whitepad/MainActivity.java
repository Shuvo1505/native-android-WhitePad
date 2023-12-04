package com.devbyheart.whitepad;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.devbyheart.whitepad.adapter.NotesAdapter;
import com.devbyheart.whitepad.database.DBHelper;
import com.devbyheart.whitepad.models.Notes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    RecyclerView recyclerView;
    NotesAdapter notesAdapter;
    List<Notes> notes = new ArrayList<>();
    DBHelper database;
    ImageView set, in;
    FloatingActionButton fab_add;
    SearchView searchView;
    Notes selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_home);
        fab_add = findViewById(R.id.fab_add);
        searchView = findViewById(R.id.search_bar);
        set = findViewById(R.id.app_sett);
        in = findViewById(R.id.star_info);

        Intent splash = new Intent(MainActivity.this, SplashScreen.class);
        startActivity(splash);

        database = DBHelper.getInstance(this);

        notes = database.mainDAO().getall();

        updateRecycler(notes);

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent load_notes = new Intent(MainActivity.this, NotesTaker.class);
                //startActivityForResult(load_notes,101);
                activityResultLauncher.launch(load_notes);
            }
        });
        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation bounce = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                in.startAnimation(bounce);
                AlertDialog.Builder sho = new AlertDialog.Builder(MainActivity.this);
                sho.setIcon(R.drawable.star);
                sho.setCancelable(true);
                sho.setTitle("Star/Unstar Feature");
                sho.setMessage("You can star/unstar your favourite or important notes." + " Just tap and hold your desired note to see options.");
                sho.setPositiveButton("Ok", null);
                sho.show();
            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation bounce = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                set.startAnimation(bounce);
                Intent setting = new Intent(MainActivity.this, Settings.class);
                startActivity(setting);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Notes> filteredlist = new ArrayList<>();
                for (Notes singlenote : notes) {
                    if (singlenote.getTitle().toLowerCase().contains(newText.toLowerCase()) || singlenote.getNotes().toLowerCase().contains(newText.toLowerCase())) {
                        filteredlist.add(singlenote);
                    }
                }
                notesAdapter.filterlist(filteredlist);
                return true;
            }
        });
    }

    private void updateRecycler(List<Notes> notes) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        notesAdapter = new NotesAdapter(MainActivity.this, notes, noteItemListener);
        recyclerView.setAdapter(notesAdapter);
    }

    private void showPop(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, cardView);
        popupMenu.setOnMenuItemClickListener(MainActivity.this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.pin) {
            if (selected.isPinned()) {
                database.mainDAO().pin(selected.getID(), false);
                Toast.makeText(this, "Unstarred", Toast.LENGTH_SHORT).show();
                updateRecycler(notes);
            } else {
                database.mainDAO().pin(selected.getID(), true);
                Toast.makeText(this, "Starred", Toast.LENGTH_SHORT).show();
                updateRecycler(notes);
            }
            notes.clear();
            notes.addAll(database.mainDAO().getall());
            notesAdapter.notifyDataSetChanged();
        } else if (menuItem.getItemId() == R.id.del) {
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
            database.mainDAO().delete(selected);
            notes.remove(selected);
            notesAdapter.notifyDataSetChanged();
            updateRecycler(notes);
        }
        return true;
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == 101) {
                Intent intent = result.getData();
                if (intent != null) {
                    Notes new_notes = (Notes) intent.getSerializableExtra("note");
                    database.mainDAO().insert(new_notes);
                    notes.clear();
                    notes.addAll(database.mainDAO().getall());
                    notesAdapter.notifyDataSetChanged();
                    updateRecycler(notes);
                }
            } else if (result.getResultCode() == 102) {
                Intent intent = result.getData();
                if (intent != null) {
                    Notes new_notes = (Notes) intent.getSerializableExtra("note_c");
                    database.mainDAO().update(new_notes.getID(), new_notes.getTitle(), new_notes.getNotes());
                    notes.clear();
                    notes.addAll(database.mainDAO().getall());
                    notesAdapter.notifyDataSetChanged();
                    updateRecycler(notes);
                }
            }
        }
    });


    private final NoteItemListener noteItemListener = new NoteItemListener() {
        @Override
        public void onclick(Notes notes) {
            Intent intent = new Intent(MainActivity.this, NotesTaker.class);
            intent.putExtra("old_note", notes);
            activityResultLauncher.launch(intent);
        }

        @Override
        public void onLongClick(Notes notes, CardView cardView) {
            selected = new Notes();
            selected = notes;
            showPop(cardView);
        }
    };
}