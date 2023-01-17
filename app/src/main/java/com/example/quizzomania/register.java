package com.example.quizzomania;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import papaya.in.sendmail.SendMail;

public class register extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quizzomania-4a40f-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText username = findViewById(R.id.username);
        final EditText email = findViewById(R.id.email);
        final EditText password = findViewById(R.id.password);
        final EditText repassword = findViewById(R.id.repassword);

        final Button registerbtn = findViewById(R.id.signupbtn);

        final TextView loginnowbtn = findViewById(R.id.loginnowbtn);

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String usernametxt = username.getText().toString();
                final String emailtxt = email.getText().toString();
                final String passwordtxt = password.getText().toString();
                final String repasswordtxt = repassword.getText().toString();

                if (usernametxt.isEmpty() || emailtxt.isEmpty() || passwordtxt.isEmpty()||repasswordtxt.isEmpty())
                {
                        Toast.makeText(register.this, "Please Fill all the Details", Toast.LENGTH_SHORT).show();
                }
                else if(!passwordtxt.equals(repasswordtxt)){
                    Toast.makeText(register.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(usernametxt)) {
                                username.setError("Username Already Exists");
                                Toast.makeText(register.this, "Username already Exists", Toast.LENGTH_SHORT).show();
                            } else {
                                databaseReference.child("users").child(usernametxt).child("email").setValue(emailtxt);
                                databaseReference.child("users").child(usernametxt).child("password").setValue(passwordtxt);

                                Toast.makeText(register.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                SendMail mail = new SendMail("quizzomania1@gmail.com","ucgjqqlgjykgceuj",
                                        emailtxt,"Congratulations, Registration Successfull",
                                        "Congratulations,\n"+usernametxt +" on successfully registering in Quizzomania..!!! \nYour Username is : "
                                +usernametxt +"\nYour Password is :"+passwordtxt);
                                mail.execute();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
        });
        loginnowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(register.this,loginactivity.class));
            }
        });
    }
}