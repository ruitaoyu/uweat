package com.example.terryyu.uweat;
import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class seattle_campus extends AppCompatActivity {
    SeattleFoodDB myDb;
    LinearLayout root;
    TextView t[];
    Button b;
    ScrollView scrollview;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDb = new SeattleFoodDB(this);
        Cursor res = myDb.getAllData();
        setContentView(R.layout.seattle_campus);
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearlayout);
        //TextView txtview = new TextView(this);

        StringBuffer buffer = new StringBuffer();
        if(res.getCount() != 0) {
            int i=0;
            while(res.moveToNext()) {
                System.out.println(res.getString(1)+"   "+res.getString(2)+"   "+res.getString(3));
                buffer.append("Id :"+ res.getString(0)+"\n");
                buffer.append("Name :"+ res.getString(1)+"\n");
                buffer.append("Price :"+ res.getString(2)+"\n");
                buffer.append("Quantity :"+ res.getString(3)+"\n\n");
                // Textview to for foodname, price and quantity available from database
                TextView foodDetail=new TextView(this);
                foodDetail.setText(res.getString(1)+"       "+"$"+res.getString(2)+"each"+"     "+"Maximum available = "+res.getString(3));

                // text field to enter the quantity to order by customer
                editText = new EditText(this);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                editText.setText("0");

                i=Integer.parseInt(res.getString(0));
                //System.out.println(i);
                editText.setId(i);

                // add foodDetail and editText in layOut
                linearLayout.addView(foodDetail);
                linearLayout.addView(editText);

            }
            //Order Button
            Button orderBtn= new Button(this);
            orderBtn.setText("Order");
            orderBtn.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //condition to check if the order quantity is greater than quantity available
                            if(checkErrorOrderQuantity()== false) {
                                Cursor res = myDb.getAllData();
                                if(res.getCount() != 0) {
                                    int i = 0;
                                    EditText text_field;
                                    while (res.moveToNext()) {
                                        text_field = (EditText) findViewById(Integer.parseInt(res.getString(0)));
                                        //pass id of food and quantity ordered in OrderFoodQuantity method of SeattleFoodDB
                                        myDb.OrderFoodQuantity(Integer.parseInt(res.getString(0)),Integer.parseInt(text_field.getText().toString()));
                                        System.out.println("$$$$$$$$");


                                    }
                                }

                            }else{
                                System.out.println("error in order");
                            }

                        }


                    });


            linearLayout.addView(orderBtn);


//            showMessage("Data",buffer.toString());


        }


    }

    /**
     * Method to check if the order food is greater than food available
     * @return
     */
    public boolean checkErrorOrderQuantity(){

        Cursor res = myDb.getAllData();
        Boolean error=false;
        EditText text_field;
        if(res.getCount() != 0) {
            int i = 0;

            while (res.moveToNext()) {


                text_field = (EditText) findViewById(Integer.parseInt(res.getString(0)));
                System.out.println("--------------");
                if(text_field.getText().toString().equals("")){
//                    int i =0;

//                    text_field.setText(0);

                    System.out.println(Integer.parseInt(text_field.getText().toString()));

                }
                if(Integer.parseInt(res.getString(0)) < Integer.parseInt(text_field.getText().toString()) ){
                    error = true;
                }

            }
        }
        return error;
    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
