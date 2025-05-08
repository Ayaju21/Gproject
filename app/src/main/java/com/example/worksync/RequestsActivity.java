package com.example.worksync;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class RequestsActivity extends AppCompatActivity {

    private LinearLayout checkInLayout, salaryLayout, homeLayout, attendanceLayout, requestsLayout;
    private CardView leaveRequestCardView, overtimeRequestCardView;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);

        // تفعيل زر الرجوع في ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // إظهار زر الرجوع في ActionBar
            getSupportActionBar().setDisplayShowHomeEnabled(true);  // التأكد من إظهار زر الرجوع
        }

        // ربط العناصر في XML بالكود
        initializeViews();
        setBackButtonListener();
        setBottomNavigationListeners();

        // تفعيل النقر على CardView للانتقال إلى الأنشطة المناسبة
        leaveRequestCardView.setOnClickListener(v -> navigateToActivity(LeaveRequest.class));
        overtimeRequestCardView.setOnClickListener(v -> navigateToActivity(OvertimeRequests.class));
    }

    private void initializeViews() {
        // ربط العناصر في XML بالكود
        backButton = findViewById(R.id.backButton);
        checkInLayout = findViewById(R.id.checkInLayout);
        salaryLayout = findViewById(R.id.salaryLayout);
        homeLayout = findViewById(R.id.homeLayout);
        attendanceLayout = findViewById(R.id.attendanceLayout);
        requestsLayout = findViewById(R.id.requestsLayout);
        leaveRequestCardView = findViewById(R.id.leaveRequestCardView);
        overtimeRequestCardView = findViewById(R.id.overtimeRequestCardView);
    }

    private void setBackButtonListener() {
        // تفعيل زر الرجوع عند الضغط عليه
        backButton.setOnClickListener(v -> onBackPressed());  // العودة إلى النشاط السابق
    }

    private void setBottomNavigationListeners() {
        // إنشاء مصفوفة تحتوي على العناصر التي نريد إضافة الـ OnClickListener لها
        LinearLayout[] bottomNavItems = {homeLayout, requestsLayout, checkInLayout, salaryLayout, attendanceLayout};

        // إضافة Listener لكل عنصر في المصفوفة
        for (LinearLayout item : bottomNavItems) {
            item.setOnClickListener(v -> {
                navigateToActivity(getActivityClass(item));  // التنقل للنشاط المناسب
                updateSelection(item);  // تحديث حالة التحديد
            });
        }
    }

    private void navigateToActivity(Class<?> activityClass) {
        // التبديل إلى الأنشطة المناسبة عند النقر على العناصر
        Intent intent = new Intent(RequestsActivity.this, activityClass);
        startActivity(intent);
    }

    // تحديد النشاط المناسب بناءً على العنصر الذي تم النقر عليه
    private Class<?> getActivityClass(LinearLayout item) {
        if (item == homeLayout) return MainActivity.class;
        if (item == requestsLayout) return RequestsActivity.class;
        return null;  // في حالة لا يوجد تطابق
    }

    // تحديث حالة التحديد للـ Bottom Navigation Items
    private void updateSelection(LinearLayout selectedLayout) {
        // إلغاء التحديد لجميع العناصر
        homeLayout.setSelected(false);
        requestsLayout.setSelected(false);
        checkInLayout.setSelected(false);
        salaryLayout.setSelected(false);
        attendanceLayout.setSelected(false);

        // تحديد العنصر الذي تم النقر عليه
        selectedLayout.setSelected(true);
    }

    // التعامل مع الضغط على زر الرجوع في ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();  // العودة إلى النشاط السابق عند الضغط على زر الرجوع في ActionBar
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
