package com.example.androidprojekt;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import java.util.List;

public class PostAdapter extends ArrayAdapter<Post> {
    private Context context;
    private List<Post> posts;

    public PostAdapter(@NonNull Context context, @LayoutRes int resource,@NonNull List<Post> objects){
        super(context,resource,objects);
        this.context=context;
        this.posts=objects;
    }
    @Override
    public View getView(final int pos, View convertView, ViewGroup parent){
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView=inflater.inflate(R.layout.list_post,parent,false);

        TextView userid=rowView.findViewById(R.id.userid);
        TextView id=rowView.findViewById(R.id.id);
        TextView title=rowView.findViewById(R.id.title1);
        TextView body=rowView.findViewById(R.id.body1);

        userid.setText(String.format("USER_ID: %d ",posts.get(pos).getUserId()));
        id.setText(String.format("id: %d",posts.get(pos).getId()));
        title.setText(String.format("title: %s",posts.get(pos).getTitle()));
        body.setText(String.format("body: %s",posts.get(pos).getText()));
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context,PostActivity.class);
                i.putExtra("userId",String.valueOf(posts.get(pos).getUserId()));
                i.putExtra("Id",String.valueOf(posts.get(pos).getId()));
                i.putExtra("title",String.valueOf(posts.get(pos).getTitle()));
                i.putExtra("body",String.valueOf(posts.get(pos).getText()));
                context.startActivity(i);
            }
        });
        return rowView;
    }
}
