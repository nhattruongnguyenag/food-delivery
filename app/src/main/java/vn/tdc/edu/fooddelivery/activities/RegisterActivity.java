package vn.tdc.edu.fooddelivery.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import vn.tdc.edu.fooddelivery.R;

public class RegisterActivity extends AbstractActivity implements View.OnClickListener {
    private TextView tvLoginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);

        tvLoginLink = findViewById(R.id.tvLoginLink);
        tvLoginLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tvLoginLink) {
            switchActivity(LoginActivity.class,"Login");
        }
    }
}