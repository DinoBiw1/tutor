package com.example.melodyhacker.tutor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
//        FileLog token = new FileLog();
//        token.setToken("1231424",getApplicationContext());
//        String read = token.getToken(getApplication());
//        Toast.makeText(this, read, Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(MainActivity.this,Login.class);
        startActivity(intent);
    }
}

