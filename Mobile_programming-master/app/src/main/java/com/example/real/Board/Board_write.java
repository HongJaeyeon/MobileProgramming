package com.example.real.Board;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.real.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Board_write extends AppCompatActivity implements Button.OnClickListener{

    EditText title,contents;
    Button submit,back;
    FirebaseFirestore Database;
    // Uid
    FirebaseAuth mAuth;
    //timestamp
    String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);

        // EditText 객체 참조
        title = (EditText)findViewById(R.id.title);
        contents=(EditText)findViewById(R.id.contents);

        //Button 객체 참조

        submit = (Button)findViewById(R.id.submit_btn);
        back = (Button)findViewById(R.id.back);

        submit.setOnClickListener(this);


    }
     // 글쓰는 메소드
    public void store(String title_val,String content_val){

        Database = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        Map<String,String> board = new HashMap<>();
        board.put("title",title_val);
        board.put("content",content_val);
        board.put("writer_uid",mAuth.getUid());
        board.put("Board_timestamp",timeStamp);



        Database.collection("Board")
                .add(board)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(),"성공적으로 저장되었습니다.",Toast.LENGTH_SHORT).show();

                    }
                });
    }


    //이벤트 리스너
    public void onClick(View v){
        switch (v.getId()){
            case R.id.submit_btn:
                store(title.getText().toString(),contents.getText().toString()); //firebase로 전송
                finish();
                break;
        }

    }


}
