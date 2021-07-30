package com.example.portfolio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class LoginScreen extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        setTitle("Share & Care");

        databaseHelper = new DatabaseHelper(this);
    }

    public void btn_login(android.view.View view)
    {
        String email = ((EditText)findViewById( R.id.login_email)).getText().toString();
        String password = ((EditText)findViewById( R.id.register_password)).getText().toString();

        if (databaseHelper.CheckLogin(email,password))
        {
            Intent intent = new Intent(this,HomeScreen.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Incorrect Credentials", Toast.LENGTH_SHORT).show();
        }

    }
}