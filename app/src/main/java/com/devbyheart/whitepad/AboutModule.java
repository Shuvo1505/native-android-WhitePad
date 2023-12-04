package com.devbyheart.whitepad;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutModule extends AppCompatActivity {
    ImageView bk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_module);

        bk = findViewById(R.id.app_back);

        bk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation bounce = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.bounce);
                bk.startAnimation(bounce);
                finish();
            }
        });
    }
}