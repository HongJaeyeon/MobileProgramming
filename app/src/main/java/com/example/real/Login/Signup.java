package com.example.real.Login;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.real.Main.MainActivity;
import com.example.real.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


import java.util.regex.Pattern;

public class Signup extends AppCompatActivity implements Button.OnClickListener{
    private static final String TAG = MainActivity.class.getSimpleName(); // TAG - Fragement 해결
    private static FirebaseAuth mAuth;
    private int PressCheck = 0;


    //비밀번호 정규식
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");

    EditText User_want_email,User_want_pw,User_want_pw_check,User_name;
    Button to_Main,Sign_up,Email_check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        mAuth = FirebaseAuth.getInstance();

        //Button 객체

       to_Main = (Button)findViewById(R.id.Main_btn);
       Sign_up = (Button)findViewById(R.id.Signup_btn);
       Email_check = (Button)findViewById(R.id.Email_check_btn);

        //Edittext 객체
       User_want_email = (EditText)findViewById(R.id.User_want_email);
       User_want_pw = (EditText)findViewById(R.id.User_want_pw);
       User_want_pw_check = (EditText)findViewById(R.id.User_want_pw_check);
       User_name = (EditText)findViewById(R.id.User_name);


        //리스너 등록
        to_Main.setOnClickListener(this);
        Sign_up.setOnClickListener(this);
        Email_check.setOnClickListener(this);

    } // OnCreate End

    //회원가입

    private void CreateUser(String email, String pw){
        mAuth.createUserWithEmailAndPassword(email,pw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //회원가입 성공
                            Toast.makeText(getApplicationContext(),"회원가입에 성공하셨습니다.",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"회원가입에 실패하셨습니다.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }//End of CreateUser

    private boolean isValidEmail(){
        if(User_want_email.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"공백이 있습니다.",Toast.LENGTH_SHORT).show();
            return false; // 공백 여부
        } else if (!Patterns.EMAIL_ADDRESS.matcher(User_want_email.getText().toString()).matches()){
            Toast.makeText(getApplicationContext(),"올바르지 않은 이메일 형식입니다.",Toast.LENGTH_SHORT).show();
            return false; // 이메일형식 여부
        } else {
            Toast.makeText(getApplicationContext(),"사용가능한 이메일 입니다.",Toast.LENGTH_SHORT).show();
            return true;
        }
    }



    public void onClick(View v){

        switch(v.getId()){
            case R.id.Email_check_btn:
                if(isValidEmail()){
                    PressCheck=1;
                    break;
                } else {
                    break;
                }


            case R.id.Signup_btn:

                if(PressCheck==0){
                    Toast.makeText(getApplicationContext(),"이메일 체크를 하세요",Toast.LENGTH_SHORT).show();
                    break;
                }
                if(!User_want_pw.getText().toString().equals(User_want_pw_check.getText().toString())){
                    Toast.makeText(getApplicationContext(),"비밀번호를 다르게 입력하셨습니다.",Toast.LENGTH_SHORT).show();
                    break;
                }
                if(PressCheck==1 && User_want_pw.getText().toString().equals(User_want_pw_check.getText().toString())) {
                    CreateUser(User_want_email.getText().toString(), User_want_pw.getText().toString());
                    Toast.makeText(getApplicationContext(),"성공적으로 가입 되었습니다." ,Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                }


            case R.id.Main_btn:
                finish();
                break;
        }

    }









}






