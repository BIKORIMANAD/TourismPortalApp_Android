package com.example.tourism_portal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tourism_portal.models.Site;

import java.util.List;

public class SiteTypeSpinnerAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<Site> listData;
    private Context context;

    public SiteTypeSpinnerAdapter(Context context, List<Site> listData) {
        this.context = context;
        layoutInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }
    @Override
    public Object getItem(int position) {
        return (Site)listData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder spinnerHolder;
        if(convertView == null){
            spinnerHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.spinner_donation_type_list, parent, false);
            spinnerHolder.spinnerItemList = (TextView)convertView.findViewById(R.id.donation_type_spinner_list);
            convertView.setTag(spinnerHolder);
        }else{
            spinnerHolder = (ViewHolder)convertView.getTag();
        }
        spinnerHolder.spinnerItemList.setText(listData.get(position).getSiteName());
        return convertView;
    }
    class ViewHolder{
        TextView spinnerItemList;
    }
}
