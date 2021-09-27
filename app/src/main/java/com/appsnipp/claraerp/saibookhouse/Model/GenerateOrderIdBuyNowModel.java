package com.appsnipp.claraerp.saibookhouse.Model;

import com.google.gson.annotations.SerializedName;

public class GenerateOrderIdBuyNowModel{

	@SerializedName("response")
	private Response response;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public Response getResponse(){
		return response;
	}

	public String getMessage(){
		return message;
	}

	public int getStatus(){
		return status;
	}

	public class Response{

		@SerializedName("total_amount")
		private int totalAmount;

		@SerializedName("order_id")
		private String orderId;

		public int getTotalAmount(){
			return totalAmount;
		}

		public String getOrderId(){
			return orderId;
		}
	}
}