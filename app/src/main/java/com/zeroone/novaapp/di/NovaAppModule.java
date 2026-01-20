package com.zeroone.novaapp.di;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zeroone.novaapp.BuildConfig;
import com.zeroone.novaapp.appApi.ApiHelper;
import com.zeroone.novaapp.appApi.ApiHelperImplementation;
import com.zeroone.novaapp.appApi.ApiService;
import com.zeroone.novaapp.interceptors.RequestInterceptor;
import com.zeroone.novaapp.interceptors.ResponseInterceptor;
import com.zeroone.novaapp.utils.Constants;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class NovaAppModule {


    /**
     * providing OKHttpClient dependency
     * @return OKHttpClient object
     */
    @Provides
    public static OkHttpClient providesOkHttpClient(){
        return new OkHttpClient
                .Builder()
                .addInterceptor(new RequestInterceptor())
                .addInterceptor(new ResponseInterceptor())
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .readTimeout(Constants.NETWORK_CALL_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    /**
     *
     * @return GsonBuilder object
     */
    @Provides
    public static Gson providesGson(){
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    /**
     *
     * @param gson
     * @return GsonConverterFactory object
     */
    @Provides
    public static GsonConverterFactory providesGsonConverterFactory(Gson gson){
        return GsonConverterFactory.create(gson);
    }

    /**
     *
     * @param okHttpClient
     * @param converterFactory
     * @return
     */
    @Provides
    public static Retrofit providesRetrofit(OkHttpClient okHttpClient, GsonConverterFactory converterFactory){
        return new Retrofit
                .Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(converterFactory)
                .client(okHttpClient)
                .build();
    }


    /**
     *
     * @param retrofit
     * @return
     *
     * Providing ApiService dependency
     */
    @Provides
    public static ApiService providesApiService(Retrofit retrofit){
        return retrofit.create(ApiService.class);
    }

    /**
     *
     * @param apiHelperImplementation
     * @return
     *
     * Provides ApiHelperImplementation dependency
     */
    @Provides
    public static ApiHelper providesApiHelper(ApiHelperImplementation apiHelperImplementation) {
        return apiHelperImplementation;
    }



}
