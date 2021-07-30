package com.example.portfolio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseHelper = new DatabaseHelper(this);
    }

    public void btn_register(android.view.View view)
    {
        String name = ((EditText)findViewById( R.id.name)).getText().toString();
        String email = ((EditText)findViewById( R.id.email)).getText().toString();
        String password = ((EditText)findViewById( R.id.register_password)).getText().toString();
        if(databaseHelper.Insert(name,email,password))
        {
            Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,LoginScreen.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Error! Please Try later!", Toast.LENGTH_SHORT).show();
        }
       
    }
}