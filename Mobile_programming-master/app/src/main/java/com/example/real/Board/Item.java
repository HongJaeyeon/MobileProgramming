package com.example.real.Board;


import com.google.firebase.auth.FirebaseAuth;

public class Item {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();//댓글 작성하는 사람의 uid를 넣을 것입니다 하하

    /* attribute */

    String title,contents;    // 이걸 기본으로 하고 나중에
    String timestamp; // 나중에 추가 할 예정
    String write_id;




    /* 생성자 */
    public void Item(){
        title =null;
        contents=null;
        timestamp= null;
        String writer_id = mAuth.getUid();
    }

    public void Item(String Title,String Contents , String Timestamp){
        title = Title;
        contents = Contents;
        timestamp = Timestamp;
        String writer_id = mAuth.getUid();

    }

    public void Item(String Title,String Contents){
        title = Title;
        contents = Contents;
        timestamp = null; // 이거 한번에 쓰는 법 있을텐데  this()였나
        String writer_id = mAuth.getUid();

    }


    //getter & setter


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


    public String getWrite_id() {return write_id;}

    public void setWrite_id(String write_id) {this.write_id = write_id;}


//parcel method




}//end of class
