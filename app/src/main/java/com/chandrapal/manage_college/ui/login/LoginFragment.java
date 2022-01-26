package com.chandrapal.manage_college.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chandrapal.manage_college.MainActivity;
import com.chandrapal.manage_college.R;
import com.chandrapal.manage_college.databinding.FragmentLoginBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class LoginFragment extends Fragment {

    private TextView textViewSignup, textViewAfterLogin;
    private Button buttonLogin;
    private EditText editTextUsername, editTextPassword;
    private ProgressBar progressBar;
    private String URL = "https://manage-college.000webhostapp.com/verify_users.php";
    private FragmentLoginBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextUsername = binding.username;
        editTextPassword = binding.password;
        progressBar = binding.progress;
        buttonLogin = binding.buttonLogIn;
        textViewAfterLogin = binding.afterlogintext;
        textViewSignup = binding.signupText;

        binding.signupText.setOnClickListener(view1 -> {
            NavHostFragment.findNavController(com.chandrapal.manage_college.ui.login.LoginFragment.this)
                    .navigate(R.id.action_LoginFragment_to_SignupFragment);
        });

        buttonLogin.setOnClickListener(v->{
            String username, password;
            username = editTextUsername.getText().toString().trim();
            password = editTextPassword.getText().toString().trim();

            if(!username.equals("") && !password.equals("") ) {
                textViewAfterLogin.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        textViewAfterLogin.setVisibility(View.VISIBLE);
                        if (response.equals("success")) {
                            textViewAfterLogin.setText("Log in Successfull, You will be redirected to main page soon");
                            Toast.makeText(getContext(), "Log in Successfull, You will be redirected to main page soon", Toast.LENGTH_SHORT).show();

                            String dir = "https://manage-college.000webhostapp.com/upload_image/images/";
                            StringRequest stringRequest2 = new StringRequest(Request.Method.POST, "https://manage-college.000webhostapp.com/get_users.php", response2 -> {
                                try {
                                    JSONArray jsonArray2 = new JSONArray(response2);
                                    SharedPreferences sharedPreferences = requireContext().getSharedPreferences("loginInfo", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    for (int i = 0; i < jsonArray2.length(); i++) {
                                        JSONObject jsonObject = jsonArray2.getJSONObject(i);
                                        editor.putString("id",jsonObject.getString("id") );
                                        editor.putString("username",jsonObject.getString("username") );
                                        editor.putString("fullname", jsonObject.getString("fullname"));
                                        editor.putString("email", jsonObject.getString("email"));
                                        editor.putString("phone",jsonObject.getString("phone") );
                                        editor.putString("bio", jsonObject.getString("bio"));
                                        editor.putString("userType", jsonObject.getString("u_type"));
                                        editor.putString("profileImage", jsonObject.getString("profile_image"));
                                        editor.putString("status", jsonObject.getString("status"));
                                    }
                                    editor.apply();
                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                    startActivity(intent);
                                    requireActivity().finish();

                                } catch (Exception e) {
                                    e.printStackTrace();
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
                                    data.put("column", "username");
                                    data.put("value", username);
                                    return data;
                                }
                            };
                            RequestQueue requestQueue2 = Volley.newRequestQueue(requireContext());
                            requestQueue2.add(stringRequest2);

                        } else if (response.equals("failed")) {
                            textViewAfterLogin.setTextColor(Color.parseColor("#8B0000"));
                            textViewAfterLogin.setText("Incorrect username or password...");
                            Toast.makeText(getContext(), "Incorrect username or password...please try again!", Toast.LENGTH_SHORT).show();
                        } else {
                            textViewAfterLogin.setTextColor(Color.parseColor("#8B0000"));
                            textViewAfterLogin.setText("Something went wrong, couldn't log in, please try again.");
                            Toast.makeText(getContext(), "Something went wrong, couldn't log in, please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, error -> {
                    progressBar.setVisibility(View.GONE);
                    textViewAfterLogin.setTextColor(Color.parseColor("#8B0000"));
                    textViewAfterLogin.setVisibility(View.VISIBLE);
                    textViewAfterLogin.setText("Something went wrong, couldn't log in, please try again...");
                    Toast.makeText(getContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> data = new HashMap<>();
                        data.put("username", username);
                        data.put("password", password);
                        return data;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);
            } else{
                textViewAfterLogin.setTextColor(Color.parseColor("#8B0000"));
                textViewAfterLogin.setText("All fields are required");
                textViewAfterLogin.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
