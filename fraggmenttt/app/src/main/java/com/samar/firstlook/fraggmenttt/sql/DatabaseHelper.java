package com.samar.firstlook.fraggmenttt.sql;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.samar.firstlook.fraggmenttt.Model.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION=5;
    //database name
    public static final String DATABASE_NAME="UserManager2.db";
    //table name
    public static final String TABLE_USER="user";

    //table columns
    public static final String COLUMN_USER_ID="user_id";
    public static final String COLUMN_USER_FIRSTNAME="user_first_name";
    public static final String COLUMN_USER_LASTNAME= "user_last_name";
    public static final String COLUMN_USER_DOB="user_dob";
    public static final String COLUMN_USER_PHONE_NUMBER="user_phone_no";
    public static final String COLUMN_USER_EMAIL="user_email";
    public static final String COLUMN_USER_PAN="user_pan";
   public static final String COLUMN_USER_IMAGE="user_image";



    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_FIRSTNAME + " TEXT," + COLUMN_USER_LASTNAME + " TEXT, "
            + COLUMN_USER_DOB + " TEXT, " + COLUMN_USER_PHONE_NUMBER + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT,"  + COLUMN_USER_PAN + " TEXT, "
            + COLUMN_USER_IMAGE + " TEXT " + ")";


    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;


    private Context ctx;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
        this.ctx=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
       // db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER_TABLE);

        onCreate(db);
    }

    public void AddUser(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_FIRSTNAME, user.getFirstname());
        values.put(COLUMN_USER_LASTNAME,user.getLastname());
         values.put(COLUMN_USER_DOB,user.getDob());
        values.put(COLUMN_USER_PHONE_NUMBER,user.getPhone_no());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PAN, user.getPan());
        values.put(COLUMN_USER_IMAGE, user.getImage());


        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }





    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */

    public List<User> getALLUser()
    {
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_FIRSTNAME,
                COLUMN_USER_LASTNAME,
                COLUMN_USER_DOB,
                COLUMN_USER_PHONE_NUMBER,
                COLUMN_USER_EMAIL,
                COLUMN_USER_PAN,
               COLUMN_USER_IMAGE };

                List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                COLUMN_USER_FIRSTNAME + " ASC");

        if(cursor!=null) {
            if (cursor.moveToFirst()) {

                do {

                    User user = new User();
                    user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                    user.setFirstname(cursor.getString(cursor.getColumnIndex(COLUMN_USER_FIRSTNAME)));
                    user.setLastname(cursor.getString(cursor.getColumnIndex(COLUMN_USER_LASTNAME)));
                    user.setDob(cursor.getString(cursor.getColumnIndex(COLUMN_USER_DOB)));
                    user.setPhone_no(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PHONE_NUMBER)));
                    user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                    user.setPan(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PAN)));
                     user.setImage(cursor.getString(cursor.getColumnIndex(COLUMN_USER_IMAGE)));
//                // Adding user record to list
                    userList.add(user);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();

        return userList;
    }




    //CRUD LEFT

//delete user

    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }




 //   check duplicates
    public boolean checkUser(String firstname, String lastname,String dob,String phone_no,String email,String pan) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_FIRSTNAME + " = ?" + " AND " + COLUMN_USER_LASTNAME + " = ?" + COLUMN_USER_DOB + " = ?" + " AND " +
                COLUMN_USER_PHONE_NUMBER + " = ?" + " AND "  +  COLUMN_USER_EMAIL + " = ?" + " AND "  +  COLUMN_USER_PAN + " = ?" ;

        // selection arguments
        String[] selectionArgs = {firstname,lastname,dob,phone_no,email,pan};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }


}
