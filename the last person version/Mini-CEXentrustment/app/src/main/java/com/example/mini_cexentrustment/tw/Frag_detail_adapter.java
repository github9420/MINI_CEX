package com.example.mini_cexentrustment.tw;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mini_cexentrustment.R;

import java.util.List;

/**
 * Created by rorensu on 2017/11/20.
 */

public class Frag_detail_adapter extends ArrayAdapter<Item_detail> {
    Context context;
    List<Item_detail> objects;
    public Frag_detail_adapter(Context context, List<Item_detail> objects) {
        super(context, 0, objects);
        this.context = context;
        this.objects = objects;
    }
    public View getView(int position, View view, ViewGroup parent)
    {
        ChatCell chatCell = new ChatCell();

        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.frg_detail_list_item, parent, false);

        chatCell.subject = (TextView) view.findViewById(R.id.frg_detail_subject);
        chatCell.teacherandstudentname = (TextView) view.findViewById(R.id.frg_teacher_student);
        chatCell.date = (TextView) view.findViewById(R.id.frg_detail_date);
        chatCell.status = (TextView) view.findViewById(R.id.frg_status);
        Item_detail chat = getItem(position);

        chatCell.subject.setText(chat.subject);
        chatCell.teacherandstudentname.setText(chat.teachername);
        chatCell.date.setText(chat.evaluateDateTime);
        chatCell.status.setText(chat.status);
        return view;
    }

    private static class ChatCell
    {
        //Chat items
        TextView subject;
        TextView teacherandstudentname;
        TextView date;
        TextView status;
    }
}
