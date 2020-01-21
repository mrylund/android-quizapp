package com.example.quiz_selvtest.Fragment.my_quizzes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.quiz_selvtest.R;

import java.util.ArrayList;

public class QuizAdapter extends ArrayAdapter<Quiz> {

    private static final String TAG = "QuizAdapter";

    private Context mContext;
    private int mResource;

    public QuizAdapter(Context context, int resource,  ArrayList<Quiz> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String name = getItem(position).getName();
        String code = getItem(position).getCode();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName =  convertView.findViewById(R.id.name);
        TextView tvCode =  convertView.findViewById(R.id.code);
        ImageView delete =  convertView.findViewById(R.id.delete);

        tvName.setText(name);
        tvCode.setText(code);

        return convertView;
    }
}
