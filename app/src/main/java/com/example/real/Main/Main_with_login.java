package com.example.real.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.real.R;
import com.google.firebase.auth.FirebaseAuth;

public class Main_with_login extends AppCompatActivity implements Button.OnClickListener{



    private FirebaseAuth mAuth;
    private Button Logout,Board,Profile,Match;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_with_login);
        mAuth = FirebaseAuth.getInstance();



        Logout = (Button)findViewById(R.id.Logout_btn);
        Board = (Button)findViewById(R.id.Board_btn);
        Profile = (Button)findViewById(R.id.Profile_btn);
        Match = (Button)findViewById(R.id.Matching_btn);


        //리스너
        Logout.setOnClickListener(this);
        Board.setOnClickListener(this);
        Profile.setOnClickListener(this);
        Match.setOnClickListener(this);

        Toast.makeText(this, mAuth.getUid(), Toast.LENGTH_SHORT).show();
    }

    private void log_out (){
        mAuth.signOut();
    }



    public void onClick(View v){
        switch(v.getId()){

            case R.id.Logout_btn:
                log_out();
                Toast.makeText(getApplicationContext(), "성공적으로 로그아웃 하였습니다.", Toast.LENGTH_SHORT).show();
                finish();
                break;

            case R.id.Board_btn:
                Intent main_to_board = new Intent(getApplicationContext(), com.example.real.Board.Board.class);
                startActivity(main_to_board);
                break;

            case R.id.Matching_btn:
                Intent main_to_match = new Intent(getApplicationContext(),com.example.real.Chat.Chat_list.class);
                startActivity(main_to_match);
                break;


        }
    }









}



