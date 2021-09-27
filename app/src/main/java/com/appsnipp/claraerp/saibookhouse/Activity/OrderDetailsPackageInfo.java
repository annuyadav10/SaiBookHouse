package com.appsnipp.claraerp.saibookhouse.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appsnipp.claraerp.saibookhouse.Adapter.PackageInfoAdapter;
import com.appsnipp.claraerp.saibookhouse.Adapter.PurchagedPackageInfoAdapter;
import com.appsnipp.claraerp.saibookhouse.Model.PurchageOrderPackageInfoModel;
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

public class OrderDetailsPackageInfo extends AppCompatActivity implements View.OnClickListener {
    TextView packagename,packageprice;
    ApiHolder apiHolder;
    Sharedpreferences sharedpreferences;
    String customer_id="",school_id="",category_order="",package_id="";
    ImageView backarrow,cart;
    RecyclerView showTableofPurchagePackage;
    List<PurchageOrderPackageInfoModel.PackageItemsItem> packageItemsItems=new ArrayList<>();
    PurchagedPackageInfoAdapter purchagedPackageInfoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details_package_info);
        init();
        getValueFromIntent();
        setClicklistner();
        HitApiForPurchagedPackageInfo();


    }
    public void getValueFromIntent(){
        if(getIntent().hasExtra("school_id")){
            school_id=getIntent().getStringExtra("school_id");
        }
        if(getIntent().hasExtra("category_order")){
            category_order=getIntent().getStringExtra("category_order");
        }
        if(getIntent().hasExtra("package_id")){
            package_id=getIntent().getStringExtra("package_id");
        }
    }

    public void init(){
        sharedpreferences=Sharedpreferences.getInstance(this);
        apiHolder= APIClient.getclient().create(ApiHolder.class);
        customer_id=sharedpreferences.getCustomerData("customer_id");
        backarrow=findViewById(R.id.back);
        cart=findViewById(R.id.cart);
        showTableofPurchagePackage=findViewById(R.id.purchagePackage);
        packagename=findViewById(R.id.packagename);
        packageprice=findViewById(R.id.price);
    }

    public void setClicklistner(){
        cart.setOnClickListener(this);
        backarrow.setOnClickListener(this);
    }

    public void HitApiForPurchagedPackageInfo(){
        Constant.loadingpopup(OrderDetailsPackageInfo.this,"Loading Package Info");
        Map<String,String> fields=new HashMap<>();
        fields.put("school_id",school_id);
        fields.put("category_order",category_order);
        fields.put("package_id",package_id);
        fields.put("customer_id",customer_id);//
        Call<PurchageOrderPackageInfoModel> call=apiHolder.getPurchageOrderPackageInfo(Constant.Headers(),fields);
        call.enqueue(new Callback<PurchageOrderPackageInfoModel>() {
            @Override
            public void onResponse(Call<PurchageOrderPackageInfoModel> call, Response<PurchageOrderPackageInfoModel> response) {
              Constant.StopLoader();
              if(response.isSuccessful()){
                  PurchageOrderPackageInfoModel getdata=response.body();
                  long errorcode=getdata.getStatus();
                  if(errorcode==200){
                      setdata(getdata);
                  }
              }
              else {
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
                      Toast.makeText(OrderDetailsPackageInfo.this, e.getMessage(), Toast.LENGTH_LONG).show();
                  }
              }
            }

            @Override
            public void onFailure(Call<PurchageOrderPackageInfoModel> call, Throwable t) {
                Constant.StopLoader();
            }
        });

    }
    public void setdata(PurchageOrderPackageInfoModel getdata){
        PurchageOrderPackageInfoModel.PackageInfo packageInfo=getdata.getResponse().getPackageInfo();
        packagename.setText(packageInfo.getPackageName());
        packageprice.setText("₹" + packageInfo.getPackagePrice());

        packageItemsItems=getdata.getResponse().getPackageItems();
        if(packageItemsItems.size()>0){
            setvalueTable(packageItemsItems);
        }

    }
    public void setvalueTable(List<PurchageOrderPackageInfoModel.PackageItemsItem> packageItemsItems){
        showTableofPurchagePackage .setHasFixedSize(true);
        showTableofPurchagePackage.setLayoutManager(new LinearLayoutManager(this));
        purchagedPackageInfoAdapter=new PurchagedPackageInfoAdapter(OrderDetailsPackageInfo.this,packageItemsItems);
        showTableofPurchagePackage.setAdapter(purchagedPackageInfoAdapter);
        purchagedPackageInfoAdapter.notifyDataSetChanged();
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