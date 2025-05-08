package com.example.worksync;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LeaveRequest extends AppCompatActivity {

    private RecyclerView leaveRequestsRecyclerView;
    private LeaveRequestAdapter leaveRequestAdapter;
    private List<LeaveRequestModel> leaveRequestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listofleave);

        // Initialize RecyclerView
        leaveRequestsRecyclerView = findViewById(R.id.leaveRequestsRecyclerView);
        leaveRequestsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize list to hold leave requests
        leaveRequestList = new ArrayList<>();

        // Fetch leave data from the server
        fetchLeaveDataFromServer();

        // New Leave Request Button functionality
        findViewById(R.id.newLeaveRequestButton).setOnClickListener(v -> {
            // Go to the LeaveRequest screen when the button is clicked
            Intent intent = new Intent(LeaveRequest.this, LeaveRequests.class); // Ensure LeaveRequests class exists
            startActivity(intent);
        });

        // Back button functionality
        findViewById(R.id.backButton).setOnClickListener(v -> finish());  // Close current activity and go back
    }

    // Method to fetch leave data from the server
    private void fetchLeaveDataFromServer() {
        String url = "http://10.0.2.2/leave_requests/get_leave_request.php";  // Adjust the URL as needed

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Parse the JSON response
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject leaveRequest = jsonArray.getJSONObject(i);
                                String id = leaveRequest.getString("id");  // استلام الـ id
                                String leaveType = leaveRequest.getString("leave_type");
                                String startDate = leaveRequest.getString("start_date");
                                String endDate = leaveRequest.getString("end_date");

                                // Add to the list
                                leaveRequestList.add(new LeaveRequestModel(id, leaveType, startDate, endDate));
                            }

                            // Set the adapter for RecyclerView
                            leaveRequestAdapter = new LeaveRequestAdapter(leaveRequestList);
                            leaveRequestsRecyclerView.setAdapter(leaveRequestAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LeaveRequest.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                error -> Toast.makeText(LeaveRequest.this, "Error fetching data: " + error.toString(), Toast.LENGTH_SHORT).show()
        );

        // Add the request to the request queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    // Adapter class for RecyclerView
    class LeaveRequestAdapter extends RecyclerView.Adapter<LeaveRequestAdapter.LeaveRequestViewHolder> {

        private List<LeaveRequestModel> leaveRequests;

        public LeaveRequestAdapter(List<LeaveRequestModel> leaveRequests) {
            this.leaveRequests = leaveRequests;
        }

        @Override
        public LeaveRequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leave_request_item, parent, false);
            return new LeaveRequestViewHolder(view);
        }

        @Override
        public void onBindViewHolder(LeaveRequestViewHolder holder, int position) {
            LeaveRequestModel leaveRequest = leaveRequests.get(position);
            holder.leaveTypeTextView.setText(leaveRequest.getLeaveType());
            holder.leaveDatesTextView.setText(leaveRequest.getStartDate() + " to " + leaveRequest.getEndDate());

            // Set onClickListener on the CardView
            holder.itemView.setOnClickListener(v -> {
                // Send leaveRequestId to LeaveOverview activity
                Intent intent = new Intent(LeaveRequest.this, LeaveOverview.class);
                intent.putExtra("leaveRequestId", leaveRequest.getId());  // Send the ID
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return leaveRequests.size();
        }

        // ViewHolder for each leave request
        class LeaveRequestViewHolder extends RecyclerView.ViewHolder {
            TextView leaveTypeTextView, leaveDatesTextView;

            public LeaveRequestViewHolder(View itemView) {
                super(itemView);
                leaveTypeTextView = itemView.findViewById(R.id.leaveTypeTextView);
                leaveDatesTextView = itemView.findViewById(R.id.leaveDatesTextView);
            }
        }
    }

    // LeaveRequestModel class to hold the data
    class LeaveRequestModel {
        private String id;  // إضافة خاصية id
        private String leaveType;
        private String startDate;
        private String endDate;

        // Constructor
        public LeaveRequestModel(String id, String leaveType, String startDate, String endDate) {
            this.id = id;
            this.leaveType = leaveType;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        // Getter methods
        public String getId() {
            return id;  // إرجاع قيمة الـ id
        }

        public String getLeaveType() {
            return leaveType;
        }

        public String getStartDate() {
            return startDate;
        }

        public String getEndDate() {
            return endDate;
        }
    }

}