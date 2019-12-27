package com.example.real.Board;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.real.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;

import javax.annotation.Nullable;

//https://stackoverflow.com/questions/51082933/firestore-collection-to-custom-listview 참조

public class Board extends AppCompatActivity implements Button.OnClickListener{
    Button write,board_to_main,fragment;
    FirebaseFirestore data = FirebaseFirestore.getInstance();
    ListView listview;
    static ArrayList<Item> Itemlist = new ArrayList<Item>();

    FirebaseAuth mAuth ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board);
        listview  = (ListView)findViewById(R.id.listview1);


        write = (Button)findViewById(R.id.write);
        board_to_main = (Button)findViewById(R.id.board_to_main);
        fragment = (Button)findViewById(R.id.Fragment);
        fragment.setVisibility(View.INVISIBLE);

        write.setOnClickListener(this);
        board_to_main.setOnClickListener(this);
        fragment.setOnClickListener(this);

        //ArrayList setup
        mAuth =FirebaseAuth.getInstance();

        //set the Adapter




        //DB에서 읽어오기 ---> doc ref

        CollectionReference collectionReference = data.collection("Board");

        collectionReference.orderBy("Board_timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot DocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        Itemlist.clear();

                        Bundle bundle = new Bundle();
                        for(DocumentSnapshot snapshots : DocumentSnapshots){
                            Item item = new Item();
                            item.setTitle(snapshots.getString("title"));
                            item.setContents(snapshots.getString("content"));
                            item.setWrite_id(snapshots.getId());       // snapshots.getId()는 그 문서의 id로 알고있는데,
                            Itemlist.add(item);
                            snapshots.getString("writer_uid");
                        }
                        Adapter adapter  = new Adapter(Board.this, Itemlist);
                        adapter.notifyDataSetChanged();
                        listview.setAdapter(adapter);
                    }
                });





       /*data.collection("Board").addSnapshotListener(new EventListener<QuerySnapshot>() {
           @Override
           public void onEvent(@Nullable QuerySnapshot DocumentSnapshots, @Nullable FirebaseFirestoreException e) {
               Itemlist.clear();

               for(DocumentSnapshot snapshots : DocumentSnapshots){
                   Item item = new Item();
                   item.setTitle(snapshots.getString("title"));
                   item.setContents(snapshots.getString("content"));
                   item.setWrite_id(snapshots.getId());       // snapshots.getId()는 그 문서의 id로 알고있는데,
                   Itemlist.add(item);
               }
               Adapter adapter  = new Adapter(Board.this, Itemlist);
               adapter.notifyDataSetChanged();
               listview.setAdapter(adapter);

           }
       });*/

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Board_Fragment fragment1 = new Board_Fragment();

                // Fragment Data 전달 - Bundle
                Bundle bundle = new Bundle ();
                bundle.putString("title", Itemlist.get(position).getTitle());
                bundle.putString("content",Itemlist.get(position).getContents());
                bundle.putString("write_id",Itemlist.get(position).getWrite_id());//getWrite_id 이거는 스냅샷 아이디임
                bundle.putString("snapshot_writer_id",Itemlist.get(position).write_id); // snapshot을 쓴 id
                fragment1.setArguments(bundle);

                Toast.makeText(getApplicationContext(), Itemlist.get(position).write_id, Toast.LENGTH_SHORT).show();


                getSupportFragmentManager().beginTransaction().replace(R.id.test1,fragment1).commit();




            }
        });


    } // end of onCreate


    public void onClick(View v){
        switch (v.getId()){
            case R.id.write:
                Intent intent = new Intent(getApplicationContext(), Board_write.class);
                startActivity(intent);
                break;

            case R.id.board_to_main:
                finish();
                break;


        }

    }// end of onclick


    //문서에 접근하기 위한 메소드












} // end of class



