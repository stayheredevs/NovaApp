package com.zeroone.novaapp.interceptors;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zeroone.novaapp.utils.AppLog;
import com.zeroone.novaapp.utils.SharedPreference;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ResponseInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        AppLog.Log("interceptor","===============RESPONSE REQUEST===============");

        Response response = chain.proceed(chain.request());

        if (response.isSuccessful()) {

            Response.Builder builder = response.newBuilder();

            String contentType = response.header("Content-Type");

            if (TextUtils.isEmpty(contentType)) {
                contentType = "application/json";
            }

            try {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();

                //Accessing request body only once and storing the value on a variable
                String responseBody = response.body().string();

                AppLog.Log("responseBody_raw", responseBody);

                /**
                 * decrypt the response at this point
                 */
                /*String server_response = AppUtils.decrypt(interceptorResponseModel.getAPIResponse());
                AppLog.Log("response_decryption", server_response);*/


                //json object that will contain decrypted response
                JSONObject jsonObject = new JSONObject();

                //converting decrypted string to json object
                jsonObject.put("data", SharedPreference.Companion.removeSpecialCharacters(responseBody));
                builder.body(ResponseBody.create(jsonObject.toString(), MediaType.parse(contentType)));


                return builder.build();

            } catch (Exception e){
                AppLog.Log("json_exception", e.getMessage());
            }

            return builder.build();

        } else {
            AppLog.Log("response_error", "error::: " + response.code());
        }

        return response;
    }
}
