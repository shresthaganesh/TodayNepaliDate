package com.example.ganesh.nepalidate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

/**
 * Created by ganesh on 3/23/2018.
 */

public class AdditionalFeatures extends Activity {
    TextView label;
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        label = findViewById(R.id.splash_label);
        Animation animation = AnimationUtils.loadAnimation(AdditionalFeatures.this, android.R.anim.fade_out);
        animation.setDuration(3000);
        label.startAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(AdditionalFeatures.this, MainActivity.class));
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}
