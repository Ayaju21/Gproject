package com.example.worksync;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

public class ListLeaveAdapter extends RecyclerView.Adapter<ListLeaveAdapter.LeaveViewHolder> {

    private List<LeaveRequests.LeavePeriod> leaveList; // استخدام LeaveRequests.LeavePeriod
    private OnLeaveClickListener listener;

    // تمرير القائمة والمستمع للمؤثر
    public ListLeaveAdapter(List<LeaveRequests.LeavePeriod> leaveList, OnLeaveClickListener listener) {
        this.leaveList = leaveList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LeaveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // ربط الكارت بتصميمه
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leave_request_item, parent, false);
        return new LeaveViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaveViewHolder holder, int position) {
        LeaveRequests.LeavePeriod leave = leaveList.get(position); // استخدام LeaveRequests.LeavePeriod

        // تنسيق التواريخ باستخدام SimpleDateFormat
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String fromDate = dateFormat.format(leave.getStartDate()); // استخدام getStartDate()
        String toDate = dateFormat.format(leave.getEndDate()); // استخدام getEndDate()

        holder.leaveDateTextView.setText("From: " + fromDate + " To: " + toDate);

        // إضافة وظيفة النقر على الكارت
        holder.itemView.setOnClickListener(v -> listener.onLeaveClick(leave));
    }

    @Override
    public int getItemCount() {
        return leaveList.size();
    }

    public static class LeaveViewHolder extends RecyclerView.ViewHolder {
        TextView leaveDateTextView;

        public LeaveViewHolder(@NonNull View itemView) {
            super(itemView);
            leaveDateTextView = itemView.findViewById(R.id.leaveDateTextView);
        }
    }

    // واجهة لإرسال البيانات عند النقر
    public interface OnLeaveClickListener {
        void onLeaveClick(LeaveRequests.LeavePeriod leave); // استخدام LeaveRequests.LeavePeriod هنا
    }
}
