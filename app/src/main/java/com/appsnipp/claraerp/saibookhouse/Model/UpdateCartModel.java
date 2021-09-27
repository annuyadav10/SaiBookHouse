
package com.appsnipp.claraerp.saibookhouse.Model;

import com.google.gson.annotations.SerializedName;


public class UpdateCartModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private Long mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

}
