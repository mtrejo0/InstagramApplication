package com.example.myapplication.models;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

import org.parceler.Parcels;

public class DetailsActivity extends AppCompatActivity {

    public ImageView ivImage;
    public TextView tvDescription;
    public TextView tvUser;
    public TextView tvUserTop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        ivImage = findViewById(R.id.ivImage);
        tvDescription = findViewById(R.id.tvDescription);
        tvUser = findViewById(R.id.tvUser);
        tvUserTop =  findViewById(R.id.tvUserTop);


        final Post post = Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));




        tvDescription.setText(post.getDescription());

        tvUser.setText(post.getUser().getUsername());

        tvUserTop.setText(post.getUser().getUsername());


        String imageUrl = post.getImage().getUrl();
        Glide.with(this)
                .load(imageUrl)
                .into(ivImage);




    }
}
