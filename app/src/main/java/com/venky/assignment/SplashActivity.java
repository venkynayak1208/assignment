package com.venky.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent a = new Intent(SplashActivity.this, MainActivity.class);
                    a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(a);
                    finish();
            }
        }, 5000);
    }
}