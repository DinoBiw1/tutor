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
import com.example.melodyhacker.tutor.Adapter.ListClassAdapter;
import com.example.melodyhacker.tutor.Tools.DeCode;
import com.example.melodyhacker.tutor.Values.City;
import com.example.melodyhacker.tutor.Values.Url;
import com.example.melodyhacker.tutor.Model.GetSetClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListClass extends AppCompatActivity implements ListClassAdapter.ItemClickListener {
    City city = new City();
    String status;
    RequestQueue requestQueue;
    Url url = new Url();
    ListClassAdapter adapter_list;
    ArrayList<GetSetClass> arrayList;
    ImageView seach;
    RecyclerView recyclerView;
    ArrayList<GetSetClass> list;
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
//        arrayList = new ArrayList<>();
//        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        dialog = new Dialog(ListClass.this);
        dialog.setContentView(R.layout.dialog_end_activity);
        del = (ImageView) dialog.findViewById(R.id.del);
        name = (TextView) dialog.findViewById(R.id.name);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                delData(status);
            }
        });

    }

    public void findData(final String id_tutor) {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url.list_class, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("list_class");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json = jsonArray.getJSONObject(i);
//                                "Op_ID": "OP0006",
//                                    "R_ID": "1",
//                                    "Op_NameCourse": "",
//                                    "Sub_name": "วิทยาศาสตร์",
//                                    "C_Name": "ประฐมศึกษา",
//                                    "Dis_Name": "เมือง",
//                                    "Op_Price": "0",
//                                    "Op_Description": "",
//                                    "Op_Status": "1",
//                                    "R_FristName": "สุกานดา",
//                                    "R_LastName": "ใจแก้ว",
//                                    "R_Username": "tutor",
//                                    "R_Password": "password",
//                                    "R_DisName": "เมือง",
//                                    "R_URLIMG": "http://localhost/webtutor2/img/a11fe593006587fb89559b68b06a996a.jpg",
//                                    "R_Phon": "0912345678",
//                                    "R_Line": "nn3011",
//                                    "R_Email": "sukanda6576@gmail.com",
//                                    "R_Status": "tutor",
//                                    "R_Gender": "หญิง",
//                                    "R_University": "ราชภัฏบุรีรัมย์",
//                                    "R_Faculty": "วิทยาศาสตร์",
//                                    "R_Description": "วิทยาการคอมพิวเตอร์",
//                                    "R_Date": "2019-02-09 00:54:59"
//                            }
                        list.add(new GetSetClass(
                                json.getString("Op_NameCourse"),
                                json.getString("R_DisName"),
                                json.getString("Op_Price"),
                                json.getString("C_Name"),
                                "",
                                json.getString("R_Email"),
                                json.getString("R_Phon"),
                                json.getString("R_Description"),
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
                Intent intent = new Intent(ListClass.this, NoInternet.class);
                startActivity(intent);
                Toast.makeText(ListClass.this, "Net Work Problem " + error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AbstractMethodError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("id_tutor", id_tutor);
                return parameters;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void setList(ArrayList<GetSetClass> data_list) {
        adapter_list = new ListClassAdapter(this, data_list);
        adapter_list.setClickListener(this);
        recyclerView.setAdapter(adapter_list);
    }

    @Override
    public void onItemClick(View view, int position) {
//        adapter_list.getItem(position).toString()
//        Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
        status = list.get(position).getNameClass();
        name.setText(status);
        dialog.show();
    }


    public void delData(final String name) {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url.del_class, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(ListClass.this, Login.class);
                startActivity(intent);
                Toast.makeText(ListClass.this, response, Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Intent intent = new Intent(ListClass.this, NoInternet.class);
                startActivity(intent);
                Toast.makeText(ListClass.this, "Net Work Problem " + error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AbstractMethodError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("token", token.getToken(getApplication()));
                parameters.put("name_class", name);

                return parameters;
            }
        };
        requestQueue.add(stringRequest);
    }
}