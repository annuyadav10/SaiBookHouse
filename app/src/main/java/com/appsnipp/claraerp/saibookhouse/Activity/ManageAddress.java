package com.appsnipp.claraerp.saibookhouse.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appsnipp.claraerp.saibookhouse.Model.MyProfileModel;
import com.appsnipp.claraerp.saibookhouse.Model.ProfileUpdateModel;
import com.appsnipp.claraerp.saibookhouse.Model.StateListModel;
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

public class
ManageAddress extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout address,area,city,pincode;
    TextView submit,edit_txt;
    Sharedpreferences sharedpreferences;
    ApiHolder apiHolder;
    ImageView backarrow,cart;
    String customerId="";
    Spinner statespinner;
    List<StateListModel.ResponseItem>StateList=new ArrayList<>();
    List<String> spinnerstatelist=new ArrayList<>();
    String userState="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_address);
        init();
        setDisableField();
        HitApiForGetAddressData();
        setOnclicklistner();
    }
    public void init(){
        sharedpreferences=Sharedpreferences.getInstance(this);
        apiHolder= APIClient.getclient().create(ApiHolder.class);
        customerId=sharedpreferences.getCustomerData("customer_id");
        address=findViewById(R.id.address);
        area=findViewById(R.id.area);
        city=findViewById(R.id.city);
        pincode=findViewById(R.id.pincode);
        submit=findViewById(R.id.submitbutton);
        edit_txt=findViewById(R.id.edittxt);
        backarrow=findViewById(R.id.back);
        cart=findViewById(R.id.cart);
        statespinner=findViewById(R.id.State);
        cart.setVisibility(View.INVISIBLE);


    }
    public void HitApiForGetAddressData(){
        Constant.loadingpopup(ManageAddress.this,"Fetching Address");
        List<String> list = new ArrayList<>();
        list.add("customer_address");
        list.add("area");
        list.add("city");
        list.add("customer_state");
        list.add("state_pincode");

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
                        Toast.makeText(ManageAddress.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
        spinnerstatelist.clear();
        MyProfileModel.Response response=getdata.getResponse();
        address.getEditText().setText(response.getCustomerAddress());
        area.getEditText().setText(response.getArea());
        city.getEditText().setText(response.getCity());
        pincode.getEditText().setText(response.getPincode());
        sharedpreferences.setCustomerData("customer_address",response.getCustomerAddress());
        spinnerstatelist.add(response.getState());
        statespinner.setAdapter(new ArrayAdapter<String>(ManageAddress.this, R.layout.spinner_item,R.id.spinneritem, spinnerstatelist));
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edittxt:
                if(edit_txt.getText().equals("Edit Address")){
                    setEnableField();
                }
                else {
                    if(address.getEditText().getText().length()==0||city.getEditText().getText().length()==0||pincode.getEditText().getText().length()==0||
                            area.getEditText().getText().length()==0||statespinner.getSelectedItem().equals("Select State")){
                        HitApiForGetAddressData();

                    }
                    else {
                        setDisableField();
                    }
                }
                break;
            case R.id.submitbutton:
                if(address.getEditText().getText().length()==0||city.getEditText().getText().length()==0||pincode.getEditText().getText().length()==0||
                        area.getEditText().getText().length()==0||statespinner.getSelectedItem().equals("Select State")||pincode.getEditText().getText().length()<6){
                    if(address.getEditText().getText().length()==0){
                        address.getEditText().setError("Enter Address!!");
                        address.getEditText().requestFocus();
                        return;
                    }
                    if(city.getEditText().getText().length()==0){
                        city.getEditText().setError("Enter City !!");
                        city.getEditText().requestFocus();
                        return;
                    }
                    if(pincode.getEditText().getText().length()==0){
                        pincode.getEditText().setError("Enter Pincode!!");
                        pincode.getEditText().requestFocus();
                        return;
                    }
                    if(area.getEditText().getText().length()==0){
                        area.getEditText().setError("Enter Area!!");
                        area.getEditText().requestFocus();
                        return;
                    }
                    if(statespinner.getSelectedItemPosition()==0){
                        alert(ManageAddress.this,"Please Select State");
                        return;
                    }
                    if(pincode.getEditText().getText().length()<6){
                        pincode.getEditText().setError("Enter Valid Pincode!!");
                        pincode.getEditText().requestFocus();
                        return;
                    }

                }
                else {
                        HitApiForAddressUpdate();
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
    public void setEnableField(){
        edit_txt.setText("Cancel");
        submit.setVisibility(View.VISIBLE);
        address.getEditText().setEnabled(true);
        city.getEditText().setEnabled(true);
        pincode.getEditText().setEnabled(true);
        area.getEditText().setEnabled(true);
        HitApiForState();
        statespinner.setEnabled(true);
    }

    public void setDisableField(){
        edit_txt.setText("Edit Address");
        submit.setVisibility(View.GONE);
        address.getEditText().setEnabled(false);
        city.getEditText().setEnabled(false);
        pincode.getEditText().setEnabled(false);
        area.getEditText().setEnabled(false);
        statespinner.setEnabled(false);
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
                            statespinner.setAdapter(new ArrayAdapter<String>(ManageAddress.this, R.layout.spinner_item,R.id.spinneritem, spinnerstatelist));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<StateListModel> call, Throwable t) {

            }
        });

    }

    public void HitApiForAddressUpdate(){
        Constant.loadingpopup(ManageAddress.this,"Address  is Updating");
        Map<String,String> fields=new HashMap<>();

        fields.put("customer_id",customerId);
        fields.put("customer_address",address.getEditText().getText().toString());
        fields.put("area",area.getEditText().getText().toString());
        fields.put("city",city.getEditText().getText().toString());
        fields.put("customer_state",statespinner.getSelectedItem().toString());
        fields.put("state_pincode",pincode.getEditText().getText().toString());

        Call<ProfileUpdateModel>call=apiHolder.addressUpdate(Constant.Headers(),fields);
        call.enqueue(new Callback<ProfileUpdateModel>() {
            @Override
            public void onResponse(Call<ProfileUpdateModel> call, Response<ProfileUpdateModel> response) {
                Constant.StopLoader();
                if(response.isSuccessful()){
                    ProfileUpdateModel getdata=response.body();
                    long errorCode=getdata.getStatus();
                    if(errorCode==200){
                        Toast.makeText(ManageAddress.this,getdata.getMessage(),Toast.LENGTH_SHORT).show();
                        sharedpreferences.setCustomerData("customer_address",address.getEditText().getText().toString());
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
                        Toast.makeText(ManageAddress.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileUpdateModel> call, Throwable t) {
                Constant.StopLoader();
            }
        });

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