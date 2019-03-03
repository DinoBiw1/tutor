package com.example.melodyhacker.tutor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class ProfileStudent extends AppCompatActivity implements View.OnClickListener {
    ImageView seach, edit, list, exit, image_profile, ratting, see;
    TextView name, last_name, address, tel, etc, username;
    FileLog token = new FileLog();
    RequestQueue requestQueue;
    Url url = new Url();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_profile_activity);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        loadData();
        username = (TextView) findViewById(R.id.username);
        name = (TextView) findViewById(R.id.name);
        last_name = (TextView) findViewById(R.id.last_name);
        address = (TextView) findViewById(R.id.address);
        tel = (TextView) findViewById(R.id.tel);
        etc = (TextView) findViewById(R.id.etc);
        seach = (ImageView) findViewById(R.id.seach);
        edit = (ImageView) findViewById(R.id.edit);
        list = (ImageView) findViewById(R.id.list);
        exit = (ImageView) findViewById(R.id.exit);
        ratting = (ImageView) findViewById(R.id.ratting);
        see = (ImageView) findViewById(R.id.see);
        image_profile = (ImageView) findViewById(R.id.img_profile);
        seach.setOnClickListener(this);
        edit.setOnClickListener(this);
        list.setOnClickListener(this);
        see.setOnClickListener(this);
        ratting.setOnClickListener(this);
        exit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.seach:
                Intent seach = new Intent(this, SeachClass.class);
//                seach.putExtra("status", "student");
                startActivity(seach);
                break;
            case R.id.edit:
                Intent edit_intent = new Intent(ProfileStudent.this, EditProfile.class);
                startActivity(edit_intent);
                break;
            case R.id.ratting:
                Intent ratting_intent = new Intent(ProfileStudent.this, ListRatting.class);
                startActivity(ratting_intent);
                break;
            case R.id.list:
                Intent list_intent = new Intent(ProfileStudent.this, ListStudy.class);
                startActivity(list_intent);
                break;
            case R.id.see:
                Intent see_intent = new Intent(ProfileStudent.this, SeeRatting.class);
                startActivity(see_intent);
                break;
            case R.id.exit:
                token.setToken("", getApplicationContext());
                Toast.makeText(this, "Log out" + token.getToken(getApplication()), Toast.LENGTH_SHORT).show();
                Intent exit = new Intent(ProfileStudent.this, Login.class);
                startActivity(exit);
                break;
        }

    }

    private void loadData() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url.profile, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                String[] ar = response.split("--");
                username.setText(ar[0]);
                name.setText(ar[1]);
                last_name.setText(ar[2]);
                address.setText(ar[7]);
                tel.setText(ar[4]);
                etc.setText(ar[5]);
                String debug = url.path_mage + ar[12];
                System.out.print(debug);
                Picasso.get().load(debug).into(image_profile);
                System.out.print(ar);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Intent intent = new Intent(ProfileStudent.this, NoInternet.class);
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