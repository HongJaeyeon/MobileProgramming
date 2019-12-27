package com.example.real.Board;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.real.R;

import java.util.ArrayList;

public class Adapter_comment extends ArrayAdapter<Comment> {

    public Adapter_comment(Context context, ArrayList<Comment> object){
        super(context, 0 , object);
    }


    public View getView(int position, View convertView , ViewGroup parent){

        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.comment_layout, parent, false);
        }

        Comment comment = getItem(position);

        TextView text_comment = (TextView)convertView.findViewById(R.id.textComment);

        text_comment.setText(comment.getComment());

        return convertView;

    }

}
