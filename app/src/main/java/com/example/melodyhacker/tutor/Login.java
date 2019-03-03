package com.example.melodyhacker.tutor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.melodyhacker.tutor.Tools.DeCode;
import com.example.melodyhacker.tutor.Values.Url;

import java.util.HashMap;
import java.util.Map;

public class Login extends Activity {
    RequestQueue requestQueue;
    Url url = new Url();
    DeCode deCode = new DeCode();
    ImageView login;
    EditText username, password;
    TextView register;
    String[] ar, ar2;
    FileLog token = new FileLog();
    ProgressDialog loading;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        loading = ProgressDialog.show(this, "Login in ...", "Please wait...", false, false);
        checkToken();
        username = (EditText) findViewById(R.id.user_name);
        password = (EditText) findViewById(R.id.pass_word);
        register = (TextView) findViewById(R.id.register);
        login = (ImageView) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login("username");
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }

    private void checkToken() {
        String debug = token.getToken(getApplication());
        if (token.getToken(getApplication()).equals("")) {
            Toast.makeText(this, "Login !!!!!!!!!!!!!!!!!!", Toast.LENGTH_SHORT).show();
            loading.dismiss();
        } else {
            login("token");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    private void login(final String status) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url.login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                ar = deCode.getHash(response).split("----");
                switch (ar[0]) {
                    case "succss":
                        ar2 = ar[1].split("_");
                        token.setToken(response, getApplicationContext());
                        Toast.makeText(Login.this, "Succss Login ", Toast.LENGTH_LONG).show();

                        String debug = ar[1];
                        if (ar2[0].equals("tutor")) {
                            Intent intent = new Intent(Login.this, ProfileTutor.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(Login.this, ProfileStudent.class);
                            startActivity(intent);
                        }
                        break;
                    default:
                        Toast.makeText(Login.this, response + "Some Wrong User or Password !!!", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Intent intent=new Intent(Login.this,NoInternet.class);
                startActivity(intent);
                Toast.makeText(Login.this, "Sorry Disconnect to NetWorking or Some Wrong System !!! " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AbstractMethodError {
                Map<String, String> parameters = new HashMap<String, String>();
                if (status.equals("token")) {
                    String data = token.getToken(getApplication());
                    parameters.put("token", data);
                } else {
                    parameters.put("username", username.getText().toString());
                    parameters.put("password", password.getText().toString());
                }
                return parameters;
            }
        };
        requestQueue.add(stringRequest);
    }
///////////////////////////////////////////////////////////////////////////////

}
