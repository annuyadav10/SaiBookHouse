package com.appsnipp.claraerp.saibookhouse.Activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appsnipp.claraerp.saibookhouse.Adapter.SuggestionAdapter;
import com.appsnipp.claraerp.saibookhouse.Model.SchoolListModel;
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

public class SearchSchoolActivity extends AppCompatActivity {
    List<SchoolListModel.ResponseItem> responses = new ArrayList<>();
    List<SchoolListModel.ResponseItem> searchresults = new ArrayList<>();
    ApiHolder apiHolder;
    EditText search;
    RecyclerView suggestions;
    SuggestionAdapter adapter;
    Sharedpreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_school);
        Init();
        HitApiForSuggestion();
    }

    public void Init() {
        sharedpreferences= Sharedpreferences.getInstance(SearchSchoolActivity.this);
        apiHolder = APIClient.getclient().create(ApiHolder.class);
        search = findViewById(R.id.search);
        suggestions = findViewById(R.id.suggestions);
        adapter = new SuggestionAdapter(this, searchresults);
        suggestions.setLayoutManager(new LinearLayoutManager(this));
        suggestions.setAdapter(adapter);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchresults.clear();
                if (search.getText().length() > 0) {
                    for (int i = 0; i < responses.size(); i++) {
                        if ((responses.get(i).getSchoolName().toLowerCase()).contains(search.getText().toString().toLowerCase())) {
                            searchresults.add(responses.get(i));
                        }
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void HitApiForSuggestion() {
        String customerid=sharedpreferences.getCustomerData("customer_id");
        Constant.loadingpopup(SearchSchoolActivity.this, "Fetching Schools");
        Map<String, String> fields = new HashMap<>();
        fields.put("customer_id", customerid);
        Call<SchoolListModel> call = apiHolder.getSchoollist(Constant.Headers(), fields);
        call.enqueue(new Callback<SchoolListModel>() {
            @Override
            public void onResponse(Call<SchoolListModel> call, Response<SchoolListModel> response) {
                Constant.StopLoader();
                if (response.isSuccessful()) {
                    SchoolListModel getdata = response.body();
                    long errorcode = getdata.getStatus();
                    if (errorcode == 200) {
                        responses = getdata.getResponse();
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
                        Toast.makeText(SearchSchoolActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SchoolListModel> call, Throwable t) {
                Constant.StopLoader();

            }
        });


    }

}