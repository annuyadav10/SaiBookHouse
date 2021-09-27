package com.appsnipp.claraerp.saibookhouse.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appsnipp.claraerp.saibookhouse.Model.MyProfileModel;
import com.appsnipp.claraerp.saibookhouse.Model.ProfileUpdateModel;
import com.appsnipp.claraerp.saibookhouse.Network.APIClient;
import com.appsnipp.claraerp.saibookhouse.Network.ApiHolder;
import com.appsnipp.claraerp.saibookhouse.R;
import com.appsnipp.claraerp.saibookhouse.Utils.Constant;
import com.appsnipp.claraerp.saibookhouse.Utils.Sharedpreferences;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfile extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout name,email,mobNumber,altrmobNumber;
    TextView submit,edit_txt;
    Sharedpreferences sharedpreferences;
    ApiHolder apiHolder;
    ImageView backarrow,cart;
    String customerId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        init();
        setDisableField();
        HitApiForGetProfileData();
        setOnclicklistner();
    }

    public void init(){
        sharedpreferences=Sharedpreferences.getInstance(this);
        apiHolder= APIClient.getclient().create(ApiHolder.class);
        customerId=sharedpreferences.getCustomerData("customer_id");
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        mobNumber=findViewById(R.id.phone);
        altrmobNumber=findViewById(R.id.alternumber);
        submit=findViewById(R.id.submitbutton);
        edit_txt=findViewById(R.id.edittxt);
        backarrow=findViewById(R.id.back);
        cart=findViewById(R.id.cart);
        cart.setVisibility(View.INVISIBLE);
    }
    public void HitApiForGetProfileData(){
        Constant.loadingpopup(MyProfile.this,"Fetching Profile");
        List<String> list = new ArrayList<>();
        list.add("customer_mobile");
        list.add("customer_email");
        list.add("customer_name");
        list.add("alternate_number");
        String sendrequestfor = TextUtils.join(", ", list);

        Map<String,String> fields=new HashMap<>();
        fields.put("customer_id",customerId);
        fields.put("request_for",sendrequestfor);
        Call<MyProfileModel> call=apiHolder.getProfileData(Constant.Headers(),fields);
        call.enqueue(new Callback<MyProfileModel>() {
            @Override
            public void onResponse(Call<MyProfileModel> call, Response<MyProfileModel> response) {
                Constant.StopLoader();
                if(response.isSuccessful()){
                    MyProfileModel getdata=response.body();
                    long errorcode=getdata.getStatus();
                    if(errorcode==200){
                        setData(getdata);
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
                        Toast.makeText(MyProfile.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyProfileModel> call, Throwable t) {

            }
        });
    }

    public void setData(MyProfileModel getdata){
        setDisableField();
        MyProfileModel.Response response=getdata.getResponse();
        name.getEditText().setText(response.getCustomerName());
        email.getEditText().setText(response.getCustomerEmail());
        mobNumber.getEditText().setText(response.getCustomerMobile());
        altrmobNumber.getEditText().setText(response.getAlternateNumber());
        sharedpreferences.setCustomerData("customer_name",response.getCustomerName());
        sharedpreferences.setCustomerData("customer_mail",response.getCustomerEmail());
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edittxt:
                if(edit_txt.getText().equals("Edit Profile")){
                  setEnableField();
                }
                else {
                    if(name.getEditText().getText().length()==0||email.getEditText().getText().length()==0||mobNumber.getEditText().getText().length()==0||
                            altrmobNumber.getEditText().getText().length()==0){
                        HitApiForGetProfileData();
                    }
                    else {
                        setDisableField();
                    }
                }
                break;
            case R.id.submitbutton:
                  if(name.getEditText().getText().length()==0||email.getEditText().getText().length()==0||mobNumber.getEditText().getText().length()==0||
                          altrmobNumber.getEditText().getText().length()==0){
                      if(name.getEditText().getText().length()==0){
                       name.getEditText().setError("Enter Name!!");
                       name.getEditText().requestFocus();
                       return;
                      }
                      if(email.getEditText().getText().length()==0){
                          email.getEditText().setError("Enter Email Id!!");
                          email.getEditText().requestFocus();
                          return;
                      }
                      if(mobNumber.getEditText().getText().length()==0){
                          mobNumber.getEditText().setError("Enter Mobile Number!!");
                          mobNumber.getEditText().requestFocus();
                          return;
                      }
                      if(altrmobNumber.getEditText().getText().length()==0){
                          altrmobNumber.getEditText().setError("Enter Alternate Mobile Number!!");
                          altrmobNumber.getEditText().requestFocus();
                          return;
                      }
                  }
                  else {
                      String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                      if(email.getEditText().getText().toString().matches(emailPattern)){
                        HitApiForProfileUpdate();
                      }
                      else {
                          Toast.makeText(MyProfile.this,"Enter Valid Email Id",Toast.LENGTH_SHORT).show();
                      }

                  }

                break;

            case R.id.back:
                finish();
                break;
        }

    }
    public void setOnclicklistner(){
        edit_txt.setOnClickListener(this);
        submit.setOnClickListener(this);
        backarrow.setOnClickListener(this);
    }
    public void setEnableField(){
        edit_txt.setText("Cancel");
        submit.setVisibility(View.VISIBLE);
        name.getEditText().setEnabled(true);
        email.getEditText().setEnabled(true);
        mobNumber.getEditText().setEnabled(true);
        altrmobNumber.getEditText().setEnabled(true);
    }

    public void setDisableField(){
        edit_txt.setText("Edit Profile");
        submit.setVisibility(View.GONE);
        name.getEditText().setEnabled(false);
        email.getEditText().setEnabled(false);
        mobNumber.getEditText().setEnabled(false);
        altrmobNumber.getEditText().setEnabled(false);
    }

    public void HitApiForProfileUpdate(){
        Constant.loadingpopup(MyProfile.this,"Profile  is Updating");
        Map<String,String> fields=new HashMap<>();

        fields.put("customer_id",customerId);
        fields.put("customer_name",name.getEditText().getText().toString());
        fields.put("customer_mobile",mobNumber.getEditText().getText().toString());
        fields.put("customer_email",email.getEditText().getText().toString());
        fields.put("alternate_number",altrmobNumber.getEditText().getText().toString());

        Call<ProfileUpdateModel>call=apiHolder.profileUpdate(Constant.Headers(),fields);
        call.enqueue(new Callback<ProfileUpdateModel>() {
            @Override
            public void onResponse(Call<ProfileUpdateModel> call, Response<ProfileUpdateModel> response) {
                Constant.StopLoader();
                if(response.isSuccessful()){
                    ProfileUpdateModel getdata=response.body();
                    long errorCode=getdata.getStatus();
                    if(errorCode==200){
                        Toast.makeText(MyProfile.this,getdata.getMessage(),Toast.LENGTH_SHORT).show();
                        sharedpreferences.setCustomerData("customer_id",customerId);
                        sharedpreferences.setCustomerData("customer_name",name.getEditText().getText().toString());
                        sharedpreferences.setCustomerData("customer_mobile",mobNumber.getEditText().getText().toString());
                        sharedpreferences.setCustomerData("alternate_number",altrmobNumber.getEditText().getText().toString());
                        setDisableField();
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
                        Toast.makeText(MyProfile.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileUpdateModel> call, Throwable t) {
                Constant.StopLoader();
            }
        });

    }
}