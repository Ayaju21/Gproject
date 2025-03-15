package com.example.worksync;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize top bar icons
        ImageView menuIcon = findViewById(R.id.menuIcon);
        ImageView profileIcon = findViewById(R.id.profileIcon);

        // Initialize buttons
        MaterialButton btnAttendance = findViewById(R.id.btnAttendance);
        MaterialButton btnSalary = findViewById(R.id.btnSalary);
        MaterialButton btnLeave = findViewById(R.id.btnLeave);
        MaterialButton btnOver = findViewById(R.id.btnOver);

        // Set click listeners for top bar icons
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Menu clicked", Toast.LENGTH_SHORT).show();
            }
        });

        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Profile clicked", Toast.LENGTH_SHORT).show();
            }
        });

        // Set click listeners for buttons
        btnAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Attendance clicked", Toast.LENGTH_SHORT).show();
            }
        });

        btnSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Salary clicked", Toast.LENGTH_SHORT).show();
            }
        });

        btnLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Leave Requests clicked", Toast.LENGTH_SHORT).show();
            }
        });

        btnOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Overtime Requests clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}