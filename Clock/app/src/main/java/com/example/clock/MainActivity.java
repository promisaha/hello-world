package com.example.clock;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.time.*;
import java.time.format.DateTimeFormatter;



public class MainActivity extends AppCompatActivity {

    Button footer_button2;
    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        footer_button2 = (Button) findViewById(R.id.footer_button2);//declaring button 3

        footer_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Main24hr.class));

            }
        });

        TextView textView=findViewById(R.id.date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
        String currentTime = dateFormat.format(new Date());
        textView.setText(currentTime);


        TextView textView9 = findViewById(R.id.date1);
        ZoneId zd = ZoneId.of("Europe/Paris");
        LocalTime localTime = LocalTime.now(zd);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
        String parisTime = localTime.format(formatter);
        textView9.setText(parisTime);

        TextView textView10 = findViewById(R.id.date2);
        ZoneId timezone = ZoneId.of("Europe/London");
        LocalTime localT = LocalTime.now(timezone);
        DateTimeFormatter formatted = DateTimeFormatter.ofPattern("h:mm a");
        String londonTime = localT.format(formatted);
        textView10.setText(londonTime);

        TextView textView11 = findViewById(R.id.date3);
        ZoneId times = ZoneId.of("Asia/Bangkok");
        LocalTime bTime = LocalTime.now(times);
        DateTimeFormatter formatting = DateTimeFormatter.ofPattern("h:mm a");
        String bangkokTime = bTime.format(formatting);
        textView11.setText(bangkokTime);

        TextView textView12 = findViewById(R.id.date4);
        ZoneId zoned = ZoneId.of("Asia/Seoul");
        LocalTime sTime = LocalTime.now(zoned);
        DateTimeFormatter timeformatted = DateTimeFormatter.ofPattern("h:mm a");
        String seoulTime = sTime.format(timeformatted);
        textView12.setText(seoulTime);


    }
}
