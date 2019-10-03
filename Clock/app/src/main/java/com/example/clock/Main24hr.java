package com.example.clock;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Main24hr extends AppCompatActivity {
    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main24hr);

        TextView textView=findViewById(R.id.date);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String currentDateandTime = sdf.format(new Date());
        textView.setText(currentDateandTime);

        TextView textView9 = findViewById(R.id.date1);
        ZoneId zd = ZoneId.of("Europe/Paris");
        LocalTime localTime = LocalTime.now(zd);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String parisTime = localTime.format(formatter);
        textView9.setText(parisTime);

        TextView textView10 = findViewById(R.id.date2);
        ZoneId timezone = ZoneId.of("Europe/London");
        LocalTime localT = LocalTime.now(timezone);
        DateTimeFormatter formatted = DateTimeFormatter.ofPattern("HH:mm");
        String londonTime = localT.format(formatted);
        textView10.setText(londonTime);

        TextView textView11 = findViewById(R.id.date3);
        ZoneId times = ZoneId.of("Asia/Bangkok");
        LocalTime bTime = LocalTime.now(times);
        DateTimeFormatter formatting = DateTimeFormatter.ofPattern("HH:mm");
        String bangkokTime = bTime.format(formatting);
        textView11.setText(bangkokTime);

        TextView textView12 = findViewById(R.id.date4);
        ZoneId zoned = ZoneId.of("Asia/Seoul");
        LocalTime sTime = LocalTime.now(zoned);
        DateTimeFormatter timeformatted = DateTimeFormatter.ofPattern("HH:mm");
        String seoulTime = sTime.format(timeformatted);
        textView12.setText(seoulTime);

    }
}
