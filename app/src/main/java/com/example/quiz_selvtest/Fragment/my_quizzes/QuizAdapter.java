package com.example.quiz_selvtest.Fragment.my_quizzes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.quiz_selvtest.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;


// Inspirered by: https://www.youtube.com/watch?v=E6vE8fqQPTE&t=1s
public class QuizAdapter extends ArrayAdapter<Quiz> {
    private Context mContext;
    private int mResource;

    QuizAdapter(Context context, int resource, ArrayList<Quiz> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        String name = Objects.requireNonNull(getItem(position)).getName();
        String code = Objects.requireNonNull(getItem(position)).getCode();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName =  convertView.findViewById(R.id.name);
        TextView tvCode =  convertView.findViewById(R.id.code);
        // TODO: Make an onclick on the  delete button
        //ImageView delete =  convertView.findViewById(R.id.delete);


        tvName.setText(name);
        tvCode.setText(code);

        return convertView;
    }
}
