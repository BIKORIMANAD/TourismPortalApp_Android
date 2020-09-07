package com.example.tourism_portal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tourism_portal.models.Site;

import java.util.List;


public class StudentDetailsAdapter extends RecyclerView.Adapter<StudentDetailsAdapter.MyViewHolder> {
    private Context context;
    private List<Site> siteList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView student_name, student_gender, student_classname, student_dateofbirth;

        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            student_name = view.findViewById(R.id.student_name);
            student_gender = view.findViewById(R.id.student_gender);
            student_classname = view.findViewById(R.id.student_classname);
            student_dateofbirth = view.findViewById(R.id.student_dateofbirth);
            thumbnail = view.findViewById(R.id.student_image);
        }
    }

    public StudentDetailsAdapter(Context context, List<Site> siteList) {
        this.context = context;
        this.siteList = siteList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_details_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Site site = siteList.get(position);

        holder.student_name.setText(site.getSiteName());
        holder.student_gender.setText(site.getLocation());
        holder.student_classname.setText(site.getDescription());

        Glide.with(context)
                .load( MyApplication.getInstance().getString(R.string.server)+ site.getImagePath())
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return siteList.size();
    }
}
