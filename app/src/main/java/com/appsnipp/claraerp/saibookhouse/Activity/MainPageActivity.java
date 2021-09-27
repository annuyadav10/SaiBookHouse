package com.appsnipp.claraerp.saibookhouse.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.appsnipp.claraerp.saibookhouse.Fragments.cart.Cart;
import com.appsnipp.claraerp.saibookhouse.Fragments.dashboard.DashBoard;
import com.appsnipp.claraerp.saibookhouse.Fragments.help.HelpFragment;
import com.appsnipp.claraerp.saibookhouse.Fragments.profile.Profile;
import com.appsnipp.claraerp.saibookhouse.Model.CartCountModel;
import com.appsnipp.claraerp.saibookhouse.Model.CartItemsModel;
import com.appsnipp.claraerp.saibookhouse.Model.MyProfileModel;
import com.appsnipp.claraerp.saibookhouse.Model.PaymentFailureModel;
import com.appsnipp.claraerp.saibookhouse.Network.APIClient;
import com.appsnipp.claraerp.saibookhouse.Network.ApiHolder;
import com.appsnipp.claraerp.saibookhouse.R;
import com.appsnipp.claraerp.saibookhouse.Utils.Constant;
import com.appsnipp.claraerp.saibookhouse.Utils.Sharedpreferences;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPageActivity extends AppCompatActivity implements PaymentResultWithDataListener {
    FragmentManager manager;
    public BottomNavigationView navView;
    int Count = 1;
    String customerId = "";
    Sharedpreferences sharedpreferences;
    ApiHolder apiHolder;
    TextView itemcount;
    public String OrderId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Init();
        HitApiForGetProfileData();

    }

    public void Init() {
        sharedpreferences = Sharedpreferences.getInstance(this);
        apiHolder = APIClient.getclient().create(ApiHolder.class);
        customerId = sharedpreferences.getCustomerData("customer_id");
        navView = findViewById(R.id.bottom_navigation);
        manager = getSupportFragmentManager();
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                HitApiForCartCount();
                int id = item.getItemId();
                if (id == R.id.home) {
                    Fragment account = new DashBoard();
                    switchTofragment(account);
                    return true;
                } else if (id == R.id.profile) {
                    Fragment products = new Profile();
                    switchTofragment(products);
                    return true;
                } else if (id == R.id.cart) {
                    Fragment products = new Cart();
                    switchTofragment(products);
                    return true;
                } else if (id == R.id.help) {
                    Fragment products = new HelpFragment();
                    switchTofragment(products);
                    return true;
                } else {
                    return false;
                }
            }
        });
        navView.setSelectedItemId(R.id.home);
        itemcount=findViewById(R.id.itemcount);

    }

    public void switchTofragment(Fragment fragment) {
        manager.beginTransaction().replace(R.id.nav_host_fragment, fragment).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        int seletedItemId = bottomNavigationView.getSelectedItemId();
        if (R.id.home != seletedItemId) {
            setHomeItem(MainPageActivity.this);
        } else {
            if (Count == 2) {
                finish();
            } else {
                Count = 2;
                Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Count = 1;
                    }
                }, 2000);
            }
        }
    }

    public static void setHomeItem(Activity activity) {
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                activity.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }

    @Override
    protected void onResume() {
        super.onResume();
        HitApiForCartCount();
    }

    public void HitApiForGetProfileData() {
        List<String> list = new ArrayList<>();
        list.add("customer_mobile");
        list.add("customer_email");
        list.add("customer_name");
        list.add("alternate_number");
        String sendrequestfor = TextUtils.join(", ", list);

        Map<String, String> fields = new HashMap<>();
        fields.put("customer_id", customerId);
        fields.put("request_for", sendrequestfor);
        Call<MyProfileModel> call = apiHolder.getProfileData(Constant.Headers(), fields);
        call.enqueue(new Callback<MyProfileModel>() {
            @Override
            public void onResponse(Call<MyProfileModel> call, Response<MyProfileModel> response) {
                if (response.isSuccessful()) {
                    MyProfileModel getdata = response.body();
                    long errorcode = getdata.getStatus();
                    if (errorcode == 200) {
                        setData(getdata);
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<MyProfileModel> call, Throwable t) {
            }
        });
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
                        if (getdata.getResponse().getCount() > 0) {
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
                        Toast.makeText(MainPageActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CartCountModel> call, Throwable t) {

            }
        });


    }


    public void setData(MyProfileModel getdata) {
        MyProfileModel.Response response = getdata.getResponse();
        sharedpreferences.setCustomerData("customer_mail", response.getCustomerEmail());
        sharedpreferences.setCustomerData("customer_name", response.getCustomerName());
        sharedpreferences.setCustomerData("customer_mobile", response.getCustomerMobile());
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        HitApiForCartCount();
        Constant.loadingpopup(MainPageActivity.this, "Processing");
        Map<String, String> fields = new HashMap<>();
        fields.put("customer_id", customerId);
        fields.put("order_id", paymentData.getOrderId());
        fields.put("payment_id", paymentData.getPaymentId());
        fields.put("type", "success");
        fields.put("description", "Success");
        fields.put("reason", "Success");
        Call<PaymentFailureModel> call = apiHolder.paymentfailure(Constant.Headers(), fields);
        call.enqueue(new Callback<PaymentFailureModel>() {
            @Override
            public void onResponse(Call<PaymentFailureModel> call, Response<PaymentFailureModel> response) {
                Constant.StopLoader();
                if (response.isSuccessful()) {
                    PaymentFailureModel getdata = response.body();
                    long errorcode = getdata.getStatus();
                    if (errorcode == 200) {
                        new Cart().HitApiForCart(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<PaymentFailureModel> call, Throwable t) {
                Constant.StopLoader();
                new Cart().HitApiForCart(true);
            }
        });
    }


    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        HitApiForCartCount();
        String description = "", reason = "";
        try {

            JSONObject obj = new JSONObject(s);
            reason = obj.getJSONObject("error").getString("reason");
            description = obj.getJSONObject("error").getString("description");

        } catch (Throwable tx) {

        }
        Constant.loadingpopup(MainPageActivity.this, "Processing");
        Map<String, String> fields = new HashMap<>();
        fields.put("customer_id", customerId);
        fields.put("order_id", OrderId);
        fields.put("payment_id", "failure");
        fields.put("type", "failure");
        fields.put("description", description);
        fields.put("reason", reason);
        Call<PaymentFailureModel> call = apiHolder.paymentfailure(Constant.Headers(), fields);
        call.enqueue(new Callback<PaymentFailureModel>() {
            @Override
            public void onResponse(Call<PaymentFailureModel> call, Response<PaymentFailureModel> response) {
                Constant.StopLoader();
                if (response.isSuccessful()) {
                    PaymentFailureModel getdata = response.body();
                    long errorcode = getdata.getStatus();
                    if (errorcode == 200) {
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

    public void failAlert() {
        final Dialog myDialog2 = new Dialog(MainPageActivity.this, R.style.Theme_Transparent);
        myDialog2.setContentView(R.layout.failedpayment);
        myDialog2.setCanceledOnTouchOutside(false);
        TextView accept = myDialog2.findViewById(R.id.accept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog2.dismiss();
                /*Intent intent = new Intent(MainPageActivity.this, PackageInfo.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);*/
            }
        });
        myDialog2.show();
    }

    /*public void successAlert() {
        final Dialog myDialog2 = new Dialog(MainPageActivity.this, R.style.Theme_Transparent);
        myDialog2.setContentView(R.layout.successpayment);
        myDialog2.setCanceledOnTouchOutside(false);
        TextView accept = myDialog2.findViewById(R.id.accept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog2.dismiss();
                Intent intent = new Intent(MainPageActivity.this, MyOrder.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        myDialog2.show();
    }*/
}