package vn.tdc.edu.fooddelivery.fragments.user;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import vn.tdc.edu.fooddelivery.R;
import vn.tdc.edu.fooddelivery.components.FormartCurentcy;
import vn.tdc.edu.fooddelivery.fragments.AbstractFragment;
import vn.tdc.edu.fooddelivery.models.ProductModel_Test;
import vn.tdc.edu.fooddelivery.utils.FileUtils;

public class PaymentFragment extends AbstractFragment {
    private EditText note;
    private EditText deliveryAddress;
    private EditText edt_date;
    private Button buttonBuy;
    private TextView txtPrice;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private View fragmentLayout = null;
    private EditText edtHours;
    private EditText edtMinute;
    private CartFragment cartFragment = new CartFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentLayout = inflater.inflate(R.layout.payment_layout, container, false);
        //--------------------------Processing-------------------
        anhXa();
        AnhXaDatePicket();
        buyBtnClick();
        CalculateAndAssign(FileUtils.cartList);
        hoursPicker();
        minutePicker();
        //--------------------------------------------
        return fragmentLayout;
    }


    public void hoursPicker() {
        edtHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnhXaTimePicket();
            }
        });
    }

    public void minutePicker() {
        edtMinute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnhXaTimePicket();
            }
        });
    }

    private void buyBtnClick() {
        buttonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean timeOk = validateTimeAction();
                boolean dateOk = validateDateAction();
                if (timeOk && dateOk) {
                    Toast.makeText(fragmentLayout.getContext(), "Mua", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(fragmentLayout.getContext(), "loi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //--------------------Star validate date field---------------------//
    private boolean validateDateAction() {
        //date
        boolean dateOk = checkDateCharacter(edt_date.getText() + "");
        if (dateOk == false) {
            if (edt_date.getError() == null) {
                edt_date.setError("Yêu cần nhập đúng định dạng : d/m/y");
            }
        } else {
            edt_date.setError(null);
        }
        return dateOk;
    }

    //---------------------validate date-------------------------//
    private boolean checkDateCharacter(String date) {

        ArrayList<String> arrayList = new ArrayList<>();
        int index = 0;
        String parts = null;
        boolean ok = false;
        int count = 0;
        if (date.trim().isEmpty()) {
            edt_date.setError("Vui lòng không để trống");
            return ok;
        } else {
            if (date.contains("/")) {
                for (int i = 0; i < date.length(); i++) {
                    if (date.charAt(i) == '/') {
                        count++;
                        //lay ngay va thang!
                        try {
                            arrayList.add(date.substring(index, i));
                            index = i + 1;
                            //lay nam!
                            if (count == 2) {
                                arrayList.add(date.substring(index));
                            }
                        } catch (Exception exception) {
                            return ok;
                        }
                    } else if (date.charAt(i) == ' ') {
                        edt_date.setError("yeu cau khong nhap khoang trong");
                        return ok;
                    } else {
                        if (!isNumber(date.charAt(i) + "")) {
                            edt_date.setError("yeu cau khong nhap cac ky tu dac biet");
                            return ok;
                        }
                    }
                }
                if (count == 2 && arrayList.size() == 3) {
                    if (validateDate(arrayList)) {
                        ok = true;
                    }
                }
            }
        }
        return ok;
    }

    public boolean validateDate(ArrayList<String> arrayList) {
        boolean ok = false;
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).trim().isEmpty()) {
                return ok;
            }
        }
        //Thang 1-12
        int day = Integer.parseInt(arrayList.get(0).trim());
        int month = Integer.parseInt(arrayList.get(1).trim());
        int year = Integer.parseInt(arrayList.get(2).trim());
        //thang 30 ngay = 4 6 9 11

        if (month <= 0 || month > 12 || day <= 0 || day > 31 || year <= 0) {
            if (month <= 0 || month > 12) {
                edt_date.setError("khong ton tai thang " + month);
            } else if (day <= 0 || day > 31) {
                edt_date.setError("khong ton tai ngay " + day);
            } else {
                edt_date.setError("khong ton tai nam " + year);
            }
            return ok;
        } else {
            if (month == 4 || month == 6 || month == 9 || month == 11) {
                if (day > 30) {
                    edt_date.setError("thang " + month + " khong co ngay " + day);
                    return ok;
                }
            } else if (month == 2) {
                //nam nhuan
                if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                    if (day > 29) {
                        edt_date.setError("thang " + month + " khong co ngay " + day);
                        return ok;
                    }
                } else {
                    if (day > 28) {
                        edt_date.setError("nam " + year + " la nam nhuan nen thang " + month + " khong co ngay " + day);
                        return ok;
                    }
                }
            } else {
                if (day > 31) {
                    edt_date.setError("thang " + month + " khong co ngay " + day);
                    return ok;
                }
            }
        }
        //past all conditions
        ok = true;
        return ok;
    }

    public static boolean isNumber(String str) {
        return str.matches("-?\\d+(\\d+)?");
    }
//---------------------------End-------------------------------------//

    public boolean validateTimeAction() {
        boolean timeIsOke = validateTime();
        if (timeIsOke) {
            edtHours.setError(null);
            edtMinute.setError(null);
        }
        return timeIsOke;
    }

    public boolean validateTime() {
        boolean ok = false;
        String strHours = edtHours.getText().toString();
        String strMinute = edtMinute.getText().toString();
        boolean hoursIsNumber = false;
        boolean minuteIsNumber = false;
        hoursIsNumber = isNumber(strHours);
        minuteIsNumber = isNumber(strMinute);
        if (strHours.trim().isEmpty() || strMinute.trim().isEmpty()) {
            if (strHours.trim().isEmpty()) {
                edtHours.setError("Vui lòng không để trống giờ");
            }
            if (strMinute.trim().isEmpty()) {
                edtMinute.setError("Vui lòng không để trống phút");
            }
            return ok;
        } else {
            if (hoursIsNumber && minuteIsNumber) {
                int hoursInt = Integer.parseInt(edtHours.getText().toString());
                int minuteInt = Integer.parseInt(edtMinute.getText().toString());
                if (hoursInt < 0 || hoursInt > 23) {
                    edtHours.setError("Không tồn tại " + hoursInt + " giờ");
                    return ok;
                }
                if (minuteInt < 0 || minuteInt > 59) {
                    edtMinute.setError("Không tồn tại " + minuteInt + " phút");
                    return ok;
                }
                ok = true;
            } else {
                if (hoursIsNumber == false) {
                    if (strHours.contains(" ")) {
                        edtHours.setError("Vui lòng không nhập khoảng trắng");
                    } else {
                        edtHours.setError("Vui lòng nhập số và không chứa các ký tự đặt biệt");
                    }
                }
                if (minuteIsNumber == false) {
                    if (strMinute.contains(" ")) {
                        edtMinute.setError("Vui lòng không nhập khoảng trắng");
                    } else {
                        edtMinute.setError("Vui lòng nhập số và không chứa các ký tự đặt biệt");
                    }
                }
            }
        }
        return ok;
    }

    //-----------------------Star date picket-----------------------------//
    public void AnhXaDatePicket() {
        edt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(fragmentLayout.getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener, year, month, day);
                datePickerDialog.setIcon(R.drawable.bien_canh_bao_vang);
                datePickerDialog.setTitle("Hãy nhập ngày nhập hàng");
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

    //-----------------------End date picket-----------------------------//
    public void anhXa() {
        //Action
        note = fragmentLayout.findViewById(R.id.edt_memory_payment_screen);
        deliveryAddress = fragmentLayout.findViewById(R.id.edt_address_receive_item_address_payment_screen);
        txtPrice = fragmentLayout.findViewById(R.id.txt_price_paymetn_screen);
        edt_date = fragmentLayout.findViewById(R.id.edt_date_receive_item_payment_screen);
        edtHours = fragmentLayout.findViewById(R.id.spinner_hours_payment_screen);
        edtMinute = fragmentLayout.findViewById(R.id.spinner_minute_payment_screen);
        //layout
        buttonBuy = fragmentLayout.findViewById(R.id.btn_buy_payment_screen);
    }

    //-----------------------Picker hours-------------------------//
    public void AnhXaTimePicket() {

        int hoursOfDay = 23;
        int minute = 55;
        boolean is24HoursView = true;
        TimePickerDialog timePickerDialog = new TimePickerDialog(fragmentLayout.getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        edtHours.setText(hourOfDay + "");
                        edtMinute.setText(minute + "");
                    }
                }, hoursOfDay, minute, is24HoursView);
        timePickerDialog.setIcon(R.drawable.bien_canh_bao_vang);
        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.setTitle("Hãy chọn thời gian giao hàng");
        timePickerDialog.show();

    }

    //------------------------Total---------------------------//
    public void CalculateAndAssign(ArrayList<ProductModel_Test> cartArrayList) {
        int sum = 0;
        for (int i = 0; i < cartArrayList.size(); i++) {
            sum += cartArrayList.get(i).getPrice() * cartArrayList.get(i).getQty();
        }
        String value = FormartCurentcy.format(sum + "");
        if (txtPrice == null) {
            txtPrice = fragmentLayout.findViewById(R.id.total_cart_screen);
        }
        txtPrice.setText(value + " đồng ");
    }


}