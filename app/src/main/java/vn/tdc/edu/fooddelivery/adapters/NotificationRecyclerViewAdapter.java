package vn.tdc.edu.fooddelivery.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.models.NotificationModel_Test;

public class NotificationRecyclerViewAdapter extends RecyclerView.Adapter<NotificationRecyclerViewAdapter.MyViewHolder> {
    private Activity activity;
    private int layout_ID;
    private ArrayList<NotificationModel_Test> arrayList;
    //B2: Definition variable
    private onRecyclerViewOnClickListener onRecyclerViewOnClickListener;

    public NotificationRecyclerViewAdapter(Activity activity, int layout_ID, ArrayList<NotificationModel_Test> arrayList) {
        this.activity = activity;
        this.layout_ID = layout_ID;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        CardView cardView = (CardView) layoutInflater.inflate(viewType, parent, false);
        return new MyViewHolder(cardView);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        NotificationModel_Test notification = arrayList.get(position);
        holder.txt_title.setText(notification.getTitle());
        holder.txt_timing.setText(String.valueOf(notification.getTime()) + " phút trước");
        holder.txt_content_notification.setText(notification.getContent());
        holder.img.setImageDrawable(activity.getResources().getDrawable(notification.getImg(), activity.getTheme()));
        //Catch event click
        holder.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onRecyclerViewOnClickListener != null) {
                    onRecyclerViewOnClickListener.onItemRecyclerViewClickListener(position, holder.itemView);
                }
            }
        };
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return layout_ID;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txt_title;
        private TextView txt_timing;
        private TextView txt_content_notification;
        private ImageView img;
        private View.OnClickListener onClickListener;

        public MyViewHolder(@NonNull View v) {
            super(v);
            txt_title = v.findViewById(R.id.txt_title_notification_screen);
            txt_timing = v.findViewById(R.id.txt_timing_notification_screen);
            txt_content_notification = v.findViewById(R.id.txt_content_notification_screen);
            img = v.findViewById(R.id.img_notification_screen);
            //Catch click event
            txt_title.setOnClickListener(this);
            txt_timing.setOnClickListener(this);
            txt_content_notification.setOnClickListener(this);
            img.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onClickListener != null) {
                onClickListener.onClick(view);
            }
        }
    }

    //B1:Definition interface
    public interface onRecyclerViewOnClickListener {
        public void onItemRecyclerViewClickListener(int position, View cardView);
    }

    //B4:Setter

    public void setOnRecyclerViewOnClickListener(NotificationRecyclerViewAdapter.onRecyclerViewOnClickListener onRecyclerViewOnClickListener) {
        this.onRecyclerViewOnClickListener = onRecyclerViewOnClickListener;
    }
}
