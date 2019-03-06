package com.example.melodyhacker.tutor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.melodyhacker.tutor.Adapter.ListRattingAdapter;
import com.example.melodyhacker.tutor.Model.GetSetRatting;
import com.example.melodyhacker.tutor.Tools.DeCode;
import com.example.melodyhacker.tutor.Values.Url;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by melodyhacker on 2/17/19.
 */

public class Ratting extends AppCompatActivity {
    RequestQueue requestQueue;
    Url url = new Url();
    ImageView ok, image;
    TextView full_name;
    EditText comm;
    FileLog token = new FileLog();
    String name, last_name, img, id, count, rate, com;
    RatingBar rat;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_activity);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        Bundle bundle = getIntent().getExtras();
        count = "0";
        if (bundle != null) {
            name = bundle.getString("name");
            last_name = bundle.getString("last_name");
            img = bundle.getString("img");
            id = bundle.getString("id");
            rate = bundle.getString("rate");
        }
        full_name = (TextView) findViewById(R.id.name);
        image = (ImageView) findViewById(R.id.img);
        full_name.setText(name + " " + last_name);
        comm = (EditText) findViewById(R.id.command);
        com = comm.getText().toString();
        Picasso.get().load(url.path_mage + img).into(image);

        rat = (RatingBar) findViewById(R.id.rat);
        rat.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Float ratingvalue = (Float) rat.getRating();
                count = ratingvalue.toString();
            }
        });

        ok = (ImageView) findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                putData(id, count, comm.getText().toString());
            }
        });

    }

    ///////////////////////////////////////////////////////////////////////////////

    public void putData(final String id, final String rat, final String com) {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url.ratting, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Ratting.this, response, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Ratting.this, Login.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Intent intent = new Intent(Ratting.this, NoInternet.class);
                startActivity(intent);
                Toast.makeText(Ratting.this, "Net Work Problem " + error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AbstractMethodError {
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("token", token.getToken(getApplication()));
                parameters.put("id", id);
                parameters.put("rat", rat);
                parameters.put("com", com);
                return parameters;
            }
        };
        requestQueue.add(stringRequest);
    }


}

