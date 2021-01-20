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
        Log.i(TAG, "#Database Helper DatabaseHelper.onCreate ... ");
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
                    "('Cities and Countries'),"+
                    "('Family'),"+
                    "('School'),"+
                    "('Languages'),"+
                    "('Geographic Terminology'),"+
                    "('House Ware & Kitchen'),"+
                    "('House & Garden'),"+
                    "('Things and Materials'),"+
                    "('Travel'),"+
                    "('Transport'),"+
                    "('Music'),"+
                    "('Human Body'),"+
                    "('Health & Diseases'),"+
                    "('Festival'),"+
                    "('Shopping')";

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
                +"('South Korea','Seoul','6'),"
                +"('The sister of your mother or father, or the wife of your uncle','aunt','7'),"
                +"('A very young child','baby','7'),"
                +"('A male with the same parents as someone else','brother','7 '),"
                +"('Your daddy is your male parent','daddy','7'),"
                +"('Parents and children','family','7'),"
                +"('The mother of your mummy or daddy','grandma','7'),"
                +"('The father of your mummy or daddy','grandpa','7'),"
                +"('Your mummy is your female parent','mummy','7'),"
                +"('Where the teacher writes','board','8'),"
                +"('What you read','book','8'),"
                +"('For making mathematical calculations','calculator','8 '),"
                +"('A place where you study','classroom','8'),"
                +"('Awarded to a student by a school','diploma','8'),"
                +"('A small model of the Earth.','globe','8'),"
                +"('Shows the features of an area','map','8'),"
                +"('Somebody who gives students homework','teacher','8'),"
                +"('You do it to show sorrow by shedding tears','cry','9'),"
                +"('You do it when you are hungry','eat','9'),"
                +"('You do it to understand the written or printed word','read','9 '),"
                +"('You do it when you are thirsty','drink','9'),"
                +"('Faster than walking','run','9'),"
                +"('You do it when you make musical sounds.','sing','9'),"
                +"('When you do it, you rest your legs','sit','9'),"
                +"('You do it every night','sleep','9'),"
                +"('The ...is so high','sky' ,'10'),"
                +"('We are standing on the...','land','10'),"
                +"('A ... shoots up','mountain','10 '),"
                +"('Many animals live in a...','forest','10'),"
                +"('There are wide green ...','fields','10'),"
                +"('A ... has lots of rain.','rainforest','10'),"
                +"('A ... is a very hot and dry place','desert','10'),"
                +"('The ... is very deep and blue','sea','10'),"
                +"('A kitchen device for cooking','cooker','11'),"
                +"('You can drink from it.','cup','11'),"
                +"('Keeps food cold','fridge','11 '),"
                +"('A pot in which you boil water','kettle','11'),"
                +"('You can cut things with this item','knife','11'),"
                +"('A large deep cup with a handle','mug','11'),"
                +"('A shallow, wide, open container used to cook food','pan','11'),"
                +"('LUsed for brewing teat','teapot','11'),"
                +"('A chair with a back and arms','armchair','12'),"
                +"('A place to sleep','bed','12'),"
                +"('With shelves for storing books','bookcase','12 '),"
                +"('A seat for one person, with a support for the back' ,'chair','12'),"
                +"('Used to store household objects','cupboard','12'),"
                +"('With a writing surface and drawers' ,'desk','12'),"
                +"('A seat for more than one person','sofa ','12'),"
                +"('Has a flat top and one or more legs','table','12'),"

                +"('a hard substance that forms the branches and trunks of trees','wood ' ,'13'),"
                +"('a chemical element, such as iron or gold, or a mixture of such elements, such as steel','metal','13'),"
                +"('a hard, transparent material, used to make windows, bottles','glass','13 '),"
                +"('material like very thick, stiff paper, usually pale brown in colour, used especially for making boxes','cardboard','13'),"
                +"('thin, flat material made from crushed wood or cloth, used for writing, printing, or drawing on','paper','13'),"
                +"('an artificial substance that can be shaped when soft into many different forms','plastic','13'),"
                +"('cloth or material for making clothes, covering furniture','fabric','13'),"
                +"('Type of fabric','cotton','13'),"
                +"('A public place where money is kept','bank','14'),"
                +"('A place for worship','church','14'),"
                +"('A place where films are shown','cinema','14 '),"
                +"('A place for servicing vehicles with gasoline and oi','gas station','14'),"
                +"('You go there when you are sick','hospital','14'),"
                +"('A building for a large collection of books','library','14'),"
                +"('Collects nature items or things that people created.','museum','14'),"
                +"('It is responsible for mail delivery.','post office','14'),"
                +"('Has two wheels, a handle bar and pedals.','bike','15'),"
                +"('A small vessel for travelling over water','boat','15'),"
                +"('A large vehicle that takes many passengers to places','bus','15 '),"
                +"('A motor vehicle with four wheels' ,'car','15'),"
                +"('A flying machine with a propeller on top','helicopter','15'),"
                +"('A large truck' ,'lorry','15'),"
                +"('A motor vehicle with two wheels and a strong frame','motorcycle ','15'),"
                +"('A machine that flies in the sky','plane','15'),"


                +"('The sister of your mother or father, or the wife of your uncle','aunt','16'),"
                +"('A very young child','baby','16'),"
                +"('A male with the same parents as someone else','brother','16 '),"
                +"('Your daddy is your male parent','daddy','16'),"
                +"('Parents and children','family','16'),"
                +"('The mother of your mummy or daddy','grandma','16'),"
                +"('The father of your mummy or daddy','grandpa','16'),"
                +"('Your mummy is your female parent','mummy','16'),"
                +"('Where the teacher writes','board','17'),"
                +"('What you read','book','17'),"
                +"('For making mathematical calculations','calculator','17'),"
                +"('A place where you study','classroom','17'),"
                +"('Awarded to a student by a school','diploma','17'),"
                +"('A small model of the Earth.','globe','17'),"
                +"('Shows the features of an area','map','17'),"
                +"('Somebody who gives students homework','teacher','17'),"
                +"('You do it to show sorrow by shedding tears','cry','18'),"
                +"('You do it when you are hungry','eat','18'),"
                +"('You do it to understand the written or printed word','read','18'),"
                +"('You do it when you are thirsty','drink','18'),"
                +"('Faster than walking','run','18'),"
                +"('You do it when you make musical sounds.','sing','18'),"
                +"('When you do it, you rest your legs','sit','18'),"
                +"('You do it every night','sleep','18'),"
                +"('The ...is so high','sky' ,'19'),"
                +"('We are standing on the...','land','19'),"
                +"('A ... shoots up','mountain','19 '),"
                +"('Many animals live in a...','forest','19'),"
                +"('There are wide green ...','fields','19'),"
                +"('A ... has lots of rain.','rainforest','19'),"
                +"('A ... is a very hot and dry place','desert','19'),"
                +"('The ... is very deep and blue','sea','19'),"
                +"('A kitchen device for cooking','cooker','20'),"
                +"('You can drink from it.','cup','20'),"
                +"('Keeps food cold','fridge','20 '),"
                +"('A pot in which you boil water','kettle','20'),"
                +"('You can cut things with this item','knife','20'),"
                +"('A large deep cup with a handle','mug','20'),"
                +"('A shallow, wide, open container used to cook food','pan','20'),"
                +"('LUsed for brewing teat','teapot','20'),"
                +"('A chair with a back and arms','armchair','20'),"
                +"('A place to sleep','bed','20'),"
                +"('With shelves for storing books','bookcase','20 '),"
                +"('A seat for one person, with a support for the back' ,'chair','20'),"
                +"('Used to store household objects','cupboard','20'),"
                +"('With a writing surface and drawers' ,'desk','20'),"
                +"('A seat for more than one person','sofa ','20'),"
                +"('Has a flat top and one or more legs','table','20')";


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
        String query = "SELECT * FROM " + TABLE_TOPIC;
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

    // Get topic by ID
    public Topic getTopicByID(int id) {
        String query = "SELECT * FROM " + TABLE_TOPIC + " WHERE COLUMN_TOPIC_ID = " + id;
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);

        cursor.moveToFirst();

        return new Topic(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
    }

    // update star topic
    public void updateStarTopic(int star,int topicId){
        String query = "UPDATE "+ TABLE_TOPIC
                + " SET " + COLUMN_TOPIC_STAR+ " = " + star
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
