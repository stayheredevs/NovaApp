package com.zeroone.novaapp.interceptors;

import android.text.TextUtils;

import androidx.annotation.NonNull;


import com.zeroone.novaapp.utils.AppLog;
import com.zeroone.novaapp.utils.AppUtils;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        AppLog.Log("interceptor","===============REQUEST INTERCEPTOR===============");


        /**
         * extracting request body payload from the request then process it and pass it back so as request to proceed
         */

        RequestBody requestBody = null;

        Request request = chain.request();

        AppLog.Log("requestBody_raw_toString", request.toString());

        if (request.body() != null){

            String contentType = "";
            if (TextUtils.isEmpty(contentType)) {
                contentType = "application/json; charset=utf-8";
            }

            AppLog.Log("requestBody_raw", AppUtils.Companion.bodyToStringConverter(request));

            //logic to escape special characters (\") and replace them with (") character on the request body
            String escapedRequest_raw = AppUtils.Companion.bodyToStringConverter(request).replace("\\\"", "\"");

            //escaping (") quotes from start and end of escapedRequestPayload string
            String escapedRequest_final = escapedRequest_raw.substring(1, escapedRequest_raw.length()-1);


            AppLog.Log("requestBody_final", escapedRequest_final);

            /**
             * Encrypt request payload here here
             */

            //creating request body json
            JSONObject jsonObject = null;
            try {
                //JSONObject dataObject = new JSONObject(escapedRequest_final);

                jsonObject = new JSONObject(escapedRequest_final);

                //jsonObject.put("data", jsonObject);

            } catch (Exception e){
                AppLog.Log("json_exception", e.getMessage());
            }

            //AppLog.Log("requestBody_json1", jsonObject.toString());

            //re-constructing request body
            requestBody = RequestBody.create(String.valueOf(jsonObject), MediaType.parse(contentType));

            AppLog.Log("requestBody_json2", requestBody.toString());

        } else {
            AppLog.Log("requestBody_json3", "empty request body");
        }



        //building new request
//        request = request
//                .newBuilder()
//                .method(request.method(), requestBody)
//                .build();

        if (!request.method().equalsIgnoreCase("GET")){
            request = request
                    .newBuilder()
                    .method(request.method(), requestBody)
                    .build();
        } else {
            request = request
                    .newBuilder()
                    .build();
        }



        return chain.proceed(request);
    }

}
