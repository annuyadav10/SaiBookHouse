package com.appsnipp.claraerp.saibookhouse.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.appsnipp.claraerp.saibookhouse.Utils.Sharedpreferences;
import com.bumptech.glide.Glide;
import com.appsnipp.claraerp.saibookhouse.Activity.PackageInfo;
import com.appsnipp.claraerp.saibookhouse.Model.SchoolPackageList;
import com.appsnipp.claraerp.saibookhouse.R;

import java.util.List;

public class SchoolPackageAdapter extends RecyclerView.Adapter<SchoolPackageAdapter.favouriteholder> {
    Context context;
    List<SchoolPackageList.ResponseItem> SchoolListModels;
    Sharedpreferences sharedpreferences;

    public SchoolPackageAdapter(Context context, List<SchoolPackageList.ResponseItem> SchoolListModels) {
        this.context = context;
        this.SchoolListModels = SchoolListModels;
        sharedpreferences=Sharedpreferences.getInstance(context);
    }

    @NonNull
    @Override
    public favouriteholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.schoolpackagesdesign, parent, false);
        favouriteholder favouriteholder = new favouriteholder(view);
        return favouriteholder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final favouriteholder holder, int position) {
        holder.name.setText(SchoolListModels.get(position).getPackageName());
        if(!SchoolListModels.get(position).getPackagePrice().equals("")){
        holder.price.setText("₹"+SchoolListModels.get(position).getPackagePrice());}
        holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(context, PackageInfo.class);
               intent.putExtra("PackageId",SchoolListModels.get(position).getPackageId());
               intent.putExtra("SchoolName",SchoolListModels.get(position).getSchoolName());
               intent.putExtra("PackageName",SchoolListModels.get(position).getPackageName());
               intent.putExtra("Price",SchoolListModels.get(position).getPackagePrice());
               intent.putExtra("SchoolId",SchoolListModels.get(position).getSchoolId());

               sharedpreferences.setPacakgeData("PackageId",SchoolListModels.get(position).getPackageId());
               sharedpreferences.setPacakgeData("SchoolName",SchoolListModels.get(position).getSchoolName());
               sharedpreferences.setPacakgeData("PackageName",SchoolListModels.get(position).getPackageName());
               sharedpreferences.setPacakgeData("Price",SchoolListModels.get(position).getPackagePrice());
               sharedpreferences.setPacakgeData("Schoo0lId",SchoolListModels.get(position).getSchoolId());
               context.startActivity(intent);
           }
       });


    }

    @Override
    public int getItemCount() {
        return SchoolListModels.size();
    }

    public class favouriteholder extends RecyclerView.ViewHolder {
        TextView name,price;
        ImageView image;

        public favouriteholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            image = itemView.findViewById(R.id.image);
        }
    }
}
