package com.appsnipp.claraerp.saibookhouse.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.appsnipp.claraerp.saibookhouse.Activity.OrderDetails;
import com.appsnipp.claraerp.saibookhouse.Activity.PackageInfo;
import com.appsnipp.claraerp.saibookhouse.Model.CustomerOrderModel;
import com.appsnipp.claraerp.saibookhouse.Model.SchoolPackageList;
import com.appsnipp.claraerp.saibookhouse.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CustomerOrderAdapter extends RecyclerView.Adapter<CustomerOrderAdapter.favouriteholder> {
    Context context;
    List<CustomerOrderModel.ResponseItem> SchoolListModels;

    public CustomerOrderAdapter(Context context, List<CustomerOrderModel.ResponseItem> SchoolListModels) {
        this.context = context;
        this.SchoolListModels = SchoolListModels;
    }

    @NonNull
    @Override
    public favouriteholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.orderitemlayout, parent, false);
        favouriteholder favouriteholder = new favouriteholder(view);
        return favouriteholder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final favouriteholder holder, int position) {
        SimpleDateFormat Old=new SimpleDateFormat("YYYY-MM-dd");
        SimpleDateFormat New =new SimpleDateFormat("dd-MM-YYYY");

        if(SchoolListModels.get(position).getStatus().equals("Order Placed"))
        {
            holder.orderstatus.setText(SchoolListModels.get(position).getStatus());
            holder.orderstatus.setTextColor(context.getColor(R.color.actionbarcolor));
        }
        else if(SchoolListModels.get(position).getStatus().equals("Order Cancelled")){
            holder.orderstatus.setText(SchoolListModels.get(position).getStatus());
            holder.orderstatus.setTextColor(context.getColor(R.color.ordercancel));

        } else if(SchoolListModels.get(position).getStatus().equals("Ready For Dispatched")){
            holder.orderstatus.setText(SchoolListModels.get(position).getStatus());
            holder.orderstatus.setTextColor(context.getColor(R.color.orderdispatchedcolor));

        }else if(SchoolListModels.get(position).getStatus().equals("Order Dispatched")){
            holder.orderstatus.setText(SchoolListModels.get(position).getStatus());
            holder.orderstatus.setTextColor(context.getColor(R.color.design_default_color_primary_dark));
        }
        else{
            holder.orderstatus.setText(SchoolListModels.get(position).getStatus());
            holder.orderstatus.setTextColor(context.getColor(R.color.orderdelivered));
        }

        holder.packagename.setText(SchoolListModels.get(position).getPackageName());
        try {
            Date date=new Date();
            holder.orderdate.setText(New.format(Old.parse(SchoolListModels.get(position).getOrderedDate())));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(!SchoolListModels.get(position).getAmountPaid().equals("")){
        holder.packageprice.setText("₹"+SchoolListModels.get(position).getAmountPaid());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(context, OrderDetails.class);
               intent.putExtra("order_id",SchoolListModels.get(position).getOrderId());
               context.startActivity(intent);
           }
       });


    }

    @Override
    public int getItemCount() {
        return SchoolListModels.size();
    }

    public class favouriteholder extends RecyclerView.ViewHolder {
        TextView orderstatus,orderdate,packagename,packageprice;

        public favouriteholder(@NonNull View itemView) {
            super(itemView);
            orderstatus = itemView.findViewById(R.id.orderstatus);
            orderdate = itemView.findViewById(R.id.placeddate);
            packagename = itemView.findViewById(R.id.packagename);
            packageprice = itemView.findViewById(R.id.packageprice);
        }
    }
}
