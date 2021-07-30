package com.example.portfolio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Share & Care");
    }

    public void btn_register(android.view.View view)
    {
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
    }

    public void btn_login(android.view.View view)
    {
        Intent intent = new Intent(this,LoginScreen.class);
        startActivity(intent);
    }
}