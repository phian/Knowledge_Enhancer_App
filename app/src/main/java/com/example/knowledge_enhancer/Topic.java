package com.example.knowledge_enhancer;

public class Topic {
    private int topicId;
    private String topicTitle;
    private int topicStar;

    public Topic(){

    }
    public Topic (int topicId,String topicTitle,int topicStar){
        this.topicId = topicId;
        this.topicTitle =topicTitle;
        this.topicStar=topicStar;
    }

    public int getTopicId() {
        return topicId;
    }

    public int getTopicStar() {
        return topicStar;
    }

    public String getTopicTitle(){
        return topicTitle;
    }
}
