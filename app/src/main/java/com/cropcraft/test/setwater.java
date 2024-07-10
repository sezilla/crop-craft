package com.cropcraft.test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class setwater extends AppCompatActivity implements View.OnClickListener {


    private TimePicker tpam;
    private TimePicker tppm;
    private Button btn_water1;
    private Button btn_water2;



    Button buttonback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setwater);

        btn_water1 = findViewById(R.id.setwateram);
        tpam = findViewById(R.id.timeam);

        btn_water2 = findViewById(R.id.setwaterpm);
        tppm = findViewById(R.id.timepm);

        btn_water1.setOnClickListener(this);
        btn_water2.setOnClickListener(this);


        buttonback = findViewById(R.id.back);



        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(setwater.this, settings.class);
                startActivity(intent1);
            }
        });

    }



    @Override
    public void onClick(View view){

        Calendar calam=Calendar.getInstance();
        calam.set(calam.get(Calendar.YEAR),
                calam.get(Calendar.MONTH),
                calam.get(Calendar.DAY_OF_MONTH),
                        tpam.getHour(),
                        tpam.getMinute(),0);
        Alarm_setam(calam.getTimeInMillis());

        Calendar calpm=Calendar.getInstance();
        calpm.set(calpm.get(Calendar.YEAR),
                calpm.get(Calendar.MONTH),
                calpm.get(Calendar.DAY_OF_MONTH),
                tpam.getHour(),
                tpam.getMinute(),0);
        Alarm_setpm(calpm.getTimeInMillis());
    }

    private void Alarm_setam(long timeInMillis) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, Alarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        if (alarmManager != null) {
            // Use setInexactRepeating for better battery optimization
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent);
            Toast.makeText(this, "Your Water Schedule in morning is Set", Toast.LENGTH_LONG).show();
        }
    }

    private void Alarm_setpm(long timeInMillis) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, Alarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        if (alarmManager != null) {
            // Use setInexactRepeating for better battery optimization
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent);
            Toast.makeText(this, "Your Water Schedule afternoon is Set", Toast.LENGTH_LONG).show();
        }
    }


}