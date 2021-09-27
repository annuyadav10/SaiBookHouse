package com.appsnipp.claraerp.saibookhouse.Model;

import com.google.gson.annotations.SerializedName;

public class MyProfileModel{

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

		@SerializedName("area")
		private String area;

		@SerializedName("customer_address")
		private String customerAddress;

		@SerializedName("customer_mobile")
		private String customerMobile;

		@SerializedName("alternate_number")
		private String alternateNumber;

		@SerializedName("customer_email")
		private String customerEmail;

		@SerializedName("customer_name")
		private String customerName;

		@SerializedName("city")
		private String city;

		@SerializedName("state_pincode")
		private String pincode;

		@SerializedName("customer_state")
		private String state;

		public String getPincode() {
			return pincode;
		}

		public void setPincode(String pincode) {
			this.pincode = pincode;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getArea(){
			return area;
		}

		public String getCustomerAddress(){
			return customerAddress;
		}

		public String getCustomerMobile(){
			return customerMobile;
		}

		public String getAlternateNumber(){
			return alternateNumber;
		}

		public String getCustomerEmail(){
			return customerEmail;
		}

		public String getCustomerName(){
			return customerName;
		}
	}
}