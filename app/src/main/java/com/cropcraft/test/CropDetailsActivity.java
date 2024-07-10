package com.cropcraft.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CropDetailsActivity extends AppCompatActivity {

    Button buttonback;

    private TextView cropNameTextView, cropInfoTextView, cropHowTextView, cropWhereTextView,
            cropWhenTextView, cropHarvestTextView, cropAddInfoTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_details);

        cropNameTextView = findViewById(R.id.cropNameTextView);
        cropInfoTextView = findViewById(R.id.cropInfoTextView);
        cropHowTextView = findViewById(R.id.cropHowTextView);
        cropWhereTextView = findViewById(R.id.cropWhereTextView);
        cropWhenTextView = findViewById(R.id.cropWhenTextView);
        cropHarvestTextView = findViewById(R.id.cropHarvestTextView);
        cropAddInfoTextView = findViewById(R.id.cropAddInfoTextView);
        buttonback = findViewById(R.id.back);

        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CropDetailsActivity.this, Crops.class);
                startActivity(intent);
            }
        });


        // Get the crop name from the intent
        String cropName = getIntent().getStringExtra("cropName");

        // Fetch additional data from Firestore based on crop name
        FirebaseFirestore.getInstance()
                .collection("Crops List")
                .whereEqualTo("Crop Name", cropName)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);

                        // Display data in TextViews
                        cropNameTextView.setText(documentSnapshot.getString("Crop Name"));
                        cropInfoTextView.setText(documentSnapshot.getString("Crop Info"));
                        cropHowTextView.setText(documentSnapshot.getString("Crop How"));
                        cropWhereTextView.setText(documentSnapshot.getString("Crop Where"));
                        cropWhenTextView.setText(documentSnapshot.getString("Crop When"));
                        cropHarvestTextView.setText(documentSnapshot.getString("Crop Harvest"));
                        cropAddInfoTextView.setText(documentSnapshot.getString("Crop Addinfo"));
                    }
                });
    }
}
