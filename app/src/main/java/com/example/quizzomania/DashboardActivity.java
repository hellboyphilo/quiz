package com.example.quizzomania;

import static android.speech.tts.TextToSpeech.ERROR;
import static android.speech.tts.TextToSpeech.QUEUE_FLUSH;
import static com.example.quizzomania.MainActivity.list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class DashboardActivity extends AppCompatActivity {

    private TextView questions;
    private TextView question;

    private AppCompatButton option1, option2, option3, option4;
    private Timer quizTimer;
    private int totalTimeInMins = 1;
    private int seconds = 0;
    private  List<Modelclass> questionslist = new ArrayList<>();
    private int currentQuestionPosition=0;
    private AppCompatButton nextBtn;
    private String selectedOptionByUser = "";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        final ImageView backBtn = findViewById(R.id.backBtn);
        final String getSelectedTopicName = getIntent().getStringExtra("category");
        final TextView timer = findViewById(R.id.timer);
        questions = findViewById(R.id.questions);
        question = findViewById(R.id.question);

        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        nextBtn = findViewById(R.id.nextBtn);

        final TextView selectedTopicName = findViewById(R.id.topicName);

        selectedTopicName.setText(getSelectedTopicName);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quizzomania-4a40f-default-rtdb.firebaseio.com/");
        ProgressDialog progressDialog = new ProgressDialog(DashboardActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.child(getSelectedTopicName).getChildren()){
                    final String getQuestion = dataSnapshot.child("question").getValue(String.class);
                    final String getOption1 = dataSnapshot.child("option1").getValue(String.class);
                    final String getOption2 = dataSnapshot.child("option2").getValue(String.class);
                    final String getOption3 = dataSnapshot.child("option3").getValue(String.class);
                    final String getOption4 = dataSnapshot.child("option4").getValue(String.class);
                    final String getAnswer = dataSnapshot.child("answer").getValue(String.class);

                    Modelclass Questionslist = new Modelclass(getQuestion,getOption1,getOption2,getOption3,getOption4,getAnswer,"");
                    questionslist.add(Questionslist);
                }
                progressDialog.hide();
                questions.setText((currentQuestionPosition+1)+"/"+questionslist.size());
                question.setText(questionslist.get(0).getQuestion());
                option1.setText(questionslist.get(0).getOption1());
                option2.setText(questionslist.get(0).getOption2());
                option3.setText(questionslist.get(0).getOption3());
                option4.setText(questionslist.get(0).getOption4());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        startTimer(timer);

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedOptionByUser.isEmpty()){
                    selectedOptionByUser=option1.getText().toString();
                    option1.setBackgroundResource(R.drawable.round_back_red10);
                    option1.setTextColor(Color.WHITE);

                    revealAnswer();

                    questionslist.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
                }
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedOptionByUser.isEmpty()){
                    selectedOptionByUser=option2.getText().toString();
                    option2.setBackgroundResource(R.drawable.round_back_red10);
                    option2.setTextColor(Color.WHITE);

                    revealAnswer();

                    questionslist.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
                }
            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedOptionByUser.isEmpty()){
                    selectedOptionByUser=option3.getText().toString();
                    option3.setBackgroundResource(R.drawable.round_back_red10);
                    option3.setTextColor(Color.WHITE);

                    revealAnswer();

                    questionslist.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
                }
            }
        });

        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedOptionByUser.isEmpty()){
                    selectedOptionByUser=option4.getText().toString();
                    option4.setBackgroundResource(R.drawable.round_back_red10);
                    option4.setTextColor(Color.WHITE);

                    revealAnswer();

                    questionslist.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedOptionByUser.isEmpty()){
                    Toast.makeText(DashboardActivity.this, "Please Select an option", Toast.LENGTH_SHORT).show();
                }
                else{
                    changeNextQuestion();
                }
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quizTimer.purge();
                quizTimer.cancel();

                startActivity(new Intent(DashboardActivity.this, category.class));
                finish();
            }
        });
    }


    private void changeNextQuestion(){
        currentQuestionPosition++;
        if((currentQuestionPosition+1)==questionslist.size()){
            nextBtn.setText("Submit Quiz");
        }
        if(currentQuestionPosition < questionslist.size()){
            selectedOptionByUser="";

            option1.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            option1.setTextColor(Color.parseColor("#1F6BB8"));

            option2.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            option2.setTextColor(Color.parseColor("#1F6BB8"));

            option3.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            option3.setTextColor(Color.parseColor("#1F6BB8"));

            option4.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            option4.setTextColor(Color.parseColor("#1F6BB8"));

            questions.setText((currentQuestionPosition+1)+"/"+questionslist.size());
            question.setText(questionslist.get(currentQuestionPosition).getQuestion());
            option1.setText(questionslist.get(currentQuestionPosition).getOption1());
            option2.setText(questionslist.get(currentQuestionPosition).getOption2());
            option3.setText(questionslist.get(currentQuestionPosition).getOption3());
            option4.setText(questionslist.get(currentQuestionPosition).getOption4());
        }
        else{
            final String getUsername = getIntent().getStringExtra("username");
            Intent intent = new Intent(getApplicationContext(),WonActivity.class);

            int totalnumberofquestions = questionslist.size();
            intent.putExtra("correct",getCorrectAnswers());
            intent.putExtra("incorrect",getInCorrectAnswers());
            intent.putExtra("total",totalnumberofquestions);
            intent.putExtra("username",getUsername);
            startActivity(intent);
            finish();
        }
    }

    public void startTimer(TextView timerTextView) {
        quizTimer = new Timer();
        quizTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (seconds == 0) {
                    totalTimeInMins--;
                    seconds = 59;
                } else if (seconds == 0 && totalTimeInMins == 0) {
                    quizTimer.purge();
                    quizTimer.cancel();

                    Toast.makeText(DashboardActivity.this, "Time Over", Toast.LENGTH_SHORT).show();
                    final String getUsername = getIntent().getStringExtra("username");
                    Intent intent = new Intent(DashboardActivity.this, WonActivity.class);
                    intent.putExtra("correct", getCorrectAnswers());
                    intent.putExtra("incorrect", getInCorrectAnswers());
                    intent.putExtra("username",getUsername);
                    startActivity(intent);
                    finish();
                } else {
                    seconds--;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String finalMinutes = String.valueOf(totalTimeInMins);
                        String finalSeconds = String.valueOf(seconds);

                        if (finalMinutes.length() == 1) {
                            finalMinutes = "0" + finalMinutes;
                        }
                        if (finalSeconds.length() == 1) {
                            finalSeconds = "0" + finalSeconds;
                        }
                        timerTextView.setText(finalMinutes + ":" + finalSeconds);
                    }
                });
            }
        }, 1000, 1000);

    }

    private int getCorrectAnswers() {
        int correctAnswers = 0;
        for (int i = 0; i < questionslist.size(); i++) {
            final String getUserSelectedAnswer = questionslist.get(i).getUserSelectedAnswer();
            final String getAnswer = questionslist.get(i).getAnswer();

            if (getUserSelectedAnswer.equals(getAnswer)) {
                correctAnswers++;
            }
        }
        return correctAnswers;
    }

    private int getInCorrectAnswers() {
        int correctAnswers = 0;
        for (int i = 0; i < questionslist.size(); i++) {
            final String getUserSelectedAnswer = questionslist.get(i).getUserSelectedAnswer();
            final String getAnswer = questionslist.get(i).getAnswer();

            if (!getUserSelectedAnswer.equals(getAnswer)) {
                correctAnswers++;
            }
        }
        return correctAnswers;
    }

    @Override
    public void onBackPressed() {
        quizTimer.purge();
        quizTimer.cancel();

        startActivity(new Intent(DashboardActivity.this, category.class));
        finish();
    }

    private void revealAnswer(){
        final String getAnswer = questionslist.get(currentQuestionPosition).getAnswer();

        if(option1.getText().toString().equals(getAnswer)){
                option1.setBackgroundResource(R.drawable.round_back_green10);
                option1.setTextColor(Color.WHITE);
        }
        else if(option2.getText().toString().equals(getAnswer)){
            option2.setBackgroundResource(R.drawable.round_back_green10);
            option2.setTextColor(Color.WHITE);
        }
        else if(option3.getText().toString().equals(getAnswer)){
            option3.setBackgroundResource(R.drawable.round_back_green10);
            option3.setTextColor(Color.WHITE);
        }
        else if(option4.getText().toString().equals(getAnswer)){
            option4.setBackgroundResource(R.drawable.round_back_green10);
            option4.setTextColor(Color.WHITE);
        }
    }
}