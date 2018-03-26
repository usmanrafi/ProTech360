package fyp.protech360;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Aliyan on 3/12/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ProTech360.db";
    public static final String TABLE_A = "emergency_details";
    public static final String COL_A1 = "Message";
    public static final String COL_A2 = "num1";
    public static final String COL_A3 = "num2";
    public static final String COL_A4 = "num3";
    public static final String COL_A5 = "userID";


    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_A +" (" + COL_A1 + " TEXT," + COL_A2 + " TEXT," + COL_A3 + " TEXT," + COL_A4 + " TEXT,"+COL_A5+" LONG, PRIMARY KEY("+COL_A5+"))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_A);
        onCreate(db);
    }

    public boolean insertData(String message,String num1, String num2, String num3, Long userID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_A1,message);
        contentValues.put(COL_A2,num1);
        contentValues.put(COL_A3,num2);
        contentValues.put(COL_A4,num3);
        contentValues.put(COL_A5,userID);
        long result = db.insert(TABLE_A,null,contentValues);
        return (result != -1);
    }

    public Cursor getEmergencyData(String userID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.query(true,TABLE_A,new String[]{COL_A1,COL_A2,COL_A3,COL_A4,COL_A5},COL_A5 + " = ?",new String[]{userID},null,null,null,null);
        return res;
    }

}
