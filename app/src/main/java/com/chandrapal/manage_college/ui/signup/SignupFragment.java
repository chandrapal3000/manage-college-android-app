package com.chandrapal.manage_college.ui.signup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chandrapal.manage_college.R;
import com.chandrapal.manage_college.databinding.FragmentSignupBinding;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class SignupFragment extends Fragment {

    private TextInputEditText textInputEditTextFullname, textInputEditTextUsername, textInputEditTextPassword, textInputEditTextEmail;
    private Button buttonSignUp;
    private TextView textViewLogin, aftersignuptext;
    private ProgressBar progressBar;
    private final String URL = "https://manage-college.000webhostapp.com/insert_users.php";
    private FragmentSignupBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSignupBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textInputEditTextFullname = binding.fullname;
        textInputEditTextUsername = binding.username;
        textInputEditTextEmail = binding.email;
        textInputEditTextPassword = binding.password;
        buttonSignUp = binding.buttonSignUp;
        textViewLogin = binding.loginText;
        progressBar = binding.progress;
        aftersignuptext = binding.aftersignuptext;

        textViewLogin.setOnClickListener(view1 -> NavHostFragment.findNavController(com.chandrapal.manage_college.ui.signup.SignupFragment.this)
                .navigate(R.id.action_SignupFragment_to_LoginFragment));

        buttonSignUp.setOnClickListener(v -> {

            String fullname, username, email, password;
            fullname = String.valueOf(textInputEditTextFullname.getText());
            username = String.valueOf(textInputEditTextUsername.getText());
            email = String.valueOf(textInputEditTextEmail.getText());
            password = String.valueOf(textInputEditTextPassword.getText());

            if (!fullname.equals("") && !username.equals("") && !email.equals("") && !password.equals("")) {
                aftersignuptext.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
                    aftersignuptext.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    String message;
                    if(response.equals("success"))
                        message = "Sign up successfull";
                    else if(response.equals("failed"))
                        message = "Signup failed";
                    else
                        message = "Sorry something went wrong...Couldn't signup...please Try again ";
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    aftersignuptext.setText(message);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    if(response.equals("success")) {
                        builder.setTitle("Redirect");
                        builder.setMessage("Sign up successfull, Go to login page?");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Go to login page", (dialog, which) -> NavHostFragment.findNavController(com.chandrapal.manage_college.ui.signup.SignupFragment.this)
                                .navigate(R.id.action_SignupFragment_to_LoginFragment));
                        builder.setNegativeButton("cancel", (dialog, which) -> dialog.cancel());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                    else{
                        builder.setTitle("Response from the server");
                        builder.setMessage("Sign up failed, Try again later!");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Try Again", (dialog, which) -> dialog.cancel());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }, error -> {
                    aftersignuptext.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    aftersignuptext.setText("Sorry something went wrong...Please try again");
                    Toast.makeText(getContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }){
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> data = new HashMap<>();
                        data.put("fullname", fullname);
                        data.put("username", username);
                        data.put("password", password);
                        data.put("email", email);
                        return data;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);
            }else{
                aftersignuptext.setVisibility(View.VISIBLE);
                aftersignuptext.setText("Please Fill All The required fields!");
                Toast.makeText(getContext(),"Please Fill All The required fields!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }

        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
