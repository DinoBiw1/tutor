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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.melodyhacker.tutor.Adapter.ListRattingAdapter;

import com.example.melodyhacker.tutor.Tools.DeCode;
import com.example.melodyhacker.tutor.Values.Url;
import com.example.melodyhacker.tutor.Model.GetSetRatting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by melodyhacker on 2/17/19.
 */

public class ListRatting extends AppCompatActivity implements ListRattingAdapter.ItemClickListener {
    RequestQueue requestQueue;
    Url url = new Url();
    DeCode deCode = new DeCode();
    ImageView login;
    EditText username, password;
    TextView register;
    String[] ar, ar2;
    FileLog token = new FileLog();
    ProgressDialog loading;
    ListRattingAdapter adapter_list;
    ArrayList<GetSetRatting> list;
    RecyclerView recyclerView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        list = new ArrayList<>();
        loadData();

    }

    ///////////////////////////////////////////////////////////////////////////////

    public void loadData() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url.list_tutor, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("list_tutor");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json = jsonArray.getJSONObject(i);

                        list.add(new GetSetRatting(
                                json.getString("R_ID"),
                                json.getString("R_FristName"),
                                json.getString("R_LastName"),
                                json.getString("R_URLIMG"),""));
                    }
                    setList(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Intent intent = new Intent(ListRatting.this, NoInternet.class);
                startActivity(intent);
                Toast.makeText(ListRatting.this, "Net Work Problem " + error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AbstractMethodError {
                Map<String, String> parameters = new HashMap<String, String>();
//                parameters.put("token", token.getToken(getApplication()));
                return parameters;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void setList(ArrayList<GetSetRatting> data_list) {
        adapter_list = new ListRattingAdapter(this, data_list);
        adapter_list.setClickListener(this);
        recyclerView.setAdapter(adapter_list);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, Ratting.class);
        intent.putExtra("id", list.get(position).getId());
        intent.putExtra("name", list.get(position).getName());
        intent.putExtra("last_name", list.get(position).getLast_name());
        intent.putExtra("img", list.get(position).getImage());
        startActivity(intent);

    }

}

