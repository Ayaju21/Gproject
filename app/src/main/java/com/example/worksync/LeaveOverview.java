package com.example.worksync;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class LeaveOverview extends AppCompatActivity {

    private TextView leaveTypeTextView, reasonTextView, requestDateTextView, startDateTextView, endDateTextView, fileNameTextView, timeRangeTextView;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaveoverview);

        leaveTypeTextView = findViewById(R.id.typeOfVacationTextView);
        reasonTextView = findViewById(R.id.reasonOfVacationTextView);
        requestDateTextView = findViewById(R.id.requestDateTextView);
        startDateTextView = findViewById(R.id.startDateTextView);
        endDateTextView = findViewById(R.id.endDateTextView);
        fileNameTextView = findViewById(R.id.attachmentTextView);

        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> onBackPressed());

        Intent intent = getIntent();
        String leaveType = intent.getStringExtra("leaveType");
        String reason = intent.getStringExtra("reason");
        String selectedDates = intent.getStringExtra("selectedDates");
        String fileName = intent.getStringExtra("fileName");


        leaveTypeTextView.setText("Leave Type: " + (leaveType != null ? leaveType : "N/A"));
        reasonTextView.setText("Reason: " + (reason != null ? reason : "N/A"));
        requestDateTextView.setText("Request Date: " + (selectedDates != null ? selectedDates : "N/A"));
        fileNameTextView.setText("File: " + (fileName != null && !fileName.isEmpty() ? fileName : "No file selected"));



        startDateTextView.setText("Start Date: " + (selectedDates != null ? selectedDates : "N/A"));
        endDateTextView.setText("End Date: " + (selectedDates != null ? selectedDates : "N/A"));
    }
}
