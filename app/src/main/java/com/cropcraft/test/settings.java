package com.cropcraft.test;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class settings extends AppCompatActivity {

    FirebaseAuth auth;
    Button buttonlogout, wbutton;
    Button buttonback;
    Button buttonwater;
    Button nuttonfertilizer;
    Button crops;
    Button feedbacks;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        auth = FirebaseAuth.getInstance();
        buttonlogout = findViewById(R.id.logout);
        buttonback = findViewById(R.id.back);
        buttonwater = findViewById(R.id.water);
        crops = findViewById(R.id.crops);
        feedbacks = findViewById(R.id.feedback);
        nuttonfertilizer = findViewById(R.id.fertilizer);
        wbutton = findViewById(R.id.myweather);


        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(settings.this, MainActivity.class);
                startActivity(intent);
            }
        });


        crops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(settings.this, Crops.class);
                startActivity(intent);
            }
        });

        buttonwater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(settings.this, setwater.class);
                startActivity(intent);
            }
        });

        nuttonfertilizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(settings.this, setfertilizer.class);
                startActivity(intent);
            }
        });
        wbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(settings.this, WeatherPage.class);
                startActivity(intent);
            }
        });

        feedbacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(settings.this, feedbacks.class);
                startActivity(intent);
            }
        });


        buttonlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });


    }
}