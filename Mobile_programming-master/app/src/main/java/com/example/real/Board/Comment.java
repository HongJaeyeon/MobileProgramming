package com.example.real.Board;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;

public class Comment {
    //어트리뷰트
    String comment ;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();  // 여기서 선언하면 오류가 날지 확인하기
    Date date = new Date();
    // 생성자
    public void Comment(){
        comment = null;
        String uid = mAuth.getUid();
        date.getTime();

    }

    public void Comment (String comment_val){
        comment = comment_val;
        String uid = mAuth.getUid();
        date.getTime();
    }

    // getter & setter

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}
