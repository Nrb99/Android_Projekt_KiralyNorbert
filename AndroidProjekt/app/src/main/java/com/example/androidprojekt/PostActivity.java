package com.example.androidprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostActivity extends AppCompatActivity {
    EditText edituID;
    EditText edtTitle;
    EditText editText;
    EditText edtId;
    Button btnSave;
    Button btnDel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        edituID=findViewById(R.id.userId);
        edtTitle=findViewById(R.id.title2);
        editText=findViewById(R.id.body2);
        btnDel=findViewById(R.id.button2);
        btnSave=findViewById(R.id.button);
        edtId=findViewById(R.id.id1);
        ApiInterface apiService=RetrofitClient.getClient().create(ApiInterface.class);
        Bundle extras=getIntent().getExtras();
        final String id=extras.getString("Id");
        String userId=extras.getString("userId");
        String title=extras.getString("title");
        String body=extras.getString("body");

        edituID.setText(userId);
        edtTitle.setText(title);
        editText.setText(body);

        if(id!=null&& id.trim().length()>0){
            edtId.setFocusable(false);
        }else{
            edtId.setVisibility(View.INVISIBLE);
            btnDel.setVisibility(View.INVISIBLE);
        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Post p=new Post();
                p.setTitle(edtTitle.getText().toString());
                p.setUserId(Integer.parseInt(edituID.getText().toString()));
                p.setText(editText.getText().toString());

                if(id!=null&& id.trim().length()>0){
                    updatePost(Integer.parseInt(id),p);
                }else{
                    addPost(p);

                }
            }
        });
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePost(Integer.parseInt(id));
            }
        });


    }

    public void addPost(Post p){
        ApiInterface apiService=RetrofitClient.getClient().create(ApiInterface.class);
        Call<Post> call=apiService.addPost(p);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()){
                    Toast.makeText(PostActivity.this,"Post created successfuly",Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });
    }
    public void updatePost(int id,Post p){
        ApiInterface apiService=RetrofitClient.getClient().create(ApiInterface.class);
        Call<Post> call=apiService.updatePost(id,p);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()){
                    Toast.makeText(PostActivity.this, "User updated successfully!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });
    }
    public void deletePost(int id){
        ApiInterface apiService=RetrofitClient.getClient().create(ApiInterface.class);
        Call<Post> call=apiService.deletePost(id);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()){
                    Toast.makeText(PostActivity.this,"Post deleted successfully",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return  super.onOptionsItemSelected(item);
    }
}