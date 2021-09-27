package com.appsnipp.claraerp.saibookhouse.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appsnipp.claraerp.saibookhouse.Model.DropQueryModel;
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

public class DropQuery extends AppCompatActivity implements View.OnClickListener{
    TextInputLayout name,email,mobNumber;
    EditText Query;
    TextView submit;
    Sharedpreferences sharedpreferences;
    ApiHolder apiHolder;
    ImageView backarrow;
    String customerId="",customerMobile="",customerName="",customerMail="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop_query);
        init();
        setOnclicklistner();
    }

    public void init(){
        sharedpreferences=Sharedpreferences.getInstance(this);
        apiHolder= APIClient.getclient().create(ApiHolder.class);
        customerId=sharedpreferences.getCustomerData("customer_id");
        customerName=sharedpreferences.getCustomerData("customer_name");
        customerMobile=sharedpreferences.getCustomerData("customer_mobile");
        customerMail=sharedpreferences.getCustomerData("customer_mail");

        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        mobNumber=findViewById(R.id.phone);
        backarrow=findViewById(R.id.back);
        Query=findViewById(R.id.query);
        Query.requestFocus();
        submit=findViewById(R.id.submitbutton);
        setvalueIfDataHas();

    }
    public void setOnclicklistner(){
        submit.setOnClickListener(this);
        backarrow.setOnClickListener(this);
    }

    public void setvalueIfDataHas(){
        name.getEditText().setText(customerName);
        mobNumber.getEditText().setText(customerMobile);
        email.getEditText().setText(customerMail);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submitbutton:
                if (name.getEditText().getText().length() == 0 || email.getEditText().getText().length() == 0 || mobNumber.getEditText().getText().length() == 0 ||
                        Query.getText().length() == 0) {
                    if (name.getEditText().getText().length() == 0) {
                        name.getEditText().setError("Enter Name!!");
                        name.getEditText().requestFocus();
                        return;
                    }
                    if (email.getEditText().getText().length() == 0) {
                        email.getEditText().setError("Enter Email Id!!");
                        email.getEditText().requestFocus();
                        return;
                    }
                    if (mobNumber.getEditText().getText().length() == 0) {
                        mobNumber.getEditText().setError("Enter Mobile Number!!");
                        mobNumber.getEditText().requestFocus();
                        return;
                    }
                    if (Query.getText().length()== 0) {
                        Query.setError("Enter Query!!");
                        Query.requestFocus();
                        return;
                    }
                } else {
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    if (email.getEditText().getText().toString().matches(emailPattern)) {
                        HitApiForAddDropQuery();
                    } else {
                        Toast.makeText(DropQuery.this, "Enter Valid Email Id", Toast.LENGTH_SHORT).show();
                    }

                }

                break;

            case R.id.back:
                finish();
                break;
        }

    }
    public void HitApiForAddDropQuery(){
        Constant.loadingpopup(DropQuery.this,"Adding drop query");
        Map<String,String> fields=new HashMap<>();
        fields.put("customer_id",customerId);
        fields.put("customer_name",name.getEditText().getText().toString());
        fields.put("customer_mobile",mobNumber.getEditText().getText().toString());
        fields.put("customer_email",email.getEditText().getText().toString());
        fields.put("customer_query",Query.getText().toString());

        Call<DropQueryModel> call=apiHolder.getDropQuery(Constant.Headers(),fields);
        call.enqueue(new Callback<DropQueryModel>() {
            @Override
            public void onResponse(Call<DropQueryModel> call, Response<DropQueryModel> response) {
                Constant.StopLoader();
                if(response.isSuccessful()){
                    DropQueryModel getdata=response.body();
                    long errorcode=getdata.getStatus();
                    if(errorcode==201){
                        Toast.makeText(DropQuery.this,getdata.getMessage(),Toast.LENGTH_SHORT).show();
                        emptyfield();
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
                        Toast.makeText(DropQuery.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<DropQueryModel> call, Throwable t) {
                Constant.StopLoader();
            }
        });


    }
    public void emptyfield(){
       /* name.getEditText().setText("");
        mobNumber.getEditText().setText("");
        email.getEditText().setText("");*/
        Query.setText("");
    }
}