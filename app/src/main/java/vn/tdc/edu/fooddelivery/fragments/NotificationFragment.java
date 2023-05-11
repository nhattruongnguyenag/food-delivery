package vn.tdc.edu.fooddelivery.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.activities.MainActivity;
import vn.tdc.edu.fooddelivery.adapters.MyNotificationRecyclerViewAdapter_Hanh;
import vn.tdc.edu.fooddelivery.models.NotificationModel_Hanh;
import vn.tdc.edu.fooddelivery.utils.FileUtils;

public class NotificationFragment extends AbstractFragment {
    private RecyclerView recyclerView;
    private MyNotificationRecyclerViewAdapter_Hanh myAdapter;
    private View fragmentLayout = null;
    private LinearLayout linearLayoutWrapper;
    private MainActivity mainActivity = new MainActivity();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentLayout = inflater.inflate(R.layout.fragment_notification, container, false);
        //---------------------Start---------------------------
        fakeData();
        anhXa();
        clickEvent();
        createAnimation();
        //---------------------End-----------------------------
        return fragmentLayout;
    }

    public void createAlertDialog(int cart) {
        AlertDialog.Builder builder = new AlertDialog.Builder(fragmentLayout.getContext());
        builder.setIcon(R.drawable.ic_baseline_warning_24);
        builder.setTitle("Bạn có muốn xóa sản phẩm này chứ!");

        builder.setPositiveButton("có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FileUtils.arrayListNotifications.remove(cart);
                updateArrayListCart();
                createAnimation();
                mainActivity.catchDataNotifyIcon();
            }
        });

        builder.setNegativeButton("không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    public void updateArrayListCart() {
        myAdapter.notifyDataSetChanged();
    }


    public void anhXa() {

        linearLayoutWrapper = fragmentLayout.findViewById(R.id.linearLayout_wrapper_notification);
        recyclerView = fragmentLayout.findViewById(R.id.recyclerView_notification_screen);
        //Setup
        LinearLayoutManager layoutManager = new LinearLayoutManager(fragmentLayout.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        myAdapter = new MyNotificationRecyclerViewAdapter_Hanh((Activity) fragmentLayout.getContext(), R.layout.notification_layout_item, FileUtils.arrayListNotifications);
        recyclerView.setAdapter(myAdapter);
    }

    private void clickEvent() {
        myAdapter.setOnRecyclerViewOnClickListener(new MyNotificationRecyclerViewAdapter_Hanh.onRecyclerViewOnClickListener() {
            @Override
            public void onItemRecyclerViewClickListener(int position, View cardView) {
                createAlertDialog(position);
            }
        });
    }

    public void createAnimation() {
        if (FileUtils.arrayListNotifications.size() == 0) {
            LottieAnimationView anim = new LottieAnimationView(fragmentLayout.getContext());
            anim.setAnimation(R.raw.nothing_gift4);
            TranslateAnimation ta = new TranslateAnimation(0, 0, Animation.RELATIVE_TO_SELF, 100);
            ta.setDuration(1000);
            ta.setFillAfter(true);
            anim.startAnimation(ta);
            anim.playAnimation();
            anim.loop(true);
            linearLayoutWrapper.addView(anim);
        } else {
            linearLayoutWrapper.removeAllViews();
        }
    }

    public void fakeData() {
        NotificationModel_Hanh notification1 = new NotificationModel_Hanh(R.drawable.ic_baseline_circle_notifications_24,
                "Đặt món thành công",
                1,
                " Đơn hàng bản đã được tiếp nhận và sẽ " +
                        "được giao trong khoảng thời gian sớm nhất" +
                        ". Xin chân thành cảm ơn.");
        NotificationModel_Hanh notification2 = new NotificationModel_Hanh(R.drawable.anh_nhopng,
                "Đặt món không thành công",
                1,
                " Đơn hàng bản đã không được tiếp nhận và sẽ " +
                        "được giao trong khoảng thời gian sớm nhất" +
                        ". Xin chân thành cảm ơn.");
        if (FileUtils.arrayListNotifications == null) {
            FileUtils.arrayListNotifications = new ArrayList<>();
        }
    }
}