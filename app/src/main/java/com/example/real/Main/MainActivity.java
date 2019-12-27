package com.example.real.Main;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.real.Login.Signup;
import com.example.real.Login.login;
import com.example.real.R;


public class MainActivity extends AppCompatActivity implements Button.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button Signup = (Button)findViewById(R.id.Signup_btn);
        Button Login = (Button)findViewById(R.id.Login_btn);
        Button Match = (Button)findViewById(R.id.Matching_btn);
        Button Board = (Button)findViewById(R.id.Board_btn);

        Signup.setOnClickListener(this);
        Login.setOnClickListener(this);
        Match.setOnClickListener(this);
        Board.setOnClickListener(this);




    }
    public void onClick(View v){
        switch(v.getId()){
            case R.id.Signup_btn:
                Intent to_signup = new Intent(getApplicationContext(), Signup.class);
                startActivity(to_signup);
                break;


            case R.id.Login_btn:
                Intent to_Login = new Intent(getApplicationContext(), login.class);
                startActivity(to_Login); // 인텐트 객체를 하나만 만들어서 해도 되는가?
                break;


            case R.id.Board_btn:
                Toast.makeText(getApplicationContext(), "로그인 후 사용가능합니다.", Toast.LENGTH_SHORT).show();
                break;

            case R.id.Matching_btn:
                Toast.makeText(getApplicationContext(), "로그인 후 사용가능합니다.", Toast.LENGTH_SHORT).show();
                break;


        }
    }


}




