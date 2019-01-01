package com.example.terryyu.uweat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

public class SchoolListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_school_list);

        ImageView seattleView = (ImageView) findViewById(R.id.imageView2);
        seattleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SchoolListActivity.this, seattle_campus.class);
                startActivity(intent);
            }
        });
    }
}
