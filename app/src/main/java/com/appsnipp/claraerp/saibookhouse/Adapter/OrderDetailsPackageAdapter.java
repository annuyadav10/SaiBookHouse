package com.appsnipp.claraerp.saibookhouse.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.appsnipp.claraerp.saibookhouse.Activity.OrderDetails;
import com.appsnipp.claraerp.saibookhouse.Activity.OrderDetailsPackageInfo;
import com.appsnipp.claraerp.saibookhouse.Model.CustomerOrderModel;
import com.appsnipp.claraerp.saibookhouse.Model.OrderDetailsModel;
import com.appsnipp.claraerp.saibookhouse.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrderDetailsPackageAdapter extends RecyclerView.Adapter<OrderDetailsPackageAdapter.favouriteholder> {
    Context context;
    List<OrderDetailsModel.PackageInfoItem> packageInfoItems;

    public OrderDetailsPackageAdapter(Context context, List<OrderDetailsModel.PackageInfoItem> packageInfoItems) {
        this.context = context;
        this.packageInfoItems = packageInfoItems;
    }

    @NonNull
    @Override
    public favouriteholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.packagedetailitemlayout, parent, false);
        favouriteholder favouriteholder = new favouriteholder(view);
        return favouriteholder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final favouriteholder holder, int position) {
        holder.packagename.setText(packageInfoItems.get(position).getPackageTitle());
        if(!packageInfoItems.get(position).getPackagePrice().equals("")){
        holder.packageprice.setText("₹"+packageInfoItems.get(position).getPackagePrice());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(context, OrderDetailsPackageInfo.class);
               intent.putExtra("school_id",packageInfoItems.get(position).getSchoolId());
               intent.putExtra("package_id",packageInfoItems.get(position).getPackageId());
               intent.putExtra("category_order",packageInfoItems.get(position).getCategoryOrder());
               context.startActivity(intent);
           }
       });


    }

    @Override
    public int getItemCount() {
        return packageInfoItems.size();
    }

    public class favouriteholder extends RecyclerView.ViewHolder {
        TextView packagename,packageprice;

        public favouriteholder(@NonNull View itemView) {
            super(itemView);
            packagename = itemView.findViewById(R.id.packagename);
            packageprice = itemView.findViewById(R.id.packageprice);
        }
    }
}
