package com.example.quiz_selvtest.Fragment.my_courses;

import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

public class Course{
    private String courseID;
    private String courseName;
    ArrayList<Course> quizList = new ArrayList<>();

    public Course(String courseID, String courseName){
        this.courseID = courseID;
        this.courseName = courseName;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public ArrayList<Course> getQuizList() {
        return quizList;
    }

    public void setQuizList(ArrayList<Course> quizList) {
        this.quizList = quizList;
    }

}
