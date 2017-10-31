package com.example.admin.myapplication.activity.model;

/**
 * Created by Admin on 10/31/2017.
 */

public class Answer {
    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public String getUrl_play() {
        return url_play;
    }

    public void setUrl_play(String url_play) {
        this.url_play = url_play;
    }

    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;
    private int correct;
    private String url_play;
}
