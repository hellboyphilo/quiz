package com.example.quizzomania;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class category extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

    }

    public void categoryCoding(View view) {
        String username = getIntent().getStringExtra("UserName");
        String category = "coding";
        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        intent.putExtra("category", category);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    public void categoryGK(View view) {
        String username = getIntent().getStringExtra("UserName");
        String category = "gk";
        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        intent.putExtra("category", category);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    public void categorySports(View view) {
        String username = getIntent().getStringExtra("UserName");
        String category = "sports";
        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        intent.putExtra("category", category);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    public void categoryMovies(View view) {
        String username = getIntent().getStringExtra("UserName");
        String category = "movies";
        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        intent.putExtra("category", category);
        intent.putExtra("username",username);
        startActivity(intent);
    }
}