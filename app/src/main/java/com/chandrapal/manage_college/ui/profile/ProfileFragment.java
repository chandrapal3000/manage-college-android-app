package com.chandrapal.manage_college.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.transition.Fade;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.chandrapal.manage_college.LoginActivity;
import com.chandrapal.manage_college.R;
import com.chandrapal.manage_college.SettingsActivity;
import com.chandrapal.manage_college.UpdateProfileActivity;
import com.github.drjacky.imagepicker.ImagePicker;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public class ProfileFragment extends Fragment {

    String dir = "https://manage-college.000webhostapp.com/upload_image/images/";

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView welcome = root.findViewById(R.id.welcome);
        CollapsingToolbarLayout collapsing_username_profile = root.findViewById(R.id.collapseingToolbar_fragment_profile);
        TextView fullname = root.findViewById(R.id.fullnameFragmentProfile);
        TextView username = welcome;
        TextView phone = root.findViewById(R.id.phoneFragmentProfile);
        TextView email = root.findViewById(R.id.emailFragmentProfile);
        TextView bio = root.findViewById(R.id.bioFragmentProfile);
        TextView userType = root.findViewById(R.id.userTypeFragmentProfile);
        ShapeableImageView profileImage = root.findViewById(R.id.profileImageFragmentProfile);
        ShapeableImageView cover_profile_image = root.findViewById(R.id.CoverImage_profile);
        TextView status = root.findViewById(R.id.statusFragmentProfile);
        AppBarLayout appBarLayout = root.findViewById(R.id.appbar_fragment_profile);
        ConstraintLayout constraintLayout = root.findViewById(R.id.profile_container_constraint_layout);
        NestedScrollView nestedScrollView = root.findViewById(R.id.profile_container_nested_scrollview);
        FloatingActionButton floating_update_profile_button = root.findViewById(R.id.floating_update_profile_button);
        ConstraintLayout constraintLayout_teacher_tasks = root.findViewById(R.id.constraintLayout_teacher_tasks_fragment_profile);

        floating_update_profile_button.setOnClickListener(v->{
            Intent intent = new Intent(getContext(), UpdateProfileActivity.class);
            startActivity(intent);
        });

        Toolbar toolbar = root.findViewById(R.id.toolbar_fragment_profile);

        toolbar.setOnMenuItemClickListener(item->{
            if(item.getItemId()==R.id.action_about_me_always){
                Toast.makeText(getContext(),"Visit my website", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://chandrapal.gq"));
                startActivity(intent);
            }
            if(item.getItemId()==R.id.action_settings) {
                Toast.makeText(requireActivity(),"Opening settings", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), SettingsActivity.class);
                startActivity(intent);
            }
            if(item.getItemId()==R.id.action_faq){
                Toast.makeText(requireActivity(),"Frequently asked questions", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://manage-college.000webhostapp.com/contact.php"));
                startActivity(intent);
            }
            if(item.getItemId()==R.id.action_about_us){
                Toast.makeText(getContext(),"Visit my website", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://chandrapal.gq"));
                startActivity(intent);
            }
            if(item.getItemId()==R.id.action_visit_website){
                Toast.makeText(requireActivity(),"Visit our website", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://manage-college.000webhostapp.com"));
                startActivity(intent);
            }

            return false;
        });

//        profileImage.setVisibility(View.GONE);

//        appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {
//            try {
//
//                if (Math.abs(verticalOffset) - appBarLayout.getTotalScrollRange() == 0) {
//
//                    Transition transition = new Slide(Gravity.TOP);
//
//                    transition.setDuration(300);
//                    transition.addTarget(profileImage);
//
//                    TransitionManager.beginDelayedTransition(constraintLayout, transition);
//                    profileImage.setVisibility(View.VISIBLE);
////                floatingImagePicker.setVisibility(View.VISIBLE);
//                } else {
//
////                Transition transition = new Slide(Gravity.TOP);
////
////                transition.setDuration(300);
////                transition.addTarget(profileImage);
////
////                TransitionManager.beginDelayedTransition(constraintLayout, transition);
//
//                    profileImage.setVisibility(View.GONE);
////                floatingImagePicker.setVisibility(View.GONE);
//                }
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//
//        });

        Button logout = root.findViewById(R.id.logout);
        Button login = root.findViewById(R.id.login);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        if(!sharedPreferences.contains("username")){
            fullname.setVisibility(View.GONE);
            phone.setVisibility(View.GONE);
            email.setVisibility(View.GONE);
            bio.setVisibility(View.GONE);
            userType.setVisibility(View.GONE);
            status.setVisibility(View.GONE);
            floating_update_profile_button.setVisibility(View.GONE);
            welcome.setText("Log in to see profile details and more!");
            login.setVisibility(View.VISIBLE);
            login.setOnClickListener(v->{
                Toast.makeText(requireActivity(), "Log In", Toast.LENGTH_LONG).show();
                startActivity(new Intent(requireActivity(), LoginActivity.class));
            });
        }
        else {
            if(sharedPreferences.getString("userType", "").equals("teacher") || sharedPreferences.getString("userType", "").equals("admin") ) {
                constraintLayout_teacher_tasks.setVisibility(View.VISIBLE);
            }
            String usernameString = sharedPreferences.getString("username", "");
            String welcomeString = "Welcome "+usernameString;
            String fullnameString = "fullname : "+sharedPreferences.getString("fullname", "");
            String idString = "id : "+sharedPreferences.getString("id", "");
            String phoneString = "phone : "+sharedPreferences.getString("phone", "");
            String emailString = "email : "+sharedPreferences.getString("email", "");
            String bioString = "bio : "+sharedPreferences.getString("bio", "");
            String userTypeString = "userType : "+sharedPreferences.getString("userType", "");
            String profileImageString = dir+sharedPreferences.getString("profileImage", "");

            String statusString = "status : "+sharedPreferences.getString("status", "");

            welcome.setText(welcomeString);
            collapsing_username_profile.setTitle(usernameString);
            fullname.setText(fullnameString);
            phone.setText(phoneString);
            email.setText(emailString);
            bio.setText(bioString);
            userType.setText(userTypeString);
            status.setText(statusString);
            Picasso.get().load(profileImageString).into(profileImage);
            Picasso.get().load(profileImageString).into(cover_profile_image);

            logout.setVisibility(View.VISIBLE);
            logout.setOnClickListener(v -> {
                Toast.makeText(requireActivity(), "Log Out", Toast.LENGTH_LONG).show();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("username");
                editor.apply();
                startActivity(new Intent(requireActivity(), LoginActivity.class));
                requireActivity().finish();
            });
        }

        return root;
    }
}