package com.appsnipp.claraerp.saibookhouse.Network;
import com.appsnipp.claraerp.saibookhouse.Activity.DropQuery;
import com.appsnipp.claraerp.saibookhouse.Model.BuyNowModel;
import com.appsnipp.claraerp.saibookhouse.Model.CartCountModel;
import com.appsnipp.claraerp.saibookhouse.Model.CartItemsModel;
import com.appsnipp.claraerp.saibookhouse.Model.ChangePasswordModel;
import com.appsnipp.claraerp.saibookhouse.Model.CustomerOrderModel;
import com.appsnipp.claraerp.saibookhouse.Model.DropQueryModel;
import com.appsnipp.claraerp.saibookhouse.Model.ForgetOtpModel;
import com.appsnipp.claraerp.saibookhouse.Model.GenerateOrderIdBuyNowModel;
import com.appsnipp.claraerp.saibookhouse.Model.LoginResponse;
import com.appsnipp.claraerp.saibookhouse.Model.MyProfileModel;
import com.appsnipp.claraerp.saibookhouse.Model.OrderDetailsModel;
import com.appsnipp.claraerp.saibookhouse.Model.OtpverificationModel;
import com.appsnipp.claraerp.saibookhouse.Model.PackageInfoModel;
import com.appsnipp.claraerp.saibookhouse.Model.PasswordUpdateModel;
import com.appsnipp.claraerp.saibookhouse.Model.PaymentFailureModel;
import com.appsnipp.claraerp.saibookhouse.Model.ProfileUpdateModel;
import com.appsnipp.claraerp.saibookhouse.Model.PurchageOrderPackageInfoModel;
import com.appsnipp.claraerp.saibookhouse.Model.RegistrationModel;
import com.appsnipp.claraerp.saibookhouse.Model.SchoolListModel;
import com.appsnipp.claraerp.saibookhouse.Model.SchoolPackageList;
import com.appsnipp.claraerp.saibookhouse.Model.StateListModel;
import com.appsnipp.claraerp.saibookhouse.Model.UpdateCartModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface ApiHolder {
    @POST("Authentication/verify_customer")
    @FormUrlEncoded
    Call<LoginResponse>Login(@HeaderMap Map<String, String> fields1, @FieldMap Map<String, String> fields);

    @POST("Authentication/add_new_customer")
    @FormUrlEncoded
    Call<RegistrationModel>registration(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    @GET("Authentication/verify_information_and_send_otp")
    Call<OtpverificationModel>verifyOtp(@HeaderMap Map<String, String> headers, @QueryMap Map<String, String> params);

    @GET("General/get_states")
    Call<StateListModel>getStateList(@HeaderMap Map<String, String> headers);

    @GET("Customer/get_schools")
    Call<SchoolListModel>getSchoollist(@HeaderMap Map<String, String> headers, @QueryMap Map<String, String> params);


    @GET("Customer/get_school_packages")
    Call<SchoolPackageList>getschoolPackageList(@HeaderMap Map<String, String> headers, @QueryMap Map<String, String> params);

    @GET("Customer/get_package_data")
    Call<PackageInfoModel>getPackageInfo(@HeaderMap Map<String, String> headers, @QueryMap Map<String, String> params);


    @GET("Customer/get_profile")
    Call<MyProfileModel>getProfileData(@HeaderMap Map<String, String> headers, @QueryMap Map<String, String> params);


    @POST("Customer/update_customer_personal_info")
    @FormUrlEncoded
    Call<ProfileUpdateModel>profileUpdate(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    @POST("Customer/update_customer_address")
    @FormUrlEncoded
    Call<ProfileUpdateModel>addressUpdate(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    @POST("Customer/update_customer_crendentials")
    @FormUrlEncoded
    Call<ChangePasswordModel>passwordUpdate(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    @GET("Authentication/forget_password_get_otp")
    Call<ForgetOtpModel>getForgetOtpModel(@HeaderMap Map<String, String> headers, @QueryMap Map<String, String> params);

    @POST("Authentication/update_password")
    @FormUrlEncoded
    Call<PasswordUpdateModel>updatePasswordForget(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    @GET("Customer/my_orders")
    Call<CustomerOrderModel>getCustomerOrder(@HeaderMap Map<String, String> headers, @QueryMap Map<String, String> params);

    @GET("Customer/order_info")
    Call<OrderDetailsModel>getOrderDetails(@HeaderMap Map<String, String> headers, @QueryMap Map<String, String> params);

    @GET("Customer/get_purchased_order_package_info")
    Call<PurchageOrderPackageInfoModel>getPurchageOrderPackageInfo(@HeaderMap Map<String, String> headers, @QueryMap Map<String, String> params);

    @POST("Customer/add_query")
    @FormUrlEncoded
    Call<DropQueryModel>getDropQuery(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);


    @GET("Customer/buy_now")
    Call<BuyNowModel>getBuyNowDataList(@HeaderMap Map<String, String> headers, @QueryMap Map<String, String> params);

    @GET("Customer/cart_info")
    Call<CartItemsModel>getCartDataList(@HeaderMap Map<String, String> headers, @QueryMap Map<String, String> params);


    @GET("Customer/get_order_id_buy_now")
    Call<GenerateOrderIdBuyNowModel>generateOrderId(@HeaderMap Map<String, String> headers, @QueryMap Map<String, String> params);

    @GET("Customer/cart_count")
    Call<CartCountModel>getCartCount(@HeaderMap Map<String, String> headers, @QueryMap Map<String, String> params);

    @GET("Customer/genenerate_order_id_cart")
    Call<GenerateOrderIdBuyNowModel>generateOrderIdCart(@HeaderMap Map<String, String> headers, @QueryMap Map<String, String> params);

    @POST("Customer/payment_response")
    @FormUrlEncoded
    Call<PaymentFailureModel>paymentfailure(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    @POST("Customer/add_to_cart")
    @FormUrlEncoded
    Call<UpdateCartModel>addtoCart(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);



}
