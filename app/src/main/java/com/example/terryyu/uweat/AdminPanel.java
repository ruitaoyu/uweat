package com.example.terryyu.uweat;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class AdminPanel extends AppCompatActivity{
    SeattleFoodDB myDb;
    TextView txt_Msg;
    Layout lay;
    Button addBtn, viewBtn;
    EditText foodName, foodPrice, foodQuantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        myDb = new SeattleFoodDB(this);
        addBtn = (Button) findViewById(R.id.btn_add);
        viewBtn = (Button) findViewById(R.id.btn_view);
        foodName = (EditText) findViewById(R.id.item);
        foodPrice = (EditText) findViewById(R.id.price);
        foodQuantity = (EditText) findViewById(R.id.quantity);
        viewBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();

                StringBuffer buffer = new StringBuffer();
                if(res.getCount() != 0) {
                    while(res.moveToNext()) {
                        System.out.println(res.getString(1)+"   "+res.getString(2)+"   "+res.getString(3));
                        buffer.append("Id :"+ res.getString(0)+"\n");
                        buffer.append("Name :"+ res.getString(1)+"\n");
                        buffer.append("Price :"+ res.getString(2)+"\n");
                        buffer.append("Quantity :"+ res.getString(3)+"\n\n");


                    }
                    showMessage("Data",buffer.toString());


                }
                //
            }


        });


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertDatabase();

            }
        });
//        myDb = new SeattleFoodDB(this);
//        Cursor res = myDb.getAllData();

    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void insertDatabase(){
        boolean isInserted = myDb.insertData(foodName.getText().toString(),  Float.valueOf(foodPrice.getText().toString()), Integer.parseInt(foodQuantity.getText().toString()));

        if(isInserted == true) {
            Toast.makeText(AdminPanel.this,"Food Item Inserted", Toast.LENGTH_LONG).show();


        } else {
            Toast.makeText(AdminPanel.this,"Food Item Not Inserted", Toast.LENGTH_LONG).show();
        }

    }
}
