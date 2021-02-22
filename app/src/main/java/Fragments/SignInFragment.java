package Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mybus.AuthActivity;
import com.example.mybus.Constant;
import com.example.mybus.DashboardActivity;
import com.example.mybus.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignInFragment extends Fragment {
    private static final String TAG = SignInFragment.class.getSimpleName();

    private Context mContext;
    private View view;
    private TextInputLayout layoutEmail, layoutPassword;
    private TextInputEditText textEmail, textPassword;
    private TextView txtNewUser, txtSignUp;
    private Button btnSignIn, btnfp;
    private ProgressDialog dialog;

    public SignInFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        //initializing context
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_sign_in, container, false);
        init();
        return view;
    }

    private void init() {
        layoutEmail = view.findViewById(R.id.email2);
        layoutPassword = view.findViewById(R.id.password);
        textEmail = view.findViewById(R.id.txtemail2);
        textPassword = view.findViewById(R.id.txtPassword);
        txtNewUser = view.findViewById(R.id.signup);
        txtSignUp = view.findViewById(R.id.signup_txt);
        btnSignIn = view.findViewById(R.id.go_btn);
        //btnfp = view.findViewById(R.id.fp_btn);
        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);

        txtSignUp.setOnClickListener(v -> {
            //change fragments
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAuthContainer, new SignUpFragment()).commit();
        });
        btnSignIn.setOnClickListener(v -> {
            //validate fields first
            if (validate()) {

                Log.v(TAG,"Login button CLicked! and validated data");

                login();
            }
        });

        textEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (textEmail.getText().toString().isEmpty()) {
                    layoutEmail.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        textPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (textPassword.getText().toString().length() > 7) {
                    layoutPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private boolean validate() {
        if (textEmail.getText().toString().isEmpty()) {
            layoutEmail.setErrorEnabled(true);
            layoutEmail.setError("Email is required");
            return false;
        }

        if (textPassword.getText().toString().length() < 8) {
            layoutPassword.setErrorEnabled(true);
            layoutPassword.setError("Required at least 8 characters");
            return false;
        }
        return true;
    }

    public void login() {

        Log.v(TAG,"under login method");

        dialog.setMessage("Logging in");
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Constant.LOGIN, response -> {
            //we get response if connection success
            try {

                Log.v(TAG,"Under response");

                JSONObject object = new JSONObject(response);

                Log.v(TAG,"Success msg : "+object.getBoolean("success"));

                if (object.getBoolean("success")) {
                    JSONObject user = object.getJSONObject("user");
                    //make shared preference user
                    SharedPreferences userPref = getActivity().getApplicationContext().getSharedPreferences("user", getContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor = userPref.edit();
                    editor.putString("token", object.getString("token"));
                    editor.putString("name", user.getString("name"));
                    editor.putString("email", user.getString("email"));
                    //editor.putString("lastname", user.getString("lastname"));
                    //editor.putString("photo", user.getString("photo"));
                    //editor.putBoolean("isLoggedIn", true);
                    editor.apply();
                    //if success
                    startActivity(new Intent(((AuthActivity) getContext()), DashboardActivity.class));
                    ((AuthActivity) getContext()).finish();
                    Toast.makeText(getContext(), "Login Success", Toast.LENGTH_SHORT).show();
                    Log.v(TAG,"Response successful");

                    /* "id": 1,
        "name": "user",
        "email": "user@gmail.com",
        "email_verified_at": null,
        "created_at": "2020-11-22 02:00:04",
        "updated_at": "2020-11-22 02:00:04"*/
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG,"error on login : "+e.getMessage());
            }
            dialog.dismiss();
        }, error -> {
            //error if connection not success
            error.printStackTrace();
            dialog.dismiss();
        }) {
            //add parameters

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("name", "user");
                map.put("email", textEmail.getText().toString().trim());
                map.put("password", textPassword.getText().toString());
                return map;
            }
        };

        //add this request to requestqueue
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }

}

