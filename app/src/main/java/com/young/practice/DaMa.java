package com.young.practice;

import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DaMa {

    private static String TAG = "DaMa";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static OkHttpClient client = new OkHttpClient.Builder().cookieJar(new CookieJar() {
        private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            Log.e(TAG, "url.host: " + url.host());
            cookieStore.put(url.host(), cookies);
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url.host());
            return cookies != null ? cookies : new ArrayList<Cookie>();
        }
    }).build();

    public static void login() {

        Request request = new Request.Builder().get()
                .url("http://api.xunyaosoft.com/zc/zhicode/api.php?code=signIn&uPhoneNo=18086901750&uPassword=winner1204..")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "login error: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e(TAG, "login response: " + response.body().string());
            }
        });
    }

    public static void getPhoneNum() {
        Request request = new Request.Builder().get()
                .url("http://api.xunyaosoft.com/zc/zhicode/api.php?code=getPhoneNo&projName=快手")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "getPhoneNum error: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e(TAG, "getPhoneNum response: " + response.body().string());
            }
        });
    }


    public static void postJSON(String url, JSONObject obj) {
        RequestBody body = RequestBody.create(JSON, obj.toString());
        Request request = new Request.Builder().url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }


}
