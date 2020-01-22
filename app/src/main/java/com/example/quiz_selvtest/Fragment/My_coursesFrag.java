package com.example.quiz_selvtest.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quiz_selvtest.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class My_coursesFrag extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.homefragment,container,false);

        EditText courseID = v.findViewById(R.id.courseIDInput);
        Button joinCourseBTN = v.findViewById(R.id.joinCourseBTN);
        ListView myCoursesListView = v.findViewById(R.id.myCoursesList);
        ArrayList<Course> courseList = new ArrayList<>();
        return inflater.inflate(R.layout.my_courses,container,false);




    }
}
