
package com.appsnipp.claraerp.saibookhouse.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class PackageInfoModel {

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

        @SerializedName("books")
        private List<Book> mBooks;
        @SerializedName("note_books")
        private List<NoteBook> mNoteBooks;
        @SerializedName("package_info")
        private PackageInfo mPackageInfo;
        @SerializedName("stationery")
        private List<Stationery> mStationery;

        public List<Book> getBooks() {
            return mBooks;
        }

        public void setBooks(List<Book> books) {
            mBooks = books;
        }

        public List<NoteBook> getNoteBooks() {
            return mNoteBooks;
        }

        public void setNoteBooks(List<NoteBook> noteBooks) {
            mNoteBooks = noteBooks;
        }

        public PackageInfo getPackageInfo() {
            return mPackageInfo;
        }

        public void setPackageInfo(PackageInfo packageInfo) {
            mPackageInfo = packageInfo;
        }

        public List<Stationery> getStationery() {
            return mStationery;
        }

        public void setStationery(List<Stationery> stationery) {
            mStationery = stationery;
        }
        public class Book {

            @SerializedName("price")
            private String mPrice;
            @SerializedName("quantity")
            private String mQuantity;
            @SerializedName("title")
            private String mTitle;
            @SerializedName("total_amount")
            private Long mTotalAmount;

            public String getPrice() {
                return mPrice;
            }

            public void setPrice(String price) {
                mPrice = price;
            }

            public String getQuantity() {
                return mQuantity;
            }

            public void setQuantity(String quantity) {
                mQuantity = quantity;
            }

            public String getTitle() {
                return mTitle;
            }

            public void setTitle(String title) {
                mTitle = title;
            }

            public Long getTotalAmount() {
                return mTotalAmount;
            }

            public void setTotalAmount(Long totalAmount) {
                mTotalAmount = totalAmount;
            }

        }
        public class NoteBook {

            @SerializedName("hsn_code")
            private String mHsnCode;
            @SerializedName("price")
            private String mPrice;
            @SerializedName("quantity")
            private String mQuantity;
            @SerializedName("total_amount")
            private Long mTotalAmount;
            @SerializedName("type")
            private String mType;

            public String getHsnCode() {
                return mHsnCode;
            }

            public void setHsnCode(String hsnCode) {
                mHsnCode = hsnCode;
            }

            public String getPrice() {
                return mPrice;
            }

            public void setPrice(String price) {
                mPrice = price;
            }

            public String getQuantity() {
                return mQuantity;
            }

            public void setQuantity(String quantity) {
                mQuantity = quantity;
            }

            public Long getTotalAmount() {
                return mTotalAmount;
            }

            public void setTotalAmount(Long totalAmount) {
                mTotalAmount = totalAmount;
            }

            public String getType() {
                return mType;
            }

            public void setType(String type) {
                mType = type;
            }

        }
        public class Stationery {

            @SerializedName("hsn_code")
            private String mHsnCode;
            @SerializedName("price")
            private String mPrice;
            @SerializedName("quantity")
            private String mQuantity;
            @SerializedName("total_amount")
            private Long mTotalAmount;
            @SerializedName("type")
            private String mType;

            public String getHsnCode() {
                return mHsnCode;
            }

            public void setHsnCode(String hsnCode) {
                mHsnCode = hsnCode;
            }

            public String getPrice() {
                return mPrice;
            }

            public void setPrice(String price) {
                mPrice = price;
            }

            public String getQuantity() {
                return mQuantity;
            }

            public void setQuantity(String quantity) {
                mQuantity = quantity;
            }

            public Long getTotalAmount() {
                return mTotalAmount;
            }

            public void setTotalAmount(Long totalAmount) {
                mTotalAmount = totalAmount;
            }

            public String getType() {
                return mType;
            }

            public void setType(String type) {
                mType = type;
            }

        }
        public class PackageInfo {

            @SerializedName("class_name")
            private String mClassName;
            @SerializedName("package_language")
            private String mPackageLanguage;
            @SerializedName("package_name")
            private String mPackageName;
            @SerializedName("package_price")
            private String mPackagePrice;

            public String getClassName() {
                return mClassName;
            }

            public void setClassName(String className) {
                mClassName = className;
            }

            public String getPackageLanguage() {
                return mPackageLanguage;
            }

            public void setPackageLanguage(String packageLanguage) {
                mPackageLanguage = packageLanguage;
            }

            public String getPackageName() {
                return mPackageName;
            }

            public void setPackageName(String packageName) {
                mPackageName = packageName;
            }

            public String getPackagePrice() {
                return mPackagePrice;
            }

            public void setPackagePrice(String packagePrice) {
                mPackagePrice = packagePrice;
            }

        }
    }
}
