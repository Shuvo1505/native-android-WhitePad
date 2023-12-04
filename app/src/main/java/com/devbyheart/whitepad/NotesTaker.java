package com.devbyheart.whitepad;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.devbyheart.whitepad.models.Notes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NotesTaker extends AppCompatActivity {
    EditText edit_note, edit_title;
    ImageView save_note, exit, in, mic;
    Notes notes;
    Button bl, gr, re, vi, oran;
    boolean oldnotes = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_taker);

        save_note = findViewById(R.id.save_note);
        exit = findViewById(R.id.exit);
        in = findViewById(R.id.info_col);
        mic = findViewById(R.id.mic_access);

        bl = (Button) findViewById(R.id.blue);
        gr = (Button) findViewById(R.id.green);
        re = (Button) findViewById(R.id.red);
        vi = (Button) findViewById(R.id.violet);
        oran = (Button) findViewById(R.id.orange);

        edit_note = findViewById(R.id.edit_note);
        edit_title = findViewById(R.id.edit_title);

        bl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation bounce = AnimationUtils.loadAnimation
                        (getApplicationContext(), R.anim.bounce);
                bl.startAnimation(bounce);
                edit_title.setHintTextColor(getResources().getColor(R.color.white));
                edit_title.setTextColor(getResources().getColor(R.color.white));

                edit_note.setHintTextColor(getResources().getColor(R.color.white));
                edit_note.setTextColor(getResources().getColor(R.color.white));

                edit_title.setBackground(getResources().getDrawable(R.drawable.note_back_blue));
                edit_note.setBackground(getResources().getDrawable(R.drawable.note_back_blue));
            }
        });
        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation bounce = AnimationUtils.loadAnimation
                        (getApplicationContext(), R.anim.bounce);
                in.startAnimation(bounce);
                AlertDialog.Builder sho = new AlertDialog.Builder(NotesTaker.this);
                sho.setIcon(R.drawable.colour);
                sho.setCancelable(true);
                sho.setTitle("Colour Palette");
                sho.setMessage("You can choose your favourite colour for " +
                        "note title and body from the colour palette." +
                        " Remember that it will not show any effect on your notes " +
                        "after being saved. " +
                        "It is just for" +
                        " optional purpose.");
                sho.setPositiveButton("Ok", null);
                sho.show();
            }
        });
        gr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation bounce = AnimationUtils.loadAnimation
                        (getApplicationContext(), R.anim.bounce);
                gr.startAnimation(bounce);
                edit_title.setHintTextColor(getResources().getColor(R.color.black));
                edit_title.setTextColor(getResources().getColor(R.color.black));

                edit_note.setHintTextColor(getResources().getColor(R.color.black));
                edit_note.setTextColor(getResources().getColor(R.color.black));

                edit_title.setBackground(getResources().getDrawable(R.drawable.note_back_green));
                edit_note.setBackground(getResources().getDrawable(R.drawable.note_back_green));
            }
        });
        re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation bounce = AnimationUtils.loadAnimation
                        (getApplicationContext(), R.anim.bounce);
                re.startAnimation(bounce);
                edit_title.setHintTextColor(getResources().getColor(R.color.white));
                edit_title.setTextColor(getResources().getColor(R.color.white));

                edit_note.setHintTextColor(getResources().getColor(R.color.white));
                edit_note.setTextColor(getResources().getColor(R.color.white));

                edit_title.setBackground(getResources().getDrawable(R.drawable.note_back));
                edit_note.setBackground(getResources().getDrawable(R.drawable.note_back));
            }
        });
        vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation bounce = AnimationUtils.loadAnimation
                        (getApplicationContext(), R.anim.bounce);
                vi.startAnimation(bounce);
                edit_title.setHintTextColor(getResources().getColor(R.color.black));
                edit_title.setTextColor(getResources().getColor(R.color.black));

                edit_note.setHintTextColor(getResources().getColor(R.color.black));
                edit_note.setTextColor(getResources().getColor(R.color.black));

                edit_title.setBackground(getResources().getDrawable(R.drawable.note_back_violet));
                edit_note.setBackground(getResources().getDrawable(R.drawable.note_back_violet));
            }
        });
        oran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation bounce = AnimationUtils.loadAnimation
                        (getApplicationContext(), R.anim.bounce);
                oran.startAnimation(bounce);
                edit_title.setHintTextColor(getResources().getColor(R.color.black));
                edit_title.setTextColor(getResources().getColor(R.color.black));

                edit_note.setHintTextColor(getResources().getColor(R.color.black));
                edit_note.setTextColor(getResources().getColor(R.color.black));

                edit_title.setBackground(getResources().getDrawable(R.drawable.note_back_orange));
                edit_note.setBackground(getResources().getDrawable(R.drawable.note_back_orange));
            }
        });

        notes = new Notes();
        try {
            notes = (Notes) getIntent().getSerializableExtra("old_note");
            edit_title.setText(notes.getTitle());
            edit_note.setText(notes.getNotes());
            exit.setVisibility(View.INVISIBLE);

            Intent send_data = new Intent();
            send_data.putExtra("note_c", notes);
            setResult(102, send_data);

            oldnotes = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        save_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation bounce = AnimationUtils.loadAnimation
                        (NotesTaker.this, R.anim.bounce);
                save_note.startAnimation(bounce);
                String title = edit_title.getText().toString();
                String desc = edit_note.getText().toString();

                if (desc.isEmpty()) {
                    Toast.makeText(NotesTaker.this,
                            "Please add some notes", Toast.LENGTH_SHORT).show();
                    return;
                }
                SimpleDateFormat format_D = new SimpleDateFormat(
                        "EEE, d MMM yyyy HH:mm a");
                Date date = new Date();

                if (!oldnotes) {
                    notes = new Notes();
                }
                notes.setTitle(title);
                notes.setNotes(desc);
                notes.setDate(format_D.format(date));

                Toast.makeText(NotesTaker.this,
                        "Saved", Toast.LENGTH_SHORT).show();

                Intent send_data = new Intent();
                send_data.putExtra("note", notes);
                //setResult(Activity.RESULT_OK, send_data);
                setResult(101, send_data);
                finish();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation bounce = AnimationUtils.loadAnimation
                        (NotesTaker.this, R.anim.bounce);
                exit.startAnimation(bounce);
                Toast.makeText(NotesTaker.this,
                        "Discarded", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.
                    RECORD_AUDIO}, 1);
        }
        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation bounce = AnimationUtils.loadAnimation
                        (NotesTaker.this, R.anim.bounce);
                mic.startAnimation(bounce);
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, 10);
                } else {
                    Toast.makeText(NotesTaker.this,
                            "Your device doesn't support speech input",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra
                            (RecognizerIntent.EXTRA_RESULTS);
                    if (edit_title.isFocused()) {
                        edit_title.setText(result.get(0));
                    } else if (edit_note.isFocused()) {
                        edit_note.setText(result.get(0));
                    }
                    else {
                        edit_note.setText(result.get(0));
                    }
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder box = new AlertDialog.Builder(NotesTaker.this);
        box.setMessage("Save or Discard your notes to exit.");
        box.setCancelable(true);
        box.show();
    }
}