package com.example.real.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.real.Main.Main_with_login;
import com.example.real.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity implements Button.OnClickListener{

    Button login,Main,Signup;
    EditText Email,Pw;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //Button
        login = (Button)findViewById(R.id.login_btn);
        Main = (Button)findViewById(R.id.Main_btn);
        Signup=(Button)findViewById(R.id.login_to_signup);

        // Edittext
        Email = (EditText)findViewById(R.id.user_email);
        Pw=(EditText)findViewById(R.id.user_pw);

        //Firebase instance
        mAuth = FirebaseAuth.getInstance();

        //Button Listener
        login.setOnClickListener(this);
        Main.setOnClickListener(this);




    }


    private void Login(String email, String pw)
    {
        mAuth.signInWithEmailAndPassword(email,pw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"로그인에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                            Intent to_login_main = new Intent(getApplicationContext(), Main_with_login.class);
                            startActivity(to_login_main);

                    } else {
                            Toast.makeText(getApplicationContext(),"로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();


                        }
                }

        });
    }


    public void  onClick(View v){
        switch(v.getId()){
            case R.id.login_btn:
                Login(Email.getText().toString(),Pw.getText().toString());
                break;

            case R.id.Main_btn:
                finish();
                break;


        }


    }





}



