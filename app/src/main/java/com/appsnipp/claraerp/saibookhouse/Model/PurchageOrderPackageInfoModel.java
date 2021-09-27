package com.appsnipp.claraerp.saibookhouse.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PurchageOrderPackageInfoModel{

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

		@SerializedName("package_items")
		private List<PackageItemsItem> packageItems;

		@SerializedName("package_info")
		private PackageInfo packageInfo;

		public List<PackageItemsItem> getPackageItems(){
			return packageItems;
		}

		public PackageInfo getPackageInfo(){
			return packageInfo;
		}
	}

	public class PackageItemsItem{

		@SerializedName("quantity")
		private String quantity;

		@SerializedName("total_amount")
		private int totalAmount;

		@SerializedName("total_amount_after_gst")
		private String totalAmountAfterGst;

		@SerializedName("sgst_amount")
		private String sgstAmount;

		@SerializedName("title")
		private String title;

		@SerializedName("hsn_code")
		private String hsnCode;

		@SerializedName("cgst_amount")
		private String cgstAmount;

		@SerializedName("rate_exclusive_amount")
		private String rateExclusiveAmount;

		public String getQuantity(){
			return quantity;
		}

		public int getTotalAmount(){
			return totalAmount;
		}

		public String getTotalAmountAfterGst(){
			return totalAmountAfterGst;
		}

		public String getSgstAmount(){
			return sgstAmount;
		}

		public String getTitle(){
			return title;
		}

		public String getHsnCode(){
			return hsnCode;
		}

		public String getCgstAmount(){
			return cgstAmount;
		}

		public String getRateExclusiveAmount(){
			return rateExclusiveAmount;
		}
	}

	public class PackageInfo{

		@SerializedName("package_name")
		private String packageName;

		@SerializedName("package_price")
		private String packagePrice;

		public String getPackageName(){
			return packageName;
		}

		public String getPackagePrice(){
			return packagePrice;
		}
	}
}