package com.example.androidprojekt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public List<Post> postList=new ArrayList<Post>();
    Button add;
    Button listPost;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        add=findViewById(R.id.addPost);
        listPost=findViewById(R.id.getPosts);
        lv=findViewById(R.id.listview);
        listPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getPostsList();
            }
        });
    add.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent =new Intent(MainActivity.this,PostActivity.class);
            intent.putExtra("userId", "");
            intent.putExtra("Id", "");
            intent.putExtra("title", "");
            intent.putExtra("body", "");
            startActivity(intent);
        }
    });

    }

    public void getPostsList(){
        ApiInterface apiService=RetrofitClient.getClient().create(ApiInterface.class);
        Call<List<Post>> call=apiService.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(response.isSuccessful()){
                    postList=response.body();
                    lv.setAdapter(new PostAdapter(MainActivity.this,R.layout.list_post,postList));
                }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.d("TAG","Response = "+t.toString());
            }
        });
    }
}