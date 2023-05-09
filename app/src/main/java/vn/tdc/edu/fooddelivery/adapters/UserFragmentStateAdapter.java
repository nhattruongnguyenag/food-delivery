package vn.tdc.edu.fooddelivery.adapters;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import retrofit2.Callback;
import vn.tdc.edu.fooddelivery.fragments.admin.UsersListFragment;
import vn.tdc.edu.fooddelivery.models.RoleModel;
import vn.tdc.edu.fooddelivery.models.UserModel;

public class UserFragmentStateAdapter extends FragmentStateAdapter {
    private Fragment fragment;
    private List<RoleModel> listRoles;
    public void setListRoles(List<RoleModel> listRoles) {
        this.listRoles = listRoles;
    }

    public UserFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public UserFragmentStateAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public UserFragmentStateAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        fragment = new UsersListFragment();

        if (position > 0) {
            ((UsersListFragment) fragment).setRoleModel(listRoles.get(position - 1));
        }

        return fragment;
    }


    @Override
    public int getItemCount() {
        return listRoles.size() + 1;
    }
}
