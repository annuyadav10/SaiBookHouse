package com.appsnipp.claraerp.saibookhouse.Fragments.help.profile;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appsnipp.claraerp.saibookhouse.Activity.ChangePassword;
import com.appsnipp.claraerp.saibookhouse.Activity.LoginScreen;
import com.appsnipp.claraerp.saibookhouse.Activity.ManageAddress;
import com.appsnipp.claraerp.saibookhouse.Activity.MyOrder;
import com.appsnipp.claraerp.saibookhouse.Activity.MyProfile;
import com.appsnipp.claraerp.saibookhouse.R;
import com.appsnipp.claraerp.saibookhouse.Utils.Sharedpreferences;

public class Profile extends Fragment implements View.OnClickListener{
    TextView username;
    CardView myprofilecard,changepasswordcard,manageaddresscard,myordercard,logoutcard;
    Sharedpreferences sharedpreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        init(root);
        setclicklistner();
        return root;
    }

     public  void init(View root){
        sharedpreferences=Sharedpreferences.getInstance(getContext());
        username=root.findViewById(R.id.username);
        myprofilecard=root.findViewById(R.id.card1);
        changepasswordcard=root.findViewById(R.id.card2);
        manageaddresscard=root.findViewById(R.id.card3);
        myordercard=root.findViewById(R.id.card4);
        logoutcard=root.findViewById(R.id.card5);
        username.setText("Hello " +sharedpreferences.getCustomerData("customer_name"));
     }

     public void setclicklistner(){
         myprofilecard.setOnClickListener(this);
         changepasswordcard.setOnClickListener(this);
         manageaddresscard.setOnClickListener(this);
         myordercard.setOnClickListener(this);
         logoutcard.setOnClickListener(this);
     }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.card1:
                startActivity(new Intent(getContext(), MyProfile.class));
                break;
            case R.id.card2:
                startActivity(new Intent(getContext(), ChangePassword.class));
                break;
            case R.id.card3:
                startActivity(new Intent(getContext(), ManageAddress.class));
                break;
            case R.id.card4:
                startActivity(new Intent(getContext(), MyOrder.class));
                break;
            case R.id.card5:
                alert(getContext(),"Are You Sure,You Want To Logout !!");
                break;
        }

    }

    public void alert(Context ctx, String message) {
        final Dialog myDialog2 = new Dialog(ctx, R.style.Theme_Transparent);
        myDialog2.setContentView(R.layout.logoutalert);
        myDialog2.setCanceledOnTouchOutside(true);
        TextView message1=myDialog2.findViewById(R.id.title);
        TextView accept = myDialog2.findViewById(R.id.accept);
        TextView cancel = myDialog2.findViewById(R.id.cancel);
        message1.setText(message);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog2.dismiss();
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getActivity().getSharedPreferences("PREFS_NAME", 0);
                preferences.edit().clear().commit();
                sharedpreferences.islogged(false);
                Intent intent=new Intent(getContext(), LoginScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        myDialog2.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        username.setText("Hello " +sharedpreferences.getCustomerData("customer_name"));
    }
}