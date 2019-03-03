package com.example.melodyhacker.tutor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.melodyhacker.tutor.Values.Gender;
import com.example.melodyhacker.tutor.Values.Url;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by melodyhacker on 2/8/19.
 */

public class EditProfile extends AppCompatActivity {
    ImageView student, tutor, save, image_profile, image_upload;
    EditText name, last_name, address, tel, etc, email, line, faculty, where, password;
    TextView username;
    RequestQueue requestQueue;
    String status, image;
    FileLog token = new FileLog();
    Bitmap bitmap;
    Gender gender = new Gender();
    Url url = new Url();
    private int PICK_IMAGE_REQUEST = 1;
    Spinner gender_spin;
    ArrayAdapter<String> gender_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_activity);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        username = (TextView) findViewById(R.id.username);
//        password = (EditText) findViewById(R.id.password);
        name = (EditText) findViewById(R.id.name);
        last_name = (EditText) findViewById(R.id.last_name);
        gender_spin = (Spinner) findViewById(R.id.gender);
        address = (EditText) findViewById(R.id.address);
        tel = (EditText) findViewById(R.id.tel);
        etc = (EditText) findViewById(R.id.etc);
        email = (EditText) findViewById(R.id.email);
        line = (EditText) findViewById(R.id.line);
        where = (EditText) findViewById(R.id.where);
        faculty = (EditText) findViewById(R.id.faculty);
//        image_profile = (ImageView) findViewById(R.id.img_profile);
//        image_upload = (ImageView) findViewById(R.id.upload);
        save = (ImageView) findViewById(R.id.save);
        gender_adapter = new ArrayAdapter<String>(this, R.layout.item_seach, gender.listGender());
        gender_spin.setAdapter(gender_adapter);
//        image_upload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showFileChooser();
//            }
//        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putData();
            }
        });
        loadData();
    }

    private void loadData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url.profile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String[] ar = response.split("--");
                username.setText(ar[0]);
                name.setText(ar[1]);
                last_name.setText(ar[2]);
                if (ar[3] != null) {
                    int spinnerPosition = gender_adapter.getPosition(ar[3]);
                    gender_spin.setSelection(spinnerPosition);
                }
                tel.setText(ar[4]);
                line.setText(ar[5]);
                email.setText(ar[6]);
                address.setText(ar[7]);
                where.setText(ar[8]);
                faculty.setText(ar[9]);
                etc.setText(ar[10] + " >>>>> Date Register : " + ar[11]);
//                Picasso.get().load(url.path_mage + ar[12]).into(image_profile);
//                System.out.print(ar);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditProfile.this, " Try Login ^-^", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditProfile.this, Login.class);
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

    ///////////////////////////////////////////////////////////////////////////////
    private void putData() {
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url.edit_profile,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        s = s.replace("\n", "");
                        loading.dismiss();
                        Toast.makeText(EditProfile.this, s, Toast.LENGTH_SHORT).show();
                        if (s.equals("Chang Succss")) {
                            Toast.makeText(EditProfile.this, "Chang Succss Try Login ^-^", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditProfile.this, Login.class);
                            startActivity(intent);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        loading.dismiss();
                        Toast.makeText(EditProfile.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put("token", token.getToken(getApplication()));
                params.put("name", name.getText().toString());
                params.put("last_name", last_name.getText().toString());
                params.put("tel", tel.getText().toString());
                params.put("line", line.getText().toString());
                params.put("address", address.getText().toString());
                params.put("faculty", faculty.getText().toString());
                params.put("etc", etc.getText().toString());
                params.put("email", email.getText().toString());
                params.put("gender", gender_spin.getSelectedItem().toString());

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


///////////////////////////////////////////////////////////////////////////////

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String input = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return input;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                image = getStringImage(bitmap);
                image_profile.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}