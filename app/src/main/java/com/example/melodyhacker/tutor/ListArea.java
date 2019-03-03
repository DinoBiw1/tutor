package com.example.melodyhacker.tutor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.melodyhacker.tutor.Adapter.ListClassAdapter;
import com.example.melodyhacker.tutor.Values.Url;
import com.example.melodyhacker.tutor.Model.GetSetClass;

import java.util.ArrayList;

public class ListArea extends AppCompatActivity implements ListClassAdapter.ItemClickListener {
    RequestQueue requestQueue;
    Url url = new Url();
    ListClassAdapter adapter;
    ArrayList<GetSetClass> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        arrayList = new ArrayList<>();
        ArrayList<GetSetClass> list = new ArrayList<>();
        String city = new String();
//        loadData(city);
        RecyclerView recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ListClassAdapter(this, list);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }
//
//    public void loadData(final String city) {
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url.list_class, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AbstractMethodError {
//                Map<String, String> parameters = new HashMap<String, String>();
//                parameters.put("area", city);
//                return parameters;
//            }
//        };
//        requestQueue.add(stringRequest);
//
//    }
//
//
    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }


}