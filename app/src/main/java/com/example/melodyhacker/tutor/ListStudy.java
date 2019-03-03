package com.example.melodyhacker.tutor;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.melodyhacker.tutor.Adapter.ListStudyAdapter;
import com.example.melodyhacker.tutor.Tools.DeCode;
import com.example.melodyhacker.tutor.Values.City;
import com.example.melodyhacker.tutor.Values.Url;
import com.example.melodyhacker.tutor.Model.GetSetStudy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by melodyhacker on 2/17/19.
 */

public class ListStudy  extends AppCompatActivity implements ListStudyAdapter.ItemClickListener {
    City city = new City();
    String status;
    RequestQueue requestQueue;
    Url url = new Url();
    ListStudyAdapter adapter_list;
    ArrayList<GetSetStudy> arrayList;
    RecyclerView recyclerView;
    ArrayList<GetSetStudy> list;
    FileLog token = new FileLog();
    String[] ar, ar2;
    DeCode deCode = new DeCode();
    Dialog dialog;
    TextView name;
    ImageView del;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
        arrayList = new ArrayList<>();
        list = new ArrayList<>();
        recyclerView = findViewById(R.id.list);
        String data_token = token.getToken(getApplication());
        ar = deCode.getHash(data_token).split("----");
        ar2 = ar[1].split("_");
        findData(ar2[1]);
        arrayList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        dialog = new Dialog(ListStudy.this);
        dialog.setContentView(R.layout.dialog_end_activity);
        del = (ImageView) dialog.findViewById(R.id.del);
        name = (TextView) dialog.findViewById(R.id.name);
//        del.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.cancel();
////                delData(status);
//            }
//        });

    }

    public void findData(final String id_tutor) {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url.list_study, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(ListStudy.this, response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("list_study");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json = jsonArray.getJSONObject(i);
//                        String name_class,
//                        String degree_class,
//                        String area_class,
//                        String time,
//                        String position,
//                        String clash,
//                        String date,
//                        String status

                        list.add(new GetSetStudy(
                                json.getString("An_Subjects"),
                                json.getString("An_Class"),
                                json.getString("An_District"),
                                json.getString("An_Time"),
                                json.getString("AN_Position"),
                                json.getString("An_Price"),
                                json.getString("An_Date"),
                                json.getString("AN_Status")
                          ));
                    }
                    setList(list);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Intent intent = new Intent(ListStudy.this, NoInternet.class);
                startActivity(intent);
                Toast.makeText(ListStudy.this, "Net Work Problem " + error.toString(), Toast.LENGTH_SHORT).show();

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

    public void setList(ArrayList<GetSetStudy> data_list) {
        adapter_list = new ListStudyAdapter(this, data_list);
        adapter_list.setClickListener(this);
        recyclerView.setAdapter(adapter_list);
    }

    @Override
    public void onItemClick(View view, int position) {
//        adapter_list.getItem(position).toString()
//        Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
//        status = list.get(position).getNameClass();
//        name.setText(status);
//        dialog.show();
    }


//    public void delData(final String name) {
//        requestQueue = Volley.newRequestQueue(getApplicationContext());
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url.del_class, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Intent intent = new Intent(ListStudy.this, Login.class);
//                startActivity(intent);
//                Toast.makeText(ListStudy.this, response, Toast.LENGTH_SHORT).show();
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Intent intent = new Intent(ListStudy.this, NoInternet.class);
//                startActivity(intent);
//                Toast.makeText(ListStudy.this, "Net Work Problem " + error.toString(), Toast.LENGTH_SHORT).show();
//
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AbstractMethodError {
//                Map<String, String> parameters = new HashMap<String, String>();
//                parameters.put("token", token.getToken(getApplication()));
//                parameters.put("name_class", name);
//
//                return parameters;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }
}