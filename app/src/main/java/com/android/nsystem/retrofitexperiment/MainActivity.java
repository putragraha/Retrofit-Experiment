package com.android.nsystem.retrofitexperiment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.nsystem.retrofitexperiment.api.JsonPlaceHolderPostApi;
import com.android.nsystem.retrofitexperiment.entity.Post;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView mResultTextView;
    private JsonPlaceHolderPostApi mJsonPlaceHolderPostApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mResultTextView = findViewById(R.id.text_view_result);

        /*
        This HttpLoggingInterceptor remain as a logging helper for each http request
        Every log can be found at logcat
         */
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Request newRequest = originalRequest.newBuilder()
                                .header("Interceptor-Header","xyz")
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .build();

        /*
         By default, GSON remove null value from json object,
         by init serialize null, and send it as parameter for GsonConverterFactory
         it will send null also in json object
         */
        Gson gson = new GsonBuilder().serializeNulls().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        mJsonPlaceHolderPostApi = retrofit.create(JsonPlaceHolderPostApi.class);

//        getAllPosts();
//        getAllPosts(1);
//        getPost(1);

//        createPost();
        updatePost();
//        deletePost();
    }

    private void deletePost() {
        int id = 5;
        Call<Void> call = mJsonPlaceHolderPostApi.deletePost(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                mResultTextView.setText("Code : " + response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                mResultTextView.setText(t.getMessage());
            }
        });
    }

    private void updatePost() {
        int postId = 5;
        Post post = new Post(5, null, "New Text");
        Call<Post> postCall = mJsonPlaceHolderPostApi.putPost(postId, post);
//        Call<Post> postCall = mJsonPlaceHolderPostApi.patchPost("abc", postId, post);

        postCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    Post post = response.body();
                    String result = "";
                    result += "Code : " + response.code() + "\n";
                    result += "User ID : " + post.getUserId() + "\n";
                    result += "ID : " + post.getId() + "\n";
                    result += "Title : " + post.getTitle() + "\n";
                    result += "Text : " + post.getText() + "\n\n";

                    mResultTextView.setText(result);
                } else {
                    mResultTextView.setText("Error happen with code : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                mResultTextView.setText(t.getMessage());
            }
        });
    }

    private void createPost() {
        Post post = new Post(5, "New", "New Text");
        Call<Post> postCall = mJsonPlaceHolderPostApi.createPost(post);

        postCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    Post post = response.body();
                    String result = "";
                    result += "User ID : " + post.getUserId() + "\n";
                    result += "ID : " + post.getId() + "\n";
                    result += "Title : " + post.getTitle() + "\n";
                    result += "Text : " + post.getText() + "\n\n";

                    mResultTextView.setText(result);
                } else {
                    mResultTextView.setText("Error happen with code : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                mResultTextView.setText(t.getMessage());
            }
        });
    }

    private void getAllPosts(int id) {
//        Call<List<Post>> listCall = mJsonPlaceHolderPostApi.getPosts();
        Call<List<Post>> listCall = mJsonPlaceHolderPostApi.getPostFromQuery(id);

        listCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    List<Post> postList = response.body();

                    for (Post post : postList) {
                        String result = "";
                        result += "User ID : " + post.getUserId() + "\n";
                        result += "ID : " + post.getId() + "\n";
                        result += "Title : " + post.getTitle() + "\n";
                        result += "Text : " + post.getText() + "\n\n";

                        mResultTextView.append(result);
                    }
                } else {
                    mResultTextView.setText("Error happen with code : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                mResultTextView.setText(t.getMessage());
            }
        });
    }

    private void getPost(int id) {
        Call<Post> postCall = mJsonPlaceHolderPostApi.getPost(id);
        postCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    Post post = response.body();
                    String result = "";
                    result += "User ID : " + post.getUserId() + "\n";
                    result += "ID : " + post.getId() + "\n";
                    result += "Title : " + post.getTitle() + "\n";
                    result += "Text : " + post.getText() + "\n\n";

                    mResultTextView.setText(result);
                } else {
                    mResultTextView.setText("Error happen with code : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                mResultTextView.setText(t.getMessage());
            }
        });
    }
}
