package com.appsnipp.claraerp.saibookhouse.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appsnipp.claraerp.saibookhouse.Adapter.OrderDetailsPackageAdapter;
import com.appsnipp.claraerp.saibookhouse.Model.OrderDetailsModel;
import com.appsnipp.claraerp.saibookhouse.Network.APIClient;
import com.appsnipp.claraerp.saibookhouse.Network.ApiHolder;
import com.appsnipp.claraerp.saibookhouse.R;
import com.appsnipp.claraerp.saibookhouse.Utils.Constant;
import com.appsnipp.claraerp.saibookhouse.Utils.Sharedpreferences;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetails extends AppCompatActivity implements View.OnClickListener{
    TextView orderId,CustomerName,CustomerAddress,CustomerEmail,CustomerPhoneNo,OredrDate,transactionStatus,TransactionId,TransactionDate,Price,deliverprice,totalamt;
     RecyclerView orderpackagedetailsshow;
     ApiHolder apiHolder;
     Sharedpreferences sharedpreferences;
    String customerId="",OrderId="";
    ImageView backarrow,cart;
    List<OrderDetailsModel.PackageInfoItem> OrderPackageList=new ArrayList<>();
    OrderDetailsPackageAdapter orderDetailsPackageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        getValueFromIntent();
        init();
        HitApiForGetPackagedetails();
        setClicklistner();

    }
    public void getValueFromIntent(){
        if(getIntent().hasExtra("order_id")){
            OrderId=getIntent().getStringExtra("order_id");
        }
    }

    public void setClicklistner(){
        cart.setOnClickListener(this);
        backarrow.setOnClickListener(this);
    }
    public void init(){
        sharedpreferences=Sharedpreferences.getInstance(this);
        apiHolder= APIClient.getclient().create(ApiHolder.class);
        customerId=sharedpreferences.getCustomerData("customer_id");
        backarrow=findViewById(R.id.back);
        cart=findViewById(R.id.cart);
        orderpackagedetailsshow=findViewById(R.id.showpackage);
        orderId=findViewById(R.id.OrderId);
        CustomerName=findViewById(R.id.customername);
        CustomerAddress=findViewById(R.id.address);
        CustomerPhoneNo=findViewById(R.id.mobNo);
        CustomerEmail=findViewById(R.id.emailId);
        OredrDate=findViewById(R.id.orderdate);
        transactionStatus=findViewById(R.id.transacrionstatus);
        TransactionId=findViewById(R.id.transcationid);
        TransactionDate=findViewById(R.id.transactiondate);
        Price=findViewById(R.id.priceamt);
        deliverprice=findViewById(R.id.deliveryamt);
        totalamt=findViewById(R.id.totalamt);
        orderpackagedetailsshow.setHasFixedSize(true);
        orderpackagedetailsshow.setLayoutManager(new LinearLayoutManager(this));
    }

    public void HitApiForGetPackagedetails(){
        Constant.loadingpopup(OrderDetails.this,"Loading Order Details");
        Map<String,String> fields=new HashMap<>();
        fields.put("order_id",OrderId);
        fields.put("customer_id",customerId);
        Call<OrderDetailsModel> call=apiHolder.getOrderDetails(Constant.Headers(),fields);
        call.enqueue(new Callback<OrderDetailsModel>() {
            @Override
            public void onResponse(Call<OrderDetailsModel> call, Response<OrderDetailsModel> response) {
                Constant.StopLoader();
                if(response.isSuccessful()){
                    OrderDetailsModel getdata=response.body();
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
                        Toast.makeText(OrderDetails.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderDetailsModel> call, Throwable t) {
                Constant.StopLoader();
            }
        });
    }
    public void setdata(OrderDetailsModel getdata){
        SimpleDateFormat Old=new SimpleDateFormat("YYYY-MM-dd");
        SimpleDateFormat New =new SimpleDateFormat("dd-MM-YYYY");
        OrderDetailsModel.Response response1= getdata.getResponse();
        orderId.setText(response1.getOrderId());
        CustomerName.setText(response1.getCustomerName());
        CustomerAddress.setText(response1.getCustomerAddress());
        CustomerEmail.setText(response1.getCustomerEmail());
        CustomerPhoneNo.setText(response1.getCustomerMobile());
        transactionStatus.setText("Success");
        TransactionId.setText(response1.getBankTransactionId());
        try {
            OredrDate.setText(New.format(Old.parse(response1.getOrderedDate())));
            TransactionDate.setText(New.format(Old.parse(response1.getTransactionDate())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Price.setText("₹"+ response1.getAmountPaid());
        deliverprice.setText("₹"+ response1.getDelieveryCharges());
        totalamt.setText("₹" + response1.getTotalPackagePrice());

        OrderPackageList=response1.getPackageInfo();
        if(OrderPackageList.size()>0){
            setAdapter(OrderPackageList);
        }
    }

    public void setAdapter(List<OrderDetailsModel.PackageInfoItem> OrderPackageList){
        orderDetailsPackageAdapter=new OrderDetailsPackageAdapter(OrderDetails.this,OrderPackageList);
        orderpackagedetailsshow.setAdapter(orderDetailsPackageAdapter);
        orderDetailsPackageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cart:
                break;
            case R.id.back:
                finish();
                break;
        }
    }
}