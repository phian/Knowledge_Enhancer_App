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
                + COLUMN_TOPIC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + COLUMN_TOPIC_TITLE + " TEXT, "
                + COLUMN_TOPIC_STAR + " INTEGER " + "DEFAULT 0)";

        String scriptCreateQuiz =
                "CREATE TABLE " + TABLE_QUIZ + " ( "
                + COLUMN_QUIZ_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + COLUMN_QUIZ_QUESTION + " TEXT, "
                + COLUMN_QUIZ_ANSWER + " TEXT, "
                + COLUMN_TOPIC_ID + " INTEGER, "
                + "FOREIGN KEY (" + COLUMN_TOPIC_ID + ") REFERENCES " + TABLE_TOPIC + "(" + COLUMN_TOPIC_ID +"))";

        String scriptInsertDatatoTableTopic= "INSERT INTO " + TABLE_TOPIC + "(" + COLUMN_TOPIC_TITLE + ")  " + "VALUES " +
        "('Jobs'),"+
        "('Sports'),"+
        "('Food and Drinks'),"+
        "('Animals'),"+
        "('Cloths'),"+
        "('Cities and Countries')";

        String scriptInsertDatatoTableQuiz= "INSERT INTO " + TABLE_QUIZ + "(" + COLUMN_QUIZ_QUESTION + "," + COLUMN_QUIZ_ANSWER +"," + COLUMN_TOPIC_ID + ")" + "VALUES"
                +"('Look after the finances in an organisation','Accountants','1'),"
                +"('Bake bread','Bakers','1'),"
                +"('Shave mens beards and cut mens hair','Barbers','1 '),"
                +"('Serve drinks','Barmen','1'),"
                +"('Prepare and sell meat','Butchers','1'),"
                +"('Look after people s teeth','Dentists','1'),"
                +"('Look after patients','Nurses','1'),"
                +"('Look after people s animals','Vets','1'),"
                +"('Track and field sports such as running or jumping','athletics','2'),"
                +"('You throw the ball into a basket','basketball','2'),"
                +"('Fighting with the fists','boxing','2 '),"
                +"('Riding a bicycle','cycling','2'),"
                +"('Two competitors fight each other using very thin swords','fencing','2'),"
                +"('A game played with a ball on a large field','football','2'),"
                +"('A sport you play in an ice rink','hockey','2'),"
                +"('Gliding on skis','skiing','2'),"
                +"('Made from flour, baked an an oven','bread','3'),"
                +"('Small bread rolls','buns','3'),"
                +"('A rich spread made from cream','butter','3 '),"
                +"('A sweet baked food','cake','3'),"
                +"('A food made from milk have white or yellow','cheese','3'),"
                +"('A low alcohol drink','beer','3'),"
                +"('A mixed drink made of two or more ingredients','cocktail','3'),"
                +"('Made from cocoa powder milk and sugar','cocoa','3'),"
                +"('A meat-eating reptile','crocodile' ,'4'),"
                +"('Has a long nose','elephant','4'),"
                +"('Has a long neck','giraffe','4 '),"
                +"('Grey and big, loves water','hippo','4'),"
                +"('A pouched mammal that hops','kangaroo','4'),"
                +"('A bird that cannot fly','penguin','4'),"
                +"('The king of animals','lion','4'),"
                +"('Has a horn on its noses','rhino','4'),"
                +"('A shirt for women and girls','blouse','5'),"
                +"('We wear it to keep our heads warm','cap','5'),"
                +"('We wear it on top of our clothes to keep us warm','coat','5 '),"
                +"('For women and girls consists of bodice and skirt in one piece','dress','5'),"
                +"('A short coat with long sleeves','jacket','5'),"
                +"('You wear it round your neck to keep yourself warm','scarf','5'),"
                +"('A garment with sleeves a collar and a front opening','shirt','5'),"
                +"('Like a dress but with no top part','skirt','5'),"
                +"('Vietnam','Hanoi','6'),"
                +"('China','Beijing','6'),"
                +"('United Kingdom','London','6 '),"
                +"('France' ,'Paris','6'),"
                +"('Germany','Berlin','6'),"
                +"('Malaysia' ,'Kuala Lumpur','6'),"
                +"('United States','Washington D.C','6'),"
                +"('South Korea','Seoul','6')";


        // Execute script.
        db.execSQL(scriptCreateTopic);
        db.execSQL(scriptCreateQuiz);
        db.execSQL(scriptInsertDatatoTableTopic);
        db.execSQL(scriptInsertDatatoTableQuiz);
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

            topics.add(new Topic(topicId,topicTitle , topicStar));

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
