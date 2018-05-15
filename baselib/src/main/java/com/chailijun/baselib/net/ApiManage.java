package com.chailijun.baselib.net;


import android.text.TextUtils;

import com.chailijun.baselib.utils.C;
import com.chailijun.baselib.utils.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;



public class ApiManage {

    public static ApiManage apiManage;

    private ApiManage() {
    }

    public static ApiManage getInstence() {
        if (apiManage == null) {
            synchronized (ApiManage.class) {
                if (apiManage == null) {
                    apiManage = new ApiManage();
                    initOkHttpClient();
                }
            }
        }
        return apiManage;
    }

    private static final Interceptor mRewriteCacheControlInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {

            Response originalResponse = chain.proceed(chain.request());
            if (NetworkUtils.isNetworkAvailable(C.get())) {
                int maxAge = 60; // 在线缓存在1分钟内可读取
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .removeHeader("User-Agent")
                        .header("Cache-Control", "public, max-age=" + maxAge)
//                        .addHeader("platform","android")
//                        .addHeader("username", "15921482010")
                        .build();

            } else {
                int maxStale = 60 * 60 * 24 * 28; // 离线时缓存保存4周
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                        .addHeader("platform", "android")
//                        .addHeader("username", "15921482010")
                        .build();
            }
        }
    };

    private static File httpCacheDirectory = new File(C.get().getCacheDir(), "goodfather_Cache");
    private static Cache cache = new Cache(httpCacheDirectory, 1024 * 1024 * 10);//10M
    private static final long DEFAULT_TIMEOUT = 5000;
    private Object monitor = new Object();
    private ApiService apiService;
    private static OkHttpClient mOkHttpClient;


    private static void initOkHttpClient() {

        if (mOkHttpClient == null) {

            OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder()
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                    .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                    .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                    .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                    .addInterceptor(/*mRewriteCacheControlInterceptor*/new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {

                            Request.Builder builder = chain.request()
                                    .newBuilder();
                            Request request = builder
//                                    .addHeader("username", user == null ? "" : user.username)
                                    .addHeader("platform", "android")
                                    .build();
                            return chain.proceed(request);
                        }
                    })
                    .retryOnConnectionFailure(true)
                    .cache(ApiManage.cache);

            /*if (L.DEBUG) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(
                        new HttpLoggingInterceptor.Logger() {
                            @Override
                            public void log(String message) {
                                if (TextUtils.isEmpty(message)) return;
                                String s = message.substring(0, 1);
                                //如果收到响应是json才打印
                                if ("{".equals(s) || "[".equals(s)) {
                                    L.d("请求数据: " + message);
                                }
                            }
                        });
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                httpBuilder.addInterceptor(interceptor);
            }*/
            mOkHttpClient = httpBuilder.build();
        }
    }

    public ApiService getApiService() {
        if (apiService == null) {
            synchronized (monitor) {
                if (apiService == null) {
                    apiService = new Retrofit.Builder()
                            .baseUrl(ApiConstant.API_BASE_URL)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(mOkHttpClient)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build().create(ApiService.class);
                }
            }
        }
        return apiService;
    }

}
