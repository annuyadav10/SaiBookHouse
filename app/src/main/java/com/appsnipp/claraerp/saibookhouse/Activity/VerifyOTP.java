package com.appsnipp.claraerp.saibookhouse.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appsnipp.claraerp.saibookhouse.Model.RegistrationModel;
import com.appsnipp.claraerp.saibookhouse.Network.APIClient;
import com.appsnipp.claraerp.saibookhouse.Network.ApiHolder;
import com.appsnipp.claraerp.saibookhouse.R;
import com.appsnipp.claraerp.saibookhouse.Utils.Constant;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyOTP extends AppCompatActivity implements View.OnClickListener {
    TextView verifyOTPTxt,resendotp,countdown;
    EditText OTP1,OTP2,OTP3,OTP4,OTP5;
    String OTP, username, userArea, landmark, city, state, pincode, contactNo, alterContNumber, emailAddress, password,ClassName;
    String EnterOtp;
    ApiHolder apiHolder;
    CountDownTimer countDownTimer;
    boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_o_t_p);
        getIntentValue();
        init();
        setclicklistner();
    }

    public void getIntentValue(){
        try {
            if (Constant.ClassType.equals("RegistrationScreen")) {
                if (getIntent().hasExtra("OTP")) {
                    OTP = getIntent().getStringExtra("OTP");
                }
                if (getIntent().hasExtra("customer_name")) {
                    username = getIntent().getStringExtra("customer_name");
                }
                if (getIntent().hasExtra("customer_mobile")) {
                    contactNo = getIntent().getStringExtra("customer_mobile");
                }
                if (getIntent().hasExtra("customer_email")) {
                    emailAddress = getIntent().getStringExtra("customer_email");
                }
                if (getIntent().hasExtra("alternate_number")) {
                    alterContNumber = getIntent().getStringExtra("alternate_number");
                }
                if (getIntent().hasExtra("customer_address")) {
                    landmark = getIntent().getStringExtra("customer_address");
                }
                if (getIntent().hasExtra("area")) {
                    userArea = getIntent().getStringExtra("area");
                }
                if (getIntent().hasExtra("city")) {
                    city = getIntent().getStringExtra("city");
                }
                if (getIntent().hasExtra("customer_state")) {
                    state = getIntent().getStringExtra("customer_state");
                }
                if (getIntent().hasExtra("state_pincode")) {
                    pincode = getIntent().getStringExtra("state_pincode");
                }
                if (getIntent().hasExtra("hash_password")) {
                    password = getIntent().getStringExtra("hash_password");
                }
            } else {
                if (getIntent().hasExtra("OTP")) {
                    OTP = getIntent().getStringExtra("OTP");
                }
            }
        }
        catch (Exception e){
            
        }

    }

    public void init() {
        // call retrofit class............................
        apiHolder= APIClient.getclient().create(ApiHolder.class);
        verifyOTPTxt = findViewById(R.id.verifyOtp);
        resendotp = findViewById(R.id.resendotp);
        countdown = findViewById(R.id.countdown);
        OTP1 = findViewById(R.id.OTP1);
        OTP2 = findViewById(R.id.OTP2);
        OTP3 = findViewById(R.id.OTP3);
        OTP4 = findViewById(R.id.OTP4);
        OTP5 = findViewById(R.id.OTP5);
    }

    public void setclicklistner() {
        verifyOTPTxt.setOnClickListener(this);
        resendotp.setOnClickListener(this);
        OTP1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(OTP1.getText().length() == 1)
                    OTP2.requestFocus();
                return false;
            }
        });
        OTP2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(OTP2.getText().length() == 1)
                    OTP3.requestFocus();
                return false;
            }
        });
        OTP3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(OTP3.getText().length() == 1)
                    OTP4.requestFocus();
                return false;
            }
        });
        OTP4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(OTP4.getText().length() == 1)
                    OTP5.requestFocus();
                return false;
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.verifyOtp:
                if (OTP1.getText().length() == 0 || OTP2.getText().length() == 0 || OTP3.getText().length() == 0 || OTP4.getText().length() == 0 || OTP5.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Enter OTP ", Toast.LENGTH_SHORT).show();
                } else {
                    EnterOtp = OTP1.getText().toString() + OTP2.getText().toString() + OTP3.getText().toString() + OTP4.getText().toString() + OTP5.getText().toString();
                    try {
                        if(OTP.equals(EnterOtp)) {
                            try {
                                if (Constant.ClassType.equals("RegistrationScreen")) {
                                    Timer();
                                    resendotp.setVisibility(View.GONE);
                                    HitApiForAddUser();
                                } else {
                                    // for Forgot OTPword..........................................
                                    Intent intent = new Intent(getApplicationContext(), Forgot.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }

                            }
                            catch (Exception e){

                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Enter Valid OTP",Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.resendotp:
                if (OTP1.getText().length() == 0 || OTP2.getText().length() == 0 || OTP3.getText().length() == 0 || OTP4.getText().length() == 0 || OTP5.getText().length() == 0 ) {
                    Toast.makeText(getApplicationContext(), "Enter OTP", Toast.LENGTH_SHORT).show();
                }
                else {
                    flag = true;
                    HitApiForAddUser();
                }
                break;
        }

    }
    public void HitApiForAddUser(){
        Constant.loadingpopup(VerifyOTP.this,"Adding User");
        Map<String,String> fields=new HashMap<>();
        fields.put("customer_name",username);
        fields.put("customer_mobile",contactNo);
        fields.put("customer_email",emailAddress);
        fields.put("alternate_number",alterContNumber);
        fields.put("customer_address",landmark);
        fields.put("area",userArea);
        fields.put("city",city);
        fields.put("customer_state",state);
        fields.put("state_pincode",pincode);
        fields.put("hash_password",OTP);

        Call<RegistrationModel>call=apiHolder.registration(Constant.Headers(),fields);
        call.enqueue(new Callback<RegistrationModel>() {
            @Override
            public void onResponse(Call<RegistrationModel> call, Response<RegistrationModel> response) {
                Constant.StopLoader();
                if(response.isSuccessful()){
                    RegistrationModel getdata=response.body();
                    long errorcode=getdata.getStatus();
                    if(errorcode==201){
                        Constant.ClassType="";
                        resendotp.setVisibility(View.VISIBLE);
                        countdown.setVisibility(View.GONE);
                        countdown.setText("00:00");
                        Toast.makeText(getApplicationContext(),getdata.getMessage(),Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(),LoginScreen.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        if(flag==true) {
                            Timer();
                            resendotp.setVisibility(View.GONE);
                        }
                        else {
                            try {
                                resendotp.setVisibility(View.VISIBLE);
                                countdown.setVisibility(View.GONE);
                                countdown.setText("00:00");
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                Toast.makeText(VerifyOTP.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(),"Server Error!!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                }
                else {
                    Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RegistrationModel> call, Throwable t) {
                Constant.StopLoader();
            }
        });
    }

    public void Timer() {
        countdown.setVisibility(View.VISIBLE);
        countDownTimer = new CountDownTimer(120000, 1000) {
            public void onTick(long millisUntilFinished) {
                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);
                long secondsgone = 120 - seconds;
                long secondsleft = 0;

                if (secondsgone > 60) {
                    secondsleft = 120 - secondsgone;
                } else {
                    secondsleft = 60 - secondsgone;
                }

                String Minutes = String.valueOf(minutes);
                String Seconds = String.valueOf(secondsleft);

                if (Minutes.length() == 1) {
                    String nn = Minutes;
                    Minutes = "0" + nn;
                }

                if (Seconds.length() == 1) {
                    String nn = Seconds;
                    Seconds = "0" + nn;
                }

                countdown.setText(Minutes + ":" + Seconds);

                if (countdown.getText().equals("00:01")) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            countdown.setText("00:00");
                            resendotp.setVisibility(View.VISIBLE);
                        }
                    }, 1000);
                }
            }

            public void onFinish() {

            }

        };
        countDownTimer.start();

    }
}