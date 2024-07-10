package com.cropcraft.test;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    Button button1;
    Button button2;
    TextView textView;
    FirebaseUser user;
    ListView listView;
    List<String> scheduleList;
    ScheduleAdapter scheduleAdapter;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        LinearLayout tomatoLayout = findViewById(R.id.tomato);
        LinearLayout ampalayaLayout = findViewById(R.id.ampalaya);
        LinearLayout sitawLayout = findViewById(R.id.sitaw);
        LinearLayout bellpepperLayout = findViewById(R.id.bellpepper);
        LinearLayout cauliflowerLayout = findViewById(R.id.cauliflower);
        LinearLayout kangkongLayout = findViewById(R.id.kangkong);
        LinearLayout bottlegourdLayout = findViewById(R.id.bottlegourd);
        LinearLayout patolaLayout = findViewById(R.id.patola);
        LinearLayout labanosLayout = findViewById(R.id.labanos);



        tomatoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSchedule("Tomato");
            }
        });

        ampalayaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSchedule("Ampalaya");
            }
        });

        sitawLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSchedule("Sitaw");
            }
        });

        bellpepperLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSchedule("Bell Pepper");
            }
        });

        cauliflowerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSchedule("Cauliflower");
            }
        });

        kangkongLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSchedule("Kang-kong");
            }
        });

        bottlegourdLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSchedule("Bottle Gourd");
            }
        });

        patolaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSchedule("Patola");
            }
        });

        labanosLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSchedule("Labanos");
            }
        });


        auth = FirebaseAuth.getInstance();
        button1 = findViewById(R.id.settings);
        button2 = findViewById(R.id.btn_add);
        textView = findViewById(R.id.user_details);
        user = auth.getCurrentUser();

        FrameLayout sheet = findViewById(R.id.popup);

        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        } else {
            textView.setText(user.getEmail());
        }

        BottomSheetBehavior<FrameLayout> bottomSheetBehavior = BottomSheetBehavior.from(sheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setPeekHeight(0);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, settings.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MainActivity", "Button 2 clicked");
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        initializeListView();
    }

    private void initializeListView() {
        listView = findViewById(R.id.schedulelist);
        scheduleList = new ArrayList<>();
        scheduleAdapter = new ScheduleAdapter(this, scheduleList);
        listView.setAdapter(scheduleAdapter);
    }

    private void addSchedule(String scheduleName) {
        scheduleList.add(scheduleName);
        scheduleAdapter.notifyDataSetChanged();
    }
}
