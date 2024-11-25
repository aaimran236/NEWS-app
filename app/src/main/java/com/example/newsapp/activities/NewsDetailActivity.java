package com.example.newsapp.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;
import com.example.newsapp.model.HomePageModel;
import com.example.newsapp.rest.ApiClient;
import com.example.newsapp.rest.ApiInterface;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsDetailActivity extends AppCompatActivity {
    private Toolbar toolbar;

    private TextView sourceName,newsTitle,newsDesc,newsDate,newsView,labelSimilar;

    private Button viewMore;

    private ProgressBar progressBar;

    private ImageView image,small_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_news_detail);


//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

     inItViews();

     loadNewsDetails();
    }

    private void loadNewsDetails() {
        ApiInterface apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        Map<String,String> params=new HashMap<>();


        Call<HomePageModel> call=apiInterface.getHomePageApi(params);

        call.enqueue(new Callback<HomePageModel>() {
            @Override
            public void onResponse(Call<HomePageModel> call, Response<HomePageModel> response) {

                ///Updating the news layout
                HomePageModel.News detailNews=response.body().getNews().get(0);
                newsTitle.setText(detailNews.getTitle());
                newsDesc.setText(detailNews.getPostContent());

                if (detailNews.getImage().length()>1){
                    Glide.with(NewsDetailActivity.this)
                            .load(detailNews.getImage())
                            .into(image);
                }else {
                    image.setVisibility(View.GONE);
                }

                sourceName.setText(detailNews.getSource());
                if (detailNews.getSourceLogo()!=null){
                    Glide.with(NewsDetailActivity.this)
                            .load(detailNews.getSourceLogo())
                            .into(small_icon);
                }

                viewMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String newsUrl="";
                        if (detailNews.getSourceUrl()!=null){
                            newsUrl=detailNews.getUrl();
                        }else {
                            newsUrl=detailNews.getSourceUrl();
                        }
                    }
                });


            }

            @Override
            public void onFailure(Call<HomePageModel> call, Throwable throwable) {
                progressBar.setVisibility(View.GONE);

            }
        });
    }

    private void inItViews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle("");
        toolbar.setNavigationIcon(R.drawable.icon_arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        sourceName=findViewById(R.id.source_name);
        newsTitle=findViewById(R.id.news_title);
        newsDesc=findViewById(R.id.news_desc);
        newsDate=findViewById(R.id.news_date);
        newsView=findViewById(R.id.news_view);
        labelSimilar=findViewById(R.id.label_similar_news);

        viewMore=findViewById(R.id.view_more);

        progressBar=findViewById(R.id.progressBarNews);

        image=findViewById(R.id.news_image);

        small_icon=findViewById(R.id.small_logo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.share){
            Toast.makeText(this, "News share icon is clicked", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}