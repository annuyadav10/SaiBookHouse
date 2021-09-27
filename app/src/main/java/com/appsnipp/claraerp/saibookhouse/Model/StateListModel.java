package com.appsnipp.claraerp.saibookhouse.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class StateListModel{

	@SerializedName("response")
	private List<ResponseItem> response;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public List<ResponseItem> getResponse(){
		return response;
	}

	public String getMessage(){
		return message;
	}

	public int getStatus(){
		return status;
	}
	public class ResponseItem{

		@SerializedName("state_name")
		private String stateName;

		public String getStateName(){
			return stateName;
		}
	}
}