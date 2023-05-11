package vn.tdc.edu.fooddelivery.fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.models.CartModel_Hanh;
import vn.tdc.edu.fooddelivery.utils.FileUtils;

public class PaymentFragment extends AbstractFragment {
    private Button buttonBuy;
    private EditText edt_date;
    private TextView txtPrice;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private View fragmentLayout = null;
    private CartFragment cartFragment = new CartFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentLayout = inflater.inflate(R.layout.payment_layout, container, false);
        //---------------------------------------------
        anhXa();
        AnhXaDatePicket();
//      Catch event buy click
        buyBtnClick();
        CalculateAndAssign(FileUtils.cartList);
        //--------------------------------------------
        return fragmentLayout;
    }

    private void buyBtnClick() {
        buttonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(fragmentLayout.getContext(), "Mua", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void AnhXaDatePicket() {
        edt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(fragmentLayout.getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                m = m + 1;
                String dateValue = d + "/" + m + "/" + y;
                edt_date.setText(dateValue);
            }
        };
    }

    public void anhXa() {
        txtPrice = fragmentLayout.findViewById(R.id.txt_price_paymetn_screen);
        edt_date = fragmentLayout.findViewById(R.id.edt_date_receive_item_payment_screen);
        buttonBuy = fragmentLayout.findViewById(R.id.btn_buy_payment_screen);
    }

    public void CalculateAndAssign(ArrayList<CartModel_Hanh> cartArrayList) {
        int sum = 0;
        for (int i = 0; i < cartArrayList.size(); i++) {
            sum += cartArrayList.get(i).getPrice() * cartArrayList.get(i).getQty();
        }
        String value = cartFragment.formatCurrentcy(sum + "");
        if (txtPrice == null) {
            txtPrice = fragmentLayout.findViewById(R.id.total_cart_screen);
        }
        txtPrice.setText(value + " đồng ");
    }
}