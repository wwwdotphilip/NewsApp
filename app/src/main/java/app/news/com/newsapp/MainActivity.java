package app.news.com.newsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import app.news.com.newsapp.API.Article;
import app.news.com.newsapp.API.News;
import app.news.com.newsapp.API.Sources;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private WebView article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        article = (WebView) findViewById(R.id.wbArticle);
        article.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                Log.i(TAG, "Finished loading URL: " +url);
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e(TAG, "Error: " + description);
            }
        });

        final RequestQueue queue = Volley.newRequestQueue(this);
        News.getInstance().getSources(queue, null, null);
        News.getInstance().setCallback(new News.Callback() {
            @Override
            public void onSourceResponse(Sources[] sources) {
                Log.v(TAG, "Found "+sources.length+ " sources");
                News.getInstance().getArticle(queue, sources[0].id, null);
            }

            @Override
            public void onArticleResponse(Article[] articles) {
                Log.v(TAG, "Found "+articles.length + " articles");
                article.loadUrl(articles[0].url);
            }

            @Override
            public void onError(String error) {
                Log.v(TAG, error);
            }
        });
    }
}
