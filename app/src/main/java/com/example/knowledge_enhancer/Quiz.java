package com.example.knowledge_enhancer;

public class Quiz {

    private int quizId;
    private String quizQuestion;
    private String quizAnswer;
    private int topicId;

    public  Quiz (){

    }

    public Quiz(int quizId,String quizQuestion,String quizAnswer,int topicId){
        this.quizId=quizId;
        this.quizQuestion=quizQuestion;
        this.quizAnswer=quizAnswer;
        this.topicId=topicId;
    }

    public int getQuizId(){
        return quizId;
    }

    public String getQuizQuestion(){
        return quizQuestion;
    }

    public String getQuizAnswer(){
        return quizAnswer;
    }

    public int getTopicId() {
        return topicId;
    }
}
