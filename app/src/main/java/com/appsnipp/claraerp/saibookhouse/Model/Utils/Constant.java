package com.appsnipp.claraerp.saibookhouse.Model.Utils;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.widget.TextView;

import com.appsnipp.claraerp.saibookhouse.BuildConfig;
import com.appsnipp.claraerp.saibookhouse.R;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Constant {
    public static String BaseUrl="https://saibookhouse.com/App/";
    public static String X_API_KEY="adde9d54-c905-4b71-88d7-6d10056bab8d";//Header
    public static String AppVersion="";
    public static String UserType="";
    public static String IconName="";
    public static String empl_id="empl_id";
    public static String Version="version";
    public static String emplType="emp_type";
    public static String department="department";
    public static String UserName="emp_name";
    public static String UserEmail="email";
    public static String islogged="logged";
    public static String designation="designation";
    public static String branchid="branchid";
    public static String logintype="logintype";
    public static String emplytype="emplytype";
    public static String admissionId="admissionId";
    public static String LoginType="";
    public static String AdmissionId="";
    public static String BranchId="";
    public static String emp_id="";
    public static String EmpUserType="";
    public static boolean is_logged=false;
    public static ArrayList<String> ImageList=new ArrayList<>();
    public static ArrayList<String> Description=new ArrayList<>();


// for loder during fetching server data....................................................................................................
    public static String mess = "";
    public static Dialog dialog;
    public static int step = 0;
    public static TextView loadingText;
    public static Handler handler = new Handler();
    public static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (step == 0) {
                loadingText.setText(mess + ".");
                step = 1;

            } else if (step == 1) {
                loadingText.setText(mess + "..");
                step = 2;
            } else {
                loadingText.setText(mess + "...");
                step = 0;
            }
            Repeat(mess);
        }
    };


    public static void loadingpopup(Context context, String text) {
        dialog = new Dialog(context, R.style.Theme_Transparent);
        dialog.setContentView(R.layout.loadingpopup);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        loadingText = dialog.findViewById(R.id.loadingtext);
        loadingText.setText(text);
        Repeat(text);
        dialog.show();
    }

    public static void Repeat(final String text) {
        mess = text;
        handler.postDelayed(runnable, 100);
    }

    public static void StopLoader() {
        step = 0;
        handler.removeCallbacks(runnable);
        dialog.dismiss();

    }
    public static Map<String, String> Headers(){
        Map<String, String> fields1 = new HashMap<>();

        if (BuildConfig.DEBUG) {
            fields1.put("Accept", "application/json");
            fields1.put("X-API-KEY", com.appsnipp.claraerp.saibookhouse.Utils.Constant.X_API_KEY);
        }
        else {
            fields1.put("Accept", "application/json");
            fields1.put("X-API-KEY", com.appsnipp.claraerp.saibookhouse.Utils.Constant.X_API_KEY);
        }
        return  fields1;
    }


  public static boolean NetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public static String parseDateToddMMyyyy(String time) {

        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd- MM-yyyy");
        Date date = null;
        try {
            date = inputFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStr = outputFormat.format(date);
        return outputDateStr;
    }

    public static Date parseDateToddMMyyyyy(String time) {

        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd- MM-yyyy");
        Date date = null;
        try {
            date = inputFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String parseDateStringYYYYMMDD(String datee){
        DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = inputFormat.parse(datee);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStr = outputFormat.format(date);
        return outputDateStr;
    }
    // convert password in md5...................................
    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes("UTF-8"));
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        } catch(UnsupportedEncodingException ex){
        }
        return null;
    }

}
