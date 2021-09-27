package com.appsnipp.claraerp.saibookhouse.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailsModel{

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

        @SerializedName("transaction_date")
        private String transactionDate;

        @SerializedName("customer_address")
        private String customerAddress;

        @SerializedName("pincode")
        private String pincode;

        @SerializedName("customer_mobile")
        private String customerMobile;

        @SerializedName("amount_paid")
        private String amountPaid;

        @SerializedName("bank_transaction_id")
        private String bankTransactionId;

        @SerializedName("customer_state")
        private String customerState;

        @SerializedName("city")
        private String city;

        @SerializedName("total_package_price")
        private String totalPackagePrice;

        @SerializedName("customer_email")
        private String customerEmail;

        @SerializedName("ordered_date")
        private String orderedDate;

        @SerializedName("delievery_charges")
        private String delieveryCharges;

        @SerializedName("customer_name")
        private String customerName;

        @SerializedName("order_id")
        private String orderId;

        @SerializedName("package_info")
        private List<PackageInfoItem> packageInfo;

        public String getArea(){
            return area;
        }

        public String getTransactionDate(){
            return transactionDate;
        }

        public String getCustomerAddress(){
            return customerAddress;
        }

        public String getPincode(){
            return pincode;
        }

        public String getCustomerMobile(){
            return customerMobile;
        }

        public String getAmountPaid(){
            return amountPaid;
        }

        public String getBankTransactionId(){
            return bankTransactionId;
        }

        public String getCustomerState(){
            return customerState;
        }

        public String getCity(){
            return city;
        }

        public String getTotalPackagePrice(){
            return totalPackagePrice;
        }

        public String getCustomerEmail(){
            return customerEmail;
        }

        public String getOrderedDate(){
            return orderedDate;
        }

        public String getDelieveryCharges(){
            return delieveryCharges;
        }

        public String getCustomerName(){
            return customerName;
        }

        public String getOrderId(){
            return orderId;
        }

        public List<PackageInfoItem> getPackageInfo(){
            return packageInfo;
        }
    }

    public class PackageInfoItem{

        @SerializedName("price_after_quantity")
        private String priceAfterQuantity;

        @SerializedName("quantity")
        private String quantity;

        @SerializedName("school_id")
        private String schoolId;

        @SerializedName("package_id")
        private String packageId;

        @SerializedName("package_title")
        private String packageTitle;

        @SerializedName("package_price")
        private String packagePrice;

        @SerializedName("category_order")
        private String categoryOrder;

        public String getPriceAfterQuantity(){
            return priceAfterQuantity;
        }

        public String getQuantity(){
            return quantity;
        }

        public String getSchoolId(){
            return schoolId;
        }

        public String getPackageId(){
            return packageId;
        }

        public String getPackageTitle(){
            return packageTitle;
        }

        public String getPackagePrice(){
            return packagePrice;
        }

        public String getCategoryOrder(){
            return categoryOrder;
        }
    }

}