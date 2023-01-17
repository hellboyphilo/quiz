package com.example.quizzomania;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class loginactivity extends AppCompatActivity {



    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quizzomania-4a40f-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loginactivity);

        Button loginbtn = findViewById(R.id.loginbtn);
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        TextView registernowbtn = findViewById(R.id.registernowbtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String usernametxt = username.getText().toString();
                final String passwordtxt = password.getText().toString();

                if(usernametxt.isEmpty() || passwordtxt.isEmpty())
                {
                    Toast.makeText(loginactivity.this, "Please Enter your mobile or password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(usernametxt)){
                                final String getpassword = snapshot.child(usernametxt).child("password").getValue(String.class);

                                if(getpassword.equals(passwordtxt))
                                {
                                    Toast.makeText(loginactivity.this, "Logged in Sucessfully", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(loginactivity.this,category.class);
                                    intent.putExtra("UserName",usernametxt);
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    Toast.makeText(loginactivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();   
                                }
                            }
                            else
                            {
                                Toast.makeText(loginactivity.this, "Wrong username", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        registernowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginactivity.this,register.class));
            }
        });


    }
}