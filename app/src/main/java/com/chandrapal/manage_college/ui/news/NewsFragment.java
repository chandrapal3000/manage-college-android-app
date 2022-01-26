package com.chandrapal.manage_college.ui.news;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chandrapal.manage_college.Model;
import com.chandrapal.manage_college.R;
import com.chandrapal.manage_college.UserProfileActivity;
import com.chandrapal.manage_college.ui.photos.AdapterPhotos;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsFragment extends Fragment implements AdapterNews.OnProfileClickInterface {

    private List<Model> newsHolder;
    private List<Model> usersHolder;
    private String message;
    private String usersResponse = "";

    private final static String DIR = "https://manage-college.000webhostapp.com/upload_image/images/";
    private final static String WEB_URL_GET_USERS = "https://manage-college.000webhostapp.com/get_users.php";
    private final static String WEB_URL_GET_PHOTOS = "https://manage-college.000webhostapp.com/get_photos.php";
    private final static String WEB_URL_GET_NEWS = "https://manage-college.000webhostapp.com/get_news.php";

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_news, container, false);

        newsHolder = new ArrayList<>();
        usersHolder = new ArrayList<>();

        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        ProgressBar progressBar = root.findViewById(R.id.progressBar);

        //        Users

        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, WEB_URL_GET_USERS, response -> {
            if(response.equals("Doesn't look like anything to me!") || response.equals("empty table/orderby/ascdesc!") || response.equals("oversmart mat ban, ja pucha h wo fill kar") ){
                Toast.makeText(requireActivity(),response,Toast.LENGTH_LONG).show();
            }else {
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    message = jsonArray.getJSONObject(0).getString("message");
                    if(message.equals("positive")) {

                        usersResponse = "success";

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Model users = new Model();
                            users.setUsersId(jsonObject.getString("id"));
                            users.setUsersFullname(jsonObject.getString("fullname"));
                            users.setUsersRegNum(jsonObject.getString("reg_num"));
                            users.setUsersUsername(jsonObject.getString("username"));
                            users.setUsersPhone(jsonObject.getString("phone"));
                            users.setUsersEmail(jsonObject.getString("email"));
                            users.setUsersBio(jsonObject.getString("bio"));
                            users.setUsersType(jsonObject.getString("u_type"));
                            users.setUsersProfileImage(jsonObject.getString("profile_image"));
                            users.setUsersStatus(jsonObject.getString("status"));

                            usersHolder.add(users);
                        }
                    } else {
                        Toast.makeText(getContext(), "No users found", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
            usersResponse = "error";
            Toast.makeText(requireActivity(), error.toString().trim(), Toast.LENGTH_LONG).show();
        }){
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("column", "");
                data.put("value", "");
                return data;
            }
        };
        RequestQueue requestQueue2 = Volley.newRequestQueue(requireActivity());
        requestQueue2.add(stringRequest2);

//        Users

        StringRequest stringRequest = new StringRequest(Request.Method.POST, WEB_URL_GET_NEWS, response -> {
            if(response.equals("Doesn't look like anything to me!") || response.equals("empty table/orderby/ascdesc!") || response.equals("oversmart mat ban, ja pucha h wo fill kar") ){
                progressBar.setVisibility(View.GONE);
                Toast.makeText(requireActivity(),response,Toast.LENGTH_LONG).show();
            }else {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    message = jsonArray.getJSONObject(0).getString("message");
                    if(message.equals("positive")) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Model users = new Model();
                        users.setNewsId(jsonObject.getString("id"));
                        users.setNewsTitle(jsonObject.getString("title"));
                        users.setNewsNews(jsonObject.getString("news"));
                        users.setNewsUploadedBy(jsonObject.getString("uploaded_by"));

                        String newsUserIdString = jsonObject.getString("user_id");
                        String newsUserProfileImageString = "";

                        for(int j = 0; j < usersHolder.size(); j++){

                            if(newsUserIdString.equals(usersHolder.get(j).getUsersId())){
                                newsUserProfileImageString = usersHolder.get(j).getUsersProfileImage();
                            }

                        }

                        if(newsUserProfileImageString.equals("")){
                            newsUserProfileImageString = "1406992643.jpg";
                        }

                        users.setNewsUserId(jsonObject.getString("user_id"));
                        users.setNewsUserProfileImage(DIR+newsUserProfileImageString);
                        users.setNewsBranch(jsonObject.getString("branch"));
                        users.setNewsRelatedDocument(jsonObject.getString("related_document"));
                        users.setNewsUploadedTime(jsonObject.getString("uploaded_time"));
                        users.setNewsStatus(jsonObject.getString("status"));
                        users.setNewsPinned(jsonObject.getString("pinned"));

                        newsHolder.add(users);
                    }
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(new AdapterNews(newsHolder, this));
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "No data found", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
            progressBar.setVisibility(View.GONE);
            if (isAdded()) {
                Toast.makeText(requireActivity(), error.toString().trim(), Toast.LENGTH_LONG).show();

                new MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Connection error")
                        .setMessage("Could not connect to the server, Make sure you are connected to the internet, if your internet is fine then that may be a server error")
                        .setCancelable(true)
                        .setPositiveButton("Try Again", (dialog, which) -> {
                            NavHostFragment.findNavController(com.chandrapal.manage_college.ui.news.NewsFragment.this)
                                    .navigate(R.id.go_to_NewsFragment);
                        })
                        .show();

            }
        }){
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("column", "");
                data.put("value", "");
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());
        requestQueue.add(stringRequest);

        return root;
    }

    @Override
    public void onProfileClickMethod(int position) {
        Intent intent = new Intent(getContext(), UserProfileActivity.class);
        intent.putExtra("user_id", newsHolder.get(position).getNewsUserId());
        intent.putExtra("uploaded_by", newsHolder.get(position).getNewsUploadedBy());
        startActivity(intent);
//        requireActivity().overridePendingTransition(R.anim.nav_default_pop_enter_anim,R.anim.nav_default_pop_exit_anim);
    }
}