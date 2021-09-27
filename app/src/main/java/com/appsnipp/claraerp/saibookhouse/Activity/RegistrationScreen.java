package com.appsnipp.claraerp.saibookhouse.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appsnipp.claraerp.saibookhouse.Model.OtpverificationModel;
import com.appsnipp.claraerp.saibookhouse.Model.StateListModel;
import com.appsnipp.claraerp.saibookhouse.Network.APIClient;
import com.appsnipp.claraerp.saibookhouse.Network.ApiHolder;
import com.appsnipp.claraerp.saibookhouse.R;
import com.appsnipp.claraerp.saibookhouse.Utils.Constant;
import com.appsnipp.claraerp.saibookhouse.Utils.Sharedpreferences;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationScreen extends AppCompatActivity implements View.OnClickListener{
    TextView submit,loginTxt;
    Spinner statespinner;
    ApiHolder apiHolder;
    Sharedpreferences sharedpreferences;
    List<StateListModel.ResponseItem>StateList=new ArrayList<>();
    List<String> spinnerstatelist=new ArrayList<>();
    //TextInputEditText userName,userArea,userlandmark,userCity,userpincode,userContactNumber,userAlternateNumber,userEmailId,userPassword,userCofrmPass;
    String userState="";
     TextInputLayout UserName,UserArea,UserLandmark,UserCity,UserPincode,UserContactNo,UserAlterNo,UserEmail,UserPass,UserConfrmPass;
     CheckBox checkBox;
     ImageView showpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_screen);
        init();
        HitApiForState();
        setClicklistner();

    }
    public void init(){
        // call sharedpreference class.............................
        sharedpreferences= Sharedpreferences.getInstance(RegistrationScreen.this);
        // call retrofit class............................
        apiHolder= APIClient.getclient().create(ApiHolder.class);
        submit=findViewById(R.id.submitbutton);
        statespinner=findViewById(R.id.State);
        UserName=findViewById(R.id.name);
        UserArea=findViewById(R.id.area);
        UserLandmark=findViewById(R.id.address);
        UserCity=findViewById(R.id.city);
        UserPincode=findViewById(R.id.pincode);
        UserContactNo=findViewById(R.id.contactno);
        UserAlterNo=findViewById(R.id.contactno2);
        UserEmail=findViewById(R.id.email);
        UserPass=findViewById(R.id.pass);
        UserConfrmPass=findViewById(R.id.cnfrmpass);
        checkBox=findViewById(R.id.checkbox);
        showpass=findViewById(R.id.showpass);
        loginTxt=findViewById(R.id.login);
    }
    public void setClicklistner(){
        submit.setOnClickListener(this);
        showpass.setOnClickListener(this);
        loginTxt.setOnClickListener(this);
        statespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    userState=spinnerstatelist.get(position).toString();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submitbutton:
                if(UserName.getEditText().getText().length()==0||UserArea.getEditText().getText().length()==0||UserLandmark.getEditText().getText().length()==0||UserCity.getEditText().getText().length()==0||UserPincode.getEditText().getText().length()==0||UserContactNo.getEditText().getText().length()==0||UserAlterNo.getEditText().getText().length()==0||UserEmail.getEditText().getText().length()==0||UserPass.getEditText().getText().length()==0||UserConfrmPass.getEditText().getText().length()==0||statespinner.getSelectedItemPosition()==0)
                {
                    if(UserName.getEditText().getText().length()==0){
                        UserName.setError("Enter User Name!!");
                        UserName.requestFocus();
                        return;
                    }
                    if(UserArea.getEditText().getText().length()==0){
                        UserArea.setError("Enter Area !!");
                        UserArea.requestFocus();
                        return;
                    }
                    if(UserLandmark.getEditText().getText().length()==0){
                        UserLandmark.setError("Enter Full Address!!");
                        UserLandmark.requestFocus();
                        return;
                    }
                    if(UserCity.getEditText().getText().length()==0){
                        UserCity.setError("Enter City!!");
                        UserCity.requestFocus();
                        return;
                    }
                    if(UserPincode.getEditText().getText().length()==0){
                        UserPincode.setError("Enter PinCode!!");
                        UserPincode.requestFocus();
                        return;
                    }
                    if(UserContactNo.getEditText().getText().length()==0){
                        UserContactNo.setError("Enter Contact No!!");
                        UserContactNo.requestFocus();
                        return;
                    }
                    if(UserContactNo.getEditText().getText().length()<10){
                        UserContactNo.setError("Enter Valid Contact No!!");
                        UserContactNo.requestFocus();
                        return;
                    }
                    if(UserAlterNo.getEditText().getText().length()==0){
                        UserAlterNo.setError("Enter Alternate Contact No!!");
                        UserAlterNo.requestFocus();
                        return;
                    }
                    if(UserAlterNo.getEditText().getText().length()<10){
                        UserAlterNo.setError("Enter Valid Alternate Contact No!!");
                        UserAlterNo.requestFocus();
                        return;
                    }
                    if(UserEmail.getEditText().getText().length()==0){
                        UserEmail.setError("Enter Email Id!!");
                        UserEmail.requestFocus();
                        return;
                    }
                    if(UserPass.getEditText().getText().length()==0){
                        UserPass.setError("Enter Password!!");
                        UserPass.requestFocus();
                        return;
                    }
                    if(UserConfrmPass.getEditText().getText().length()==0){
                        UserConfrmPass.setError("Enter Confirm Password!!");
                        UserConfrmPass.requestFocus();
                        return;
                    }
                    if(statespinner.getSelectedItemPosition()==0){
                        alert(RegistrationScreen.this,"Please Select State");
                        return;
                    }
                }
                else {
                    if (checkBox.isChecked()) {
                        if (UserConfrmPass.getEditText().getText().toString().trim().equals(UserPass.getEditText().getText().toString().trim())) {
                            HitApiForVeriFicationOtp(UserContactNo.getEditText().getText().toString().trim(),UserEmail.getEditText().getText().toString().trim(),UserName.getEditText().getText().toString(),UserArea.getEditText().getText().toString(),UserLandmark.getEditText().getText().toString(),UserCity.getEditText().getText().toString(),statespinner.getSelectedItem().toString(),UserPincode.getEditText().getText().toString(),UserContactNo.getEditText().getText().toString(),UserAlterNo.getEditText().getText().toString(),UserEmail.getEditText().getText().toString(),UserPass.getEditText().getText().toString());
                        } else {
                            Toast.makeText(getApplicationContext(), "Password Don't Match !!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Please Select Term and Condition Policy!!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.showpass:
                ShowHidePass(showpass);
                break;
            case R.id.login:
                Intent intent=new Intent(RegistrationScreen.this, LoginScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
            }
    }
    public void HitApiForVeriFicationOtp(String ContactNo,String EmailId,String username,String userArea,String landmark,String city,String state,String pincode,String contactNo,String alterContNumber,String emailAddress,String password){
        Constant.loadingpopup(RegistrationScreen.this,"Sending Otp On Your Register Number");
        Map<String,String> fields=new HashMap<>();
        fields.put("mobile_number",ContactNo);
        fields.put("email_id",EmailId);
        Call<OtpverificationModel> call=apiHolder.verifyOtp(Constant.Headers(),fields);
        call.enqueue(new Callback<OtpverificationModel>() {
            @Override
            public void onResponse(Call<OtpverificationModel> call, Response<OtpverificationModel> response) {
                Constant.StopLoader();
                if(response.isSuccessful()){
                    OtpverificationModel getdata=response.body();
                    long errorcode=getdata.getStatus();
                    if(errorcode==200){
                        Toast.makeText(getApplicationContext(),getdata.getMessage(),Toast.LENGTH_SHORT).show();
                        Constant.ClassType="RegistrationScreen";
                        new IntentValue(getdata,username,userArea,landmark,city,state,pincode,contactNo,alterContNumber,emailAddress,password).execute();
                    }
                }
                else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(RegistrationScreen.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(),"Server Error!!",Toast.LENGTH_SHORT).show();
                    }

                }
            }
            @Override
            public void onFailure(Call<OtpverificationModel> call, Throwable t) {
                Constant.StopLoader();

            }
        });

    }
    public void HitApiForState(){
        spinnerstatelist.clear();
        Call<StateListModel> call=apiHolder.getStateList(Constant.Headers());
        call.enqueue(new Callback<StateListModel>() {
            @Override
            public void onResponse(Call<StateListModel> call, Response<StateListModel> response) {
                if(response.isSuccessful()){
                    StateListModel getdata=response.body();
                    long errorcode=getdata.getStatus();
                    if(errorcode==200){
                        StateList=getdata.getResponse();
                        if(StateList.size()>0)
                        {
                            spinnerstatelist.add("Select State");
                            for(int i=0;i<StateList.size();i++){
                                spinnerstatelist.add(StateList.get(i).getStateName());
                            }
                            statespinner.setAdapter(new ArrayAdapter<String>(RegistrationScreen.this, android.R.layout.simple_spinner_dropdown_item, spinnerstatelist));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<StateListModel> call, Throwable t) {

            }
        });

    }

    public class IntentValue extends AsyncTask<Void,Void,Void>{
        OtpverificationModel getdata;
        String  username, userArea, landmark, city, state, pincode, contactNo, alterContNumber, emailAddress, password;

        public IntentValue(OtpverificationModel getdata,String username,String userArea,String landmark,String city,String state,String pincode,String contactNo,String alterContNumber,String emailAddress,String password) {
            this.getdata=getdata;
            this.username=username;
            this.userArea=userArea;
            this.landmark=landmark;
            this.city=city;
            this.state=state;
            this.pincode=pincode;
            this.contactNo=contactNo;
            this.alterContNumber=alterContNumber;
            this.emailAddress=emailAddress;
            this.password=password;
        }
        @Override
        public Void doInBackground(Void... voids) {
            Intent intent=new Intent(RegistrationScreen.this,VerifyOTP.class);
            try {
                intent.putExtra("OTP",Constant.Decrypt(getdata.getResponse()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            intent.putExtra("customer_name",username);
            intent.putExtra("customer_mobile",contactNo);
            intent.putExtra("customer_email",emailAddress);
            intent.putExtra("alternate_number",alterContNumber);
            intent.putExtra("customer_address",landmark);
            intent.putExtra("area",userArea);
            intent.putExtra("city",city);
            intent.putExtra("customer_state",state);
            intent.putExtra("state_pincode",pincode);
            intent.putExtra("hash_password",password);
            startActivity(intent);
            return null;
        }
    }

    public void ShowHidePass(View view){
        if(view.getId()==R.id.showpass){
            if(UserPass.getEditText().getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.hidepassicon);
                //Show Password
                UserPass.getEditText().setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                UserPass.getEditText().setSelection(UserPass.getEditText().getText().length());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.showpassicon);
                //Hide Password
                UserPass.getEditText().setTransformationMethod(PasswordTransformationMethod.getInstance());
                UserPass.getEditText().setSelection(UserPass.getEditText().getText().length());

            }
        }
    }

    public void alert(Context ctx, String message) {
        final Dialog myDialog2 = new Dialog(ctx, R.style.Theme_Transparent);
        myDialog2.setContentView(R.layout.alert);
        myDialog2.setCanceledOnTouchOutside(true);
        TextView message1=myDialog2.findViewById(R.id.title);
        TextView accept = myDialog2.findViewById(R.id.accept);
        message1.setText(message);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog2.dismiss();
            }
        });
        myDialog2.show();
    }

}