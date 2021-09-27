package com.appsnipp.claraerp.saibookhouse.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appsnipp.claraerp.saibookhouse.Model.PackageInfooModel;
import com.appsnipp.claraerp.saibookhouse.Model.PurchageOrderPackageInfoModel;
import com.appsnipp.claraerp.saibookhouse.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PurchagedPackageInfoAdapter extends RecyclerView.Adapter<PurchagedPackageInfoAdapter.Holder> {

    Context context;
    List<PurchageOrderPackageInfoModel.PackageItemsItem> data = new ArrayList<>();

    public PurchagedPackageInfoAdapter(Context context, List<PurchageOrderPackageInfoModel.PackageItemsItem> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @NotNull
    @Override
    public Holder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.orderdetailspackageinfodesign, viewGroup, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Holder holder, int i) {
            if(data!=null&&data.size()>0){
                PurchageOrderPackageInfoModel.PackageItemsItem infooModel = data.get(i);
                    holder.itemname.setMaxWidth(50);
                    holder.itemname.setText(infooModel.getTitle());
                    holder.unitprice.setText("₹" + infooModel.getRateExclusiveAmount());
                    holder.hsncode.setText(infooModel.getHsnCode());
                    holder.qnt.setText(infooModel.getQuantity());
                    holder.amountgst.setText("₹" + infooModel.getTotalAmountAfterGst());
                    holder.csgt.setText("₹" + infooModel.getCgstAmount());
                    holder.sgt.setText("₹" + infooModel.getSgstAmount());
                    holder.totalamt.setText("₹" + infooModel.getTotalAmount());
            }else {
                return;
            }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView itemname,unitprice,hsncode,sgt,csgt,amountgst,qnt,totalamt;

        public Holder(@NonNull @NotNull View itemView) {
            super(itemView);
            itemname=itemView.findViewById(R.id.itemname);
            unitprice=itemView.findViewById(R.id.unitprice);
            hsncode=itemView.findViewById(R.id.hsncode);
            sgt=itemView.findViewById(R.id.sgt);
            csgt=itemView.findViewById(R.id.csgt);
            amountgst=itemView.findViewById(R.id.amountgst);
            qnt=itemView.findViewById(R.id.qnt);
            totalamt=itemView.findViewById(R.id.totalamt);

        }
    }
}
