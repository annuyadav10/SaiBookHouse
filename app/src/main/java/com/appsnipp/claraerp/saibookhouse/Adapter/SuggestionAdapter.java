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

import com.appsnipp.claraerp.saibookhouse.Activity.SchoolPackageActivity;
import com.appsnipp.claraerp.saibookhouse.Model.SchoolListModel;
import com.appsnipp.claraerp.saibookhouse.R;

import java.util.List;

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.favouriteholder> {
    Context context;
    List<SchoolListModel.ResponseItem> SchoolListModels;

    public SuggestionAdapter(Context context, List<SchoolListModel.ResponseItem> SchoolListModels) {
        this.context = context;
        this.SchoolListModels = SchoolListModels;
    }

    @NonNull
    @Override
    public favouriteholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.suggestiondesign, parent, false);
        favouriteholder favouriteholder = new favouriteholder(view);
        return favouriteholder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final favouriteholder holder, int position) {
        holder.suggestion.setText(SchoolListModels.get(position).getSchoolName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SchoolPackageActivity.class);
                intent.putExtra("SchoolId", SchoolListModels.get(position).getSchoolId());
                intent.putExtra("SchoolName", SchoolListModels.get(position).getSchoolName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return SchoolListModels.size();
    }

    class favouriteholder extends RecyclerView.ViewHolder {
        TextView suggestion;

        public favouriteholder(@NonNull View itemView) {
            super(itemView);
            suggestion = itemView.findViewById(R.id.suggestion);
        }
    }
}
