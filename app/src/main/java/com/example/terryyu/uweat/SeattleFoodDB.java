package com.example.terryyu.uweat;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SeattleFoodDB extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "Seattle_Food.db";
    public static final String TABLE_NAME = "Seattle_table";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "ITEM_NAME";
    public static final String COL_3 = "PRICE";
    public static final String COL_4 = "QUANTITY";

    public SeattleFoodDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, ITEM_NAME TEXT, PRICE FLOAT, QUANTITY INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String food, float price, int quan ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, food);
        contentValues.put(COL_3, price);
        contentValues.put(COL_4, quan);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    /**
     * This method helps to update the database food quantity after the food order.
     * @param id, item id of a table
     * @param quantity, quantity order by customer which is to be deduced from Quantity of table
     */
    public void OrderFoodQuantity(int id, int quantity){
        SQLiteDatabase db = this.getWritableDatabase();
        //select the table row of selected id
        Cursor r = db.rawQuery("select * from  "+TABLE_NAME +" WHERE ID ="+id,null);

        if(r.getCount() != 0) {
//            int i = 0;
            while (r.moveToNext()) {
               // System.out.println("size of cursor  --- "+ r.getColumnCount());
                //System.out.println("--"+r.getString(3)+"to string \n");
                //quan variable to store the database quantity minus food ordr quantity
                int quan = Integer.parseInt(r.getString(3))-quantity;
                //System.out.println(quan);

                //query to update database quantity
                db.rawQuery("UPDATE " + TABLE_NAME+" SET QUANTITY = "+quan+" WHERE ID = "+id,null);

            }
        }






    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME,null);
        return res;
    }

//    public Cursor checkEmail(String email){
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("select * from " + TABLE_NAME + "WHERE  EMAIL =" + email,null);
//
//        return res;
//    }
}
