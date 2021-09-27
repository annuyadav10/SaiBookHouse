package com.appsnipp.claraerp.saibookhouse.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CustomerOrderModel{

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

		@SerializedName("amount_paid")
		private String amountPaid;

		@SerializedName("ordered_date")
		private String orderedDate;

		@SerializedName("package_name")
		private String packageName;

		@SerializedName("package_id")
		private String packageId;

		@SerializedName("order_id")
		private String orderId;

		@SerializedName("status")
		private String status;

		public String getAmountPaid(){
			return amountPaid;
		}

		public String getOrderedDate(){
			return orderedDate;
		}

		public String getPackageName(){
			return packageName;
		}

		public String getPackageId(){
			return packageId;
		}

		public String getOrderId(){
			return orderId;
		}

		public String getStatus(){
			return status;
		}
	}
}