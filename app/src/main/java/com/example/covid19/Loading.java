package com.example.covid19;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class Loading extends AppCompatActivity {
    private static int time_out = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent load = new Intent(Loading.this, MainActivity.class);
                startActivity(load);
                finish();
            }
        },time_out);
    }
}
