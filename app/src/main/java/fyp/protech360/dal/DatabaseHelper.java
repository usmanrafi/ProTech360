package fyp.protech360.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ProTech360.db";
    public static final String TABLE_EMERGENCY_DETAILS = "emergency_details";
    public static final String COL_NUM1 = "Num1";
    public static final String COL_NUM2 = "Num2";
    public static final String COL_NUM3 = "Num3";
    public static final String COL_MESSAGE = "Message";
    public static final String COL_ID = "ID";


    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_EMERGENCY_DETAILS +" (" + COL_NUM1 + " TEXT," + COL_NUM2 + " TEXT," + COL_NUM3+ " TEXT," + COL_MESSAGE + " TEXT,"+ COL_ID +" INT, PRIMARY KEY("+COL_ID+"))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_EMERGENCY_DETAILS);
        onCreate(db);
    }

    public boolean insertData(String message,String num1, String num2, String num3) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ID,1);
        contentValues.put(COL_MESSAGE,message);
        contentValues.put(COL_NUM1,num1);
        contentValues.put(COL_NUM2,num2);
        contentValues.put(COL_NUM3,num3);
        long result = db.insert(TABLE_EMERGENCY_DETAILS,null,contentValues);
        return (result != -1);
    }

    public Cursor getEmergencyData(String userID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.query(TABLE_EMERGENCY_DETAILS,new String[]{COL_MESSAGE,COL_NUM1,COL_NUM2,COL_NUM3,COL_ID},null,null,null,null,null);
        return res;
    }

}
