package com.appsnipp.claraerp.saibookhouse.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appsnipp.claraerp.saibookhouse.Model.LoginResponse;
import com.appsnipp.claraerp.saibookhouse.Network.APIClient;
import com.appsnipp.claraerp.saibookhouse.Network.ApiHolder;
import com.appsnipp.claraerp.saibookhouse.R;
import com.appsnipp.claraerp.saibookhouse.Utils.Constant;
import com.appsnipp.claraerp.saibookhouse.Utils.Sharedpreferences;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener {
    TextView createAccount,loginbutton,forgotbutton;
    TextInputEditText username,password;
    ApiHolder apiHolder;
    Sharedpreferences sharedpreferences;
    ImageView showpass;
    String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,Manifest.permission.CALL_PHONE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        checkPermissions();
        init();
        setclicklistner();
    }
    public void init(){
        // call sharedpreference class.............................
        sharedpreferences=Sharedpreferences.getInstance(LoginScreen.this);
        // call retrofit class............................
        apiHolder= APIClient.getclient().create(ApiHolder.class);
        createAccount=findViewById(R.id.createaccount);
        loginbutton=findViewById(R.id.loginbutton);
        forgotbutton=findViewById(R.id.forgot);
        username=findViewById(R.id.useremail);
        password=findViewById(R.id.userpass);
        showpass=findViewById(R.id.showpass);

    }

    public void setclicklistner(){
        createAccount.setOnClickListener(this);
        showpass.setOnClickListener(this);
        loginbutton.setOnClickListener(this);
        forgotbutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.createaccount:
                Intent intent=new Intent(LoginScreen.this,RegistrationScreen.class);
                startActivity(intent);
                break;
            case R.id.showpass:
                ShowHidePass(showpass);
                break;
            case R.id.loginbutton:
                if(username.getText().length()==0||password.getText().length()==0){
                    if(username.getText().length()==0){
                        username.setError("Enter Email Or Phone Number!!");
                        username.requestFocus();
                        return;
                    }
                    if(password.getText().length()==0){
                        password.setError("Enter Password!!");
                        password.requestFocus();
                        return;
                    }
                }
                else {
                    HitApiForLogin(username.getText().toString(),password.getText().toString());

                }
                break;
            case R.id.forgot:
                startActivity(new Intent(LoginScreen.this,Forgot.class));
                break;

        }

    }
    public void HitApiForLogin(String username,String pass){
        Constant.loadingpopup(LoginScreen.this,"Login");
        Map<String,String> fields=new HashMap<>();
        fields.put("authenticate_id",username);
        fields.put("auth_password",Constant.MD5(pass));
        Call<LoginResponse> call=apiHolder.Login(Constant.Headers(),fields);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Constant.StopLoader();
                if(response.isSuccessful()){
                    LoginResponse getdata= response.body();
                    long errorcode=getdata.getStatus();
                    if(errorcode==200){
                        LoginResponse.Response response1=getdata.getResponse();
                        Toast.makeText(getApplicationContext(),getdata.getMessage(),Toast.LENGTH_SHORT);
                        sharedpreferences.islogged(true);
                        sharedpreferences.setCustomerData("customer_id",response1.getCustomerId());
                        sharedpreferences.setCustomerData("customer_name",response1.getCustomerName());
                        sharedpreferences.setCustomerData("customer_mobile",response1.getCustomerMobile());
                        sharedpreferences.setCustomerData("alternate_number",response1.getAlternateNumber());
                        sharedpreferences.setCustomerData("customer_address",response1.getCustomerAddress());
                        sharedpreferences.setCustomerData("area",response1.getArea());
                        sharedpreferences.setCustomerData("city",response1.getCity());
                        sharedpreferences.setCustomerData("customer_state",response1.getCustomerState());
                        sharedpreferences.setCustomerData("state_pincode",response1.getStatePincode());
                        Intent intent=new Intent(LoginScreen.this,MainPageActivity.class);
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
                        Toast.makeText(LoginScreen.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Constant.StopLoader();

            }
        });


    }
    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ActivityCompat.checkSelfPermission(LoginScreen.this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(LoginScreen.this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
            return false;
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v("Permission", "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
        }
    }
    public void ShowHidePass(View view){

        if(view.getId()==R.id.showpass){

            if(password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.hidepassicon);
                //Show Password
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                password.setSelection(password.getText().length());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.showpassicon);
                //Hide Password
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                password.setSelection(password.getText().length());
            }
        }
    }
}