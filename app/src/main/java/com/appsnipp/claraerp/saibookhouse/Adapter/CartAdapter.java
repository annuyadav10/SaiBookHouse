package com.appsnipp.claraerp.saibookhouse.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.appsnipp.claraerp.saibookhouse.Activity.CartActivity;
import com.appsnipp.claraerp.saibookhouse.Activity.MainPageActivity;
import com.appsnipp.claraerp.saibookhouse.Activity.OrderDetails;
import com.appsnipp.claraerp.saibookhouse.Fragments.cart.Cart;
import com.appsnipp.claraerp.saibookhouse.Model.CartItemsModel;
import com.appsnipp.claraerp.saibookhouse.Model.CustomerOrderModel;
import com.appsnipp.claraerp.saibookhouse.Model.UpdateCartModel;
import com.appsnipp.claraerp.saibookhouse.Network.APIClient;
import com.appsnipp.claraerp.saibookhouse.Network.ApiHolder;
import com.appsnipp.claraerp.saibookhouse.R;
import com.appsnipp.claraerp.saibookhouse.Utils.Constant;
import com.appsnipp.claraerp.saibookhouse.Utils.Sharedpreferences;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.favouriteholder> {
    Context context;
    List<CartItemsModel.Response.Cart> SchoolListModels;
    Cart cart;
    String From;

    public CartAdapter(Context context, List<CartItemsModel.Response.Cart> schoolListModels, Cart cart, String from) {
        this.context = context;
        SchoolListModels = schoolListModels;
        this.cart = cart;
        From = from;
    }

    @NonNull
    @Override
    public favouriteholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cartdesign, parent, false);
        favouriteholder favouriteholder = new favouriteholder(view);
        return favouriteholder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final favouriteholder holder, int position) {
        holder.packagename.setText(SchoolListModels.get(position).getPackageName());
        holder.quantity.setText("Qty: " + SchoolListModels.get(position).getQuantity());
        holder.schoolname.setText(SchoolListModels.get(position).getSchoolName());
        holder.packageprice.setText("₹ " + SchoolListModels.get(position).getPackagePrice());
        holder.increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HitApi("Increment", SchoolListModels.get(position).getPackageId());
            }
        });

        holder.decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(SchoolListModels.get(position).getQuantity())>1){
                    HitApi("Decrement", SchoolListModels.get(position).getPackageId());
                }
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HitApi("Remove", SchoolListModels.get(position).getPackageId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return SchoolListModels.size();
    }

    public class favouriteholder extends RecyclerView.ViewHolder {
        TextView schoolname, packagename, packageprice, delete, quantity;
        ImageView increment, decrement;
        public EditText studentname;

        public favouriteholder(@NonNull View itemView) {
            super(itemView);
            schoolname = itemView.findViewById(R.id.schoolname);
            packagename = itemView.findViewById(R.id.packagename);
            packageprice = itemView.findViewById(R.id.packageprice);
            quantity = itemView.findViewById(R.id.quantity);
            delete = itemView.findViewById(R.id.delete);
            increment = itemView.findViewById(R.id.increment);
            decrement = itemView.findViewById(R.id.decrement);
            studentname=itemView.findViewById(R.id.studentname);

        }
    }

    public void HitApi(String Action, String PackageId) {
        Constant.loadingpopup(context, "Loading data");
        Map<String, String> fields = new HashMap<>();
        Sharedpreferences sharedpreferences = Sharedpreferences.getInstance(context);
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
                    UpdateCartModel getdata = response.body();
                    long errorcode = getdata.getStatus();
                    if (errorcode == 200) {
                        if(From.equals("Fragment")){
                            cart.HitApiForCart(false);
                            ((MainPageActivity)context).HitApiForCartCount();
                        }else{
                            ((CartActivity)context).HitApiForCart(false);
                        }

                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String status = jObjError.getString("status");
                        if (status.equals("401")) {
                            Toast.makeText(context, jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                        } else if (status.equals("400")) {
                            Toast.makeText(context, jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, jObjError.getString("message"), Toast.LENGTH_SHORT).show();

                        }

                    } catch (Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateCartModel> call, Throwable t) {
                Constant.StopLoader();
            }
        });
    }

}
