package com.cropcraft.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Crops extends AppCompatActivity {

    private Button buttonback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crops);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        final ListView listView = findViewById(R.id.listView);
        buttonback = findViewById(R.id.back);

        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Crops.this, settings.class);
                startActivity(intent);
            }
        });

        db.collection("Crops List")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<CropItem> itemsList = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String cropName = document.getString("Crop Name");
                                itemsList.add(new CropItem(cropName));
                            }

                            // Populate data into ListView using custom list item layout
                            CropAdapter arrayAdapter = new CropAdapter(
                                    Crops.this,
                                    R.layout.list_item,
                                    R.id.nameTextView,
                                    itemsList
                            );

                            listView.setAdapter(arrayAdapter);
                        } else {
                            // Handle errors
                        }
                    }
                });
    }
}
