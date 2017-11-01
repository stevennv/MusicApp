package com.example.admin.myapplication.activity.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 10/31/2017.
 */

public class Answer implements Serializable {


    public Answer(String answerA, String answerB, String answerC, String answerD, int correct, String url_play, int seekTo, int seekEnd) {
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.correct = correct;
        this.url_play = url_play;
        this.seekTo = seekTo;
        this.seekEnd = seekEnd;
    }

    public Answer() {
    }

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

    public int getSeekTo() {
        return seekTo;
    }

    public void setSeekTo(int seekTo) {
        this.seekTo = seekTo;
    }

    public int getSeekEnd() {
        return seekEnd;
    }

    public void setSeekEnd(int seekEnd) {
        this.seekEnd = seekEnd;
    }

    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;
    private int correct;
    private String url_play;
    private int seekTo;
    private int seekEnd;

//    public Map<String, Object> toMap() {
//        Map<String, Object> result = new HashMap<>();
//        result.put("answerA", answerA);
//        result.put("answerB", answerB);
//        result.put("answerC", answerC);
//        result.put("answerD", answerD);
//        result.put("correct", correct);
//        result.put("url_play", url_play);
//        result.put("seekTo", seekTo);
//        result.put("seekEnd", seekEnd);
//        return result;
//    }

}
