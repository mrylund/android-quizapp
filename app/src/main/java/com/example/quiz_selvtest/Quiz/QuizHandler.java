package com.example.quiz_selvtest.Quiz;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class QuizHandler implements Serializable {
    private List<Question> questionsNew;
    private int curQuestion;
    private boolean quizEnded;
    private int score;

    QuizHandler(String sheetID) throws IOException, CsvValidationException {
        curQuestion = 0;
        score = 0;
        quizEnded = false;
        CSVReader reader = getCSV(sheetID);
        initializeObjects(reader);
    }

    private CSVReader getCSV(String sheet) throws IOException {
            InputStream input = new URL("https://docs.google.com/spreadsheets/d/" + sheet + "/export?format=csv&id=" + sheet).openStream();
            return new CSVReaderBuilder(new InputStreamReader(input, "UTF-8")).withSkipLines(8).build();
    }

    private void initializeObjects(CSVReader reader) throws IOException, CsvValidationException {
        questionsNew = new ArrayList<>();

        String[] line;
        while ((line = reader.readNext()) != null) {
            if (line[1].equals("")) break;
            String question = line[1];
            String[] answers = {line[2], line[3], line[4], line[5]};
            int tid = Integer.parseInt(line[6]);
            String[] correctString = line[7].split(",");
            int[] correct = new int[correctString.length];
            for(int i = 0; i < correctString.length; i++) {
                correct[i] =  Integer.parseInt(correctString[i]);
            }

            Question q = new Question(question, answers, correct);
            questionsNew.add(q);
        }
    }

    boolean checkAnswers(boolean a1, boolean a2, boolean a3, boolean a4) {
        boolean allCorrect;
        boolean ca1 = false, ca2 = false, ca3 = false, ca4 = false;
        for (int n : questionsNew.get(curQuestion).getCorrectAnswers()) {
         switch (n) {
             case 1: ca1 = true; break;
             case 2: ca2 = true; break;
             case 3: ca3 = true; break;
             case 4: ca4 = true; break;
         }
        }

        System.out.println(a1 +  " : " + ca1 + "\n" +
                a2 +  " : " + ca2 + "\n" +
                a3 +  " : " + ca3 + "\n" +
                a4 +  " : " + ca4 + "\n\n\n");

        allCorrect = a1 == ca1;
        if (allCorrect) {
            allCorrect = a2 == ca2;
        }
        if (allCorrect) {
            allCorrect = a3 == ca3;
        }
        if (allCorrect) {
            allCorrect = a4 == ca4;
        }



        return allCorrect;
    }

    public void nextQuestion() {
        if (curQuestion < questionsNew.size() - 1){
            curQuestion++;
        } else {
            quizEnded = true;
        }
    }

    public String getQuestion() {
        return questionsNew.get(curQuestion).getQuestion();
    }

    public String[] getAnswers() {
        return questionsNew.get(curQuestion).getAnswers();
    }

    public boolean hasEnded() {
        return quizEnded;
    }

    public int getScore() {
        return score;
    }

    public int getQuestionCount() {
        return questionsNew.size();
    }

    public int getCurQuestionNum() { return curQuestion;}

    public void addScore() {
        score++;
    }

}
