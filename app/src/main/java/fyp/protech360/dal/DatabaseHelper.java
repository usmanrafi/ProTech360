package fyp.protech360.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import fyp.protech360.classes.EmergencyDetails;

public  class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ProTech360.db";
    public static final String TABLE_EMERGENCY_DETAILS = "emergency_details";
    public static final String COL_NUM1 = "Num1";
    public static final String COL_NUM2 = "Num2";
    public static final String COL_NUM3 = "Num3";
    public static final String COL_MESSAGE = "Message";
    public static final String COL_ID = "ID";

    public static final String TABLE_USER_DETAILS = "user_details";
    public static final String COL_NAME = "Name";
    public static final String COL_EMAIL = "Email";
    public static final String COL_IMAGE = "Image";
    public static final String COL_UID = "UserID";
    SQLiteDatabase db = this.getWritableDatabase();


    private static DatabaseHelper dbHelper = null;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public static DatabaseHelper getInstance(Context context){
        if(dbHelper == null)
            dbHelper = new DatabaseHelper(context);

        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_EMERGENCY_DETAILS +" (" + COL_NUM1 + " TEXT," + COL_NUM2 + " TEXT," + COL_NUM3+ " TEXT," + COL_MESSAGE + " TEXT,"+ COL_ID +" INT, PRIMARY KEY("+COL_ID+"))");
        db.execSQL("create table " + TABLE_USER_DETAILS +" (" + COL_UID + " TEXT," + COL_NAME + " TEXT," + COL_EMAIL +  " TEXT," + COL_IMAGE +  " TEXT, PRIMARY KEY("+COL_UID+"))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_EMERGENCY_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_USER_DETAILS);
        onCreate(db);
    }

    public boolean insertUserDetails(String UserID,String Name,String Email,String Image){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_UID,UserID);
        contentValues.put(COL_NAME,Name);
        contentValues.put(COL_EMAIL,Email);
        contentValues.put(COL_IMAGE,Image);
        long result = db.insert(TABLE_USER_DETAILS,null,contentValues);
        return (result != -1);
    }

    public Cursor getUserData(String UserID){
        Cursor res = db.query(TABLE_USER_DETAILS,new String[]{COL_NAME,COL_EMAIL,COL_IMAGE},COL_UID + " = ?",new String[]{UserID},null,null,null);
        return res;
    }

    public boolean insertEmergencyDetails(String message,String num1, String num2, String num3) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ID,1);
        contentValues.put(COL_MESSAGE,message);
        contentValues.put(COL_NUM1,num1);
        contentValues.put(COL_NUM2,num2);
        contentValues.put(COL_NUM3,num3);
        long result = db.insert(TABLE_EMERGENCY_DETAILS,null,contentValues);
        return (result != -1);
    }

    public Cursor getEmergencyData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.query(TABLE_EMERGENCY_DETAILS,new String[]{COL_MESSAGE,COL_NUM1,COL_NUM2,COL_NUM3,COL_ID},null,null,null,null,null);
        return res;
    }

    public EmergencyDetails getEmergencyDetails(){
        Cursor cursor = getEmergencyData();

        cursor.moveToFirst();
        String num1 = cursor.getString(cursor.getColumnIndex(COL_NUM1));
        String num2 = cursor.getString(cursor.getColumnIndex(COL_NUM2));
        String num3 = cursor.getString(cursor.getColumnIndex(COL_NUM3));
        String message = cursor.getString(cursor.getColumnIndex(COL_MESSAGE));

        return new EmergencyDetails(message, num1, num2, num3);
    }

}
