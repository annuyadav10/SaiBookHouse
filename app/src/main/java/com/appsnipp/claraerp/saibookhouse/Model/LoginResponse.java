package com.appsnipp.claraerp.saibookhouse.Model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse{

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

		@SerializedName("customer_state")
		private String customerState;

		@SerializedName("city")
		private String city;

		@SerializedName("customer_email")
		private String customerEmail;

		@SerializedName("state_pincode")
		private String statePincode;

		@SerializedName("customer_name")
		private String customerName;

		@SerializedName("customer_id")
		private String customerId;

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

		public String getCustomerState(){
			return customerState;
		}

		public String getCity(){
			return city;
		}

		public String getCustomerEmail(){
			return customerEmail;
		}

		public String getStatePincode(){
			return statePincode;
		}

		public String getCustomerName(){
			return customerName;
		}

		public String getCustomerId(){
			return customerId;
		}
	}
}