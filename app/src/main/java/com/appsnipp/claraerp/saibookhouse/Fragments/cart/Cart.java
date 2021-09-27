package com.appsnipp.claraerp.saibookhouse.Fragments.cart;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appsnipp.claraerp.saibookhouse.Activity.MainPageActivity;
import com.appsnipp.claraerp.saibookhouse.Activity.ManageAddress;
import com.appsnipp.claraerp.saibookhouse.Activity.MyOrder;
import com.appsnipp.claraerp.saibookhouse.Activity.PackageInfo;
import com.appsnipp.claraerp.saibookhouse.Adapter.CartAdapter;
import com.appsnipp.claraerp.saibookhouse.Model.CartItemsModel;
import com.appsnipp.claraerp.saibookhouse.Model.GenerateOrderIdBuyNowModel;
import com.appsnipp.claraerp.saibookhouse.Model.PaymentFailureModel;
import com.appsnipp.claraerp.saibookhouse.Network.APIClient;
import com.appsnipp.claraerp.saibookhouse.Network.ApiHolder;
import com.appsnipp.claraerp.saibookhouse.R;
import com.appsnipp.claraerp.saibookhouse.Utils.Constant;
import com.appsnipp.claraerp.saibookhouse.Utils.Sharedpreferences;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cart extends Fragment/* implements PaymentResultWithDataListener*/ {
    String OrderId = "", customerId = "", packageId = "", schoolId = "", CustomerAddress = "";
    Sharedpreferences sharedpreferences;
    ApiHolder apiHolder;
    TextView buynowheading, pricecount, address, changeadd, schoolname, packagename, packageprice, price, deliverycharge, totalamount, customizepackageprice, processamount;
    EditText studentname;
    RelativeLayout placeorder, packagelay;
    ImageView back;
    RecyclerView cartitems;
    LinearLayout buynowlay, checklay;
    ConstraintLayout nodata;
    NestedScrollView cartlay;
    CartAdapter adapter;
    Boolean edittextsfilled = true;
    View view;
    List<CartItemsModel.Response.Cart> c = new ArrayList<>();

    @Override
    public void onResume() {
        super.onResume();
        address.setText(sharedpreferences.getCustomerData("customer_address"));
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_buy_now, container, false);
        init(root);
        onCheckChange();
        HitApiForCart(false);
        return root;
    }


    public void init(View root) {
        apiHolder = APIClient.getclient().create(ApiHolder.class);
        sharedpreferences = Sharedpreferences.getInstance(getActivity());
        buynowheading = root.findViewById(R.id.buynowheading);
        nodata = root.findViewById(R.id.dataNotfoundLayout);
        cartlay = root.findViewById(R.id.cartlay);
        nodata.setVisibility(View.VISIBLE);
        cartlay.setVisibility(View.GONE);
        buynowheading.setText("My Cart");
        buynowheading.setGravity(Gravity.CENTER);
        address = root.findViewById(R.id.address);
        changeadd = root.findViewById(R.id.changeaddress);
        buynowlay = root.findViewById(R.id.buynowlay);
        checklay = root.findViewById(R.id.checklay);
        packagelay = root.findViewById(R.id.packagepricelay);
        cartitems = root.findViewById(R.id.items);
        cartitems.setLayoutManager(new LinearLayoutManager(getActivity()));
        cartitems.setVisibility(View.VISIBLE);
        buynowlay.setVisibility(View.GONE);
        packagelay.setVisibility(View.GONE);
        checklay.setVisibility(View.GONE);
        schoolname = root.findViewById(R.id.schoolname);
        pricecount = root.findViewById(R.id.pricecount);
        packagename = root.findViewById(R.id.packagename);
        deliverycharge = root.findViewById(R.id.deliveryamt);
        studentname = root.findViewById(R.id.studentname);
        studentname.setVisibility(View.GONE);
        placeorder = root.findViewById(R.id.placeorderlayout);
        //Price Changes
        totalamount = root.findViewById(R.id.totalamt);
        packageprice = root.findViewById(R.id.packageprice);
        price = root.findViewById(R.id.priceamt);
        customizepackageprice = root.findViewById(R.id.txt3);
        processamount = root.findViewById(R.id.totalprice);
        view = root.findViewById(R.id.view);
        view.setVisibility(View.GONE);
        back = root.findViewById(R.id.back);
        back.setVisibility(View.INVISIBLE);
        back.setEnabled(false);
        customerId = sharedpreferences.getCustomerData("customer_id");
        CustomerAddress = sharedpreferences.getCustomerData("customer_address");

    }


    public void HitApiForCart(Boolean Success) {
        Constant.loadingpopup(getActivity(), "Loading data");
        Map<String, String> fields = new HashMap<>();
        fields.put("customer_id", customerId);
        Call<CartItemsModel> call = apiHolder.getCartDataList(Constant.Headers(), fields);
        call.enqueue(new Callback<CartItemsModel>() {
            @Override
            public void onResponse(Call<CartItemsModel> call, Response<CartItemsModel> response) {
                Constant.StopLoader();
                if (response.isSuccessful()) {
                    CartItemsModel getdata = response.body();
                    long errorcode = getdata.getStatus();
                    if (errorcode == 200) {
                        setdata(getdata, Success);
                    }else{
                        nodata.setVisibility(View.VISIBLE);
                        cartlay.setVisibility(View.GONE);
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String status = jObjError.getString("status");
                        if (status.equals("401")) {
                            Toast.makeText(getActivity(), jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                        } else if (status.equals("400")) {
                            Toast.makeText(getActivity(), jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), jObjError.getString("message"), Toast.LENGTH_SHORT).show();

                        }

                    } catch (Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    nodata.setVisibility(View.VISIBLE);
                    cartlay.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<CartItemsModel> call, Throwable t) {
                Constant.StopLoader();
                nodata.setVisibility(View.VISIBLE);
                cartlay.setVisibility(View.GONE);
            }
        });
    }

    public void setdata(CartItemsModel data, Boolean Success) {
        c.clear();
        c = data.getResponse().getCart();
        if (null != c && c.size() > 0) {
            adapter = new CartAdapter(getActivity(), c, Cart.this, "Fragment");
            cartitems.setAdapter(adapter);
            price.setText("₹ " + data.getResponse().getProductsPrice());
            deliverycharge.setText("₹ " + data.getResponse().getDeliveryCharges());
            totalamount.setText("₹ " + data.getResponse().getTotalPrice());
            pricecount.setText("Price (" + data.getResponse().getItemsCount() + " Items)");
            processamount.setText("₹ " + data.getResponse().getTotalPrice());
            nodata.setVisibility(View.GONE);
            cartlay.setVisibility(View.VISIBLE);
        } else {
            nodata.setVisibility(View.VISIBLE);
            cartlay.setVisibility(View.GONE);
        }
        if (Success) {
            successAlert();
        }


    }

    public void onCheckChange() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        changeadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ManageAddress.class));
            }
        });

        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer data = new StringBuffer();
                for (int i = 0; i < c.size(); i++) {
                    CartAdapter.favouriteholder childHolder = (CartAdapter.favouriteholder) cartitems.findViewHolderForLayoutPosition(i);
                    if (childHolder.studentname.getText().length() == 0) {
                        childHolder.studentname.setError("Student Name is mandatory");
                        edittextsfilled = false;
                    } else {
                        if (data.length() > 0) {
                            data.append(",");
                            data.append(childHolder.studentname.getText().toString());
                        } else {
                            data.append(childHolder.studentname.getText().toString());
                        }
                    }
                }
                if (!edittextsfilled) {
                    edittextsfilled = true;
                } else {
                    HitApiForGenerateOrderId(data.toString());
                }

            }
        });

    }

    public void HitApiForGenerateOrderId(String studentnames) {
        Constant.loadingpopup(getActivity(), "Order is processing");
        Map<String, String> fields = new HashMap<>();
        fields.put("customer_id", customerId);
        fields.put("student_name", studentnames);
        Call<GenerateOrderIdBuyNowModel> call = apiHolder.generateOrderIdCart(Constant.Headers(), fields);
        call.enqueue(new Callback<GenerateOrderIdBuyNowModel>() {
            @Override
            public void onResponse(Call<GenerateOrderIdBuyNowModel> call, Response<GenerateOrderIdBuyNowModel> response) {
                Constant.StopLoader();
                if (response.isSuccessful()) {
                    GenerateOrderIdBuyNowModel getdata = response.body();
                    long errorcode = getdata.getStatus();
                    if (errorcode == 200) {
                        //Toast.makeText(BuyNow.getActivity(),getdata.getMessage(),Toast.LENGTH_SHORT).show();
                        GenerateOrderIdBuyNowModel.Response response1 = getdata.getResponse();
                        int Orderamount = response1.getTotalAmount();
                        OrderId = response1.getOrderId();
                        Checkout checkout = new Checkout();
                        checkout.setKeyID("rzp_live_7EZR7bj4NypB8C");
                        checkout.setImage(R.drawable.logo);
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("name", "Sai Book House");
                            jsonObject.put("description", "Payment");
                            jsonObject.put("theme.color", getActivity().getColor(R.color.colorPrimaryDark));
                            jsonObject.put("currency", "INR");
                            jsonObject.put("amount", Orderamount);
                            jsonObject.put("order_id", OrderId);
                            JSONObject preFill = new JSONObject();
                            preFill.put("contact", sharedpreferences.getCustomerData("customer_mobile"));
                            preFill.put("email", sharedpreferences.getCustomerData("customer_mail"));
                            jsonObject.put("prefill", preFill);
                            ((MainPageActivity) getActivity()).OrderId = OrderId;
                            checkout.open(getActivity(), jsonObject);

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
/*
    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        Constant.loadingpopup(getActivity(), "Processing");
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
                        HitApiForCart(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<PaymentFailureModel> call, Throwable t) {
                Constant.StopLoader();
                HitApiForCart(true);
            }
        });
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        String description = "", reason = "";
        try {

            JSONObject obj = new JSONObject(s);
            reason = obj.getJSONObject("error").getString("reason");
            description = obj.getJSONObject("error").getString("description");

        } catch (Throwable tx) {

        }
        Constant.loadingpopup(getActivity(), "Processing");
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
        final Dialog myDialog2 = new Dialog(getActivity(), R.style.Theme_Transparent);
        myDialog2.setContentView(R.layout.failedpayment);
        myDialog2.setCanceledOnTouchOutside(false);
        TextView accept = myDialog2.findViewById(R.id.accept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog2.dismiss();
                Intent intent = new Intent(getActivity(), PackageInfo.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        myDialog2.show();
    }*/

    public void successAlert() {
        final Dialog myDialog2 = new Dialog(getActivity(), R.style.Theme_Transparent);
        myDialog2.setContentView(R.layout.successpayment);
        myDialog2.setCanceledOnTouchOutside(false);
        TextView accept = myDialog2.findViewById(R.id.accept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog2.dismiss();
                Intent intent = new Intent(getActivity(), MyOrder.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        myDialog2.show();
    }
}