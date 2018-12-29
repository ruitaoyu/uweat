package com.example.terryyu.uweat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText email, password, verifyPassword;
    Button btn_Signup;
    TextView txt_Msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        myDb = new DatabaseHelper(this);
        txt_Msg = (TextView) findViewById(R.id.txt_msg);
        txt_Msg.setText("");

        btn_Signup= (Button) findViewById(R.id.btn_signup);

        btn_Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = (EditText) findViewById(R.id.signup_email);
                password= (EditText) findViewById(R.id.signup_pwd);
                verifyPassword = (EditText) findViewById(R.id.signup_verifypwd);


                String str_email = email.getText().toString();

                if(checkemail(str_email)){
                    txt_Msg.setText("Email Exist");
                    System.out.println("email exist");
                }else{
                    txt_Msg.setText("");
                    if(checkPassword(password.getText().toString(), verifyPassword.getText().toString())){
                        //if email is exist and password match
                        insertDatabase();
                        }else{
                        txt_Msg.setText("password not match");
                    }
                }
            }

            //check if the emailid already exist
            public boolean checkemail(String email){
                Cursor res = myDb.getAllData();

                if(res.getCount() != 0) {
                    while(res.moveToNext()) {
                        if(res.getString(1).equals(email)){
                            return true;
                        }

                    }

                }
                return false;
            }
            //method to check if password and verify password are same
            public boolean checkPassword(String p1, String p2){
                return p1.equals(p2);
            }
            //Create new account
            public void insertDatabase(){
                boolean isInserted = myDb.insertData(email.getText().toString(), password.getText().toString());

                if(isInserted == true) {
                    Toast.makeText(SignUpActivity.this,"New Account Created", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(SignUpActivity.this,"Account SignUp Fail", Toast.LENGTH_LONG).show();
                }

            }
//
//
        });


    }


}
