package com.example.melodyhacker.tutor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.melodyhacker.tutor.Values.Url;

/**
 * Created by melodyhacker on 2/13/19.
 */

public class NoInternet extends AppCompatActivity {
    ImageView re;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_internet_activity);
        ImageView re = (ImageView) findViewById(R.id.re);
        Toast.makeText(this, "Can not internet ", Toast.LENGTH_SHORT).show();
        re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NoInternet.this, Login.class);
                startActivity(intent);
            }
        });


//        try {
//
//            Thread.sleep(3000);
//            Intent intent = new Intent(NoInternet.this, Login.class);
//            startActivity(intent);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}