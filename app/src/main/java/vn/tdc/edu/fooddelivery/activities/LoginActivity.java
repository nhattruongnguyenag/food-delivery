package vn.tdc.edu.fooddelivery.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.activities.user.MainActivity;
import vn.tdc.edu.fooddelivery.enums.Role;
import vn.tdc.edu.fooddelivery.models.UserModel;
import vn.tdc.edu.fooddelivery.utils.Authentication;

public class LoginActivity extends AbstractActivity implements View.OnClickListener {
    private EditText edEmail, edPassword;
    private TextView tvRegisterLink;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        tvRegisterLink = findViewById(R.id.tvRegisterLink);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(this);
        tvRegisterLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLogin) {
            UserModel userLoginRequest = new UserModel();
            userLoginRequest.setId(1);
            userLoginRequest.setImageName("user_image_default.png");
            userLoginRequest.setFullName("Nguyen Van A (NV)");
            ArrayList<String> role = new ArrayList<>();
//            role.add(Role.ADMIN.getName());
            role.add(Role.SHIPPER.getName());
            role.add(Role.CUSTOMER.getName());
            userLoginRequest.setRoleCodes(role);
            userLoginRequest.setEmail(edEmail.getText().toString());
            userLoginRequest.setPassword(edPassword.getText().toString());
            login(userLoginRequest);
        } else if (view.getId() == R.id.tvRegisterLink) {
            switchActivity(RegisterActivity.class, "Register");
        }
    }

    private void login(UserModel userModel) {
        if (userModel.getEmail().equalsIgnoreCase("nhattruongnguyenag@gmail.com") && userModel.getPassword().equals("123456")) {
            ((AbstractActivity) this).switchActivity(MainActivity.class,"");
            Authentication.login(userModel);
        } else {

        }
    }
}