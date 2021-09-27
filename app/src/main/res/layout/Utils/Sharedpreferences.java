package com.appsnipp.claraerp.saibookhouse.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Sharedpreferences {

    private static Sharedpreferences mSharedPref;
    public static final String NAME = "PREFS_NAME";
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public Sharedpreferences(Context context) {
        this.sharedPreferences=context.getSharedPreferences(NAME,0);
        this.editor=this.sharedPreferences.edit();
    }

    public static Sharedpreferences getInstance(Context context)
    {
        if(mSharedPref == null) {
            mSharedPref = new Sharedpreferences(context);
        }
        return mSharedPref;
    }


    public  void setAppVersion(String version){
        this.editor=this.sharedPreferences.edit();
        this.editor.putString(Constant.Version,version);
        this.editor.commit();
    }
    public String getAppVersion(){
        return this.sharedPreferences.getString(Constant.Version,"");
    }




     public  void setemp_id(String empid){
        this.editor=this.sharedPreferences.edit();
        this.editor.putString(Constant.empl_id,empid);
        this.editor.commit();
     }
     public String getEmpid(){
        return this.sharedPreferences.getString(Constant.empl_id,"");
     }



    public  void setName(String key, String Name){
        this.editor=this.sharedPreferences.edit();
        this.editor.putString(key,Name);
        this.editor.commit();
    }
    public String getName(String key){
        return this.sharedPreferences.getString(key,"");
    }


    public  void setEmail(String email){
        this.editor=this.sharedPreferences.edit();
        this.editor.putString(Constant.UserEmail,email);
        this.editor.commit();
    }
    public String getEmail(){
        return this.sharedPreferences.getString(Constant.UserEmail,"");
    }

    public  void setdesiganation(String email){
        this.editor=this.sharedPreferences.edit();
        this.editor.putString(Constant.designation,email);
        this.editor.commit();
    }
    public String getgesignation(){
        return this.sharedPreferences.getString(Constant.designation,"");
    }

    public  void setBranchId(String branchId){
        this.editor=this.sharedPreferences.edit();
        this.editor.putString(Constant.branchid,branchId);
        this.editor.commit();
    }

    public String getBranchId(){
        return this.sharedPreferences.getString(Constant.branchid,"");
    }

    public  void setLoginType(String type){
        this.editor=this.sharedPreferences.edit();
        this.editor.putString(Constant.logintype,type);
        this.editor.commit();
    }

    public String getLoginType(){
        return this.sharedPreferences.getString(Constant.logintype,"");
    }

    public  void setEmpUserType(String type){
        this.editor=this.sharedPreferences.edit();
        this.editor.putString(Constant.emplytype,type);
        this.editor.commit();
    }

    public String getEmpUserType(){
        return this.sharedPreferences.getString(Constant.emplytype,"");
    }


    public  void setAdmissionId(String type){
        this.editor=this.sharedPreferences.edit();
        this.editor.putString(Constant.admissionId,type);
        this.editor.commit();
    }

    public String getAdmissionid(){
        return this.sharedPreferences.getString(Constant.admissionId,"");
    }

    public void islogged(Boolean aBoolean){
        this.editor=this.sharedPreferences.edit();
        this.editor.putBoolean(Constant.islogged,aBoolean);
        this.editor.commit();
    }

    public boolean getlogged(){
        return  this.sharedPreferences.getBoolean(Constant.islogged,false);
    }

    // student details..............................................

    public  void setCustomerData(String key, String value){
        this.editor=this.sharedPreferences.edit();
        this.editor.putString(key,value);
        this.editor.commit();
    }
    public String getCustomerData(String key){
        return this.sharedPreferences.getString(key,"");
    }
    // teacher details...............................................

    public  void setTeacherData(String key, String value){
        this.editor=this.sharedPreferences.edit();
        this.editor.putString(key,value);
        this.editor.commit();
    }
    public String getTeacherData(String key){
        return this.sharedPreferences.getString(key,"");     //Cntrl + Shift + R
    }


    public  void setdepartment_id(String department){
        this.editor=this.sharedPreferences.edit();
        this.editor.putString(Constant.department,department);
        this.editor.commit();
    }
    public String getdepartmentid(){
        return this.sharedPreferences.getString(Constant.department,"");
    }

   // for teacher logout .............................................................

    public void isslogged(Boolean aBoolean){
        this.editor=this.sharedPreferences.edit();
        this.editor.putBoolean(Constant.islogged,aBoolean);
        this.editor.commit();
    }

    public boolean getllogged(){
        return  this.sharedPreferences.getBoolean(Constant.islogged,false);
    }

}
