package com.example.quizzomania;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WonActivity extends AppCompatActivity {
    Button btnShare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_won);
        btnShare =findViewById(R.id.shareBtn);

        final ImageView backBtn = findViewById(R.id.backBtn);
        final ImageView logoutBtn = findViewById(R.id.logoutBtn);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quizzomania-4a40f-default-rtdb.firebaseio.com/");

        final String correctans = String.valueOf(getIntent().getIntExtra("correct",0));
        final String incorrectans = String.valueOf(getIntent().getIntExtra("incorrect",0));
        final String totalquestions = String.valueOf(getIntent().getIntExtra("total",0));
        final String username = getIntent().getStringExtra("username");

        TextView correct = findViewById(R.id.correctanswers);
        TextView incorrect = findViewById(R.id.incorrectanswers);

        correct.setText(correctans);
        incorrect.setText(incorrectans);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String date = dateFormat.format(calendar.getTime());

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.child("users").child(username).child("scores").child("date").setValue(date);
                databaseReference.child("users").child(username).child("scores").child("correct").setValue(correctans);
                databaseReference.child("users").child(username).child("scores").child("Incorrect").setValue(incorrectans);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });



        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String shareMessage= "\nI Scored "+correctans+" out of "+totalquestions+" You can try this too\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    e.toString();
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WonActivity.this, category.class));
                finish();
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WonActivity.this,loginactivity.class));
                finish();
            }
        });
    }
}