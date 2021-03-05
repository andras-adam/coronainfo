package eu.covidinfo.app.data;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Request {

    // Request url
    private final String url;

    // Initiate Gson, OkHttp and executor, and set access token
    private static final Gson gson = new Gson();
    private static final OkHttpClient client = new OkHttpClient();
    private static final ExecutorService executor = Executors.newCachedThreadPool();
    private static final String XAccessToken = "5cf9dfd5-3449-485e-b5ae-70a60e997864";

    // Request constructor
    public Request(String url) {
        this.url = url;
    }

    // Parse the json response into the provided type
    public <T> T getAs(TypeToken<T> type) {
        try {
            String response = this.getFutureResponse().get();
            return Request.gson.fromJson(response, type.getType());
        } catch (ExecutionException | InterruptedException | JsonSyntaxException e) {
            Log.e("REQUEST", e.toString());
            return null;
        }
    }

    // Run http request and return response
    private CompletableFuture<String> getFutureResponse() {
        return CompletableFuture.supplyAsync(() -> {
            okhttp3.Request request = new okhttp3.Request.Builder().header("X-Access-Token", Request.XAccessToken).url(this.url).build();
            try {
                Response response = Request.client.newCall(request).execute();
                ResponseBody body = response.body();
                return (body != null) ? body.string() : null;
            } catch (Exception e) {
                Log.e("REQUEST", e.toString());
                return null;
            }
        }, Request.executor);
    }

}
