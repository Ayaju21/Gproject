package com.example.worksync;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView dateText;
    private TextView hoursText;
    private Handler handler;
    private LinearLayout checkInLayout;
    private LinearLayout hrAssistantLayout;
    private LinearLayout homeLayout;
    private LinearLayout attendanceLayout;
    private LinearLayout requestsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateText = findViewById(R.id.dateText);
        hoursText = findViewById(R.id.hoursText);
        handler = new Handler(Looper.getMainLooper());

        // Initialize bottom navigation LinearLayouts
        checkInLayout = findViewById(R.id.checkIn); // Changed to checkIn
        hrAssistantLayout = findViewById(R.id.hr);     // Changed to hr
        homeLayout = findViewById(R.id.home);         // Changed to home
        attendanceLayout = findViewById(R.id.attendance); // Changed to attendance
        requestsLayout = findViewById(R.id.requests);   // Changed to requests

        updateDate();
        startUpdatingHours();
        setupNavigation();
    }

    private void updateDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM, dd", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());
        dateText.setText(currentDate);
    }

    private void startUpdatingHours() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        final String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                hoursText.setText(currentTime + " Hours");
                            }
                        });
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    // Handle thread interruption (e.g., log it)
                }
            }
        }).start();
    }

    private void setupNavigation() {

        homeLayout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
