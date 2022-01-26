package com.chandrapal.manage_college.ui.dashboard;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chandrapal.manage_college.FullPhotosActivity;
import com.chandrapal.manage_college.Model;
import com.chandrapal.manage_college.R;
import com.chandrapal.manage_college.SettingsActivity;
import com.chandrapal.manage_college.UserProfileActivity;
import com.chandrapal.manage_college.databinding.FragmentDashboardBinding;
import com.chandrapal.manage_college.ui.photos.AdapterPhotos;
import com.chandrapal.manage_college.ui.photos_user_profile.PhotosFragment;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class DashboardFragment extends Fragment implements AdapterPhotosDashboardFragment.onImageClickInterfaceForPhotos, AdapterNewsDashboardFragment.onClickInterfaceForNews, AdapterUsersDashboardFragment.onImageClickInterfaceForUsers, AdapterStudentsDashboardFragment.onImageClickInterfaceForStudents, AdapterTeachersDashboardFragment.onImageClickInterfaceForTeachers{

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    private List<Model> photosHolder_dashboard_fragment;
    private List<Model> newsHolder_dashboard_fragment;
    private List<Model> users_holder_dashboard_fragment;
    private List<Model> usersHolder;
    private List<Model> our_teachers_holder_dashboard_fragment;
    private List<Model> top_students_holder_dashboard_fragment;

    private final static String DIR = "https://manage-college.000webhostapp.com/upload_image/images/";
    private final static String WEB_URL_GET_USERS = "https://manage-college.000webhostapp.com/get_users.php";
    private final static String WEB_URL_GET_PHOTOS = "https://manage-college.000webhostapp.com/get_photos.php";
    private final static String WEB_URL_GET_NEWS = "https://manage-college.000webhostapp.com/get_news.php";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView seeMorePhotos = root.findViewById(R.id.seeMorePhotos);
        TextView seeMoreNews = root.findViewById(R.id.seeMoreNews);
//        LinearLayoutCompat photos_linear_layout = root.findViewById(R.id.photos_linear_layout_fragment_dashboard);
        RecyclerView recyclerView_photos = root.findViewById(R.id.recyclerView_photos_fragment_dashboard);
        RecyclerView recyclerView_news = root.findViewById(R.id.recyclerView_news_fragment_dashboard);
        RecyclerView recyclerView_top_students = root.findViewById(R.id.recyclerView_top_students_fragment_dashboard);
        RecyclerView recyclerView_our_users = root.findViewById(R.id.recyclerView_our_users_fragment_dashboard);
        RecyclerView recyclerView_our_teachers = root.findViewById(R.id.recyclerView_our_teachers_fragment_dashboard);

        Toolbar toolbar = root.findViewById(R.id.toolbar_fragment_dashboard);

        toolbar.setOnMenuItemClickListener(item->{
            if(item.getItemId()==R.id.action_about_me_always){
                Toast.makeText(getContext(),"Visit my website", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://chandrapal.gq"));
                startActivity(intent);
            }
            if(item.getItemId()==R.id.action_settings){
//                Toast.makeText(requireActivity(),"Opening settings", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), SettingsActivity.class);
                startActivity(intent);
            }
            if(item.getItemId()==R.id.action_faq){
//                Toast.makeText(requireActivity(),"Frequently asked questions", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://manage-college.000webhostapp.com/contact.php"));
                startActivity(intent);
            }
            if(item.getItemId()==R.id.action_about_us){
                Toast.makeText(getContext(),"Visit my website", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://chandrapal.gq"));
                startActivity(intent);
            }
            if(item.getItemId()==R.id.action_visit_website){
//                Toast.makeText(requireActivity(),"Visit our website", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://manage-college.000webhostapp.com"));
                startActivity(intent);
            }

            return false;
        });
        
        //users

        usersHolder =  new ArrayList<>();
        users_holder_dashboard_fragment = new ArrayList<>();
        our_teachers_holder_dashboard_fragment = new ArrayList<>();
        top_students_holder_dashboard_fragment = new ArrayList<>();

        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, WEB_URL_GET_USERS, response -> {
            if(response.equals("Doesn't look like anything to me!") || response.equals("empty table/orderby/ascdesc!") || response.equals("oversmart mat ban, ja pucha h wo fill kar") ){
//                progressBar.setVisibility(View.GONE);
                Toast.makeText(requireActivity(),response,Toast.LENGTH_LONG).show();
            }else {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    String message = jsonArray.getJSONObject(0).getString("message");
                    if(message.equals("positive")) {

                        //------------------------------------------------------------------

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
                            users.setUsersProfileImage(DIR+jsonObject.getString("profile_image"));
                            users.setUsersStatus(jsonObject.getString("status"));

                            users_holder_dashboard_fragment.add(users);
                            usersHolder.add(users);
                        }
                        Log.d(TAG, "onCreateView: "+usersHolder.get(0).getUsersProfileImage());
//                        progressBar.setVisibility(View.GONE);
                        recyclerView_our_users.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false));
                        recyclerView_our_users.setAdapter(new AdapterUsersDashboardFragment(users_holder_dashboard_fragment, this));

                        //============================================================================

                        //----------------------------------------------------------------------------

                        for (int i = 0; i < jsonArray.length(); i++) {
                            if(jsonArray.getJSONObject(i).getString("u_type").equals("student") ) {
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
                                users.setUsersProfileImage(DIR + jsonObject.getString("profile_image"));
                                users.setUsersStatus(jsonObject.getString("status"));

                                top_students_holder_dashboard_fragment.add(users);
                            }
                        }
//                        progressBar.setVisibility(View.GONE);
                        recyclerView_top_students.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false));
                        recyclerView_top_students.setAdapter(new AdapterStudentsDashboardFragment(top_students_holder_dashboard_fragment, this));

                        //================================================================================

                        //----------------------------------------------------------------------------

                        for (int i = 0; i < jsonArray.length(); i++) {
                            if(jsonArray.getJSONObject(i).getString("u_type").equals("teacher") ) {
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
                                users.setUsersProfileImage(DIR + jsonObject.getString("profile_image"));
                                users.setUsersStatus(jsonObject.getString("status"));

                                our_teachers_holder_dashboard_fragment.add(users);
                            }
                        }
//                        progressBar.setVisibility(View.GONE);
                        recyclerView_our_teachers.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false));
                        recyclerView_our_teachers.setAdapter(new AdapterTeachersDashboardFragment(our_teachers_holder_dashboard_fragment, this));

                        //================================================================================

                    } else {
//                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "No data found", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
//            progressBar.setVisibility(View.GONE);
            if(isAdded()){
                Toast.makeText(requireActivity(), error.toString().trim(), Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue2 = Volley.newRequestQueue(requireActivity());
        requestQueue2.add(stringRequest2);

        //users

        //photos

        photosHolder_dashboard_fragment = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WEB_URL_GET_PHOTOS, response -> {
            if(response.equals("Doesn't look like anything to me!") || response.equals("empty table/orderby/ascdesc!") || response.equals("oversmart mat ban, ja pucha h wo fill kar") ){
//                progressBar.setVisibility(View.GONE);
                Toast.makeText(requireActivity(),response,Toast.LENGTH_LONG).show();
            }else {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    String message = jsonArray.getJSONObject(0).getString("message");
                    if(message.equals("positive")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Model users = new Model();
                            users.setPhotosId(jsonObject.getString("id"));
                            users.setPhotosImage(DIR+jsonObject.getString("image"));
                            users.setPhotosUploadedBy(jsonObject.getString("uploaded_by"));

                            String photosUserIdString = jsonObject.getString("user_id");
                            String photosUserProfileImageString = "";

                            for(int j = 0; j < usersHolder.size(); j++){

                                if(photosUserIdString.equals(usersHolder.get(j).getUsersId())){
                                    photosUserProfileImageString = usersHolder.get(j).getUsersProfileImage();
                                }

                            }

                            if(photosUserProfileImageString.equals("")){
                                photosUserProfileImageString = DIR+"1406992643.jpg";
                            }
                            
                            users.setPhotosUserId(photosUserIdString);
                            users.setPhotosUserProfileImage(photosUserProfileImageString);
                            users.setPhotosCaption(jsonObject.getString("caption"));
                            users.setPhotosUploadedTime(jsonObject.getString("uploaded_time"));
                            users.setPhotosImageSize(jsonObject.getString("image_size"));
                            users.setPhotosStatus(jsonObject.getString("status"));
                            users.setPhotosPinned(jsonObject.getString("pinned"));

                            photosHolder_dashboard_fragment.add(users);
                        }
//                        progressBar.setVisibility(View.GONE);
                        recyclerView_photos.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false));
                        recyclerView_photos.setAdapter(new AdapterPhotosDashboardFragment(photosHolder_dashboard_fragment, this));
                    } else {
//                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "No data found", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
//            progressBar.setVisibility(View.GONE);
            if(isAdded()){
                Toast.makeText(requireActivity(), error.toString().trim(), Toast.LENGTH_LONG).show();
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

        //photos

        //news

        newsHolder_dashboard_fragment = new ArrayList<>();
        StringRequest stringRequest3 = new StringRequest(Request.Method.POST, WEB_URL_GET_NEWS, response -> {
//            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            if(response.equals("Doesn't look like anything to me!") || response.equals("empty table/orderby/ascdesc!") || response.equals("oversmart mat ban, ja pucha h wo fill kar") ){
//                progressBar.setVisibility(View.GONE);
                Toast.makeText(requireActivity(),response,Toast.LENGTH_LONG).show();
            }else {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    String message = jsonArray.getJSONObject(0).getString("message");
                    if(message.equals("positive")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Model users = new Model();
                            users.setNewsId(jsonObject.getString("id"));
                            users.setNewsTitle(jsonObject.getString("title"));
                            users.setNewsNews(jsonObject.getString("news"));
                            users.setNewsUploadedBy(jsonObject.getString("uploaded_by"));

                            String newsUserIdString = jsonObject.getString("user_id");
                            String newsUserProfileImageString = "1406992643.jpg";

                            for(int j = 0; j < users_holder_dashboard_fragment.size(); j++){

                                if(newsUserIdString.equals(users_holder_dashboard_fragment.get(j).getUsersId())){
                                    newsUserProfileImageString = users_holder_dashboard_fragment.get(j).getUsersProfileImage();
                                }

                            }

                            users.setNewsUserId(jsonObject.getString("user_id"));
                            users.setNewsUserProfileImage(DIR+newsUserProfileImageString);
                            users.setNewsBranch(jsonObject.getString("branch"));
                            users.setNewsRelatedDocument(jsonObject.getString("related_document"));
                            users.setNewsUploadedTime(jsonObject.getString("uploaded_time"));
                            users.setNewsStatus(jsonObject.getString("status"));
                            users.setNewsPinned(jsonObject.getString("pinned"));

                            newsHolder_dashboard_fragment.add(users);
                        }
//                        progressBar.setVisibility(View.GONE);
                        recyclerView_news.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.HORIZONTAL, false));
                        recyclerView_news.setAdapter(new AdapterNewsDashboardFragment(newsHolder_dashboard_fragment, this));
                    } else {
//                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "No data found", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
//            progressBar.setVisibility(View.GONE);
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
        RequestQueue requestQueue3 = Volley.newRequestQueue(requireActivity());
        requestQueue3.add(stringRequest3);

        //news

        seeMorePhotos.setOnClickListener(v->{
            NavHostFragment.findNavController(com.chandrapal.manage_college.ui.dashboard.DashboardFragment.this)
                    .navigate(R.id.go_from_dashboard_to_photos);
        });

        seeMoreNews.setOnClickListener(v->{
            NavHostFragment.findNavController(com.chandrapal.manage_college.ui.dashboard.DashboardFragment.this)
                    .navigate(R.id.go_from_dashboard_to_news);
        });

        return root;
    }

    @Override

    public void onCreate(@NonNull Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){
        menu.clear();
        menuInflater.inflate(R.menu.action_bar_menu, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();

        if (id == R.id.action_settings){
            Toast.makeText(getContext(), "Opening settings2", Toast.LENGTH_SHORT).show();
        }

        if (id == R.id.action_faq){
            Toast.makeText(getContext(), "Help", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onImageClickMethodForPhotos(int position) {
        Intent intent = new Intent(requireContext(), FullPhotosActivity.class);
        intent.putExtra("photos_id", photosHolder_dashboard_fragment.get(position).getPhotosId());
        intent.putExtra("photos_image", photosHolder_dashboard_fragment.get(position).getPhotosImage());
        intent.putExtra("photos_uploaded_by", photosHolder_dashboard_fragment.get(position).getPhotosUploadedBy());
        intent.putExtra("photos_user_id", photosHolder_dashboard_fragment.get(position).getPhotosUserId());
        intent.putExtra("photos_user_profile_image", photosHolder_dashboard_fragment.get(position).getPhotosUserProfileImage());
        intent.putExtra("photos_uploaded_time", photosHolder_dashboard_fragment.get(position).getPhotosUploadedTime());
        intent.putExtra("photos_caption", photosHolder_dashboard_fragment.get(position).getPhotosCaption());
        intent.putExtra("photos_status", photosHolder_dashboard_fragment.get(position).getPhotosStatus());
        intent.putExtra("photos_pinned", photosHolder_dashboard_fragment.get(position).getPhotosPinned());
        startActivity(intent);
    }

    @Override
    public void onImageClickMethodForUsers(int position) {
        Intent intent = new Intent(getContext(), UserProfileActivity.class);
        intent.putExtra("user_id", users_holder_dashboard_fragment.get(position).getUsersId());
        intent.putExtra("uploaded_by", users_holder_dashboard_fragment.get(position).getUsersUsername());
        intent.putExtra("user_profile_image", users_holder_dashboard_fragment.get(position).getUsersProfileImage());

        startActivity(intent);
//        requireActivity().overridePendingTransition(R.anim.nav_default_pop_enter_anim,R.anim.nav_default_pop_exit_anim);
    }

    @Override
    public void onImageClickMethodForStudents(int position) {
        Intent intent = new Intent(getContext(), UserProfileActivity.class);
        intent.putExtra("user_id", top_students_holder_dashboard_fragment.get(position).getUsersId());
        intent.putExtra("uploaded_by", top_students_holder_dashboard_fragment.get(position).getUsersUsername());
        intent.putExtra("user_profile_image", top_students_holder_dashboard_fragment.get(position).getUsersProfileImage());
        startActivity(intent);
//        requireActivity().overridePendingTransition(R.anim.nav_default_pop_enter_anim,R.anim.nav_default_pop_exit_anim);
    }

    @Override
    public void onImageClickMethodForTeachers(int position) {
        Intent intent = new Intent(getContext(), UserProfileActivity.class);
        intent.putExtra("user_id", our_teachers_holder_dashboard_fragment.get(position).getUsersId());
        intent.putExtra("uploaded_by", our_teachers_holder_dashboard_fragment.get(position).getUsersUsername());
        intent.putExtra("user_profile_image", our_teachers_holder_dashboard_fragment.get(position).getUsersProfileImage());
        startActivity(intent);
//        requireActivity().overridePendingTransition(R.anim.nav_default_pop_enter_anim,R.anim.nav_default_pop_exit_anim);
    }

    @Override
    public void onClickMethodForNews(int position) {
        Toast.makeText(getContext(), "Position : "+position, Toast.LENGTH_SHORT).show();
    }
}