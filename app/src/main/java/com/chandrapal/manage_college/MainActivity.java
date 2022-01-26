package com.chandrapal.manage_college;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.chandrapal.manage_college.databinding.ActivityMainBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Objects;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("loginInfo", MODE_PRIVATE);
        try {
            if (sharedPreferences.contains("username") ) {

//                StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://manage-college.000webhostapp.com/login.php", response -> {
//                    if (response.equals("success")) {
//                        Toast.makeText(getApplicationContext(), "You are logged in 1", Toast.LENGTH_LONG).show();
//                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                        builder.setTitle("Login Status");
//                        builder.setCancelable(true);
//                        builder.setMessage("You are logged in 1 , enjoy!");
//                        AlertDialog alertDialog = builder.create();
//                        alertDialog.show();
//                    } else if (response.equals("failed")) {
//                        Toast.makeText(getApplicationContext(), "You are not logged in 1", Toast.LENGTH_LONG).show();
//                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                        builder.setTitle("Login Status");
//                        builder.setCancelable(false);
//                        builder.setMessage("You are not logged in yet, Login now?");
//                        builder.setPositiveButton("Yes", (dialog, which) -> {
//                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//                        });
//                        builder.setNegativeButton("Not now", (dialog, which) -> dialog.cancel());
//                        AlertDialog alertDialog = builder.create();
//                        alertDialog.show();
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Something went wrong, couldn't log in, please try again...", Toast.LENGTH_SHORT).show();
//                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                        builder.setTitle("Login Status");
//                        builder.setCancelable(false);
//                        builder.setMessage("Something went wrong, couldn't log in, please try again...Response Error(diff)");
//                        builder.setNegativeButton("Ok", (dialog, which) -> dialog.cancel());
//                        AlertDialog alertDialog = builder.create();
//                        alertDialog.show();
//                    }
//                }, error -> {
//                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                    builder.setTitle("Login Status");
//                    builder.setCancelable(false);
//                    builder.setMessage("Something went wrong, couldn't log in, please try again...Volley Error");
//                    builder.setNegativeButton("Ok", (dialog, which) -> dialog.cancel());
//                    AlertDialog alertDialog = builder.create();
//                    alertDialog.show();
//                }){
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> data = new HashMap<>();
//                        data.put("username", sharedPreferences.getString("username",""));
//                        data.put("password", sharedPreferences.getString("username",""));
//                        return data;
//                    }
//                };
//                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//                requestQueue.add(stringRequest);

                Toast.makeText(getApplicationContext(), "You are logged in as "+sharedPreferences.getString("username",""), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "You are not logged in, Login now?", Toast.LENGTH_LONG).show();

                new MaterialAlertDialogBuilder(MainActivity.this)
                        .setTitle("Login Status")
                        .setMessage("You are not logged in yet, Login now?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        })
                        .setNegativeButton("Not now", (dialog, which) -> dialog.cancel())
                        .show();

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setBackground(null);

        FloatingActionButton upload_notes = findViewById(R.id.upload_notes_floating_action_button_activity_main);
        FloatingActionButton upload_photos = findViewById(R.id.upload_photos_floating_action_button_activity_main);
        FloatingActionButton upload_news = findViewById(R.id.upload_news_floating_action_button_activity_main);
        FloatingActionButton visit_website = findViewById(R.id.visit_website_floating_action_button_activity_main);

        upload_photos.setVisibility(View.VISIBLE);
        upload_news.setVisibility(View.VISIBLE);
        upload_notes.setVisibility(View.VISIBLE);
        visit_website.setVisibility(View.VISIBLE);

        upload_notes.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), UploadNotesActivity.class);
            startActivity(intent);
        });

        upload_photos.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), UploadPhotosActivity.class);
            startActivity(intent);
        });

        upload_news.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), UploadNewsActivity.class);
            startActivity(intent);
        });

        visit_website.setOnClickListener(v->{
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://manage-college.000webhostapp.com"));
            startActivity(intent);
        });

            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(binding.navView, navController);


    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.action_bar_menu, menu);
//        return true;
//    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_login);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item){
//        int id = item.getItemId();
//        if (id == R.id.action_settings){
//            Toast.makeText(getApplicationContext(), "Opening settings", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
//            startActivity(intent);
//        }
//        if (id == R.id.action_about_us) {
//            Toast.makeText(getApplicationContext(), "About us", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://manage-college.000webhostapp.com"));
//            startActivity(intent);
//        }
//
//        if (id == R.id.action_help) {
//            Toast.makeText(getApplicationContext(), "Help", Toast.LENGTH_SHORT).show();
//        }
//
//        if (id == R.id.action_visit_website) {
//            Toast.makeText(getApplicationContext(), "Visit our website", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://manage-college.000webhostapp.com"));
//            startActivity(intent);
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


}