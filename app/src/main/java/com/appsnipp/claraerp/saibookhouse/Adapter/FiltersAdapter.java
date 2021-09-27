package com.appsnipp.claraerp.saibookhouse.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.appsnipp.claraerp.saibookhouse.Activity.SchoolPackageActivity;
import com.appsnipp.claraerp.saibookhouse.R;

import java.util.List;

public class FiltersAdapter extends RecyclerView.Adapter<FiltersAdapter.favouriteholder> {
    Context context;
    List<String> FilterModels;
    List<String> Check;

    public FiltersAdapter(Context context, List<String> filterModels, List<String> check) {
        this.context = context;
        FilterModels = filterModels;
        Check = check;
    }

    @NonNull
    @Override
    public favouriteholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.filterdesign, parent, false);
        favouriteholder favouriteholder = new favouriteholder(view);
        return favouriteholder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final favouriteholder holder, int position) {
        holder.name.setText(FilterModels.get(position));
        for(int i=0;i<Check.size();i++){
            if((FilterModels.get(position)).equals(Check.get(i))){
                holder.name.setChecked(true);
                break;
            }else{
                holder.name.setChecked(false);
            }
        }
        holder.name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((SchoolPackageActivity)context).onItemClick(holder,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return FilterModels.size();
    }

    public class favouriteholder extends RecyclerView.ViewHolder {
        CheckBox name;

        public favouriteholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.filtername);
        }
    }
}
