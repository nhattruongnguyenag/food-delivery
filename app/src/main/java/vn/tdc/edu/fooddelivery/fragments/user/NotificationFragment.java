package vn.tdc.edu.fooddelivery.fragments.user;

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

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.activities.user.MainActivity;
import vn.tdc.edu.fooddelivery.adapters.NotificationRecyclerViewAdapter;
import vn.tdc.edu.fooddelivery.fragments.AbstractFragment;
import vn.tdc.edu.fooddelivery.models.NotificationModel_Test;
import vn.tdc.edu.fooddelivery.utils.FileUtils;

public class NotificationFragment extends AbstractFragment {
    private RecyclerView recyclerView;
    private NotificationRecyclerViewAdapter myAdapter;
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

    //-----------------------Fake alert-----------------//
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
        setUp();
    }

    public void setUp() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(fragmentLayout.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        myAdapter = new NotificationRecyclerViewAdapter((Activity) fragmentLayout.getContext(), R.layout.notification_layout_item, FileUtils.arrayListNotifications);
        recyclerView.setAdapter(myAdapter);
    }

    private void clickEvent() {
        myAdapter.setOnRecyclerViewOnClickListener(new NotificationRecyclerViewAdapter.onRecyclerViewOnClickListener() {
            @Override
            public void onItemRecyclerViewClickListener(int position, View cardView) {
                createAlertDialog(position);
            }
        });
    }

    //-----------------------------Animation when not have notify -----------------------//
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

    //------------------------Fake data--------------------------------//
    public void fakeData() {
        NotificationModel_Test notification1 = new NotificationModel_Test(R.drawable.ic_baseline_circle_notifications_24,
                "Đặt món thành công",
                1,
                " Đơn hàng bản đã được tiếp nhận và sẽ " +
                        "được giao trong khoảng thời gian sớm nhất" +
                        ". Xin chân thành cảm ơn.");
        NotificationModel_Test notification2 = new NotificationModel_Test(R.drawable.anh_nhopng,
                "Đặt món không thành công",
                1,
                " Đơn hàng bản đã không được tiếp nhận và sẽ " +
                        "được giao trong khoảng thời gian sớm nhất" +
                        ". Xin chân thành cảm ơn.");
        FileUtils.arrayListNotifications.add(notification1);
        FileUtils.arrayListNotifications.add(notification2);
    }
}