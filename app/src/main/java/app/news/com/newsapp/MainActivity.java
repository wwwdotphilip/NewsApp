package app.news.com.newsapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import app.news.com.newsapp.API.Article;
import app.news.com.newsapp.API.Category;
import app.news.com.newsapp.API.News;
import app.news.com.newsapp.API.Sources;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        final RequestQueue queue = Volley.newRequestQueue(this);
        News.getInstance().getSources(queue, null, null);
        News.getInstance().setCallback(new News.Callback() {
            @Override
            public void onSourceResponse(Sources[] sources) {
                Log.v(TAG, "Found " + sources.length + " sources");
//                News.getInstance().getArticle(queue, sources[0].id, null);
            }

            @Override
            public void onArticleResponse(Article[] articles) {
                Log.v(TAG, "Found " + articles.length + " articles");
//                article.loadUrl(articles[0].url);
            }

            @Override
            public void onError(String error) {
                Log.v(TAG, error);
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        for (String item : Category.getAllCategories()){
            CategoryFragment categoryFragment = CategoryFragment.NewInstance(item);
            adapter.addFragment(categoryFragment, Category.toTitle(item));
        }
        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
