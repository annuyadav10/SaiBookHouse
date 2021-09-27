package com.appsnipp.claraerp.saibookhouse.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appsnipp.claraerp.saibookhouse.Model.PackageInfooModel;
import com.appsnipp.claraerp.saibookhouse.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PackageInfoAdapter extends RecyclerView.Adapter<PackageInfoAdapter.Holder> {

    Context context;
    List<PackageInfooModel.Data> data = new ArrayList<>();
    String check;

    public PackageInfoAdapter(Context context, List<PackageInfooModel.Data> data,String checktype) {
        this.context = context;
        this.data = data;
        this.check=checktype;
    }

    @NonNull
    @NotNull
    @Override
    public Holder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.packageinfodesign, viewGroup, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Holder holder, int i) {
            if(data!=null&&data.size()>0){
                PackageInfooModel.Data infooModel = data.get(i);
                if(check.equals("Book")) {
                    holder.type.setVisibility(View.GONE);
                    holder.itemname.setText(infooModel.getTitle());
                    holder.price.setText("₹" + infooModel.getPrice());
                    holder.quantity.setText(infooModel.getQuantity());
                    holder.amount.setText("₹" + infooModel.getTotalAmount());
                }
                else {
                    holder.type.setVisibility(View.VISIBLE);
                    holder.itemname.setMaxWidth(40);
                    holder.itemname.setText(infooModel.getType());
                    holder.price.setText("₹" + infooModel.getHsnCode());
                    holder.quantity.setText(infooModel.getPrice());
                    holder.amount.setText( infooModel.getQuantity());
                    holder.type.setText("₹" + infooModel.getTotalAmount());
                }
            }else {
                return;
            }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView itemname,price,quantity,amount,type;

        public Holder(@NonNull @NotNull View itemView) {
            super(itemView);
            itemname=itemView.findViewById(R.id.itemname);
            price=itemView.findViewById(R.id.price);
            quantity=itemView.findViewById(R.id.quantity);
            amount=itemView.findViewById(R.id.totalamount);
            type=itemView.findViewById(R.id.type);

        }
    }
}
