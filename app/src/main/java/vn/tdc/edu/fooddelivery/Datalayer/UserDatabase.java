package vn.tdc.edu.fooddelivery.Datalayer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import vn.tdc.edu.fooddelivery.models.UserModel;

public class UserDatabase extends SQLiteOpenHelper {
    // Database 's properties
    private static String DB_NAME = "users";

    private static int DB_VERSION = 1;

    private static Activity context;

    ///////////////////////////////////////////////////////////////////////////////////////////
    //// Tables 's Properties
    ///////////////////////////////////////////////////////////////////////////////////////////
    // 1. User Table
    private String USER_TABLE_NAME = "user";

    private static String USER_ID = "_id";

    private static String USER_NAME = "full_name";

    private static String USER_EMAIL = "email";

    private static String USER_IMAGE = "image";


    ///////////////////////////////////////////////////////////////////////////////////////////
    //// Constructors
    ///////////////////////////////////////////////////////////////////////////////////////////
    public UserDatabase(Activity context) {
        super(context, DB_NAME, null, DB_VERSION);
        UserDatabase.context = context;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////
    //// Person Database 's Primitives - 1. Create Tables
    ///////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onCreate(SQLiteDatabase db) {
        if (db != null) {
            // SQL Statement
            String sql = "CREATE TABLE " + USER_TABLE_NAME + "("
                    + USER_ID + " INTEGER, "
                    + USER_NAME + " TEXT, "
                    + USER_EMAIL + " TEXT, "
                    + USER_IMAGE + " TEXT);";
            // Execute the SQL Statement
            try {
                db.execSQL(sql);
                Toast.makeText(context, "Tao bang thanh cong!", Toast.LENGTH_SHORT).show();
            } catch (Exception exception) {
                Toast.makeText(context, "Tao bang khong thanh cong!", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO when the databse version changes
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    //// 2. Person Database save
    ///////////////////////////////////////////////////////////////////////////////////////////
    // 1. Save persons to Person Database
    public void savePerson(UserModel person) {
        SQLiteDatabase database = getWritableDatabase();
        if (database != null) {
            ContentValues values = new ContentValues();
            values.put(USER_ID, person.getId());
            values.put(USER_NAME, person.getFullName());
            values.put(USER_EMAIL, person.getEmail());
            values.put(USER_IMAGE, person.getImageName());
            try {
                database.insert(USER_TABLE_NAME, null, values);
                Toast.makeText(context, "luu doi tuong thanh cong!", Toast.LENGTH_SHORT).show();
            } catch (Exception exception) {
                Toast.makeText(context, "luu doi tuong khong thanh cong!", Toast.LENGTH_SHORT).show();
            }
            database.close();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    //// 3. Get person from data base
    ///////////////////////////////////////////////////////////////////////////////////////////
    public UserModel getPerson() {
        //1. Create new Person object
        UserModel person = new UserModel();
        SQLiteDatabase database = getWritableDatabase();
        if (database != null) {
            String[] selectionColunm = new String[]{USER_ID, USER_NAME, USER_EMAIL, USER_IMAGE};
            String whereCondition = null;
            String[] whereAgr = null;
            String groupBy = null;
            String having = null;
            Cursor cursor = database.query(USER_TABLE_NAME, selectionColunm, whereCondition, whereAgr, groupBy, having, USER_NAME + " ASC");
            if (cursor.moveToFirst()) {
                //2. Read data from cursor and set to person object
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(USER_ID));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(USER_NAME));
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(USER_EMAIL));
                @SuppressLint("Range") String image = cursor.getString(cursor.getColumnIndex(USER_IMAGE));
                //Bin data for object
                person.setId(Integer.valueOf(id));
                person.setFullName(name);
                person.setEmail(email);
                person.setImageName(image);
            }
            database.close();
        }
        if (person.getId() != null) {
            Toast.makeText(context, "khong co user nao", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "doc thanh cong", Toast.LENGTH_SHORT).show();
        }
        return person;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    //// 4. Update user
    ///////////////////////////////////////////////////////////////////////////////////////////
    public void updatePerson(UserModel newPerson) {
        SQLiteDatabase database = getWritableDatabase();
        if (database != null) {
            //value để chứa cái giá trị mới để đưa vào database
            ContentValues values = new ContentValues();
            //Viết câu điều kiện
            String where = USER_ID + " =?";
            String[] whereArgs = new String[]{String.valueOf(newPerson.getId())};

            values.put(USER_NAME, newPerson.getFullName());
            values.put(USER_EMAIL, newPerson.getEmail());
            values.put(USER_IMAGE, newPerson.getImageName());
            int bool_Update = database.update(USER_TABLE_NAME, values, where, whereArgs);

            if (bool_Update == 1) {
                Toast.makeText(context, "Update successfull", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Update fail!", Toast.LENGTH_SHORT).show();
            }
            database.close();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    //// 5. Delete Person
    ///////////////////////////////////////////////////////////////////////////////////////////
    public void deletePerson(UserModel person) {
        SQLiteDatabase database = getWritableDatabase();
        if (database != null) {

            //Viết câu điều kiện
            String where = USER_ID + " =?";
            String[] whereArgs = new String[]{String.valueOf(person.getId())};
            int bool_delete = database.delete(USER_TABLE_NAME, where, whereArgs);


            // TODO Show dialog to user
            if (bool_delete > 0) {
                Toast.makeText(context, "xoa thanh cong!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "xoa khong thanh cong!", Toast.LENGTH_SHORT).show();
            }
            database.close();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    //// 6 . Delete table
    ///////////////////////////////////////////////////////////////////////////////////////////
    public void deleteTable() {
        SQLiteDatabase database = getWritableDatabase();
        if (database != null) {
            String sql = "DROP TABLE IF EXISTS " + USER_TABLE_NAME;
            // Execute the SQL Statement
            try {
                database.execSQL(sql);
                Toast.makeText(context, "xoa database thanh cong!", Toast.LENGTH_SHORT).show();
            } catch (Exception exception) {
                Toast.makeText(context, "xoa database that bai!", Toast.LENGTH_SHORT).show();
            }
            database.close();
        }
    }

}
