
package com.appsnipp.claraerp.saibookhouse.Model;

import com.google.gson.annotations.SerializedName;


public class CartCountModel {

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

        @SerializedName("count")
        private Long mCount;

        public Long getCount() {
            return mCount;
        }

        public void setCount(Long count) {
            mCount = count;
        }

    }
}
