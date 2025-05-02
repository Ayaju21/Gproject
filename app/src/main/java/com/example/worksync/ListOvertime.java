package com.example.worksync;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ListOvertime extends AppCompatActivity {

    private TextView titleTextView, acceptedLeavesCount, acceptedLeavesLabel;
    private Button newOvertimeRequestButton;
    private ImageView backButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listofovertime);  // Assuming this is your XML layout file

        // Initialize views
        titleTextView = findViewById(R.id.titleTextView);
        acceptedLeavesCount = findViewById(R.id.acceptedLeavesCount);
        acceptedLeavesLabel = findViewById(R.id.acceptedLeavesLabel);
        newOvertimeRequestButton = findViewById(R.id.newOvertimeRequestButton);
        backButton = findViewById(R.id.backButton);

        // Back button action
        backButton.setOnClickListener(v -> onBackPressed());

        // New Overtime Request button action
        newOvertimeRequestButton.setOnClickListener(v -> {
            Intent intent = new Intent(ListOvertime.this, OvertimeRequests.class); // Assuming OvertimeRequests is the target activity
            startActivity(intent);
        });
    }
}
