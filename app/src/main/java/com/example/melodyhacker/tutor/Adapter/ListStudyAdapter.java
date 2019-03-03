package com.example.melodyhacker.tutor.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.melodyhacker.tutor.R;
import com.example.melodyhacker.tutor.Values.Url;
import com.example.melodyhacker.tutor.Model.GetSetStudy;

import java.util.List;

public class ListStudyAdapter extends RecyclerView.Adapter<ListStudyAdapter.ViewHolder> {

    private List<GetSetStudy> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public ListStudyAdapter(Context context, List<GetSetStudy> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_list_study, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GetSetStudy data = mData.get(position);
        Url url = new Url();
//        name, area,clash, degree, stady
        holder.name.setText(data.getNameClass());
        holder.area.setText(data.getArea());
        holder.clash.setText(data.getClash());
        holder.degree.setText(data.getDegree());
        if (data.getStatus().equals("1")) {
            holder.img.setImageResource(R.drawable.yes);
            holder.stady.setText("Confirm");
        }else{
            holder.img.setImageResource(R.drawable.no);
            holder.stady.setText("Wait Confirm");
        }

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // convenience method for getting data at click position
    public GetSetStudy getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public void setClickListener(View.OnClickListener onClickListener) {
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, area, clash, degree, stady;
        ImageView img;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_status);
            area = itemView.findViewById(R.id.area_status);
            clash = itemView.findViewById(R.id.clash_status);
            degree = itemView.findViewById(R.id.degree_status);
            stady = itemView.findViewById(R.id.stady_status);
            img = itemView.findViewById(R.id.img);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}