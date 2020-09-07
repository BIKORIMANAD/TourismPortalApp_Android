package com.example.tourism_portal;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tourism_portal.models.Site;

import java.util.List;


public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.MyViewHolder> {

    private List<Site> studentsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView student_name, student_class, student_gender;

        public MyViewHolder(View view) {
            super(view);
            student_name = (TextView) view.findViewById(R.id.student_name);
            student_class = (TextView) view.findViewById(R.id.student_name);
            student_gender = (TextView) view.findViewById(R.id.student_gender);
        }
    }


    public StudentListAdapter(List<Site> studentsList) {
        this.studentsList = studentsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Site site = studentsList.get(position);
        holder.student_name.setText(site.getDescription());
        holder.student_class.setText(site.getSiteName());
        holder.student_gender.setText(site.getLocation());
    }

    @Override
    public int getItemCount() {
        return studentsList.size();
    }
}
