package com.appsnipp.claraerp.saibookhouse.Fragments.dashboard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appsnipp.claraerp.saibookhouse.Activity.SearchSchoolActivity;
import com.appsnipp.claraerp.saibookhouse.R;

public class DashBoard extends Fragment {
    CardView card1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_dash_board, container, false);
        Init(view);
        return view;
    }
    public void Init(View view){
        card1=view.findViewById(R.id.card1);
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchSchoolActivity.class));
            }
        });
    }
}