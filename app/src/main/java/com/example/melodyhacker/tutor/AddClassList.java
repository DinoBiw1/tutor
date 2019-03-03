package com.example.melodyhacker.tutor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.example.melodyhacker.tutor.Adapter.ListClassAdapter;
import com.example.melodyhacker.tutor.Values.City;
import com.example.melodyhacker.tutor.Values.Class;
import com.example.melodyhacker.tutor.Values.Degree;
import com.example.melodyhacker.tutor.Values.Url;
import com.example.melodyhacker.tutor.Model.GetSetClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by melodyhacker on 2/10/19.
 */

public class AddClassList extends AppCompatActivity {
    Class classs = new Class();
    City city = new City();
    Degree degree = new Degree();
    String status;
    TextView name, clash, etc;
    RequestQueue requestQueue;
    Url url = new Url();
    ListClassAdapter adapter_list;
    ArrayList<GetSetClass> arrayList;
    ImageView save;
    RecyclerView recyclerView;
    ArrayList<GetSetClass> list;
    Spinner area_spin, class_spin, degree_spin;
    FileLog token = new FileLog();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_class_activity);
        name = (TextView) findViewById(R.id.name);
        clash = (TextView) findViewById(R.id.clash);
        etc = (TextView) findViewById(R.id.etc);
        area_spin = (Spinner) findViewById(R.id.area);
        class_spin = (Spinner) findViewById(R.id.classs);
        degree_spin = (Spinner) findViewById(R.id.degree);
        save = (ImageView) findViewById(R.id.save);
        arrayList = new ArrayList<>();
        list = new ArrayList<>();
        final ArrayAdapter<String> area_adapter = new ArrayAdapter<String>(this, R.layout.item_seach_city, city.listCity());
        final ArrayAdapter<String> degree_adapter = new ArrayAdapter<String>(this, R.layout.item_seach_city, classs.listClass());
        final ArrayAdapter<String> class_adpter = new ArrayAdapter<String>(this, R.layout.item_seach_city, degree.listDegree());
        area_spin.setAdapter(area_adapter);
        class_spin.setAdapter(degree_adapter);
        degree_spin.setAdapter(class_adpter);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                putData(name.getText().toString(),
                        class_spin.getSelectedItem().toString(),
                        degree_spin.getSelectedItem().toString(),
                        area_spin.getSelectedItem().toString(),
                        clash.getText().toString(),
                        etc.getText().toString());

            }
        });


    }

    public void putData(final String name_class,
                        final String classs,
                        final String degree,
                        final String area,
                        final String clash,
                        final String etc) {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url.add_class, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                debug(response);
                Toast.makeText(AddClassList.this, response, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AddClassList.this, ProfileTutor.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Intent intent = new Intent(AddClassList.this, NoInternet.class);
                startActivity(intent);
                Toast.makeText(AddClassList.this, "Net Work Problem " + error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AbstractMethodError {
                Map<String, String> parameters = new HashMap<String, String>();
//                $id',$id_tutor,'$name','$class','$degree','$area','$clash','$etc','0')
                parameters.put("token", token.getToken(getApplication()));
                parameters.put("name", name_class);
                parameters.put("class", classs);
                parameters.put("degree", degree);
                parameters.put("area", area);
                parameters.put("clash", clash);
                parameters.put("etc", etc);
                return parameters;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void debug(String response) {
//        etc.setText(response);
    }
}
