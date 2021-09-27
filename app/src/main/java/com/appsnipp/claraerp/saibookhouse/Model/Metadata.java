package com.appsnipp.claraerp.saibookhouse.Model;

import com.google.gson.annotations.SerializedName;

public class Metadata{

	@SerializedName("payment_id")
	private String paymentId;

	@SerializedName("order_id")
	private String orderId;

	public String getPaymentId(){
		return paymentId;
	}

	public String getOrderId(){
		return orderId;
	}
}