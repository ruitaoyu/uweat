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
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    DatabaseHelper myDb;

    Button signIn,signUpBtn;

    EditText myEmail, myPasswod;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myDb = new DatabaseHelper(this);

        signUpBtn = (Button) findViewById(R.id.signUpBtn);
        signIn = (Button) findViewById(R.id.signInBtn);

        myEmail = (EditText) findViewById(R.id.editTextEmail);
        myPasswod = (EditText) findViewById(R.id.editTextPW);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        //AddData();

        login();


    }

    public void login() {
        signIn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if(res.getCount() == 0) {
                            showMsg("Error", "No Account In Database!");
                            return;
                        }

                        String currentEmail = myEmail.getText().toString();
                        String currentPW = myPasswod.getText().toString();

                        StringBuffer buffer = new StringBuffer();

                        if(currentEmail.equals("admin")){
                            Intent intent = new Intent(LoginActivity.this, AdminPanel.class);
                            startActivity(intent);

                        } else {
                            while (res.moveToNext()) {

//
                                if (currentEmail.equals(res.getString(1))
                                        && currentPW.equals(res.getString(2))) {
                                    Intent intent = new Intent(LoginActivity.this, SchoolListActivity.class);
                                    startActivity(intent);
                                    return;
                                } else if (currentEmail.equals(res.getString(1))) {
                                    Toast.makeText(LoginActivity.this, "Wrong Password, please enter again", Toast.LENGTH_LONG).show();

// return;
                                }
                            }
                        }
                        Toast.makeText(LoginActivity.this,"Cannot found account", Toast.LENGTH_LONG).show();
                        // show all the data
                        //showMsg("Data", buffer.toString());
                    }
                }
        );
    }

    public void showMsg(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();
    }

    private void AddData() {
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(myEmail.getText().toString(), myPasswod.getText().toString());

                if(isInserted == true) {
                    Toast.makeText(LoginActivity.this,"Data Inserted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this,"Data not inserted", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
