package com.devbyheart.whitepad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Settings extends AppCompatActivity {
    private ListView about_section;
    private ScrollView primary;
    private TextView query;
    private ImageView bk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        about_section = (ListView) findViewById(R.id.about_items);
        primary = (ScrollView) findViewById(R.id.review);
        query = (TextView) findViewById(R.id.show_query);
        bk = (ImageView) findViewById(R.id.set_back);

        ArrayList<String> about_list = new ArrayList<>();

        about_list.add("Version");
        about_list.add("About");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, about_list);
        about_section.setAdapter(arrayAdapter);

        about_section.setOnItemClickListener((parent, view, position, id) -> {
            if (position == 0) {
                query.setText("12.3.7");
            } else if (position == 1) {
                Intent about = new Intent(Settings.this, AboutModule.class);
                startActivity(about);
            }
        });
        bk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation bo = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                bk.startAnimation(bo);
                finish();
            }
        });
    }
}