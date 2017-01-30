package app.news.com.newsapp.API;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

public class News {
    private static volatile News instance;
    private Callback callback;
    private String mainUrl = "https://newsapi.org/v1/";
    private String sourceUrl = mainUrl + "sources";
    private String articleUrl = mainUrl + "articles";
    private String TAG = "NewsAPI";
    private static final String apiKey = "11692568f8ee423792237852e3c2e3de";

    public String status;
    public String source;
    public String sortBy;
    public String message;

    private Sources[] sources;
    private Article[] articles;

    public static News getInstance() {
        if (instance == null) {
            synchronized (News.class) {
                if (instance == null) {
                    instance = new News();
                }
            }
        }
        return instance;
    }

    public News(){

    }

    public interface Callback{
        void onSourceResponse(Sources[] sources);
        void onArticleResponse(Article[] articles);
        void onError(String error);
    }

    public void setCallback(Callback callback){
        this.callback = callback;
    }

    public void getSources(RequestQueue queue, String category, String sortBy){
        String url = sourceUrl;
        if (category != null){
            url = url + "?category="+category;
        }
        if (sortBy != null){
            url = url + "&sortBy="+sortBy;
        }
        Log.v(TAG, url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                News news  = new Gson().fromJson(response, News.class);
                if (callback != null){
                    callback.onSourceResponse(news.sources);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (callback != null){
                    callback.onError(error.toString());
                }
            }
        });
        queue.add(stringRequest);
    }

    public void getArticle(RequestQueue queue, String source, String sortBy){
        String url = articleUrl;
        if (source != null){
            url = url + "?source="+source;
        }
        if (sortBy != null){
            url = url + "&sortBy="+sortBy;
        }
        url = url + "&apiKey="+ apiKey;
        Log.v(TAG, url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                News news  = new Gson().fromJson(response, News.class);
                if (callback != null){
                    callback.onArticleResponse(news.articles);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (callback != null){
                    callback.onError(error.toString());
                }
            }
        });
        queue.add(stringRequest);
    }
}
