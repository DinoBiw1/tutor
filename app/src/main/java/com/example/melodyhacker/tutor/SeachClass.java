package com.example.melodyhacker.tutor;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.melodyhacker.tutor.Adapter.ListAreaAdapter;
import com.example.melodyhacker.tutor.Values.City;
import com.example.melodyhacker.tutor.Values.Class;
import com.example.melodyhacker.tutor.Values.Degree;
import com.example.melodyhacker.tutor.Values.Url;
import com.example.melodyhacker.tutor.Model.GetSetArea;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SeachClass extends AppCompatActivity implements ListAreaAdapter.ItemClickListener {
    City city = new City();
    RequestQueue requestQueue;
    Url url = new Url();
    ListAreaAdapter adapter_list;
    ArrayList<GetSetArea> arrayList;
    ImageView seach, ok;
    TextView name_class;
    RecyclerView recyclerView;
    ArrayList<GetSetArea> list;
    Dialog dialog;
    String name, degree, area, clash, etc;
    FileLog token = new FileLog();
    Degree degree_list = new Degree();
    Class classs = new Class();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seach_activity);
        final Spinner spinnerAdapterClass = (Spinner) findViewById(R.id.seach_class);
        final Spinner spinnerAdapterCity = (Spinner) findViewById(R.id.seach_city);
        final Spinner spinnerAdapterDegree = (Spinner) findViewById(R.id.seach_degree);
        final ArrayAdapter<String> adapter_city = new ArrayAdapter<String>(this, R.layout.item_seach_city, city.listCity());
        final ArrayAdapter<String> adapter_degree = new ArrayAdapter<String>(this, R.layout.item_seach_degree, degree_list.listDegree());
        final ArrayAdapter<String> adapter_class = new ArrayAdapter<String>(this, R.layout.item_seach_class, classs.listClass());
        spinnerAdapterClass.setAdapter(adapter_class);
        spinnerAdapterCity.setAdapter(adapter_city);
        spinnerAdapterDegree.setAdapter(adapter_degree);
        dialog = new Dialog(SeachClass.this);
        arrayList = new ArrayList<>();
        list = new ArrayList<>();
        dialog.setContentView(R.layout.dialog_req_activity);
        ok = (ImageView) dialog.findViewById(R.id.ok);
        name_class = (TextView) dialog.findViewById(R.id.name);
        seach = (ImageView) findViewById(R.id.seach);
        seach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.list_activity);
                recyclerView = findViewById(R.id.list);
                findData(spinnerAdapterCity.getSelectedItem().toString(),
                        spinnerAdapterClass.getSelectedItem().toString(),
                        spinnerAdapterDegree.getSelectedItem().toString()
                );
                arrayList = new ArrayList<>();
                list = new ArrayList<>();
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                Toast.makeText(SeachClass.this, spinnerAdapterClass.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                putData();
            }
        });
    }

    public void findData(final String area, final String classs, final String degree) {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url.find_area, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("list_class");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json = jsonArray.getJSONObject(i);
                        list.add(new GetSetArea(
                                json.getString("Op_NameCourse"),
                                json.getString("Dis_Name"),
                                json.getString("Op_Price"),
                                json.getString("C_Name"),
                                json.getString("R_Line"),
                                json.getString("R_Email"),
                                json.getString("R_Phon"),
                                json.getString("Op_Description"),
                                json.getString("R_FristName"),
                                json.getString("R_LastName"),
                                json.getString("R_URLIMG")));
                    }

                    setList(list);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Intent intent = new Intent(SeachClass.this, SeachClass.class);
                startActivity(intent);
                Toast.makeText(SeachClass.this, "Net Work Problem " + error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AbstractMethodError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("area", area);
                parameters.put("class", classs);
                parameters.put("degree", degree);
                return parameters;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void setList(ArrayList<GetSetArea> data_list) {
        adapter_list = new ListAreaAdapter(this, data_list);
        adapter_list.setClickListener(this);
        recyclerView.setAdapter(adapter_list);
    }

    @Override
    public void onItemClick(View view, int position) {
        name = list.get(position).getNameClass();
        degree = list.get(position).getDegree();
        area = list.get(position).getArea();
        clash = list.get(position).getClash();
        etc = list.get(position).getEtc();
        name_class.setText(name);
        dialog.show();

    }


    public void putData() {

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url.req, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("\nsuccss")) {
                    Intent intent = new Intent(SeachClass.this, Login.class);
                    startActivity(intent);
                    Toast.makeText(SeachClass.this, response, Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(SeachClass.this, response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Intent intent = new Intent(SeachClass.this, SeachClass.class);
                startActivity(intent);
                Toast.makeText(SeachClass.this, "Net Work Problem " + error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AbstractMethodError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("token", token.getToken(getApplication()));
                parameters.put("name_class", name);
                parameters.put("area", area);
                parameters.put("clash", clash);
                parameters.put("degree", degree);
                parameters.put("etc", etc);

                return parameters;
            }
        };
        requestQueue.add(stringRequest);

    }
}