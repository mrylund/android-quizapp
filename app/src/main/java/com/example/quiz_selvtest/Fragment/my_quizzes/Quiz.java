package com.example.quiz_selvtest.Fragment.my_quizzes;

public class Quiz {
    private String code;
    private String name;
    private String uniqueID;

    public Quiz(String code, String name, String uniqueID) {
        this.code = code;
        this.name = name;
        this.uniqueID = uniqueID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }
}
