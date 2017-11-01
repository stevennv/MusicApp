package com.example.admin.myapplication.activity.model;

/**
 * Created by Admin on 11/1/2017.
 */

public class PreviewResult {
    public PreviewResult(int numberQuestion, boolean result) {
        this.numberQuestion = numberQuestion;
        this.result = result;
    }

    public int getNumberQuestion() {
        return numberQuestion;
    }

    public void setNumberQuestion(int numberQuestion) {
        this.numberQuestion = numberQuestion;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    private int numberQuestion;
    private boolean result;
}
