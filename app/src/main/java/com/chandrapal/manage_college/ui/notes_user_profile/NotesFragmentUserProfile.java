package com.chandrapal.manage_college.ui.notes_user_profile;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chandrapal.manage_college.BlankFragment;
import com.chandrapal.manage_college.FullNotesActivity;
import com.chandrapal.manage_college.Model;
import com.chandrapal.manage_college.R;
import com.chandrapal.manage_college.SettingsActivity;
import com.chandrapal.manage_college.UserProfileActivity;
import com.chandrapal.manage_college.databinding.FragmentHomeUserProfileBinding;
import com.chandrapal.manage_college.ui.home.HomeViewModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class NotesFragmentUserProfile extends Fragment implements AdapterNotesUserProfile.OnShowMoreListener, AdapterNotesUserProfile.OnProfileClickListener {

    private HomeViewModel homeViewModel;
    private static final String ARG_PARAM1 = "profile_image";
    private String user_id ;
    private String user_profile_image;
    private String message;

    boolean action_listview_home_menu_state = false;
    boolean action_small_cardview_home_menu_state = false;
    boolean action_cardview_home_menu_state = false;
    boolean action_search_home_menu_state = false;

    private final String DIR = "https://manage-college.000webhostapp.com/upload_image/images/";

    Menu menuGlobal;
    AdapterNotesUserProfile AdapterNotesUserProfile;
    private FragmentHomeUserProfileBinding binding;
    private List<Model> notesHolder;

    public NotesFragmentUserProfile() {
        //empty
    }

    public static NotesFragmentUserProfile newInstance(String param1) {
        NotesFragmentUserProfile fragment = new NotesFragmentUserProfile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@NonNull Bundle savedInstanceState) {
//        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeUserProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        user_id = requireActivity().getIntent().getStringExtra("user_id");
        user_profile_image = requireActivity().getIntent().getStringExtra("user_profile_image");

//        if(user_profile_image.equals("")){
//            user_profile_image = DIR+"1406992643.jpg";
//        }

        notesHolder = new ArrayList<>();
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        ProgressBar progressBar = root.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://manage-college.000webhostapp.com/get_notes.php", response -> {
            if(response.equals("Doesn't look like anything to me!") || response.equals("empty table/orderby/ascdesc!") || response.equals("oversmart mat ban, ja pucha h wo fill kar") ){
                progressBar.setVisibility(View.GONE);
                Toast.makeText(requireActivity(),response,Toast.LENGTH_LONG).show();
            }else {
                try {
//                    Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                    JSONArray jsonArray = new JSONArray(response);
                    message = jsonArray.getJSONObject(0).getString("message");
                    if(message.equals("positive")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Model notes = new Model();
                            message = jsonObject.getString("message");
                            notes.setNotesId(jsonObject.getString("id"));
                            notes.setNotesTitle(jsonObject.getString("title"));
                            notes.setNotesNotesText(jsonObject.getString("notes_text"));
                            notes.setNotesUploadedBy(jsonObject.getString("uploaded_by"));
                            notes.setNotesUserId(jsonObject.getString("user_id"));
                            notes.setNotesUserProfileImage(user_profile_image);
                            notes.setNotesBranch(jsonObject.getString("branch"));
                            notes.setNotesSubject(jsonObject.getString("subject"));
                            notes.setNotesChapter(jsonObject.getString("chapter"));
                            notes.setNotesUploadedTime(jsonObject.getString("uploaded_time"));
                            notes.setNotesStatus(jsonObject.getString("status"));
                            notes.setNotesPinned(jsonObject.getString("pinned"));

                            notesHolder.add(notes);
                        }
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        AdapterNotesUserProfile = new AdapterNotesUserProfile(notesHolder, this, this);
                        recyclerView.setAdapter(AdapterNotesUserProfile);
                        action_search_home_menu_state = true;
                        action_small_cardview_home_menu_state = true;
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "No data found", Toast.LENGTH_LONG).show();
                    }
//                    requireActivity().invalidateOptionsMenu();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(requireActivity(), error.toString().trim(), Toast.LENGTH_LONG).show();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onShowMoreClick(int position) {
        Intent intent = new Intent(getContext(), FullNotesActivity.class);
        intent.putExtra("notesId", notesHolder.get(position).getNotesId());
        intent.putExtra("notesTitle", notesHolder.get(position).getNotesTitle());
        intent.putExtra("notesNotesText", notesHolder.get(position).getNotesNotesText());
        intent.putExtra("notesUploadedBy", notesHolder.get(position).getNotesUploadedBy());
        intent.putExtra("notesUserId", notesHolder.get(position).getNotesUserId());
        intent.putExtra("notesUserProfileImage", notesHolder.get(position).getNotesUserProfileImage());
        intent.putExtra("notesBranch", notesHolder.get(position).getNotesBranch());
        intent.putExtra("notesSubject", notesHolder.get(position).getNotesSubject());
        intent.putExtra("notesChapter", notesHolder.get(position).getNotesChapter());
        intent.putExtra("notesUploadedTime", notesHolder.get(position).getNotesUploadedTime());
        intent.putExtra("notesStatus", notesHolder.get(position).getNotesStatus());
        intent.putExtra("notesPinned", notesHolder.get(position).getNotesPinned());
        startActivity(intent);
        requireActivity().overridePendingTransition(R.anim.nav_default_pop_enter_anim,R.anim.nav_default_pop_exit_anim);
    }

    @Override
    public void onProfileClick(int position) {
        Intent intent = new Intent(getContext(), UserProfileActivity.class);
        intent.putExtra("user_id", notesHolder.get(position).getNotesUserId());
        intent.putExtra("uploaded_by", notesHolder.get(position).getNotesUploadedBy());
        intent.putExtra("user_profile_image", notesHolder.get(position).getNotesUserProfileImage());

        startActivity(intent);
        requireActivity().overridePendingTransition(R.anim.nav_default_pop_enter_anim,R.anim.nav_default_pop_exit_anim);
    }
}