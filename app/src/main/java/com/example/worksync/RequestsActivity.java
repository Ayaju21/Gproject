package com.example.worksync;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class RequestsActivity extends AppCompatActivity {

    private LinearLayout checkInLayout, salaryLayout, homeLayout, attendanceLayout, requestsLayout;
    private CardView leaveRequestCardView, overtimeRequestCardView;
    private ImageView backButton;
    private NavigationHelper navigationHelper;  // إنشاء كائن من الـ Helper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);

        // تفعيل زر الرجوع في ActionBar باستخدام الـ Helper
        navigationHelper = new NavigationHelper(this);
        navigationHelper.enableBackButton();  // تفعيل زر الرجوع

        // ربط العناصر في XML بالكود
        initializeViews();

        // إعداد Bottom Navigation باستخدام الـ Helper
        LinearLayout[] bottomNavItems = {homeLayout, requestsLayout, checkInLayout, salaryLayout, attendanceLayout};
        navigationHelper.setBottomNavigationListeners(bottomNavItems, homeLayout, requestsLayout);

        // تفعيل النقر على CardView للانتقال إلى الأنشطة المناسبة
        leaveRequestCardView.setOnClickListener(v -> navigateToActivity(LeaveRequest.class));
        overtimeRequestCardView.setOnClickListener(v -> navigateToActivity(OvertimeRequest.class));
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

        // تفعيل زر الرجوع باستخدام الـ Helper
        navigationHelper.setBackButtonListener(backButton);  // استدعاء زر الرجوع
    }

    private void navigateToActivity(Class<?> activityClass) {
        // التنقل إلى الأنشطة المناسبة
        Intent intent = new Intent(RequestsActivity.this, activityClass);
        startActivity(intent);
    }
}
