package com.example.melodyhacker.tutor.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.melodyhacker.tutor.Model.GetSetRatting;
import com.example.melodyhacker.tutor.R;
import com.example.melodyhacker.tutor.Values.Url;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListRattingAdapter extends RecyclerView.Adapter<ListRattingAdapter.ViewHolder> {

    private List<GetSetRatting> mData;
    private LayoutInflater mInflater;
    private ListRattingAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    public ListRattingAdapter(Context context, List<GetSetRatting> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ListRattingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_list_ratting, parent, false);
        return new ListRattingAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ListRattingAdapter.ViewHolder holder, int position) {
        GetSetRatting data = mData.get(position);
        Url url = new Url();
        holder.name.setText(data.getName() + " " + data.getLast_name());
        holder.rate.setText(data.getRate());
        Picasso.get().load(url.path_mage + data.getImage()).into(holder.img);


    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // convenience method for getting data at click position
    public GetSetRatting getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ListRattingAdapter.ItemClickListener itemClickListener) {
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
        TextView name,rate;
        ImageView img;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            rate = itemView.findViewById(R.id.rate);
            img = itemView.findViewById(R.id.img);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}