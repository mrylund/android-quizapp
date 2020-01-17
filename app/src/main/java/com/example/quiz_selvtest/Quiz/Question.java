package com.example.quiz_selvtest.Quiz;

public class Question {
    private String question;
    private String[] answers;
    private int[] correctAnswers;

    public Question(String question, String[] answers, int[] correctAnswers) {
        this.question = question;
        this.answers = answers;
        this.correctAnswers = correctAnswers;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public int[] getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int[] correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getAnswerCount() {
        return answers.length;
    }

    public int getCorrectAnswerCount() {
        return correctAnswers.length;
    }
}
