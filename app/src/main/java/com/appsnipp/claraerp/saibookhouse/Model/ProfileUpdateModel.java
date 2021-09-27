package com.appsnipp.claraerp.saibookhouse.Model;

import com.google.gson.annotations.SerializedName;

public class ProfileUpdateModel{

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public String getMessage(){
		return message;
	}

	public int getStatus(){
		return status;
	}
}