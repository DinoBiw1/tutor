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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by melodyhacker on 1/31/19.
 */

public class Register extends AppCompatActivity {
    ImageView student, tutor, save, image_profile, image_upload;
    EditText name, last_name, address, tel, etc, study, username, password,where, email, faculty, line;
    RequestQueue requestQueue;
    String status, image;
    Bitmap bitmap;
    Url url = new Url();
    private int PICK_IMAGE_REQUEST = 1;
    Gender gender = new Gender();
    Spinner gender_spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main_activity);
        student = (ImageView) findViewById(R.id.student);
        tutor = (ImageView) findViewById(R.id.tutor);
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = "student";
                setStatus();

            }
        });
        tutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = "tutor";
                setStatus();
            }
        });

    }

    private void setStatus() {
        setContentView(R.layout.register_activity);
        image_profile = (ImageView) findViewById(R.id.img_profile);
        image_upload = (ImageView) findViewById(R.id.upload);
        save = (ImageView) findViewById(R.id.save);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        name = (EditText) findViewById(R.id.name);
        last_name = (EditText) findViewById(R.id.last_name);
        tel = (EditText) findViewById(R.id.tel);
        etc = (EditText) findViewById(R.id.etc);
        address = (EditText) findViewById(R.id.address);
        where = (EditText) findViewById(R.id.where);
        email = (EditText) findViewById(R.id.email);
        line = (EditText) findViewById(R.id.line);
        faculty = (EditText) findViewById(R.id.faculty);
        final ArrayAdapter<String> gender_adapter = new ArrayAdapter<String>(this, R.layout.item_seach, gender.listGender());
        gender_spin = (Spinner) findViewById(R.id.gender);
        gender_spin.setAdapter(gender_adapter);
        image_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putData();
            }
        });
    }

    ///////////////////////////////////////////////////////////////////////////////
    private void putData() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final String status_req = "Not Register";
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url.register,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        s = s.replace("\n", "");
                        loading.dismiss();
                        Toast.makeText(Register.this, s, Toast.LENGTH_SHORT).show();
                        if (s.equals("succss")) {
                            Toast.makeText(Register.this, "Try Login ^-^", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Register.this, Login.class);
                            startActivity(intent);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        loading.dismiss();
                        Toast.makeText(Register.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                Integer hash = 0;
                hash = image.hashCode();
//                params.put("image", hash.toString());
                String debug = gender_spin.getSelectedItem().toString();
                params.put("image", image);
                params.put("username", username.getText().toString());
                params.put("password", password.getText().toString());
                params.put("area", where.getText().toString());
                params.put("name", name.getText().toString());
                params.put("last_name", last_name.getText().toString());
                params.put("address", address.getText().toString());
                params.put("tel", tel.getText().toString());
                params.put("etc", etc.getText().toString());
                params.put("where", where.getText().toString());
                params.put("faculty", faculty.getText().toString());
                params.put("e_mail", email.getText().toString());
                params.put("line", line.getText().toString());
                params.put("gender", gender_spin.getSelectedItem().toString());
                params.put("status", status);
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