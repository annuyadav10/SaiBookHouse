package com.appsnipp.claraerp.saibookhouse.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appsnipp.claraerp.saibookhouse.Adapter.PackageInfoAdapter;
import com.appsnipp.claraerp.saibookhouse.Model.CartCountModel;
import com.appsnipp.claraerp.saibookhouse.Model.PackageInfoModel;
import com.appsnipp.claraerp.saibookhouse.Model.PackageInfooModel;
import com.appsnipp.claraerp.saibookhouse.Model.UpdateCartModel;
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

public class PackageInfo extends AppCompatActivity {
    ImageView back;
    RelativeLayout cart;
    TextView schoolname, packagename, price,buynow,addcart,itemcount;
    RecyclerView bookrecyclerview,notebookrecyclerview,stationeryRecyclerview;
    ApiHolder apiHolder;
    String schoolid = "", packageid = "",schoolName="",packageName="",Price="";
    Sharedpreferences sharedpreferences;
    LinearLayout bookslayout,stationerylayout,notebooklayout;
    PackageInfoAdapter bookadapter;
    List<PackageInfooModel.Data> book = new ArrayList<>();
    List<PackageInfooModel.Data> notebook = new ArrayList<>();
    List<PackageInfooModel.Data> stationary = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_info);
        Init();
        onClick();
        HitApiForPackageInfo();
    }

    public void Init() {
        sharedpreferences = Sharedpreferences.getInstance(PackageInfo.this);
        apiHolder = APIClient.getclient().create(ApiHolder.class);
        back = findViewById(R.id.back);
        itemcount = findViewById(R.id.itemcount);
        schoolname = findViewById(R.id.schoolname);
        packagename = findViewById(R.id.packagename);
        price = findViewById(R.id.price);
        cart = findViewById(R.id.cart);
        bookslayout=findViewById(R.id.bookslayout);
        notebooklayout=findViewById(R.id.notebooklayout);
        stationerylayout=findViewById(R.id.stationerylayout);
        bookrecyclerview =findViewById(R.id.bookrecyclerview);
        notebookrecyclerview =findViewById(R.id.notebookrecyclerview);
        stationeryRecyclerview =findViewById(R.id.statinoreyrecyclerview);
        buynow=findViewById(R.id.buynow);
        addcart=findViewById(R.id.addcart);
        if (getIntent().hasExtra("SchoolName")) {
            schoolname.setText(getIntent().getStringExtra("SchoolName"));
        }
        else {
            schoolName=sharedpreferences.getPackageData("SchoolName");
            schoolname.setText(schoolName);

        }
        if (getIntent().hasExtra("PackageName")) {
            packagename.setText(getIntent().getStringExtra("PackageName"));

        }
        else {
            packageName=sharedpreferences.getPackageData("PackageName");
        }
        if (getIntent().hasExtra("Price")) {
            price.setText("₹"+getIntent().getStringExtra("Price"));
        }
        else {
            Price=sharedpreferences.getPackageData("Price");
            price.setText("₹"+Price);

        }
        if (getIntent().hasExtra("SchoolId")) {
            schoolid = getIntent().getStringExtra("SchoolId");
        }
        else {
            schoolid=sharedpreferences.getPackageData("SchoolId");
        }
        if (getIntent().hasExtra("PackageId")) {
            packageid = getIntent().getStringExtra("PackageId");
        }
        else {
            packageid=sharedpreferences.getPackageData("PackageId");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        HitApiForCartCount();
    }

    public void onClick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HitApi("Increment",packageid);
            }
        });
        buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PackageInfo.this,BuyNow.class);
                intent.putExtra("package_id",packageid);
                intent.putExtra("school_id",schoolid);
                startActivity(intent);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PackageInfo.this,CartActivity.class);
                startActivity(intent);
            }
        });
    }
    public void HitApi(String Action, String PackageId) {
        Constant.loadingpopup(PackageInfo.this, "Loading data");
        Map<String, String> fields = new HashMap<>();
        Sharedpreferences sharedpreferences = Sharedpreferences.getInstance(PackageInfo.this);
        String customerId = sharedpreferences.getCustomerData("customer_id");
        fields.put("customer_id", customerId);
        fields.put("package_id", PackageId);
        fields.put("action", Action);
        ApiHolder apiHolder = APIClient.getclient().create(ApiHolder.class);
        Call<UpdateCartModel> call = apiHolder.addtoCart(Constant.Headers(), fields);
        call.enqueue(new Callback<UpdateCartModel>() {
            @Override
            public void onResponse(Call<UpdateCartModel> call, Response<UpdateCartModel> response) {
                Constant.StopLoader();

                if (response.isSuccessful()) {
                    HitApiForCartCount();
                    UpdateCartModel getdata = response.body();
                    long errorcode = getdata.getStatus();
                    if (errorcode == 200) {
                        Toast.makeText(PackageInfo.this, "Package added to cart", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String status = jObjError.getString("status");
                        if (status.equals("401")) {
                            Toast.makeText(PackageInfo.this, jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                        } else if (status.equals("400")) {
                            Toast.makeText(PackageInfo.this, jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(PackageInfo.this, jObjError.getString("message"), Toast.LENGTH_SHORT).show();

                        }

                    } catch (Exception e) {
                        Toast.makeText(PackageInfo.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateCartModel> call, Throwable t) {
                Constant.StopLoader();
            }
        });
    }

    public void HitApiForPackageInfo() {
        String customerid = sharedpreferences.getCustomerData("customer_id");
        Constant.loadingpopup(PackageInfo.this, "Package Info");
        Map<String, String> fields = new HashMap<>();
        fields.put("customer_id", customerid);
        fields.put("school_id", schoolid);
        fields.put("package_id", packageid);
        Call<PackageInfoModel> call = apiHolder.getPackageInfo(Constant.Headers(), fields);
        call.enqueue(new Callback<PackageInfoModel>() {
            @Override
            public void onResponse(Call<PackageInfoModel> call, Response<PackageInfoModel> response) {
                Constant.StopLoader();
                if (response.isSuccessful()) {
                    PackageInfoModel getdata = response.body();
                    long errorcode = getdata.getStatus();
                    if (errorcode == 200) {
                        PackageInfoModel.Response response1 = getdata.getResponse();
                        List<PackageInfoModel.Response.Book> books = response1.getBooks();
                        List<PackageInfoModel.Response.NoteBook> noteBooks = response1.getNoteBooks();
                        List<PackageInfoModel.Response.Stationery> stationeries = response1.getStationery();

                        for (int i = 0; i < books.size(); i++) {
                            book.add(new PackageInfooModel.Data(books.get(i).getTitle(), books.get(i).getPrice(), books.get(i).getQuantity(), String.valueOf(books.get(i).getTotalAmount())));
                        }

                        for (int i = 0; i < noteBooks.size(); i++) {
                            notebook.add(new PackageInfooModel.Data(noteBooks.get(i).getType(), noteBooks.get(i).getHsnCode(), noteBooks.get(i).getPrice(), noteBooks.get(i).getQuantity(), String.valueOf(noteBooks.get(i).getTotalAmount())));
                        }

                        for (int i = 0; i < stationeries.size(); i++) {
                            stationary.add(new PackageInfooModel.Data(stationeries.get(i).getType(), stationeries.get(i).getHsnCode(),stationeries.get(i).getPrice(), stationeries.get(i).getQuantity(), String.valueOf(stationeries.get(i).getTotalAmount())));
                        }
                        if(book.size()>0||notebook.size()>0||stationary.size()>0){
                            if(book.size()>0) {
                                bookslayout.setVisibility(View.VISIBLE);
                                setBookTableInRecyclerview(book);
                            }
                            else {
                                bookslayout.setVisibility(View.GONE);
                            }
                            if(notebook.size()>0){
                                notebooklayout.setVisibility(View.VISIBLE);
                                setNoteBookTableRecyclerview(notebook);
                            }
                            else {
                                notebooklayout.setVisibility(View.GONE);
                            }
                             if(stationary.size()>0){
                                 stationerylayout.setVisibility(View.VISIBLE);
                                 setStationeryTableRecyclerview(stationary);
                             }
                             else {
                                 stationerylayout.setVisibility(View.GONE);
                             }
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
                        Toast.makeText(PackageInfo.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PackageInfoModel> call, Throwable t) {
                Constant.StopLoader();
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
                        Toast.makeText(PackageInfo.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CartCountModel> call, Throwable t) {

            }
        });
    }

    public void setBookTableInRecyclerview(List<PackageInfooModel.Data> booklist){
        bookrecyclerview.setHasFixedSize(true);
        bookrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        bookadapter=new PackageInfoAdapter(PackageInfo.this,booklist,"Book");
        bookrecyclerview.setAdapter(bookadapter);
        bookadapter.notifyDataSetChanged();
    }
    public void setNoteBookTableRecyclerview(List<PackageInfooModel.Data> booklist){
        notebookrecyclerview.setHasFixedSize(true);
        notebookrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        bookadapter=new PackageInfoAdapter(PackageInfo.this,booklist,"Notebook");
        notebookrecyclerview.setAdapter(bookadapter);
        bookadapter.notifyDataSetChanged();
    }
    public void setStationeryTableRecyclerview(List<PackageInfooModel.Data> booklist){
        stationeryRecyclerview.setHasFixedSize(true);
        stationeryRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        bookadapter=new PackageInfoAdapter(PackageInfo.this,booklist,"Stationery");
        stationeryRecyclerview.setAdapter(bookadapter);
        bookadapter.notifyDataSetChanged();
    }

}