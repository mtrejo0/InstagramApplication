package com.example.myapplication.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

import org.parceler.Parcels;

public class DetailsActivity extends AppCompatActivity {

    public ImageView ivImage;
    public TextView tvDescription;
    public TextView tvUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        ivImage = findViewById(R.id.ivImage);
        tvDescription = findViewById(R.id.tvDescription);
        tvUser = findViewById(R.id.tvUser);


        final Post post = Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));


        tvDescription.setText(post.getDescription());

        tvUser.setText(post.getUser().getUsername());

        ParseFile parseFile = post.getImage();


        parseFile.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] data, ParseException e) {
                if( e == null) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                    ivImage.setImageBitmap(bmp);


                }
                else
                {
                    e.printStackTrace();
                }
            }
        });



    }
}
