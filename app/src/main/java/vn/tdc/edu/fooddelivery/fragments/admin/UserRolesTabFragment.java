package vn.tdc.edu.fooddelivery.fragments.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.adapters.UserFragmentStateAdapter;
import vn.tdc.edu.fooddelivery.api.RoleAPI;
import vn.tdc.edu.fooddelivery.api.builder.RetrofitBuilder;
import vn.tdc.edu.fooddelivery.models.RoleModel;

public class UserRolesTabFragment extends Fragment {
    private TabLayout tabLayout;
    private List<RoleModel> listRoles;
    private ViewPager2 viewPager2;
    private UserFragmentStateAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_role_tab, container, false);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager2 = view.findViewById(R.id.page);

        createTabLayoutForUserRoles();

        return view;
    }


    private void createTabLayoutForUserRoles() {
        Call<List<RoleModel>> call = RetrofitBuilder.getClient().create(RoleAPI.class).getAll();

        call.enqueue(new Callback<List<RoleModel>>() {
            @Override
            public void onResponse(Call<List<RoleModel>> call, Response<List<RoleModel>> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    if (listRoles == null) {
                        listRoles = new ArrayList<>();
                    }
                    listRoles.clear();
                    listRoles.addAll(response.body());

                    adapter = new UserFragmentStateAdapter(UserRolesTabFragment.this);
                    adapter.setListRoles(listRoles);
                    viewPager2.setAdapter(adapter);

                    new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
                        @Override
                        public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                            if (position == 0) {
                                tab.setText("Tất cả người dùng");
                            } else {
                                adapter.setListRoles(listRoles);
                                tab.setText(listRoles.get(position - 1).getName());
                            }
                        }
                    }).attach();
                }
            }

            @Override
            public void onFailure(Call<List<RoleModel>> call, Throwable t) {

            }
        });
    }
}