package com.android.nsystem.retrofitexperiment.api;

import com.android.nsystem.retrofitexperiment.entity.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolderPostApi {

    @GET("posts")
    Call<List<Post>> getPosts();

    @GET("posts/{id}")
    Call<Post> getPost(@Path("id") int postId);

    @GET("posts")
    Call<List<Post>> getPostFromQuery(@Query("id") int postId);

    @POST("posts")
    Call<Post> createPost(@Body Post post);

    @PUT("posts/{id}")
    Call<Post> putPost(@Path("id") int id, @Body Post post);

    @Headers({"Static-Header1:123", "Static-Header2:456"})
    @PATCH("posts/{id}")
    Call<Post> patchPost(@Header("dynamic-header:{value}") String headerValue, @Path("id") int id, @Body Post post);

    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id") int id);
}
