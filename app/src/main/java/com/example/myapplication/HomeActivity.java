package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class HomeActivity extends AppCompatActivity {



    private EditText etDescription;
    private Button btnRefresh;
    private Button btnCreate;
    private static final String imagePath = "res/raw/moises.png";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        etDescription = findViewById(R.id.etDescription);
        btnCreate = findViewById(R.id.btnCreate);
        btnRefresh = findViewById(R.id.btnRefresh);


        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 loadTopPosts();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String description = etDescription.getText().toString();
                final ParseUser user = ParseUser.getCurrentUser();

                InputStream ins = getResources().openRawResource(R.raw.moises);
                try {
                    byte[] content = new byte[ins.available()];
                    final ParseFile parseFile = new ParseFile(content);
                    createPost(description, parseFile,user);
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    private void createPost(String description, ParseFile imageFile, ParseUser user)
    {
        final Post newPost = new Post();
        newPost.setDescription(description);
        newPost.setImage(imageFile);
        newPost.setUser(user);
        Log.d("HomeActivity","Create post attempt");

        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null)
                {
                    Log.d("HomeActivity","Post successful!");

                }
                else
                {
                    Log.d("HomeActivity","Post failure!");
                    e.printStackTrace();
                }
            }
        });

    }

    private void loadTopPosts()
    {
        final Post.Query postQuery = new Post.Query();
        postQuery.getTop().withUser();

        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if(e==null)
                {
                    for(int i = 0; i < objects.size();i++)
                    {

                        Log.d("HomeActivity","Post: "+i +" "+ objects.get(i).getDescription()+" "+objects.get(i).getUser().getUsername());

                    }
                }
                else
                {
                    e.printStackTrace();
                }
            }
        });
    }

}
