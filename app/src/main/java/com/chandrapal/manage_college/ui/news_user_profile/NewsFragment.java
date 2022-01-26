package com.chandrapal.manage_college.ui.news_user_profile;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
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

public class NewsFragment extends Fragment {

    private List<Model> newsHolder = new ArrayList<>();
    private String user_id ;
    private String user_profile_image;
    private String message;

    private final String DIR = "https://manage-college.000webhostapp.com/upload_image/images/";

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
        View root = inflater.inflate(R.layout.fragment_news_user_profile, container, false);

        user_id = requireActivity().getIntent().getStringExtra("user_id");
        user_profile_image = requireActivity().getIntent().getStringExtra("user_profile_image");

//        if(user_profile_image.equals("")){
//            user_profile_image = DIR+"1406992643.jpg";
//        }

        newsHolder = new ArrayList<>();
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        ProgressBar progressBar = root.findViewById(R.id.progressBar);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://manage-college.000webhostapp.com/get_news.php", response -> {
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
                        users.setNewsUserId(jsonObject.getString("user_id"));
                        users.setNotesUserProfileImage(user_profile_image);
                        users.setNewsBranch(jsonObject.getString("branch"));
                        users.setNewsRelatedDocument(jsonObject.getString("related_document"));
                        users.setNewsUploadedTime(jsonObject.getString("uploaded_time"));
                        users.setNewsStatus(jsonObject.getString("status"));
                        users.setNewsPinned(jsonObject.getString("pinned"));

                        newsHolder.add(users);
                    }
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(new AdapterNews(newsHolder));
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

//                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                builder.setTitle("Connection error");
//                builder.setMessage("Could not connect to the server, Make sure you are connected to the internet");
//                builder.setCancelable(true);
//                builder.setPositiveButton("Try Again", (dialog, which) -> {
//                    NavHostFragment.findNavController(NewsFragment.this)
//                            .navigate(R.id.go_to_NewsFragment);
//                });
//                AlertDialog alertDialog = builder.create();
//                alertDialog.show();

            }
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