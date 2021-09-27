package com.appsnipp.claraerp.saibookhouse.Model;

import com.google.gson.annotations.SerializedName;

public class BuyNowModel{

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

		@SerializedName("book_price")
		private String bookPrice;

		@SerializedName("stationery_price")
		private String stationeryPrice;

		@SerializedName("quantity")
		private String quantity;

		@SerializedName("delivery_charges")
		private String deliveryCharges;

		@SerializedName("total_price")
		private String totalPrice;

		@SerializedName("package_name")
		private String packageName;

		@SerializedName("note_book_price")
		private String noteBookPrice;

		@SerializedName("school_name")
		private String schoolName;

		@SerializedName("package_id")
		private String packageId;

		@SerializedName("package_price")
		private String packagePrice;

		public String getBookPrice(){
			return bookPrice;
		}

		public String getStationeryPrice(){
			return stationeryPrice;
		}

		public String getQuantity(){
			return quantity;
		}

		public String getDeliveryCharges(){
			return deliveryCharges;
		}

		public String getTotalPrice(){
			return totalPrice;
		}

		public String getPackageName(){
			return packageName;
		}

		public String getNoteBookPrice(){
			return noteBookPrice;
		}

		public String getSchoolName(){
			return schoolName;
		}

		public String getPackageId(){
			return packageId;
		}

		public String getPackagePrice(){
			return packagePrice;
		}
	}
}