package com.example.melodyhacker.tutor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.melodyhacker.tutor.Values.Url;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by melodyhacker on 1/31/19.
 */

public class ProfileTutor extends AppCompatActivity implements View.OnClickListener {
    ImageView seach, edit, list_class, exit, image_profile,add_class;
    TextView name, last_name, address, tel, etc, username;
    FileLog token = new FileLog();
    RequestQueue requestQueue;
    Url url = new Url();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutor_profile_activity);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        loadData();
        username = (TextView) findViewById(R.id.username);
        name = (TextView) findViewById(R.id.name);
        add_class = (ImageView) findViewById(R.id.add_class);
        last_name = (TextView) findViewById(R.id.last_name);
        address = (TextView) findViewById(R.id.address);
        tel = (TextView) findViewById(R.id.tel);
        etc = (TextView) findViewById(R.id.etc);
        seach = (ImageView) findViewById(R.id.seach);
        edit = (ImageView) findViewById(R.id.edit);
        list_class = (ImageView) findViewById(R.id.list_class);
        exit = (ImageView) findViewById(R.id.exit);
        image_profile = (ImageView) findViewById(R.id.img_profile);
        list_class.setOnClickListener(this);
        exit.setOnClickListener(this);
        edit.setOnClickListener(this);
        add_class.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_class:
                Intent add_intent =new Intent(ProfileTutor.this,AddClassList.class);
                startActivity(add_intent);
                break;
            case R.id.edit:
                Intent edit_intent =new Intent(ProfileTutor.this,EditProfile.class);
                startActivity(edit_intent);
                break;
            case R.id.list_class:
                Intent list_intent =new Intent(ProfileTutor.this,ListClass.class);
                startActivity(list_intent);
                break;
            case R.id.exit:
                token.setToken("", getApplicationContext());
                Intent exit_intent = new Intent(ProfileTutor.this, Login.class);
                startActivity(exit_intent);
                break;
        }

    }

    private void loadData() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url.profile, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                String[] ar = response.split("-");
                username.setText(ar[0]);
                name.setText(ar[1]);
                last_name.setText(ar[2]);
                address.setText(ar[3]);
                tel.setText(ar[4]);
                etc.setText(ar[5]);
                String debug = url.path_mage + ar[6];
                System.out.print(debug);
                Picasso.get().load(debug).into(image_profile);
                System.out.print(ar);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Intent intent = new Intent(ProfileTutor.this, NoInternet.class);
                startActivity(intent);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AbstractMethodError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("token", token.getToken(getApplication()));
                return parameters;
            }
        };
        requestQueue.add(stringRequest);
    }
}