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
import com.example.mybus.R;
import com.example.mybus.UserInfo;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpFragment extends Fragment {

    private static final String TAG = SignUpFragment.class.getSimpleName();

    private Context mContext;
    private View view;
    private TextInputLayout layoutEmail, layoutPassword, layoutName;
    private TextInputEditText textEmail, textPassword, textName;
    private TextView txtAccount, txtSignIn;
    private Button btnSignUp;
    private ProgressDialog dialog;


    public SignUpFragment(){}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        //initializing context
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.layout_sign_up,container,false);
        init();
        return view;
    }
    private void init() {
        layoutEmail = view.findViewById(R.id.email);
        layoutPassword = view.findViewById(R.id.password);
        layoutName = view.findViewById(R.id.name);
        textEmail = view.findViewById(R.id.email1);
        textPassword = view.findViewById(R.id.password1);
        textName = view.findViewById(R.id.fullname);
        txtAccount = view.findViewById(R.id.account);
        txtSignIn = (TextView) view.findViewById(R.id.signin);
        btnSignUp = view.findViewById(R.id.go_signup);
        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);

        txtSignIn.setOnClickListener(v -> {
            //change fragments
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAuthContainer,new SignInFragment()).commit();
        });
        btnSignUp.setOnClickListener(v ->{
            //validate fields first
            if (validate()) {
                Log.v(TAG,"Register button CLicked! and validated data");
               register();
            }
        });

        textEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (textEmail.getText().toString().isEmpty()){
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
                if (textPassword.getText().toString().length()>8){
                    layoutPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        textName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (textName.getText().toString().length()>8){
                    layoutName.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private boolean validate (){
        if (textEmail.getText().toString().isEmpty()){
            layoutEmail.setErrorEnabled(true);
            layoutEmail.setError("Email is required");
            return false;
        }

        if (textPassword.getText().toString().length()<8){
            layoutPassword.setErrorEnabled(true);
            layoutPassword.setError("Required at least 8 characters");
            return false;
        }
        if (textName.getText().toString().isEmpty()){
            layoutName.setErrorEnabled(true);
            layoutName.setError("Name is required");
            return false;
        }
        return true;
    }

    private void register(){
        dialog.setMessage("Reqistering");
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Constant.REGISTER, response -> {
            //we get response if connection success
            try {

                Log.v(TAG,"Under response");

                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    JSONObject user = object.getJSONObject("user");
                    //make shared preference user
                    SharedPreferences userPref = getActivity().getApplicationContext().getSharedPreferences("user",getContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor = userPref.edit();
                    editor.putString("token",object.getString("token"));
                    editor.putString("name",user.getString("name"));
                    editor.putString("lastname",user.getString("lastname"));
                    editor.putString("photo",user.getString("photo"));
                    editor.putBoolean("isRegistering",true);
                    editor.apply();
                    //if success
                    startActivity(new Intent(((AuthActivity)getContext()), UserInfo.class));
                    ((AuthActivity) getContext()).finish();
                    Toast.makeText(getContext(),"Register Success", Toast.LENGTH_SHORT).show();
                    Log.v(TAG,"Response successful");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            dialog.dismiss();
        },error -> {
            //error if connection not success
            error.printStackTrace();
            dialog.dismiss();
        }){
            //add parameters

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("email",textEmail.getText().toString().trim());
                map.put("password",textPassword.getText().toString());
                return map;
            }
        };

        //add this request to requestqueue
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);

    }
}
