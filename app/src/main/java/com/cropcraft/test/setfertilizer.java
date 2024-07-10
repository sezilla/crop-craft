package com.cropcraft.test;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class setfertilizer extends AppCompatActivity implements View.OnClickListener {


    private TimePicker tp;
    Button buttonback;
    Spinner dayOfWeekSpinner;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setfertilizer);

        Button btn_fertilizer = findViewById(R.id.setAlarmButton);
        tp = findViewById(R.id.fertilizertp);
        buttonback = findViewById(R.id.back);
        dayOfWeekSpinner = findViewById(R.id.dayOfWeekSpinner);
        btn_fertilizer.setOnClickListener(this);


        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(setfertilizer.this, settings.class);
                startActivity(intent1);
            }
        });

    }



    @Override
    public void onClick(View view) {
        // Get the selected day from the spinner
        String selectedDay = dayOfWeekSpinner.getSelectedItem().toString();

        // Get the selected time from the TimePicker
        Calendar calendar = Calendar.getInstance();
        calendar.set(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                tp.getHour(),
                tp.getMinute(), 0);

        // Set the calendar day of the week based on the selected day
        int dayOfWeekConstant = getDayOfWeekConstant(selectedDay);
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeekConstant);

        // Set the alarm
        Alarm_set(calendar.getTimeInMillis());
    }

    private int getDayOfWeekConstant(String day) {
        switch (day) {
            case "Sunday":
                return Calendar.SUNDAY;
            case "Monday":
                return Calendar.MONDAY;
            case "Tuesday":
                return Calendar.TUESDAY;
            case "Wednesday":
                return Calendar.WEDNESDAY;
            case "Thursday":
                return Calendar.THURSDAY;
            case "Friday":
                return Calendar.FRIDAY;
            case "Saturday":
                return Calendar.SATURDAY;
            default:
                return -1; // Invalid day
        }
    }



    private void Alarm_set(long timeInMillis) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, Alarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        if (alarmManager != null) {
            // Use setInexactRepeating for better battery optimization
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent);
            Toast.makeText(this, "Your Fertilizer Schedule is Set", Toast.LENGTH_LONG).show();
        }
    }

}