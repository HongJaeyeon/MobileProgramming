package com.example.real.Board;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.real.R;


import java.util.ArrayList;

public class Adapter extends ArrayAdapter<Item> {

    public Adapter(Context context, ArrayList<Item> object) {
        super(context, 0, object);
    }



    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.listview_item, parent, false);
        }

        Item item = getItem(position);

        TextView title = (TextView) convertView.findViewById(R.id.textTitle);

        title.setText(item.getTitle());

        return convertView;
    }
}