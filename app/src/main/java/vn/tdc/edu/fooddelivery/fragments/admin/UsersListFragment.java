package vn.tdc.edu.fooddelivery.fragments.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
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
import vn.tdc.edu.fooddelivery.activities.AbstractActivity;
import vn.tdc.edu.fooddelivery.adapters.ProductManagementRecyclerViewAdapter;
import vn.tdc.edu.fooddelivery.adapters.UserManagementRecyclerViewAdapter;
import vn.tdc.edu.fooddelivery.api.ProductAPI;
import vn.tdc.edu.fooddelivery.api.RoleAPI;
import vn.tdc.edu.fooddelivery.api.UserAPI;
import vn.tdc.edu.fooddelivery.api.builder.RetrofitBuilder;
import vn.tdc.edu.fooddelivery.components.ConfirmDialog;
import vn.tdc.edu.fooddelivery.fragments.AbstractFragment;
import vn.tdc.edu.fooddelivery.models.ProductModel;
import vn.tdc.edu.fooddelivery.models.RoleModel;
import vn.tdc.edu.fooddelivery.models.UserModel;

public class UsersListFragment extends AbstractFragment implements UserManagementRecyclerViewAdapter.OnRecylerViewItemClickListener {
    private RecyclerView recyclerViewUser;
    private UserManagementRecyclerViewAdapter adapter;
    private List<UserModel> listUsers;
    private RoleModel roleModel;

    public RoleModel getRoleModel() {
        return roleModel;
    }

    public void setRoleModel(RoleModel roleModel) {
        this.roleModel = roleModel;
    }

    public List<UserModel> getListUsers() {
        return listUsers;
    }

    public void setListUsers(List<UserModel> listUsers) {
        this.listUsers = listUsers;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users_list, container, false);
        recyclerViewUser = view.findViewById(R.id.recyclerViewUser);

        getUserListFromAPI(roleModel == null ? null : roleModel.getCode());

        return  view;
    }

    private void deleteUser(UserModel userModel) {
        Call<UserModel> call = RetrofitBuilder.getClient().create(UserAPI.class).delete(userModel);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    ((AbstractActivity) getActivity()).showMessageDialog("Xoá người dùng thành công");
                    listUsers.remove(userModel);
                    adapter.notifyDataSetChanged();
                } else {
                    ((AbstractActivity) getActivity()).showMessageDialog("Xoá người dùng thất bại");
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                ((AbstractActivity) getActivity()).showMessageDialog("Xoá người dùng thất bại");
            }
        });
    }

    private void getUserListFromAPI(String roleCode) {
        Call<List<UserModel>> call = RetrofitBuilder.getClient().create(UserAPI.class).getAll(roleCode);

        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if (response.code() == HttpURLConnection.HTTP_OK || response.code() == HttpURLConnection.HTTP_CREATED) {
                    if (listUsers == null) {
                        listUsers = new ArrayList<>();
                    }

                    listUsers.clear();
                    listUsers.addAll(response.body());

                    adapter = new UserManagementRecyclerViewAdapter((AppCompatActivity) getActivity(), R.layout.recycler_user_management, listUsers);
                    adapter.setRecylerViewItemClickListener(UsersListFragment.this);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerViewUser.setLayoutManager(layoutManager);
                    recyclerViewUser.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onButtonEditClickListener(int position) {
        ((AbstractActivity) getActivity()).setFragment(UserFormFragment.class, R.id.frameLayout, true)
                .setUserModel(listUsers.get(position));
    }

    @Override
    public void onButtonDeleteClickListener(int position) {
        ConfirmDialog confirmDialog = new ConfirmDialog(getActivity());
        confirmDialog.setTitle("Xác nhận");
        confirmDialog.setMessage("Dữ liệu đã xoá không thể hoàn tác.\nBạn có muốn tiếp tục không?");
        confirmDialog.setOnDialogComfirmAction(new ConfirmDialog.DialogComfirmAction() {
            @Override
            public void cancel() {
                confirmDialog.dismiss();
            }

            @Override
            public void ok() {
                UserModel userModel = new UserModel();
                userModel.setId(listUsers.get(position).getId());
                deleteUser(userModel);
                confirmDialog.dismiss();
            }
        });

        confirmDialog.show();
    }
}