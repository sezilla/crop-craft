package com.cropcraft.test;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class feedbacks extends AppCompatActivity {

    TextInputEditText feedbackEditText;
    TextInputEditText nameEditText;
    MaterialButton sendButton;
    Button buttonBack;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbacks);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        buttonBack = findViewById(R.id.back);
        feedbackEditText = findViewById(R.id.feed);
        nameEditText = findViewById(R.id.name);
        sendButton = findViewById(R.id.send);
        progressBar = findViewById(R.id.progressBar);


        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(feedbacks.this, settings.class);
                startActivity(intent);
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);

                String feedback = String.valueOf(feedbackEditText.getText());
                String name = String.valueOf(nameEditText.getText());


                Map<String, Object> feedbackMap = new HashMap<>();
                feedbackMap.put("Feedback", feedback);
                feedbackMap.put("Name", name);


                db.collection("Feedbacks")
                        .add(feedbackMap)
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(feedbacks.this, "Feedback Successful", Toast.LENGTH_LONG).show();
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            progressBar.setVisibility(View.GONE);
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(feedbacks.this, "Failed", Toast.LENGTH_LONG).show();
                            Log.w(TAG, "Error sending feedback", e);
                            progressBar.setVisibility(View.GONE);
                        });
            }
        });
    }
}
