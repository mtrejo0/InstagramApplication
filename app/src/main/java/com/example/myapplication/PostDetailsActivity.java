package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.models.Post;

import org.parceler.Parcels;

public class PostDetailsActivity extends AppCompatActivity {

    public ImageView ivImage;
    public TextView tvDescription;
    public TextView tvUser;
    public TextView tvUserTop;
    public TextView tvDate;
    public ImageView ivProfileImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        ivImage = findViewById(R.id.ivImage);
        tvDescription = findViewById(R.id.tvDescription);
        tvUser = findViewById(R.id.tvUser);
        tvUserTop =  findViewById(R.id.tvUserTop);
        tvDate = findViewById(R.id.tvDate);


        // gets post that was passed in to the activity to display
        final Post post = Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));

        tvDescription.setText(post.getDescription());
        tvUser.setText(post.getUser().getUsername());
        tvUserTop.setText(post.getUser().getUsername());
        tvDate.setText(post.getCreatedAt().toString().substring(0,10));

        // gets url for image post and displays it
        String imageUrl = post.getImage().getUrl();
        Glide.with(this)
                .load(imageUrl)
                .into(ivImage);

        ivProfileImage = findViewById(R.id.ivProfileImage);

        Glide.with(this)
                .load(post.getUser().getParseFile("profileImage").getUrl())
                .into(ivProfileImage);




    }
}
