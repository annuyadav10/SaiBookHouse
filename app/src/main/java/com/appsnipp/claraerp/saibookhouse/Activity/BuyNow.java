package com.appsnipp.claraerp.saibookhouse.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appsnipp.claraerp.saibookhouse.Model.BuyNowModel;
import com.appsnipp.claraerp.saibookhouse.Model.GenerateOrderIdBuyNowModel;
import com.appsnipp.claraerp.saibookhouse.Model.PaymentFailureModel;
import com.appsnipp.claraerp.saibookhouse.Model.Razorpayerrormodel;
import com.appsnipp.claraerp.saibookhouse.Network.APIClient;
import com.appsnipp.claraerp.saibookhouse.Network.ApiHolder;
import com.appsnipp.claraerp.saibookhouse.R;
import com.appsnipp.claraerp.saibookhouse.Utils.Constant;
import com.appsnipp.claraerp.saibookhouse.Utils.Sharedpreferences;
import com.google.gson.JsonObject;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultListener;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyNow extends AppCompatActivity implements PaymentResultWithDataListener {
    String OrderId="", customerId="",packageId="",schoolId="",CustomerAddress="";
    Sharedpreferences sharedpreferences;
    ApiHolder apiHolder;
    TextView address,changeadd,schoolname,packagename,packageprice,price,deliverycharge,totalamount,customizepackageprice,processamount;
    EditText studentname;
    CheckBox BookCheckBox, NoteBookCheckBox, StationeryCheckBox;
    String DeliveryCharge="",PackagePrice="",bookprice="",stationeryprice="",notebookprice="",TotalPrice="";
    RelativeLayout placeorder;
    List<String> list = new ArrayList<>();
    ImageView back;
    @Override
    public void onResume() {
        super.onResume();
        address.setText(sharedpreferences.getCustomerData("customer_address"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_now);
        init();
        intentvalue();
        HitApiForBuyNow();
        onCheckChange();
    }

    public void init(){
        apiHolder= APIClient.getclient().create(ApiHolder.class);
        sharedpreferences= Sharedpreferences.getInstance(this);
        address=findViewById(R.id.address);
        changeadd=findViewById(R.id.changeaddress);
        schoolname=findViewById(R.id.schoolname);
        packagename=findViewById(R.id.packagename);
        deliverycharge=findViewById(R.id.deliveryamt);
        studentname=findViewById(R.id.studentname);
        BookCheckBox =findViewById(R.id.txtbook);
        NoteBookCheckBox =findViewById(R.id.notebook);
        StationeryCheckBox =findViewById(R.id.stationery);
        placeorder=findViewById(R.id.placeorderlayout);
        //Price Changes
        totalamount=findViewById(R.id.totalamt);
        packageprice=findViewById(R.id.packageprice);
        price=findViewById(R.id.priceamt);
        customizepackageprice=findViewById(R.id.txt3);
        processamount=findViewById(R.id.totalprice);
        back=findViewById(R.id.back);

        if(BookCheckBox.isChecked()&&NoteBookCheckBox.isChecked()&&StationeryCheckBox.isChecked()){
            list.add("Text Books");
            list.add("Notebooks");
            list.add("School Stationery");
        }
    }
    public void intentvalue(){
        if(getIntent().hasExtra("package_id")){
            packageId=getIntent().getStringExtra("package_id");
        }
        if(getIntent().hasExtra("school_id")){
            schoolId=getIntent().getStringExtra("school_id");
        }
        customerId=sharedpreferences.getCustomerData("customer_id");
        CustomerAddress= sharedpreferences.getCustomerData("customer_address");

    }
    public void HitApiForBuyNow(){
        Constant.loadingpopup(BuyNow.this,"Loading data");
        Map<String,String> fields=new HashMap<>();
        fields.put("customer_id",customerId);
        fields.put("package_id",packageId);
        fields.put("school_id",schoolId);
        Call<BuyNowModel>call=apiHolder.getBuyNowDataList(Constant.Headers(),fields);
        call.enqueue(new Callback<BuyNowModel>() {
            @Override
            public void onResponse(Call<BuyNowModel> call, Response<BuyNowModel> response) {
                Constant.StopLoader();
                if(response.isSuccessful()){
                    BuyNowModel getdata=response.body();
                    long errorcode=getdata.getStatus();
                    if(errorcode==200){
                        setdata(getdata);
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
                        Toast.makeText(BuyNow.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BuyNowModel> call, Throwable t) {
                Constant.StopLoader();
            }
        });
    }
    public void setdata(BuyNowModel getdata){
        BuyNowModel.Response response=getdata.getResponse();
        address.setText(CustomerAddress);
        schoolname.setText(response.getSchoolName());
        packagename.setText(response.getPackageName());
        packageprice.setText("₹"+response.getPackagePrice());
        customizepackageprice.setText("( Total Cost : "+"₹"+response.getPackagePrice()+")");
        price.setText("₹"+response.getPackagePrice());
        deliverycharge.setText("₹"+response.getDeliveryCharges());
        totalamount.setText("₹"+response.getTotalPrice());
        processamount.setText("₹"+response.getTotalPrice());
        DeliveryCharge=response.getDeliveryCharges();
        PackagePrice=response.getPackagePrice();

        if(!response.getBookPrice().equals("0")){
            BookCheckBox.setVisibility(View.VISIBLE);
            BookCheckBox.setText("Text Book ("+"₹"+response.getBookPrice()+")");
            bookprice=response.getBookPrice();
            BookCheckBox.setChecked(true);
        }
        else {
            BookCheckBox.setVisibility(View.GONE);
            BookCheckBox.setChecked(false);
        }
        if(!response.getNoteBookPrice().equals("0")){

            NoteBookCheckBox.setVisibility(View.VISIBLE);
            NoteBookCheckBox.setText("Note Book ("+"₹"+response.getNoteBookPrice()+")");
            notebookprice=response.getNoteBookPrice();
            NoteBookCheckBox.setChecked(true);
        }
        else {
            NoteBookCheckBox.setVisibility(View.GONE);
            NoteBookCheckBox.setChecked(false);
        }

        if(!response.getStationeryPrice().equals("0")){

            StationeryCheckBox.setVisibility(View.VISIBLE);
            StationeryCheckBox.setText("Stationery ("+"₹"+response.getStationeryPrice()+")");
            stationeryprice=response.getStationeryPrice();
            StationeryCheckBox.setChecked(true);
        }
        else {
            StationeryCheckBox.setVisibility(View.GONE);
            StationeryCheckBox.setChecked(false);
        }

    }

    public void onCheckChange(){

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        changeadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BuyNow.this,ManageAddress.class));
            }
        });

        BookCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    list.add("Text Books");
                    PackagePrice= String.valueOf(Integer.parseInt (PackagePrice)+Integer.parseInt(bookprice));
                    TotalPrice=String.valueOf(Integer.parseInt (PackagePrice)+Integer.parseInt(DeliveryCharge));
                    packageprice.setText("₹"+PackagePrice);
                    customizepackageprice.setText("( Total Cost : "+"₹"+PackagePrice+")");//"( Total Cost : "+"₹"+response.getPackagePrice()+")"
                    price.setText("₹"+PackagePrice);
                    totalamount.setText("₹"+TotalPrice);
                    processamount.setText("₹"+TotalPrice);
                    BookCheckBox.setChecked(true);
                }
                else {
                    list.remove("Text Books");
                    PackagePrice=String.valueOf(Integer.parseInt (PackagePrice)-Integer.parseInt(bookprice));
                    TotalPrice=String.valueOf(Integer.parseInt (PackagePrice)+Integer.parseInt(DeliveryCharge));
                    packageprice.setText("₹"+PackagePrice);
                    customizepackageprice.setText("( Total Cost : "+"₹"+PackagePrice+")");
                    price.setText("₹"+PackagePrice);
                    totalamount.setText("₹"+TotalPrice);
                    processamount.setText("₹"+TotalPrice);
                    BookCheckBox.setChecked(false);
                }
            }
        });
        NoteBookCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    list.add("Notebooks");
                    PackagePrice= String.valueOf(Integer.parseInt (PackagePrice)+Integer.parseInt(notebookprice));
                    TotalPrice=String.valueOf(Integer.parseInt (PackagePrice)+Integer.parseInt(DeliveryCharge));
                    packageprice.setText("₹"+PackagePrice);
                    customizepackageprice.setText("( Total Cost : "+"₹"+PackagePrice+")");
                    price.setText("₹"+PackagePrice);
                    totalamount.setText("₹"+TotalPrice);
                    processamount.setText("₹"+TotalPrice);
                    NoteBookCheckBox.setChecked(true);
                }
                else {
                    list.remove("Notebooks");
                    PackagePrice= String.valueOf(Integer.parseInt (PackagePrice)-Integer.parseInt(notebookprice));
                    TotalPrice=String.valueOf(Integer.parseInt (PackagePrice)+Integer.parseInt(DeliveryCharge));
                    packageprice.setText("₹"+PackagePrice);
                    customizepackageprice.setText("( Total Cost : "+"₹"+PackagePrice+")");
                    price.setText("₹"+PackagePrice);
                    totalamount.setText("₹"+TotalPrice);
                    processamount.setText("₹"+TotalPrice);
                    NoteBookCheckBox.setChecked(false);
                }
            }
        });
        StationeryCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    list.add("School Stationery");
                    PackagePrice= String.valueOf(Integer.parseInt (PackagePrice)+Integer.parseInt(stationeryprice));
                    TotalPrice=String.valueOf(Integer.parseInt (PackagePrice)+Integer.parseInt(DeliveryCharge));
                    packageprice.setText("₹"+PackagePrice);
                    customizepackageprice.setText("( Total Cost : "+"₹"+PackagePrice+")");
                    price.setText("₹"+PackagePrice);
                    totalamount.setText("₹"+TotalPrice);
                    processamount.setText("₹"+TotalPrice);
                    StationeryCheckBox .setChecked(true);
                }
                else {
                    list.remove("School Stationery");
                    PackagePrice= String.valueOf(Integer.parseInt (PackagePrice)-Integer.parseInt(stationeryprice));
                    TotalPrice=String.valueOf(Integer.parseInt (PackagePrice)+Integer.parseInt(DeliveryCharge));
                    packageprice.setText("₹"+PackagePrice);
                    customizepackageprice.setText("( Total Cost : "+"₹"+PackagePrice+")");
                    price.setText("₹"+PackagePrice);
                    totalamount.setText("₹"+TotalPrice);
                    processamount.setText("₹"+TotalPrice);
                    StationeryCheckBox.setChecked(false);
                }
            }
        });

        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(studentname.getText().length()==0){
                    studentname.setError("Please Enter Student Name!!");
                    studentname.requestFocus();
                    return;
                }
                else {
                    if(BookCheckBox.isChecked()||NoteBookCheckBox.isChecked()||StationeryCheckBox.isChecked()){
                        HitApiForGenerateOrderId();
                    }
                    else {
                        Toast.makeText(BuyNow.this,"Please check atleast one pacakage category",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }
    public void HitApiForGenerateOrderId(){
        String sendrequestfor = TextUtils.join(", ", list);
        Constant.loadingpopup(BuyNow.this,"Order is processing");
        Map<String,String> fields=new HashMap<>();
        fields.put("customer_id",customerId);
        fields.put("school_id",schoolId);
        fields.put("category_selected",sendrequestfor);
        fields.put("student_name",studentname.getText().toString());
        fields.put("package_id",packageId);
        Call<GenerateOrderIdBuyNowModel> call=apiHolder.generateOrderId(Constant.Headers(),fields);
        call.enqueue(new Callback<GenerateOrderIdBuyNowModel>() {
            @Override
            public void onResponse(Call<GenerateOrderIdBuyNowModel> call, Response<GenerateOrderIdBuyNowModel> response) {
                Constant.StopLoader();
                if(response.isSuccessful()){
                    GenerateOrderIdBuyNowModel getdata=response.body();
                    long errorcode=getdata.getStatus();
                    if(errorcode==200){
                        //Toast.makeText(BuyNow.this,getdata.getMessage(),Toast.LENGTH_SHORT).show();
                        GenerateOrderIdBuyNowModel.Response response1 =getdata.getResponse();
                        int Orderamount=response1.getTotalAmount();
                        OrderId=response1.getOrderId();
                        Checkout checkout=new Checkout();
                        checkout.setKeyID("rzp_live_7EZR7bj4NypB8C");
                        checkout.setImage(R.drawable.logo);
                        JSONObject jsonObject=new JSONObject();
                        try {
                            jsonObject.put("name","Sai Book House");
                            jsonObject.put("description","Payment");
                            jsonObject.put("theme.color",getApplicationContext().getColor(R.color.colorPrimaryDark));
                            jsonObject.put("currency","INR");
                            jsonObject.put("amount",Orderamount);
                            jsonObject.put("order_id", OrderId);
                            JSONObject preFill = new JSONObject();
                            preFill.put("contact",sharedpreferences.getCustomerData("customer_mobile"));
                            preFill.put("email",sharedpreferences.getCustomerData("customer_mail"));
                            jsonObject.put("prefill", preFill);
                            checkout.open(BuyNow.this,jsonObject);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GenerateOrderIdBuyNowModel> call, Throwable t) {
                Constant.StopLoader();

            }
        });

    }

    @Override
    public void onPaymentSuccess(String s,PaymentData paymentData) {
        Constant.loadingpopup(BuyNow.this,"Processing");
        Map<String,String>fields=new HashMap<>();
        fields.put("customer_id",customerId);
        fields.put("order_id",paymentData.getOrderId());
        fields.put("payment_id",paymentData.getPaymentId());
        fields.put("type","success");
        fields.put("description","Success");
        fields.put("reason","Success");
        Call<PaymentFailureModel> call=apiHolder.paymentfailure(Constant.Headers(),fields);
        call.enqueue(new Callback<PaymentFailureModel>() {
            @Override
            public void onResponse(Call<PaymentFailureModel> call, Response<PaymentFailureModel> response) {
                Constant.StopLoader();
                if(response.isSuccessful()){
                    PaymentFailureModel getdata=response.body();
                    long errorcode=getdata.getStatus();
                    if(errorcode==200){
                        successAlert();
                    }
                }
            }

            @Override
            public void onFailure(Call<PaymentFailureModel> call, Throwable t) {
                Constant.StopLoader();
                failAlert();
            }
        });
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        String description="",reason="";
        try {

            JSONObject obj = new JSONObject(s);
            reason = obj.getJSONObject("error").getString("reason");
            description = obj.getJSONObject("error").getString("description");

        } catch (Throwable tx) {

        }
        Constant.loadingpopup(BuyNow.this,"Processing");
        Map<String,String>fields=new HashMap<>();
        fields.put("customer_id",customerId);
        fields.put("order_id",OrderId);
        fields.put("payment_id","failure");
        fields.put("type","failure");
        fields.put("description",description);
        fields.put("reason",reason);
        Call<PaymentFailureModel> call=apiHolder.paymentfailure(Constant.Headers(),fields);
        call.enqueue(new Callback<PaymentFailureModel>() {
            @Override
            public void onResponse(Call<PaymentFailureModel> call, Response<PaymentFailureModel> response) {
                Constant.StopLoader();
                if(response.isSuccessful()){
                    PaymentFailureModel getdata=response.body();
                    long errorcode=getdata.getStatus();
                    if(errorcode==200){
                        failAlert();
                    }
                }
            }

            @Override
            public void onFailure(Call<PaymentFailureModel> call, Throwable t) {
                Constant.StopLoader();
                failAlert();
            }
        });




    }
    public void failAlert(){
        final Dialog myDialog2 = new Dialog(this,R.style.Theme_Transparent);
        myDialog2.setContentView(R.layout.failedpayment);
        myDialog2.setCanceledOnTouchOutside(false);
        TextView accept = myDialog2.findViewById(R.id.accept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog2.dismiss();
                finish();
                /*Intent intent=new Intent(BuyNow.this,PackageInfo.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);*/
            }
        });
        myDialog2.show();
    }

    public void successAlert(){
        final Dialog myDialog2 = new Dialog(this,R.style.Theme_Transparent);
        myDialog2.setContentView(R.layout.successpayment);
        myDialog2.setCanceledOnTouchOutside(false);
        TextView accept = myDialog2.findViewById(R.id.accept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog2.dismiss();
                Intent intent=new Intent(BuyNow.this,MyOrder.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        myDialog2.show();
    }
}