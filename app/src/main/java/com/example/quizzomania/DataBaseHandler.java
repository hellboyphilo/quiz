/*
package com.example.quizzomania;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DataBaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "quiz";
    private static final String TABLE_NAME = "questions";
    SQLiteDatabase db = null;

    public DataBaseHandler(@Nullable Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        Log.d(DATABASE_NAME,"created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "CREATE TABLE questions(category text,level text,question text,oA text,oB text,oC text,oD text,ans text)";
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS questions");
        onCreate(db);
    }

    public void addQuestion(Modelclass modelclass){
        try {
            db=getReadableDatabase();
            ContentValues values = new ContentValues();

            values.put("category",modelclass.getCategory());
            values.put("level",modelclass.getLevel());
            values.put("question",modelclass.getQuestion());
            values.put("oA",modelclass.getoA());
            values.put("oB",modelclass.getoB());
            values.put("oC",modelclass.getoC());
            values.put("oD",modelclass.getoD());
            values.put("ans",modelclass.getAns());

            db.insert("questions",null,values);
            Log.d("Insert: ","Record Added");
            db.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
*/
