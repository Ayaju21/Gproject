package com.example.worksync;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class LeaveRequests extends AppCompatActivity {

    private ImageView backButton;
    private TextView titleTextView;
    private Spinner leaveTypeSpinner;
    private TextView selectDateLabel;
    private LinearLayout dateSelectionLayout;
    private Button todayButton;
    private Button tomorrowButton;
    private Button dateRangeButton;
    private TextView reasonLabel;
    private TextView  reasonTextView;
    private TextView attachmentLabel;
    private LinearLayout attachmentLayout;
    private TextView fileNameTextView;
    private Button chooseFileButton;
    private Button submitButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_requests); //keep the same layout

        // Initialize UI elements
        backButton = findViewById(R.id.backButton);
        titleTextView = findViewById(R.id.titleTextView);
        leaveTypeSpinner = findViewById(R.id.leaveTypeSpinner);
        selectDateLabel = findViewById(R.id.selectDateLabel);
        dateSelectionLayout = findViewById(R.id.dateSelectionLayout);
        todayButton = findViewById(R.id.todayButton);
        tomorrowButton = findViewById(R.id.tomorrowButton);
        dateRangeButton = findViewById(R.id.dateRangeButton);
        reasonLabel = findViewById(R.id.reasonLabel);
        reasonTextView = findViewById(R.id.reasonTextView);
        attachmentLabel = findViewById(R.id.attachmentLabel);
        attachmentLayout = findViewById(R.id.attachmentLayout);
        fileNameTextView = findViewById(R.id.fileNameTextView);
        chooseFileButton = findViewById(R.id.chooseFileButton);
        submitButton = findViewById(R.id.submitButton);

        // Set up leave type spinner
        setupLeaveTypeSpinner();

        // Set up button click listeners
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle back button click (e.g., navigate back)
                finish();
            }
        });

        todayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle today button click (e.g., set date to today)
                Toast.makeText(LeaveRequests.this, "Today Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        tomorrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle tomorrow button click (e.g., set date to tomorrow)
                Toast.makeText(LeaveRequests.this, "Tomorrow Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        dateRangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle date range button click (e.g., show date range picker)
                Toast.makeText(LeaveRequests.this, "Date Range Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        chooseFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle choose file button click (e.g., open file picker)
                Toast.makeText(LeaveRequests.this, "Choose File Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle submit button click (e.g., validate input and submit request)
                handleSubmitButtonClick();
            }
        });
    }

    private void setupLeaveTypeSpinner() {
        // Populate the spinner with leave types
        List<String> leaveTypes = new ArrayList<>();
        leaveTypes.add("Vacation");
        leaveTypes.add("Sick Leave");
        leaveTypes.add("Personal Leave");
        leaveTypes.add("Other");

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, leaveTypes);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        leaveTypeSpinner.setAdapter(adapter);

        // Set an OnItemSelectedListener to handle user selection
        leaveTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLeaveType = parent.getItemAtPosition(position).toString();
                // Do something with the selected leave type (e.g., store it in a variable)
                Toast.makeText(LeaveRequests.this, "Selected Leave Type: " + selectedLeaveType, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optional: Handle the case where nothing is selected
            }
        });
    }

    private void handleSubmitButtonClick() {
        // Get user input from the form
        String selectedLeaveType = leaveTypeSpinner.getSelectedItem().toString();
        String reasonForLeave = reasonTextView.getText().toString().trim();
        String fileName = fileNameTextView.getText().toString(); // Get the file name

        // Perform input validation
        if (reasonForLeave.isEmpty()) {
            reasonTextView.setError("Please enter the reason for your leave request");
            return;
        }

        //  Add more validation as needed (e.g., date validation, file selection)

        // Display a confirmation message (you can customize this)
        String message = "Leave Request Submitted:\n"
                + "Leave Type: " + selectedLeaveType + "\n"
                + "Reason: " + reasonForLeave + "\n"
                + "Attachment: " + fileName; // Include the file name in the message
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        // You would typically send the data to a server or process it further here.
        // For this example, we'll just finish the activity.
        finish();
    }
}
