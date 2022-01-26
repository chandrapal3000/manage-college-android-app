package com.chandrapal.manage_college;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chandrapal.manage_college.ui.notes_user_profile.NotesFragmentUserProfile;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class UserProfileActivity extends AppCompatActivity {

    TextView username, fullname, userType, email;
    ShapeableImageView cover_profile_image;
    String profileImageString;
    ScrollView myScrollView;
    ProgressBar progressBar;
    String WEB_URL_GET_USERS = "https://manage-college.000webhostapp.com/get_users.php";
    String dir = "https://manage-college.000webhostapp.com/upload_image/images/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        String user_id = getIntent().getStringExtra("user_id");

        BottomNavigationView navView = findViewById(R.id.BottomNavigationView_activity_user_profile);
        fullname = findViewById(R.id.fullname_activity_user_profile);
        userType = findViewById(R.id.userType_activity_user_profile);
        email = findViewById(R.id.email_activity_user_profile);
        cover_profile_image = findViewById(R.id.CoverImage_profile);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapseingToolbar_user_profile);
        collapsingToolbarLayout.setTitle(getIntent().getStringExtra("uploaded_by"));

        profileImageString = getIntent().getStringExtra("user_profile_image");

        Picasso.get().load(profileImageString).into(cover_profile_image);

            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_user_profile);
            NavigationUI.setupWithNavController(navView, navController);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, WEB_URL_GET_USERS, response -> {
            if(response.equals("Doesn't look like anything to me!") || response.equals("empty table/orderby/ascdesc!") || response.equals("oversmart mat ban, ja pucha h wo fill kar") ){
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
            }else {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        fullname.setText(jsonObject.getString("fullname"));
                        userType.setText(jsonObject.getString("u_type"));
                        email.setText(jsonObject.getString("email"));
                        profileImageString = dir+jsonObject.getString("profile_image");
                        Picasso.get().load(profileImageString).into(cover_profile_image);
                    }

                    Log.d(TAG, "onCreate: image"+profileImageString);
                    NotesFragmentUserProfile.newInstance(profileImageString);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
//            progressBar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),error.toString().trim(), Toast.LENGTH_LONG).show();
        }){
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("column", "id");
                data.put("value", user_id);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

//        final int abTitleId = getResources().getIdentifier("action_bar_title", "id", "android");
//        findViewById(abTitleId).setOnClickListener(v->{
//            myScrollView.fullScroll(ScrollView.FOCUS_UP);
//        });

    }

}