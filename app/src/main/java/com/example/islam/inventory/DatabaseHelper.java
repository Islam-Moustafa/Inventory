package com.example.islam.inventory;

/**
 * Created by islam on 3/28/2019.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.islam.inventory.DatabaseContract.PetEntry;

// import applications.editablelistview.data.DatabaseContract.PetEntry;

/**
 * Created by Mitch on 2016-05-13.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mylist255.db";
    /// public static final String TABLE_NAME = "mylist_data";
    /// public static final String COL1 = "ID";
    /// public static final String COL2 = "ITEM1";
    //// public static final String COL3 = "ITEM2";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + PetEntry.TABLE_NAME + " ("
                + PetEntry._ID + "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PetEntry.COLUMN_PRODUCTNAME + " TEXT , "
                + PetEntry.COLUMN_PRODUCTMODEL + " TEXT , "
                + PetEntry.COLUMN_IMAGE + " BLOB , "
                + PetEntry.COLUMN_PRICE + " REAL , "
                + PetEntry.COLUMN_QUANTITY + " INTEGER , "
                + PetEntry.COLUMN_SUPPLIERNAME + " TEXT , "
                + PetEntry.COLUMN_SUPPLIERPHONE + " TEXT , "
                + PetEntry.COLUMN_SUPPLIEREMAIL + " TEXT )";
        //// + PetEntry.COLUMN_PET_LASTNAME + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + PetEntry.TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String pName, String pModel, byte[] img, double price, int quantity,
                           String sName, String sPhone, String sEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PetEntry.COLUMN_PRODUCTNAME, pName);
        contentValues.put(PetEntry.COLUMN_PRODUCTMODEL, pModel);
        contentValues.put(PetEntry.COLUMN_IMAGE, img);
        contentValues.put(PetEntry.COLUMN_PRICE, price);
        contentValues.put(PetEntry.COLUMN_QUANTITY, quantity);
        contentValues.put(PetEntry.COLUMN_SUPPLIERNAME, sName);
        contentValues.put(PetEntry.COLUMN_SUPPLIERPHONE, sPhone);
        contentValues.put(PetEntry.COLUMN_SUPPLIEREMAIL, sEmail);
        ////contentValues.put(PetEntry.COLUMN_PET_SUPPLIEREMAIL, supplierEmail);

        ////  byte[] img = cursor.getBlob(imageColumnIndex);
        ////  Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);

        ////////// contentValues.put(PetEntry.COLUMN_PET_IMAGE, img);

        long result = db.insert(PetEntry.TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + PetEntry.TABLE_NAME, null);
        return data;
    }

}
