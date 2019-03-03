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
import com.example.melodyhacker.tutor.Model.GetSetArea;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListAreaAdapter extends RecyclerView.Adapter<ListAreaAdapter.ViewHolder> {

    private List<GetSetArea> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public ListAreaAdapter(Context context, List<GetSetArea> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_list_area, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GetSetArea data = mData.get(position);
        Url url = new Url();
        holder.name_class.setText(data.getNameClass());
        holder.area_class.setText(data.getArea());
        holder.degree_class.setText(data.getDegree());
        holder.address_class.setText(data.getArea());
        holder.tel_class.setText(data.getTel());
        holder.clash_class.setText(data.getClash());
        holder.etc_class.setText(data.getEtc());
        holder.email.setText(data.getEmail());
        holder.name_tutor_user.setText(data.getNameTutor());
        holder.last_name_tutor_user.setText(data.getLastNameTutor());
        Picasso.get().load(url.path_mage + data.getImage_tutor()).into(holder.img);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // convenience method for getting data at click position
    public GetSetArea getItem(int id) {
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
        TextView name_class, area_class,email, degree_class, address_class, tel_class, etc_class,clash_class, name_tutor_user, last_name_tutor_user;
        ImageView img;

        ViewHolder(View itemView) {
            super(itemView);
            name_class = itemView.findViewById(R.id.name_class);
            area_class = itemView.findViewById(R.id.area_class);
            clash_class = itemView.findViewById(R.id.clash_class);
            degree_class = itemView.findViewById(R.id.degree_class);
            address_class = itemView.findViewById(R.id.address_class);
            email = itemView.findViewById(R.id.email_class);
            tel_class = itemView.findViewById(R.id.tel_class);
            etc_class = itemView.findViewById(R.id.etc_class);
            name_tutor_user = itemView.findViewById(R.id.name_tutor_user);
            last_name_tutor_user = itemView.findViewById(R.id.last_name_tutor_user);
            img = itemView.findViewById(R.id.img);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}