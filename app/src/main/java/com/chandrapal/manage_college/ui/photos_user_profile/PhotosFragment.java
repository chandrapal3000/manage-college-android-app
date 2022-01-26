package com.chandrapal.manage_college.ui.photos_user_profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chandrapal.manage_college.Model;
import com.chandrapal.manage_college.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class PhotosFragment extends Fragment {

    private List<Model> photosHolder ;
    private List<Model> usersHolder ;
    private String user_id ;
    private String message;
    private String usersResponse = "";

    private final static String DIR = "https://manage-college.000webhostapp.com/upload_image/images/";
    private final static String WEB_URL_GET_USERS = "https://manage-college.000webhostapp.com/get_users.php";
    private final static String WEB_URL_GET_PHOTOS = "https://manage-college.000webhostapp.com/get_photos.php";

    public PhotosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_photos_user_profile, container, false);

        user_id = requireActivity().getIntent().getStringExtra("user_id");

        photosHolder = new ArrayList<>();
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
        
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WEB_URL_GET_PHOTOS, response -> {
            if(response.equals("Doesn't look like anything to me!") || response.equals("empty table/orderby/ascdesc!") || response.equals("oversmart mat ban, ja pucha h wo fill kar") ){
                progressBar.setVisibility(View.GONE);
                Toast.makeText(requireActivity(),response,Toast.LENGTH_LONG).show();
            }else {
                try {
//                    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                    JSONArray jsonArray = new JSONArray(response);
                    message = jsonArray.getJSONObject(0).getString("message");
                    if(message.equals("positive")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Model users = new Model();
                            users.setPhotosId(jsonObject.getString("id"));
                            users.setPhotosImage(DIR + jsonObject.getString("image"));
                            users.setPhotosUploadedBy(jsonObject.getString("uploaded_by"));

                            String photosUserIdString = jsonObject.getString("user_id");
                            String photosUserProfileImageString = "";

                            for(int j = 0; j < usersHolder.size(); j++){

                                if(photosUserIdString.equals(usersHolder.get(j).getUsersId())){
                                    photosUserProfileImageString = usersHolder.get(j).getUsersProfileImage();
                                }

                            }

                            if(photosUserProfileImageString.equals("")){
                                photosUserProfileImageString = "1406992643.jpg";
                            }
                            
                            users.setPhotosUserId(photosUserIdString);
                            users.setPhotosUserProfileImage(DIR+photosUserProfileImageString);
                            users.setPhotosCaption(jsonObject.getString("caption"));
                            users.setPhotosUploadedTime(jsonObject.getString("uploaded_time"));
                            users.setPhotosImageSize(jsonObject.getString("image_size"));
                            users.setPhotosStatus(jsonObject.getString("status"));
                            users.setPhotosPinned(jsonObject.getString("pinned"));

                            photosHolder.add(users);
                        }
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(new AdapterPhotos(photosHolder));
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
            Toast.makeText(requireActivity(),error.toString().trim(), Toast.LENGTH_LONG).show();
        }){
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("column", "user_id");
                data.put("value", user_id);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());
        requestQueue.add(stringRequest);
        return root;
    }

}