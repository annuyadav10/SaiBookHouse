package com.appsnipp.claraerp.saibookhouse.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appsnipp.claraerp.saibookhouse.Model.ChangePasswordModel;
import com.appsnipp.claraerp.saibookhouse.Network.APIClient;
import com.appsnipp.claraerp.saibookhouse.Network.ApiHolder;
import com.appsnipp.claraerp.saibookhouse.R;
import com.appsnipp.claraerp.saibookhouse.Utils.Constant;
import com.appsnipp.claraerp.saibookhouse.Utils.Sharedpreferences;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout old_Pass,new_Pass,cnfrm_Pass;
    TextView submit;
    Sharedpreferences sharedpreferences;
    ApiHolder apiHolder;
    ImageView backarrow,cart,showpass;
    String customerId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        init();
        setOnClickListner();
    }
    public void init(){
        sharedpreferences=Sharedpreferences.getInstance(this);
        apiHolder= APIClient.getclient().create(ApiHolder.class);
        customerId=sharedpreferences.getCustomerData("customer_id");
        old_Pass=findViewById(R.id.yourpass);
        new_Pass=findViewById(R.id.pass);
        cnfrm_Pass=findViewById(R.id.cnfrmpass);
        submit=findViewById(R.id.submitbutton);
        backarrow=findViewById(R.id.back);
        cart=findViewById(R.id.cart);
        showpass=findViewById(R.id.showpass);
        cart.setVisibility(View.INVISIBLE);
    }

    public void setOnClickListner(){
        backarrow.setOnClickListener(this);
        cart.setOnClickListener(this);
        submit.setOnClickListener(this);
        showpass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.submitbutton:
                if(old_Pass.getEditText().getText().length()==0||new_Pass.getEditText().getText().length()==0||cnfrm_Pass.getEditText().getText().length()==0)
                {
                   if(old_Pass.getEditText().getText().length()==0) {
                       old_Pass.getEditText().setError("Enter Old Password!!");
                       old_Pass.getEditText().requestFocus();
                       return;
                   }
                    if(new_Pass.getEditText().getText().length()==0) {
                        new_Pass.getEditText().setError("Enter New Password!!");
                        new_Pass.getEditText().requestFocus();
                        return;
                    }
                    if(cnfrm_Pass.getEditText().getText().length()==0) {
                        cnfrm_Pass.getEditText().setError("Enter Confirm Password!!");
                        cnfrm_Pass.getEditText().requestFocus();
                        return;
                    }
                }
                else {
                    if(new_Pass.getEditText().getText().toString().equals(cnfrm_Pass.getEditText().getText().toString())){
                        HitApiForChangePassword();
                    }
                    else {
                        Toast.makeText(ChangePassword.this,"Password don't match",Toast.LENGTH_SHORT).show();
                    }

                }
                break;
            case R.id.showpass:
                ShowHidePass(showpass);
                break;
        }

    }
    public void HitApiForChangePassword(){
        Constant.loadingpopup(ChangePassword.this,"Password is Updating");
        Map<String,String> fields=new HashMap<>();
        fields.put("customer_id",customerId);
        fields.put("old_password",Constant.MD5(old_Pass.getEditText().getText().toString()));
        fields.put("new_password",Constant.MD5(new_Pass.getEditText().getText().toString()));
        Call<ChangePasswordModel> call=apiHolder.passwordUpdate(Constant.Headers(),fields);
        call.enqueue(new Callback<ChangePasswordModel>() {
            @Override
            public void onResponse(Call<ChangePasswordModel> call, Response<ChangePasswordModel> response) {
                Constant.StopLoader();
                if(response.isSuccessful()){
                    ChangePasswordModel getdata=response.body();
                    long errorcode=getdata.getStatus();
                    if(errorcode==200){
                        Toast.makeText(ChangePassword.this,getdata.getMessage(),Toast.LENGTH_SHORT).show();
                        old_Pass.getEditText().setText("");
                        new_Pass.getEditText().setText("");
                        cnfrm_Pass.getEditText().setText("");
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
                        Toast.makeText(ChangePassword.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordModel> call, Throwable t) {
                Constant.StopLoader();
            }
        });
    }
    public void ShowHidePass(View view){

        if(view.getId()==R.id.showpass){

            if(cnfrm_Pass.getEditText().getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.hidepassicon);
                //Show Password
                cnfrm_Pass.getEditText().setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                cnfrm_Pass.getEditText().setSelection(cnfrm_Pass.getEditText().getText().length());            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.showpassicon);
                //Hide Password
                cnfrm_Pass.getEditText().setTransformationMethod(PasswordTransformationMethod.getInstance());
                cnfrm_Pass.getEditText().setSelection(cnfrm_Pass.getEditText().getText().length());

            }
        }
    }
}