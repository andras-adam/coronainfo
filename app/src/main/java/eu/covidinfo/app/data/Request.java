package eu.covidinfo.app.data;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Request {

    // Request url
    private final String url;

    // Initiate Gson, OkHttp and executor, and set access token
    private static final Gson gson = new Gson();
    private static final ExecutorService executor = Executors.newCachedThreadPool();
    private static final OkHttpClient client = new OkHttpClient.Builder().callTimeout(10, TimeUnit.SECONDS).build();

    // Request constructor
    public Request(String url) {
        this.url = url;
    }

    // Parse the json response into the provided type
    public JsonElement get() {
        try {
            String response = this.getFutureResponse().get();
            return Request.gson.fromJson(response, JsonElement.class);
        } catch (ExecutionException | InterruptedException | JsonSyntaxException e) {
            Log.e("REQUEST", e.toString());
            return null;
        }
    }

    // Run http request and return response
    private CompletableFuture<String> getFutureResponse() {
        return CompletableFuture.supplyAsync(() -> {
            okhttp3.Request request = new okhttp3.Request.Builder().url(this.url).build();
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
