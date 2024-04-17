package com.rahuldharmkar.razorpaygatewayintegration;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class MainActivity2 extends AppCompatActivity implements PaymentResultListener {

    private Button btnPayNow;
    private EditText editAmount;
    private TextView txtPaymentStatus;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        txtPaymentStatus = findViewById(R.id.paymentStatus);
        editAmount = findViewById(R.id.edit_amount);
        btnPayNow = findViewById(R.id.btn_pay);

        Checkout.preload(MainActivity2.this);

        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiatePayment(Integer.parseInt(editAmount.getText().toString()));
            }
        });
    }

    public void  initiatePayment(int amount){
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_SAeAxOVdY3v4t8");

        try {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("name", "IVEPOS DEMO");
            jsonObject.put("description", "Please Pay!");
            jsonObject.put("theme.color", "#65bc7b");
            jsonObject.put("currency", "INR");
            jsonObject.put("amount", amount * 100);

            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 5);

            jsonObject.put("retry", retryObj);

            checkout.open(MainActivity2.this, jsonObject);
        } catch (Exception e){
            Toast.makeText(MainActivity2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onPaymentSuccess(String s) {
       txtPaymentStatus.setText(s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        txtPaymentStatus.setText("Error : " + s);
    }
}