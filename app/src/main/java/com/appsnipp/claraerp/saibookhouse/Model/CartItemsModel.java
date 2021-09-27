
package com.appsnipp.claraerp.saibookhouse.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class CartItemsModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("response")
    private Response mResponse;
    @SerializedName("status")
    private Long mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Response getResponse() {
        return mResponse;
    }

    public void setResponse(Response response) {
        mResponse = response;
    }

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }
    public class Response {

        @SerializedName("cart")
        private List<Cart> mCart;
        @SerializedName("delivery_charges")
        private Long mDeliveryCharges;
        @SerializedName("items_count")
        private Long mItemsCount;
        @SerializedName("products_price")
        private Long mProductsPrice;
        @SerializedName("total_price")
        private Long mTotalPrice;

        public List<Cart> getCart() {
            return mCart;
        }

        public void setCart(List<Cart> cart) {
            mCart = cart;
        }

        public Long getDeliveryCharges() {
            return mDeliveryCharges;
        }

        public void setDeliveryCharges(Long deliveryCharges) {
            mDeliveryCharges = deliveryCharges;
        }

        public Long getItemsCount() {
            return mItemsCount;
        }

        public void setItemsCount(Long itemsCount) {
            mItemsCount = itemsCount;
        }

        public Long getProductsPrice() {
            return mProductsPrice;
        }

        public void setProductsPrice(Long productsPrice) {
            mProductsPrice = productsPrice;
        }

        public Long getTotalPrice() {
            return mTotalPrice;
        }

        public void setTotalPrice(Long totalPrice) {
            mTotalPrice = totalPrice;
        }
        public class Cart {

            @SerializedName("package_id")
            private String mPackageId;
            @SerializedName("package_name")
            private String mPackageName;
            @SerializedName("package_price")
            private Long mPackagePrice;
            @SerializedName("quantity")
            private String mQuantity;
            @SerializedName("school_name")
            private String mSchoolName;

            public String getPackageId() {
                return mPackageId;
            }

            public void setPackageId(String packageId) {
                mPackageId = packageId;
            }

            public String getPackageName() {
                return mPackageName;
            }

            public void setPackageName(String packageName) {
                mPackageName = packageName;
            }

            public Long getPackagePrice() {
                return mPackagePrice;
            }

            public void setPackagePrice(Long packagePrice) {
                mPackagePrice = packagePrice;
            }

            public String getQuantity() {
                return mQuantity;
            }

            public void setQuantity(String quantity) {
                mQuantity = quantity;
            }

            public String getSchoolName() {
                return mSchoolName;
            }

            public void setSchoolName(String schoolName) {
                mSchoolName = schoolName;
            }

        }
    }
}
