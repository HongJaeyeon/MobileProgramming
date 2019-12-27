package com.example.real.Board;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.ListFragment;

import com.example.real.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import org.w3c.dom.Document;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.net.InterfaceAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import static android.content.ContentValues.TAG;

public class Board_Fragment extends Fragment {      // DialogFragment였음
    Button exit,Submit,Magam,attend,cancle;
    TextView Frg_title,Frg_content,attend_num;
    EditText Frg_comment;
    ListView comment_listview;
    FirebaseFirestore Data;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();//댓글 작성하는 사람의 uid를 넣을 것입니다 하하
    String Uid_for_comment_writer;

    // COMMENT_LIST
    ArrayList<Comment> Comment_item_list=new ArrayList<>();

    String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());          // 되긴되는데 한국 시간이 아닌듯 합니다.




    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle savedInstanceState){




        View view = inflater.inflate(R.layout.activity_board_fragment,container,false);

        Data = FirebaseFirestore.getInstance();
        attend_num = (TextView)view.findViewById(R.id.attend_num);

        //버튼
        exit = (Button) view.findViewById(R.id.exit);
        exit.setOnClickListener(buttonListener);

        Submit = (Button)view.findViewById(R.id.comment_submit);
        Submit.setOnClickListener(submit_btn_Listener);


        //마감 버튼 ---> Uid를 가지고 새로운 컬렉션 (Chat)에 각각 document로 저장.
        Magam = (Button)view.findViewById(R.id.Mozip_magam);
        Magam.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                close_attend();


            }
        });

        attend =(Button)view.findViewById(R.id.attned);
        attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String document_id = getArguments().getString("write_id"); // document snapshot id
                DocumentReference attender = Data.collection("Board").document(document_id);
                // 중복 관리는 따로 안해줘도 될듯 싶습니다.

                attender.update("attender", FieldValue.arrayUnion(mAuth.getUid()));
                attender.update("attend_Count",FieldValue.increment(1));
                attend.setEnabled(false);




            }
        });


        cancle = (Button)view.findViewById(R.id.attend_cancle);
        cancle.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String document_id = getArguments().getString("write_id"); // document snapshot id
                DocumentReference attender = Data.collection("Board").document(document_id);

                attender.update("attender",FieldValue.arrayRemove(mAuth.getUid()));
                attender.update("attend_Count",FieldValue.increment(-1));

                attend.setEnabled(true);
                cancle.setEnabled(false);
                //attned_count 값 접근


            }
        });





        //EditText
        Frg_comment = (EditText)view.findViewById(R.id.comment_edittext);

        // listview 객체 연결
        comment_listview = (ListView)view.findViewById(R.id.comment_listview);



        //Bundle 이용한 데이터 받기    "title"- 제목 / "content" - 내용
        String Frg_title_val = getArguments().getString("title");
        String Frg_content_val = getArguments().getString("content");
        String document_id = getArguments().getString("write_id");          // 수정해야될거같긴 한데 write_id가 snapshot id임

        // 제목과 내용 설정해주기
        Frg_title = view.findViewById(R.id.Frg_title);
        Frg_content = view.findViewById(R.id.Frg_content);

        Frg_title.setText(Frg_title_val);
        Frg_content.setText(Frg_content_val);

        // 댓글 listview와 adapter 연결





//document ref를 쓰면 realtime으로 받아와야 하는 점 고려 -- 안그래도 자동으로 해주네?


       CollectionReference collectionReference = Data.collection("Board").document(document_id).collection("Comment");


        collectionReference.orderBy("comment_timestamp", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    Comment_item_list.clear();

                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    Comment comment = new Comment();
                    comment.setComment(documentSnapshot.getString("comment"));
                    Comment_item_list.add(comment);

                }
                Adapter_comment ad_comment = new Adapter_comment(getActivity(),Comment_item_list);
                comment_listview.setAdapter(ad_comment);
            }
        });






// 문서쓰는거는 이방식대로 하고, 그다음에 읽을때는 ref를 이용해야할듯 싶다.
       /*Data.collection("Board").document(document_id).collection("Comment")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        Comment_item_list.clear();


                        for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            Comment comment = new Comment();
                            comment.setComment(documentSnapshot.getString("comment"));
                            Comment_item_list.add(comment);

                            /* Toast.makeText(getContext(),documentSnapshot.getString("comment"),Toast.LENGTH_SHORT).show();       // 스냅샷은 제대로찍힙니다. 확인 */

                      /* }

                        Adapter_comment ad_comment = new Adapter_comment(getActivity(),Comment_item_list);
                        comment_listview.setAdapter(ad_comment);
                    }
                }); */



        return view;

    }

    //bye버튼
    View.OnClickListener buttonListener = new View.OnClickListener(){
        public void onClick(View v){
            FragmentManager manager = getActivity().getSupportFragmentManager();
            manager.beginTransaction().remove(Board_Fragment.this).commit();
            manager.popBackStack(); // code review 할것...
        }
    };

    //submit 버튼
    View.OnClickListener submit_btn_Listener = new View.OnClickListener() {
        public void onClick(View v) {
            store_comment();
        }
    };







    // 댓글 쓰는 메소드

    public void store_comment(){
        Data = FirebaseFirestore.getInstance();
        String write_id = getArguments().getString("write_id");

        Uid_for_comment_writer  = mAuth.getUid();

        Map<String,String> val_set = new HashMap<>();                // 그냥 해시 key에 uid를 넣고 value에 comment를 넣는 방식을 취하자
        val_set.put("comment",Frg_comment.getText().toString());
        val_set.put("comment_writer_uid",Uid_for_comment_writer);
        val_set.put("comment_timestamp",timeStamp);



        Data.collection("Board").document(write_id).collection("Comment").add(val_set)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getActivity(),"댓글이 입력되었습니다.",Toast.LENGTH_SHORT).show();
                        Frg_comment.setText("");
                    }
                });
         // 키보드 내리기 구현

    } // end of store


    // 마감 권한 여부 -->Uid가 제대로 넘어오지 않는 문제발생 [미해결] (글의 snapshot id가 넘어옴)


    /*  document 접근 방법 및 firesotre에 추가하는 예시입니다.
    Firestore에서 배열은 쿼리를 지원하지 않으므로, attender 멤버 쿼리를 위해서는 하위컬렉션을 사용해야할듯 싶습니다.
--> 데이터 모델링을 어떻게 하나여

    * DocumentSnapshot document = task.getResult();

                String doc_info = (String)document.get("title");

                Map<String,String> test = new HashMap<>();
                test.put("test",doc_info);

                Data.collection("Chat").add(test);                //

    *
    * */

    public void close_attend(){
        Data = FirebaseFirestore.getInstance();

        final String document_id = getArguments().getString("write_id");// 수정해야될거같긴 한데 write_id가 snapshot id임
        final DocumentReference documentReference = Data.collection("Board").document(document_id);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();

                List<String> attender_list = (ArrayList<String>)document.get("attender");
                Map<String,String> test = new HashMap<>();

                for(int i=0;i<attender_list.size();i++){

                    Toast.makeText(getContext(),attender_list.get(i),Toast.LENGTH_SHORT).show();
                    test.put(attender_list.get(i),"in");

                }
                
                Data.collection("Chat").add(test);                // Firestore에서 배열은 쿼리를 지원하지 않는다.




            }
        });
            Toast.makeText(getContext(), "인원모집을 마감합니다.", Toast.LENGTH_SHORT).show();
            Magam.setEnabled(false);

            //이제 Chatting Collection에 attender을 넘깁니다.






    }







} //  end of Fragment

