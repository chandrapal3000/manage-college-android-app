package com.chandrapal.manage_college.ui.notifications;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.chandrapal.manage_college.R;
import com.chandrapal.manage_college.SettingsActivity;
import com.chandrapal.manage_college.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        Toolbar toolbar = root.findViewById(R.id.toolbar_fragment_notifications);

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

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}