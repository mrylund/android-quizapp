package com.example.quiz_selvtest.Quiz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class QuizHandler implements Serializable {
    private List<String> questions;
    private List<String[]> answers;
    private List<Integer> correctAnswers;
    private static String testQuiz = "https://drive.google.com/file/d/1IuMUZaZC_8eH-iJZGTwiG0iozv9BeWg1k-HseRslW24/view";
    private int curQuestion;
    private boolean quizEnded;
    private int score;

    public QuizHandler() throws IOException {
        this("ABC123");
    }

    public QuizHandler(String quizCode) throws IOException {
        String sheet = testQuiz;
        String sheetID = getSheetID(sheet);
        questions = new ArrayList<>();
        answers = new ArrayList<>();
        correctAnswers = new ArrayList<>();
        curQuestion = 0;
        score = 0;
        quizEnded = false;
        hentOrd(sheetID);
    }


    // Get the data from the URL
    public static String hentUrl(String url) throws IOException {
        System.out.println("Henter data fra " + url);
        BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
        StringBuilder sb = new StringBuilder();
        String linje = br.readLine();
        while (linje != null) {
            sb.append(linje + "\n");
            linje = br.readLine();
        }
        return sb.toString();
    }

    private void hentOrd(String sheet) throws IOException {
        System.out.println("Henter data som kommasepareret CSV fra regnearket https://docs.google.com/spreadsheets/d/"+sheet+"/edit?usp=sharing");

        String data = hentUrl("https://docs.google.com/spreadsheets/d/" + sheet + "/export?format=csv&id=" + sheet);
        int linjeNr = 0;
        for (String linje : data.split("\n")) {
            if (linjeNr++ < 8 ) continue;
            String[] felter = linje.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1); // Regex fundet her: https://stackabuse.com/regex-splitting-by-character-unless-in-quotes/

            // Break if no more questions in the sheet
            if (felter[1].isEmpty()) break;
            // Set the question and the possible answers
            String q = felter[1];
            String[] a = {felter[2], felter[3], felter[4], felter[5]};

            // Get the correct answer number, fool proof from people placing commas and dots.
            // TODO: possibility to have multiple answers
            int ca;
            if (felter[7].contains(".") || felter[7].contains(",")) {
                String tal = felter[7].split("(?=[,.])")[0];
                tal = tal.replace("\"", "");
                ca = Integer.parseInt(tal);
                System.out.println(ca);
            } else {
                ca = Integer.parseInt(felter[7]);
            }

            // Add the information in the lists
            questions.add(q);
            answers.add(a);
            correctAnswers.add(ca);
        }
    }

    // Get the ID of the google sheets
    private String getSheetID(String url) {
        url = url.substring(url.indexOf("/d/")+3);
        url = url.substring(0, url.indexOf("/"));
        url = url.trim();
        return url;
    }

    public boolean checkAnswer(int question, int answer, boolean nextQuestion) {
        boolean correct = correctAnswers.get(question) == answer;
        if (nextQuestion) {
            if (correct) {
                addScore();
            }
            nextQuestion();
        }
        return correct;
    }
    public boolean checkAnswer(int answer, boolean nextQuestion) {
        return checkAnswer(curQuestion, answer, nextQuestion);
    }

    public int getAnswer(int question) {
        return correctAnswers.get(question);
    }
    public int getAnswer() {
        return getAnswer(curQuestion);
    }

    public void nextQuestion() {
        if (curQuestion < questions.size() - 1){
            curQuestion++;
        } else {
            quizEnded = true;
        }
    }

    public String getQuestion(int question) {
        return questions.get(curQuestion);
    }
    public String getQuestion() {
        return getQuestion(curQuestion);
    }

    public String[] getAnswers(int question) {
        return answers.get(curQuestion);
    }
    public String[] getAnswers() {
        return getAnswers(curQuestion);
    }

    public boolean hasEnded() {
        return quizEnded;
    }

    public int getScore() {
        return score;
    }

    public int getQuestionCount() {
        return questions.size();
    }

    public void addScore() {
        score++;
    }

}
