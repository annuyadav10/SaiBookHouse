package com.appsnipp.claraerp.saibookhouse.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appsnipp.claraerp.saibookhouse.Adapter.FiltersAdapter;
import com.appsnipp.claraerp.saibookhouse.Adapter.SchoolPackageAdapter;
import com.appsnipp.claraerp.saibookhouse.Model.CartCountModel;
import com.appsnipp.claraerp.saibookhouse.Model.SchoolPackageList;
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

public class SchoolPackageActivity extends AppCompatActivity {
    TextView schoolname, filter,itemcount;
    ImageView back;
    RelativeLayout cart;
    String schoolid = "";
    RecyclerView packages;
    List<SchoolPackageList.ResponseItem> responseItems = new ArrayList<>();
    List<SchoolPackageList.ResponseItem> filtered = new ArrayList<>();
    List<String> Class = new ArrayList<>();
    List<String> language = new ArrayList<>();
    List<String> Classes = new ArrayList<>();
    List<String> Languages = new ArrayList<>();
    ApiHolder apiHolder;
    String Type = "Class";
    SchoolPackageAdapter adapter;
    Sharedpreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_package);
        Init();
        HitApiForSchoolPackages();
    }

    public void Init() {
        sharedpreferences = Sharedpreferences.getInstance(SchoolPackageActivity.this);
        if (getIntent().hasExtra("SchoolId")) {
            schoolid = getIntent().getStringExtra("SchoolId");
        }
        schoolname = findViewById(R.id.schoolname);
        if (getIntent().hasExtra("SchoolName")) {
            schoolname.setText(getIntent().getStringExtra("SchoolName"));
        }
        apiHolder = APIClient.getclient().create(ApiHolder.class);
        back = findViewById(R.id.back);
        cart = findViewById(R.id.cart);
        itemcount = findViewById(R.id.itemcount);
        filter = findViewById(R.id.filter);
        packages = findViewById(R.id.packages);
        packages.setHasFixedSize(true);
        packages.setLayoutManager(new GridLayoutManager(this, 2));
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterPopup();
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SchoolPackageActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void HitApiForSchoolPackages() {
        String customerid = sharedpreferences.getCustomerData("customer_id");
        Constant.loadingpopup(SchoolPackageActivity.this, "Fetching Schools");
        Map<String, String> fields = new HashMap<>();
        fields.put("customer_id", customerid);
        fields.put("school_id", schoolid);
        fields.put("package_language", "All");
        fields.put("class", "All");
        Call<SchoolPackageList> call = apiHolder.getschoolPackageList(Constant.Headers(), fields);
        call.enqueue(new Callback<SchoolPackageList>() {
            @Override
            public void onResponse(Call<SchoolPackageList> call, Response<SchoolPackageList> response) {
                Constant.StopLoader();
                if (response.isSuccessful()) {
                    SchoolPackageList getdata = response.body();
                    long errorcode = getdata.getStatus();
                    if (errorcode == 200) {
                        responseItems = getdata.getResponse();
                        adapter = new SchoolPackageAdapter(SchoolPackageActivity.this, responseItems);
                        packages.setAdapter(adapter);
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
                        Toast.makeText(SchoolPackageActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SchoolPackageList> call, Throwable t) {
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
                        Toast.makeText(SchoolPackageActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CartCountModel> call, Throwable t) {

            }
        });


    }

    public void FilterPopup() {
        Classes.clear();
        Languages.clear();
        Dialog dialog = new Dialog(SchoolPackageActivity.this, R.style.Theme_Transparent);
        dialog.setContentView(R.layout.filterpopup);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        Classes.add("NURSERY");
        Classes.add("PP 1");
        Classes.add("PP 2");
        Classes.add("I");
        Classes.add("II");
        Classes.add("III");
        Classes.add("IV");
        Classes.add("V");
        Classes.add("VI");
        Classes.add("VII");
        Classes.add("VIII");
        Classes.add("IX");
        Classes.add("X");
        Classes.add("XI");
        Classes.add("XII");
        Languages.add("HINDI");
        Languages.add("Telugu");
        Languages.add("HT");
        Languages.add("TH");
        Languages.add("TS");
        Languages.add("HS");
        Languages.add("SANSKRIT");
        TextView classes = dialog.findViewById(R.id.classes);
        TextView languages = dialog.findViewById(R.id.languages);
        TextView apply = dialog.findViewById(R.id.apply);
        ImageView back = dialog.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        RecyclerView items = dialog.findViewById(R.id.filters);
        items.setLayoutManager(new LinearLayoutManager(this));
        FiltersAdapter adapter = new FiltersAdapter(this, Classes, Class);
        items.setAdapter(adapter);
        classes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Type = "Class";
                classes.setBackgroundResource(R.color.white);
                classes.setTextColor(ActivityCompat.getColor(SchoolPackageActivity.this, R.color.colorPrimaryDark));
                languages.setBackgroundResource(0);
                languages.setTextColor(ActivityCompat.getColor(SchoolPackageActivity.this, R.color.black));
                FiltersAdapter adapter = new FiltersAdapter(SchoolPackageActivity.this, Classes, Class);
                items.setAdapter(adapter);
            }
        });
        languages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Type = "Language";
                languages.setBackgroundResource(R.color.white);
                languages.setTextColor(ActivityCompat.getColor(SchoolPackageActivity.this, R.color.colorPrimaryDark));
                classes.setBackgroundResource(0);
                classes.setTextColor(ActivityCompat.getColor(SchoolPackageActivity.this, R.color.black));
                FiltersAdapter adapter = new FiltersAdapter(SchoolPackageActivity.this, Languages, language);
                items.setAdapter(adapter);

            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Filter();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void Filter() {
        filtered.clear();
        if (responseItems.size() > 0) {
            if (Class.size() > 0 && language.size() > 0) {
                for (int h = 0; h < responseItems.size(); h++) {
                    for (int i = 0; i < Class.size(); i++) {
                        if ((Class.get(i).toLowerCase().trim()).equals(responseItems.get(h).getClassName().toLowerCase().trim())) {
                            for (int j = 0; j < language.size(); j++) {
                                if ((language.get(j).toLowerCase().trim()).equals(responseItems.get(h).getPackageLanguage().toLowerCase().trim())) {
                                    filtered.add(responseItems.get(h));
                                }
                            }
                            adapter = new SchoolPackageAdapter(SchoolPackageActivity.this, filtered);
                            packages.setAdapter(adapter);
                        }
                    }
                }
            }else if (Class.size() > 0 && language.size() == 0) {
                for (int h = 0; h < responseItems.size(); h++) {
                    for (int i = 0; i < Class.size(); i++) {
                        if ((Class.get(i).toLowerCase().trim()).equals(responseItems.get(h).getClassName().toLowerCase().trim())) {
                            filtered.add(responseItems.get(h));
                        }
                    }
                    adapter = new SchoolPackageAdapter(SchoolPackageActivity.this, filtered);
                    packages.setAdapter(adapter);
                }
            } else if (Class.size() == 0 && language.size() > 0) {
                for (int h = 0; h < responseItems.size(); h++) {
                    for (int j = 0; j < language.size(); j++) {
                        if ((language.get(j).toLowerCase().trim()).equals(responseItems.get(h).getPackageLanguage().toLowerCase().trim())) {
                            filtered.add(responseItems.get(h));
                        }
                    }
                    adapter = new SchoolPackageAdapter(SchoolPackageActivity.this, filtered);
                    packages.setAdapter(adapter);
                }
            } else {
                adapter = new SchoolPackageAdapter(SchoolPackageActivity.this, responseItems);
                packages.setAdapter(adapter);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void onItemClick(@NonNull final FiltersAdapter.favouriteholder holder, int position) {
        boolean haveclass = false;
        boolean havelanguage = false;
        if (Type.equals("Class")) {
            for (int i = 0; i < Class.size(); i++) {
                if (Class.get(i).equals(Classes.get(position))) {
                    Class.remove(i);
                    haveclass = true;
                    break;
                } else {
                    haveclass = false;
                }
            }
            if (!haveclass) {
                Class.add(Classes.get(position));
            }
        } else {
            for (int i = 0; i < language.size(); i++) {
                if (language.get(i).equals(Languages.get(position))) {
                    language.remove(i);
                    havelanguage = true;
                    break;
                } else {
                    havelanguage = false;
                }
            }
            if (!havelanguage) {
                language.add(Languages.get(position));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        HitApiForCartCount();
    }
}