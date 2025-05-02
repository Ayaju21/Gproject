package com.example.worksync;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.database.Cursor;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

public class LeaveRequests extends AppCompatActivity {

    private static final int FILE_SELECT_CODE = 101;

    private Spinner leaveTypeSpinner;
    private EditText reasonEditText;
    private Button selectDateButton, submitButton, chooseFileButton;
    private TextView fileNameTextView;
    private List<LeavePeriod> leavePeriods = new ArrayList<>();
    private String selectedTimeRange = null;
    private Uri selectedFileUri = null;

    private final Calendar calendar = Calendar.getInstance();
    private Date selectedStartDate = null;

    public static class LeavePeriod implements Serializable {
        private String leaveType;
        private Date startDate;
        private Date endDate;

        public LeavePeriod(String leaveType, Date startDate, Date endDate) {
            this.leaveType = leaveType;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public String getLeaveType() {
            return leaveType;
        }

        public Date getStartDate() {
            return startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public String getFormattedPeriod() {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            return "From: " + sdf.format(startDate) + " To: " + sdf.format(endDate);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_requests);

        leaveTypeSpinner = findViewById(R.id.leaveTypeSpinner);
        reasonEditText = findViewById(R.id.reasonTextView);
        selectDateButton = findViewById(R.id.selectDateButton);
        submitButton = findViewById(R.id.submitButton);
        chooseFileButton = findViewById(R.id.chooseFileButton);
        fileNameTextView = findViewById(R.id.fileNameTextView);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.leave_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        leaveTypeSpinner.setAdapter(adapter);

        selectDateButton.setOnClickListener(v -> showDateSelectionDialog());
        chooseFileButton.setOnClickListener(v -> openFilePicker());
        submitButton.setOnClickListener(v -> validateForm());

        // استلام البيانات المرسلة من ListLeave
        Intent intent = getIntent();
        String leaveType = intent.getStringExtra("leaveType");
        String reason = intent.getStringExtra("reason");
        String selectedDates = intent.getStringExtra("selectedDates");
        String fileName = intent.getStringExtra("fileName");

        if (leaveType != null) {
            leaveTypeSpinner.setSelection(((ArrayAdapter) leaveTypeSpinner.getAdapter()).getPosition(leaveType));
        }

        if (reason != null) {
            reasonEditText.setText(reason);
        }

        if (selectedDates != null) {
            selectDateButton.setText(selectedDates);
        }

        if (fileName != null) {
            fileNameTextView.setText(fileName);
        }
    }
    private void validateForm() {
        String leaveType = leaveTypeSpinner.getSelectedItem().toString();
        String reason = reasonEditText.getText().toString();

        if (reason.isEmpty()) {
            Toast.makeText(this, "Please enter a reason", Toast.LENGTH_SHORT).show();
            return;
        }

        if (leavePeriods.isEmpty()) {
            Toast.makeText(this, "Please select a date range", Toast.LENGTH_SHORT).show();
            return;
        }

        LeavePeriod period = leavePeriods.get(0);

        Intent intent = new Intent();
        intent.putExtra("leaveType", leaveType);
        intent.putExtra("reason", reason);
        intent.putExtra("selectedDates", period.getFormattedPeriod());
        intent.putExtra("fileName", fileNameTextView.getText().toString());

        setResult(RESULT_OK, intent);
        finish(); // أغلق الشاشة وارجع إلى ListLeave
    }
    private void showDateSelectionDialog() {
        final Calendar startCalendar = Calendar.getInstance();

        DatePickerDialog startDateDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            startCalendar.set(year, month, dayOfMonth);
            selectedStartDate = startCalendar.getTime();

            final Calendar endCalendar = Calendar.getInstance();

            DatePickerDialog endDateDialog = new DatePickerDialog(this, (v2, y2, m2, d2) -> {
                endCalendar.set(y2, m2, d2);
                Date selectedEndDate = endCalendar.getTime();

                // احفظ التاريخين في قائمة الفترات
                String leaveType = leaveTypeSpinner.getSelectedItem().toString();
                leavePeriods.clear(); // امسح الفترات السابقة
                leavePeriods.add(new LeavePeriod(leaveType, selectedStartDate, selectedEndDate));

                // حدث الزر ليظهر التاريخ
                selectDateButton.setText("From: " +
                        new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedStartDate) +
                        " To: " +
                        new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedEndDate));

            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

            endDateDialog.setTitle("Select End Date");
            endDateDialog.show();

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        startDateDialog.setTitle("Select Start Date");
        startDateDialog.show();
    }


    private void openFilePicker() {
        // Existing file picker code
    }
}
