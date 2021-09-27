package com.appsnipp.claraerp.saibookhouse.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appsnipp.claraerp.saibookhouse.Model.ForgetOtpModel;
import com.appsnipp.claraerp.saibookhouse.Model.PasswordUpdateModel;
import com.appsnipp.claraerp.saibookhouse.Network.APIClient;
import com.appsnipp.claraerp.saibookhouse.Network.ApiHolder;
import com.appsnipp.claraerp.saibookhouse.R;
import com.appsnipp.claraerp.saibookhouse.Utils.Constant;
import com.appsnipp.claraerp.saibookhouse.Utils.Sharedpreferences;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Forgot extends AppCompatActivity implements View.OnClickListener{
    EditText mobNumber,password,cnfrmpassword;
    TextView Getotp,titletxt,head;
    ApiHolder apiHolder;
    Sharedpreferences sharedpreferences;
    ImageView showpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        init();
        setClicklictner();
    }
    public void init(){
        apiHolder= APIClient.getclient().create(ApiHolder.class);
        sharedpreferences=Sharedpreferences.getInstance(this);
        mobNumber=findViewById(R.id.enterMobNo);
        password=findViewById(R.id.password);
        showpass=findViewById(R.id.showpass);
        cnfrmpassword=findViewById(R.id.cnfrmpass);
        titletxt=findViewById(R.id.titletxt);
        head=findViewById(R.id.message);
        Getotp=findViewById(R.id.verifyOtp);
        try {
            if (Constant.ClassType.equals("ForgotScreen")) {
                head.setVisibility(View.GONE);
                titletxt.setText("RESET PASSWORD");
                mobNumber.setVisibility(View.GONE);
                password.setVisibility(View.VISIBLE);
                cnfrmpassword.setVisibility(View.VISIBLE);
                Getotp.setText("SAVE CHANGES");
                showpass.setVisibility(View.VISIBLE);
            } else {
                head.setVisibility(View.VISIBLE);
                titletxt.setText("Verify Mobile Number");
                mobNumber.setVisibility(View.VISIBLE);
                password.setVisibility(View.GONE);
                cnfrmpassword.setVisibility(View.GONE);
                Getotp.setText("GET OTP");
                showpass.setVisibility(View.GONE);
            }
        }
        catch (Exception e){

        }
    }
    public void setClicklictner(){
        Getotp.setOnClickListener(this);
        showpass.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.verifyOtp:
                try {
                    if (Constant.ClassType.equals("ForgotScreen")) {
                        if (password.getText().length() == 0 || cnfrmpassword.getText().length() == 0 || !password.getText().toString().equals(cnfrmpassword.getText().toString())) {
                            if (password.getText().length() == 0) {
                                password.setError("Enter Password!!");
                                password.requestFocus();
                                return;
                            }
                            if (cnfrmpassword.getText().length() == 0) {
                                cnfrmpassword.setError("Enter Confirm Password!!");
                                cnfrmpassword.requestFocus();
                                return;
                            }
                            if (!password.getText().toString().equals(cnfrmpassword.getText().toString())) {
                                Toast.makeText(Forgot.this, "Password Don't Match!!", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            if (!sharedpreferences.getMobNumber().equals("")) {
                                HitApiForUpdatePassWord(sharedpreferences.getMobNumber(), password.getText().toString().trim());
                            } else {
                                Toast.makeText(getApplicationContext(), "Mobile Number Is Missing!!", Toast.LENGTH_SHORT).show();
                            }
                        }

                    } else {
                        if (mobNumber.getText().length() == 0 || mobNumber.getText().length() < 10) {
                            if (mobNumber.getText().length() == 0) {
                                mobNumber.setError("Enter Mobile Number!!");
                                mobNumber.requestFocus();
                                return;
                            }
                            if (mobNumber.getText().length() < 10) {
                                mobNumber.setError("Enter Valid Mobile Number!!");
                                mobNumber.requestFocus();
                                return;
                            }
                        } else {
                            sharedpreferences.setMobNumber(mobNumber.getText().toString().trim());
                            HitApiForGetOtp(mobNumber.getText().toString());
                        }
                    }
                }
                catch (Exception e){

                }
                break;
            case R.id.showpass:
                ShowHidePass(showpass);
                break;


        }

    }

    public void ShowHidePass(View view){

        if(view.getId()==R.id.showpass){

            if(cnfrmpassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.hidepassicon);
                //Show Password
                cnfrmpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                cnfrmpassword.setSelection(cnfrmpassword.getText().length());

            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.showpassicon);
                //Hide Password
                cnfrmpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                cnfrmpassword.setSelection(cnfrmpassword.getText().length());

            }
        }
    }

    public void HitApiForGetOtp(String MobNumber){
        Constant.loadingpopup(Forgot.this,"Sending Otp On Register Number");
        Map<String,String> fields=new HashMap<>();
        fields.put("mobile_number",MobNumber);
        Call<ForgetOtpModel> call=apiHolder.getForgetOtpModel(Constant.Headers(),fields);
        call.enqueue(new Callback<ForgetOtpModel>() {
            @Override
            public void onResponse(Call<ForgetOtpModel> call, Response<ForgetOtpModel> response) {
                Constant.StopLoader();
                if(response.isSuccessful()){
                    ForgetOtpModel getdata=response.body();
                    long errorcode=getdata.getStatus();
                    if(errorcode==200){
                        Toast.makeText(Forgot.this,getdata.getMessage(),Toast.LENGTH_SHORT).show();
                        Constant.ClassType="ForgotScreen";
                        Intent intent=new Intent(Forgot.this,VerifyOTP.class);
                        try {
                            intent.putExtra("OTP",Constant.Decrypt(getdata.getResponse()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        startActivity(intent);
                    }
                }
                else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String status=jObjError.getString("status");
                        if(status.equals("401")){
                            Toast.makeText(getApplicationContext(),jObjError.getString("message"),Toast.LENGTH_SHORT).show();
                        }
                        else if (status.equals("400"))
                        {
                            Toast.makeText(getApplicationContext(),jObjError.getString("message"),Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),jObjError.getString("message"),Toast.LENGTH_SHORT).show();

                        }

                    } catch (Exception e) {
                        Toast.makeText(Forgot.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ForgetOtpModel> call, Throwable t) {
             Constant.StopLoader();
            }
        });

    }

    public void HitApiForUpdatePassWord(String mobnumber,String password){
        Constant.loadingpopup(Forgot.this,"Password is updating");
        Map<String,String>fields=new HashMap<>();
        fields.put("mobile_number",mobnumber);
        fields.put("password",Constant.MD5(password));
        Call<PasswordUpdateModel> call=apiHolder.updatePasswordForget(Constant.Headers(),fields);
        call.enqueue(new Callback<PasswordUpdateModel>() {
            @Override
            public void onResponse(Call<PasswordUpdateModel> call, Response<PasswordUpdateModel> response) {
                Constant.StopLoader();
                if(response.isSuccessful()){
                    PasswordUpdateModel getdata=response.body();
                    long errorcode=getdata.getStatus();
                    if(errorcode==200){
                        Constant.ClassType="";
                        Toast.makeText(Forgot.this,getdata.getMessage(),Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(),LoginScreen.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }
                else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String status=jObjError.getString("status");
                        if(status.equals("401")){
                            Toast.makeText(getApplicationContext(),jObjError.getString("message"),Toast.LENGTH_SHORT).show();
                        }
                        else if (status.equals("400"))
                        {
                            Toast.makeText(getApplicationContext(),jObjError.getString("message"),Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),jObjError.getString("message"),Toast.LENGTH_SHORT).show();

                        }

                    } catch (Exception e) {
                        Toast.makeText(Forgot.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PasswordUpdateModel> call, Throwable t) {

            }
        });
    }
}