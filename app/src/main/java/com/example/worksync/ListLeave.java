package com.example.worksync;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListLeave extends AppCompatActivity {

    private TextView titleTextView, acceptedLeavesCount, acceptedLeavesLabel;
    private Button newLeaveRequestButton;
    private ImageView backButton;
    private RecyclerView recyclerView;
    private ListLeaveAdapter adapter;
    private List<LeaveRequests.LeavePeriod> leaveRequestsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listofleave);

        titleTextView = findViewById(R.id.titleTextView);
        acceptedLeavesCount = findViewById(R.id.acceptedLeavesCount);
        acceptedLeavesLabel = findViewById(R.id.acceptedLeavesLabel);
        newLeaveRequestButton = findViewById(R.id.newLeaveRequestButton);
        backButton = findViewById(R.id.backButton);
        recyclerView = findViewById(R.id.leaveRequestsRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // الحصول على البيانات المتعلقة بالإجازات
        leaveRequestsList = getLeaveRequests();

        // تعيين الـ Adapter مع تعديل الانتقال إلى LeaveOverview
        adapter = new ListLeaveAdapter(leaveRequestsList, leave -> {
            // انتقل إلى واجهة LeaveOverview بدلاً من LeaveRequests
            Intent intent = new Intent(ListLeave.this, LeaveOverview.class);
            intent.putExtra("leaveType", leave.getLeaveType());
            intent.putExtra("reason", "Your reason here"); // يمكن تعديل هذا حسب البيانات الفعلية
            intent.putExtra("selectedDates", leave.getFormattedPeriod());
            intent.putExtra("fileName", "No file attached");
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);

        // إعداد وظيفة الرجوع
        backButton.setOnClickListener(v -> onBackPressed());

        // الانتقال إلى واجهة LeaveRequests لإضافة طلب إجازة جديدة
        newLeaveRequestButton.setOnClickListener(v -> {
            Intent newLeaveIntent = new Intent(ListLeave.this, LeaveRequests.class);
            startActivityForResult(newLeaveIntent, 200); // أي رقم كود تختاره

        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK && data != null) {
            String leaveType = data.getStringExtra("leaveType");
            String selectedDates = data.getStringExtra("selectedDates");

            // فقط تواريخ حالياً، يمكنك لاحقاً استرجاع السبب والملف إذا احتجتها
            LeaveRequests.LeavePeriod period = new LeaveRequests.LeavePeriod(
                    leaveType, new Date(), new Date() // التواريخ وهمية حالياً، ممكن تحسينها لاحقاً
            );

            leaveRequestsList.add(period);
            adapter.notifyItemInserted(leaveRequestsList.size() - 1);

            acceptedLeavesCount.setText(String.valueOf(leaveRequestsList.size()));
        }
    }

    // دالة للحصول على قائمة الإجازات (بيانات وهمية في هذه الحالة)
    private List<LeaveRequests.LeavePeriod> getLeaveRequests() {
        List<LeaveRequests.LeavePeriod> leaveList = new ArrayList<>();

        // تحديث عدد الإجازات المقبولة
        acceptedLeavesCount.setText(String.valueOf(leaveList.size()));

        return leaveList;
    }
}
