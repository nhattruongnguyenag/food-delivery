package vn.tdc.edu.fooddelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.tdc.edu.fooddelivery.api.RetrofitClient;

public class MainActivity extends AppCompatActivity {
    private Button btnAdd;
    private EditText name;
    private EditText price;
    private EditText quantity;
    private EditText photo;
    private EditText categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        quantity = findViewById(R.id.quantity);
        photo = findViewById(R.id.photo);
        categoryId = findViewById(R.id.categoryId);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = MainActivity.this.name.getText().toString();
                String price =  MainActivity.this.price.getText().toString();
                String quantity =  MainActivity.this.quantity.getText().toString();
                String photo = MainActivity.this.photo.getText().toString();
                String categoryId = MainActivity.this.categoryId.getText().toString();
                String description = "Chua co mo ta";

                ProductModel productModel = new ProductModel();
                productModel.setName("San pham 3 - android");

                Call<ProductModel> call = RetrofitClient.getInstance().getProductAPI().createProduct(productModel);

                call.enqueue(new Callback<ProductModel>() {
                    @Override
                    public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                        Toast.makeText(MainActivity.this, "Them thanh cong !!!", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(MainActivity.this, response.body().getName(), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<ProductModel> call, Throwable t) {
                        Log.d("add product", t.getMessage());
                        Toast.makeText(MainActivity.this, "Loi !!!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}