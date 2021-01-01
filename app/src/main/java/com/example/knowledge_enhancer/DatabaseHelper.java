package com.example.knowledge_enhancer;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "English_vocabulary";

    private static final String TABLE_TOPIC = "TABLE_TOPIC";
    private static final String COLUMN_TOPIC_ID = "COLUMN_TOPIC_ID";
    private static final String COLUMN_TOPIC_TITLE = "COLUMN_TOPIC_TITLE";
    private static final String COLUMN_TOPIC_STAR = "COLUMN_TOPIC_STAR";

    private static final String TABLE_QUIZ = "TABLE_QUIZ";
    private static final String COLUMN_QUIZ_ID = "COLUMN_QUIZ_ID";
    private static final String COLUMN_QUIZ_QUESTION = "COLUMN_QUIZ_QUESTION";
    private static final String COLUMN_QUIZ_ANSWER = "COLUMN_QUIZ_ANSWER";




    public DatabaseHelper(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        // Script to create table.
        Log.i(TAG, "DatabaseHelper.onCreate ... ");
        String scriptCreateTopic =
                "CREATE TABLE " + TABLE_TOPIC + " ( "
                + COLUMN_TOPIC_ID + " INTEGER PRIMARY KEY, "
                + COLUMN_TOPIC_TITLE + " TEXT, "
                + COLUMN_TOPIC_STAR + " INTEGER " + "DEFAULT 0)";

        String scriptCreateQuiz =
                "CREATE TABLE " + TABLE_QUIZ + " ( "
                + COLUMN_QUIZ_ID + " INTEGER PRIMARY KEY, "
                + COLUMN_QUIZ_QUESTION + " TEXT, "
                + COLUMN_QUIZ_ANSWER + " TEXT, "
                + COLUMN_TOPIC_ID + " INTEGER, "
                + "FOREIGN KEY (" + COLUMN_TOPIC_ID + ") REFERENCES " + TABLE_TOPIC + "(" + COLUMN_TOPIC_ID +"))";



        // Execute script.
        db.execSQL(scriptCreateTopic);
        db.execSQL(scriptCreateQuiz);

       // init data topic, quiz .......


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOPIC);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZ);
        // Recreate
        onCreate(db);
    }




    // =================== topic ====================

    // select all topic
    public List<Topic> getAllTopic(){
        List<Topic> topics =new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_QUIZ ;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int topicId = cursor.getInt(0);
            String topicTitle = cursor.getString(1);
            int topicStar = cursor.getInt(2);

            topics.add(new Topic(topicId,topicTitle , topicStar);

            cursor.moveToNext();
        }

        cursor.close();
        return topics;
    }

    // update star topic
    public void UpdateStarTopic(int star,int topicId){
        String query = "UPDATE "+ TABLE_TOPIC
                + " SET " +COLUMN_TOPIC_STAR+ " = " + star
                + " WHERE " + COLUMN_TOPIC_ID+ " = " + topicId;
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL(query);
    }

    // ===================== quiz ==================

    // select quiz by COLUMN_TOPIC_ID
    public List<Quiz> getAllQuizByTopicID(int topicId){
        Log.i(TAG, "MyDatabaseHelper.getAllQuizByTopicID ... " );
        List<Quiz> quizs =new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_QUIZ + " WHERE COLUMN_TOPIC_ID = " + topicId;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        //Đến dòng đầu của tập dữ liệu
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int quizId = cursor.getInt(0);
            String quizQuestion = cursor.getString(1);
            String quizAnswer = cursor.getString(2);

            quizs.add(new Quiz(quizId, quizQuestion, quizAnswer,topicId));

            cursor.moveToNext();
        }

        cursor.close();
        return quizs;
    }


    // count quiz where COLUMN_TOPIC_ID =
    public int getQuizCountByTopicID(int topicId) {
        Log.i(TAG, "MyDatabaseHelper.getQuizCountByTopicID ... " );

        String countQuery = "SELECT  * FROM " + TABLE_QUIZ + " WHERE COLUMN_TOPIC_ID = " + topicId;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        return count;
    }




}