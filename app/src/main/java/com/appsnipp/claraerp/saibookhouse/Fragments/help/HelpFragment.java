package com.appsnipp.claraerp.saibookhouse.Fragments.help;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.appsnipp.claraerp.saibookhouse.Activity.DropQuery;
import com.appsnipp.claraerp.saibookhouse.BuildConfig;
import com.appsnipp.claraerp.saibookhouse.R;

public class HelpFragment extends Fragment  implements View.OnClickListener{
    CardView cardview1,cardview2,cardview3,cardview4,cardview5;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        init(root);
        setclicklistner();
        return root;
    }
    public void init(View root ){
        cardview1=root.findViewById(R.id.card1);
        cardview2=root.findViewById(R.id.card2);
        cardview3=root.findViewById(R.id.card3);
        cardview4=root.findViewById(R.id.card4);
        cardview5=root.findViewById(R.id.card5);
    }

    public void setclicklistner(){
        cardview1.setOnClickListener(this);
        cardview2.setOnClickListener(this);
        cardview3.setOnClickListener(this);
        cardview4.setOnClickListener(this);
        cardview5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
         switch (v.getId()){
             case R.id.card1:
                 Intent intent=new Intent(getContext(), DropQuery.class);
                 startActivity(intent);
                 break;
             case R.id.card2:
                 Intent callIntent = new Intent(Intent.ACTION_CALL);
                 callIntent.setData(Uri.parse("tel:"+"91 9849843819"));//change the number
                 startActivity(callIntent);
                 break;
             case R.id.card3:
                 Intent intent1 = new Intent(Intent.ACTION_SEND);
                 intent1.setType("plain/text");
                 intent1.putExtra(Intent.EXTRA_EMAIL, new String[] { "Saibookhouse@yahoo.in" });
                 startActivity(Intent.createChooser(intent1, ""));
                 break;
             case R.id.card4:
                 Intent i = new Intent(Intent.ACTION_VIEW);
                 i.setData(Uri.parse("market://details?id=" +getActivity().getPackageName()));
                 startActivity(i);
                 break;
             case R.id.card5:
                 try {
                     Intent shareIntent = new Intent(Intent.ACTION_SEND);
                     shareIntent.setType("text/plain");
                     shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Sai Book House");
                     String shareMessage = "http://onelink.to/q7pcfd";
                     shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                     startActivity(Intent.createChooser(shareIntent, "choose one"));
                 } catch(Exception e) {
                     //e.toString();
                 }
                 break;

         }
    }
}