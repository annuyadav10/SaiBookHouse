package com.appsnipp.claraerp.saibookhouse.Model;

import com.google.gson.annotations.SerializedName;

public class ForgetOtpModel{

	@SerializedName("response")
	private String response;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public String getResponse(){
		return response;
	}

	public String getMessage(){
		return message;
	}

	public int getStatus(){
		return status;
	}
}