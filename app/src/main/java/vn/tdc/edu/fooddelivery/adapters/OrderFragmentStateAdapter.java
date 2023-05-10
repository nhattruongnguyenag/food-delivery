package vn.tdc.edu.fooddelivery.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import vn.tdc.edu.fooddelivery.fragments.admin.OrdersListFragment;
import vn.tdc.edu.fooddelivery.fragments.admin.UsersListFragment;
import vn.tdc.edu.fooddelivery.models.RoleModel;

public class OrderFragmentStateAdapter extends FragmentStateAdapter {
    private Fragment fragment;
    private CharSequence[] listStatus;

    public CharSequence[] getListStatus() {
        return listStatus;
    }

    public void setListStatus(CharSequence[] listStatus) {
        this.listStatus = listStatus;
    }

    public OrderFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public OrderFragmentStateAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public OrderFragmentStateAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        fragment = new OrdersListFragment();

        ((OrdersListFragment) fragment).setStatus(position + 1);
        return fragment;
    }


    @Override
    public int getItemCount() {
        return listStatus.length;
    }
}