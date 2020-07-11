package com.abdallah.weathery.network;

import androidx.databinding.library.baseAdapters.BuildConfig;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClass {

    private static NetworkInterface networkInstance;



    public static NetworkInterface getNetworkInstance() {

        if (networkInstance == null) {


            LoggingInterceptor loggingInterceptor = new LoggingInterceptor.Builder()
                    .loggable(BuildConfig.DEBUG)
                    .setLevel(Level.BASIC)
                    .log(Platform.INFO)
                    .request("Request")
                    .response("Response")
                    .addHeader("version", BuildConfig.VERSION_NAME)
                    .build();

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(1000, TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor)
//                    .addInterceptor(new NetworkConnectionInterceptor(context))

                    .readTimeout(1000, TimeUnit.SECONDS)
                    .writeTimeout(1000, TimeUnit.SECONDS).build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Urls.BASE_URL)
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            networkInstance = retrofit.create(NetworkInterface.class);
        }

        return networkInstance;
    }





    public static void clearRetrofit() {
        networkInstance = null;
    }
}
