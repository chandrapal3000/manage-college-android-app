package com.chandrapal.manage_college;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import static android.content.ContentValues.TAG;

public class FullPhotosActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String dir = "https://manage-college.000webhostapp.com/upload_image/images/";
    String user_id, usernameString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_photos);

//        Objects.requireNonNull(getSupportActionBar()).hide();

        TextView username = findViewById(R.id.username_full_photos);
        TextView uploaded_time = findViewById(R.id.uploadedTime_full_photos);
        ImageView image = findViewById(R.id.image_full_photos);
        TextView caption = findViewById(R.id.caption_full_photos);
        ShapeableImageView profile_image = findViewById(R.id.profileImage_full_photos);

        String photoId = getIntent().getStringExtra("photos_id");
        usernameString = getIntent().getStringExtra("photos_uploaded_by");
        user_id = getIntent().getStringExtra("photos_user_id");
        String uploadedTimeString = getIntent().getStringExtra("photos_uploaded_time");
        String imageString = getIntent().getStringExtra("photos_image");
        String captionString = getIntent().getStringExtra("photos_caption").replace("-", " ");
        String profileImageString = getIntent().getStringExtra("photos_user_profile_image");

        Log.d(TAG, "onCreate: "+profileImageString);
        Log.d(TAG, "onCreate: "+imageString);

        username.setText(usernameString);
        uploaded_time.setText(uploadedTimeString);
        caption.setText(captionString);
        Picasso.get().load(imageString).into(image);
        Picasso.get().load(profileImageString).into(profile_image);

        username.setOnClickListener(this);
        profile_image.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
        intent.putExtra("user_id", user_id);
        intent.putExtra("uploaded_by", usernameString);
        startActivity(intent);
//        overridePendingTransition(R.anim.nav_default_pop_enter_anim,R.anim.nav_default_pop_exit_anim);
    }
}