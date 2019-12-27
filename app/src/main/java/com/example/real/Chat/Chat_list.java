package com.example.real.Chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.real.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chat_list extends AppCompatActivity {

    //버튼
    private Button GPS_SET,To_main;

    //리스트뷰
    private ListView chat_list;

    //firebase 객체
    FirebaseFirestore Data;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        //객체 참조
        chat_list = (ListView)findViewById(R.id.chat_list);

        Data = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        //나의 아이디
        String my_id = mAuth.getUid();

        //자신의 아이디가 검색되는 것으로만 쿼리 후 리스트뷰에 띄웁니다.



















    }//end of oncreate



    // firestore에서 배열을 attender_list를 뽑아온 후 자신의 uid가 포함되어있는지 확인하는 메소드 입니다.
    // 그리고 자신의 아이디가 있으면 document에  {UID : "in"} 의 쌍으로 저장됩니다. (쿼리를 위해)      --> Fragment에서 해결했습니다.

    /*private void check_my_id(){
        Data = FirebaseFirestore.getInstance();
        CollectionReference colref = Data.collection("Chat");

        colref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (QueryDocumentSnapshot document :task.getResult()){
                    List<String> attneder_list = (ArrayList<String>)document.get("attender_list");
                    Map<String,String> for_save = new HashMap<>();
                    String ID = document.getId();


                    Toast.makeText(getApplicationContext(),attneder_list.get(0),Toast.LENGTH_SHORT).show();


                   if(attneder_list.contains(mAuth.getUid())){

                        for_save.put(mAuth.getUid(),"In");
                        Data.collection("Chat").document(ID).set(for_save);

                    }



                }
            }
        });

    }*///end of method                --> 단점 : 문서의 수가 많아지면 체크하는데 시간이 너무 많이 걸림. 임시책에 불과








}//end of class
