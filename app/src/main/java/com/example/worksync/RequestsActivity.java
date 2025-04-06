package com.example.worksync;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.HashMap;
import java.util.Map;

public class RequestsActivity extends AppCompatActivity {

    //region Constants
    private static final int PURPLE_COLOR = Color.parseColor("#5C2E91");
    private static final int DEFAULT_COLOR = Color.BLACK;
    //endregion

    //region View Variables
    private ImageView backButton; // Add this line
    private final Map<Integer, ImageView> iconMap = new HashMap<>();
    private final Map<Integer, TextView> textMap = new HashMap<>();
    private final Map<Integer, Integer> originalIconColors = new HashMap<>(); // To store original icon colors
    private final Map<Integer, Integer> originalTextColors = new HashMap<>(); // To store original text colors
    private LinearLayout checkInLayout;
    private LinearLayout hrLayout;
    private LinearLayout homeLayout;
    private LinearLayout attendanceLayout;
    private LinearLayout requestsLayout;
    private LinearLayout[] bottomNavigationLayouts; // Array to store bottom navigation layouts

    private CardView leaveRequestCardView;
    private CardView overtimeRequestCardView;
    //endregion

    //region Activity Lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        initializeViews();
        storeOriginalColors(); // Store original colors
        setBottomNavigationListeners();
        setBackButtonListener(); // Add this line


        leaveRequestCardView = findViewById(R.id.leaveRequestCardView);
        overtimeRequestCardView = findViewById(R.id.overtimeRequestCardView);


        leaveRequestCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the LeaveRequests activity
                Intent intent = new Intent(RequestsActivity.this, LeaveRequests.class);
                startActivity(intent);
            }
        });
    }
    //endregion

    //region Initialization
    private void initializeViews() {
        // Initialize back button
        backButton = findViewById(R.id.backButton); // Initialize the backButton

        // Initialize bottom navigation layouts
        checkInLayout = findViewById(R.id.checkInLayout);
        hrLayout = findViewById(R.id.hrLayout);
        homeLayout = findViewById(R.id.homeLayout);
        attendanceLayout = findViewById(R.id.attendanceLayout);
        requestsLayout = findViewById(R.id.requestsLayout);

        bottomNavigationLayouts = new LinearLayout[]{checkInLayout, hrLayout, homeLayout, attendanceLayout, requestsLayout};

        // Initialize maps
        iconMap.put(R.id.checkInLayout, (ImageView) findViewById(R.id.checkInIcon));
        iconMap.put(R.id.hrLayout, (ImageView) findViewById(R.id.hrIcon));
        iconMap.put(R.id.homeLayout, (ImageView) findViewById(R.id.homeIcon));
        iconMap.put(R.id.attendanceLayout, (ImageView) findViewById(R.id.attendanceIcon));
        iconMap.put(R.id.requestsLayout, (ImageView) findViewById(R.id.requestsIcon));

        textMap.put(R.id.checkInLayout, (TextView) findViewById(R.id.checkInText));
        textMap.put(R.id.hrLayout, (TextView) findViewById(R.id.hrText));
        textMap.put(R.id.homeLayout, (TextView) findViewById(R.id.homeText));
        textMap.put(R.id.attendanceLayout, (TextView) findViewById(R.id.attendanceText));
        textMap.put(R.id.requestsLayout, (TextView) findViewById(R.id.requestsText));
    }

    private void storeOriginalColors() {
        for (Map.Entry<Integer, ImageView> entry : iconMap.entrySet()) {
            int layoutId = entry.getKey();
            ImageView icon = entry.getValue();
            originalIconColors.put(layoutId, getOriginalIconColor(icon));
            originalTextColors.put(layoutId, textMap.get(layoutId).getTextColors().getDefaultColor());
        }
    }

    private int getOriginalIconColor(ImageView imageView) {
        // Since we are only setting PorterDuffColorFilter, we can assume that here.
        // If you were setting other types of ColorFilter, you'd need more complex logic.
        return DEFAULT_COLOR; // The original color is the default color
    }
    //endregion

    //region Event Handling
    private void setBottomNavigationListeners() {
        for (LinearLayout layout : bottomNavigationLayouts) { // Use the array here
            layout.setOnClickListener(this::onBottomNavigationItemClick);
        }
    }

    private void setBackButtonListener() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to go back to MainActivity
                Intent intent = new Intent(RequestsActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Optional: Close the current activity
            }
        });
    }

    private void onBottomNavigationItemClick(View v) {
        setIconAndTextColor(v.getId(), PURPLE_COLOR);
        resetOtherItems(v.getId());

        // Handle navigation for bottom menu items if needed
        if (v.getId() == R.id.homeLayout) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else if (v.getId() == R.id.checkInLayout) {
            // startActivity(new Intent(this, CheckInActivity.class));
            // finish();
            // Add navigation for other bottom menu items
        } else if (v.getId() == R.id.hrLayout) {
            // startActivity(new Intent(this, HRActivity.class));
            // finish();
        } else if (v.getId() == R.id.attendanceLayout) {
            // startActivity(new Intent(this, AttendanceActivity.class));
            // finish();
        }
        // Requests is the current activity, no need to navigate again
    }

    private void setIconAndTextColor(int layoutId, int color) {
        ImageView icon = iconMap.get(layoutId);
        TextView text = textMap.get(layoutId);
        if (icon != null) {
            icon.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
        }
        if (text != null) {
            text.setTextColor(color);
        }
    }

    private void resetOtherItems(int currentItemId) {
        for (Integer layoutId : iconMap.keySet()) {
            if (layoutId != currentItemId) {
                setIconAndTextColor(layoutId, originalIconColors.get(layoutId));
            }
        }
    }
    //endregion
}