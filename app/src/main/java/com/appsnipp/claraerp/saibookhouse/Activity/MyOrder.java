package com.appsnipp.claraerp.saibookhouse.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appsnipp.claraerp.saibookhouse.Adapter.CustomerOrderAdapter;
import com.appsnipp.claraerp.saibookhouse.Model.CartCountModel;
import com.appsnipp.claraerp.saibookhouse.Model.CustomerOrderModel;
import com.appsnipp.claraerp.saibookhouse.Network.APIClient;
import com.appsnipp.claraerp.saibookhouse.Network.ApiHolder;
import com.appsnipp.claraerp.saibookhouse.R;
import com.appsnipp.claraerp.saibookhouse.Utils.Constant;
import com.appsnipp.claraerp.saibookhouse.Utils.Sharedpreferences;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrder extends AppCompatActivity implements View.OnClickListener {
    Sharedpreferences sharedpreferences;
    ApiHolder apiHolder;
    ImageView backarrow;
    RelativeLayout cart;
    RecyclerView orderlistrecyclerview;
    ConstraintLayout noorderfound;
    String customerId="";
    List<CustomerOrderModel.ResponseItem> orderModelList=new ArrayList<>();
    CustomerOrderAdapter adapter;
    TextView itemcount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        init();
        setClicklistner();
        HitApiForOrderList();


    }
    public void init(){
        sharedpreferences=Sharedpreferences.getInstance(this);
        apiHolder= APIClient.getclient().create(ApiHolder.class);
        customerId=sharedpreferences.getCustomerData("customer_id");
        backarrow=findViewById(R.id.back);
        cart=findViewById(R.id.cart);
        noorderfound=findViewById(R.id.dataNotfoundLayout);
        orderlistrecyclerview=findViewById(R.id.orderlist);
        noorderfound.setVisibility(View.GONE);
        itemcount = findViewById(R.id.itemcount);
        HitApiForCartCount();
    }
    public void setClicklistner(){
        cart.setOnClickListener(this);
        backarrow.setOnClickListener(this);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyOrder.this,CartActivity.class);
                startActivity(intent);
            }
        });
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
    public void HitApiForOrderList(){
        Constant.loadingpopup(MyOrder.this,"Oredr List Is Loading");
        Map<String,String> fields=new HashMap<>();
        fields.put("customer_id",customerId);
        Call<CustomerOrderModel> call=apiHolder.getCustomerOrder(Constant.Headers(),fields);
        call.enqueue(new Callback<CustomerOrderModel>() {
            @Override
            public void onResponse(Call<CustomerOrderModel> call, Response<CustomerOrderModel> response) {
                Constant.StopLoader();
                HitApiForCartCount();
                if(response.isSuccessful()){
                    CustomerOrderModel getdata=response.body();
                    long errorcode=getdata.getStatus();
                    if(errorcode==200){
                        noorderfound.setVisibility(View.GONE);
                        orderlistrecyclerview.setVisibility(View.VISIBLE);
                        orderModelList=getdata.getResponse();
                        if(orderModelList.size()>0){
                            setvalue(orderModelList);
                        }

                    }
                }
                else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String status=jObjError.getString("status");
                        noorderfound.setVisibility(View.VISIBLE);
                        orderlistrecyclerview.setVisibility(View.GONE);
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
                        Toast.makeText(MyOrder.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<CustomerOrderModel> call, Throwable t) {
                HitApiForCartCount();
                Constant.StopLoader();
                noorderfound.setVisibility(View.VISIBLE);
                orderlistrecyclerview.setVisibility(View.GONE);
            }
        });
    }

    public void setvalue(List<CustomerOrderModel.ResponseItem> orderModelList){
        orderlistrecyclerview.setHasFixedSize(true);
        orderlistrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        adapter=new CustomerOrderAdapter(MyOrder.this,orderModelList);
        orderlistrecyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void HitApiForCartCount() {
        String customerid = sharedpreferences.getCustomerData("customer_id");
        Map<String, String> fields = new HashMap<>();
        fields.put("customer_id", customerid);
        Call<CartCountModel> call = apiHolder.getCartCount(Constant.Headers(), fields);
        call.enqueue(new Callback<CartCountModel>() {
            @Override
            public void onResponse(Call<CartCountModel> call, Response<CartCountModel> response) {
                if (response.isSuccessful()) {
                    CartCountModel getdata = response.body();
                    long errorcode = getdata.getStatus();
                    if (errorcode == 200) {
                        if(getdata.getResponse().getCount()>0){
                            itemcount.setText(String.valueOf(getdata.getResponse().getCount()));
                            itemcount.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String status = jObjError.getString("status");
                        if (status.equals("401")) {
                            Toast.makeText(getApplicationContext(), jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                        } else if (status.equals("400")) {
                            Toast.makeText(getApplicationContext(), jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), jObjError.getString("message"), Toast.LENGTH_SHORT).show();

                        }

                    } catch (Exception e) {
                        Toast.makeText(MyOrder.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CartCountModel> call, Throwable t) {

            }
        });


    }
}