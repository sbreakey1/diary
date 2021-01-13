package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

public class Add extends AppCompatActivity {

    FloatingActionButton enter;
    FloatingActionButton close;
    FloatingActionButton add_back;

    TextView log;

    TextView item;

    com.example.diary.DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        enter = findViewById(R.id.enter);
        close = findViewById(R.id.close);
        log = findViewById(R.id.log);
        item = findViewById(R.id.item);
        add_back = findViewById(R.id.add_back);
        mDatabaseHelper = new com.example.diary.DatabaseHelper(getApplication());

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!(item.getText().toString().equals(""))){

                    if(mDatabaseHelper.isItem(item.getText().toString())==true){
                        log.setText("Item already exists");
                    }
                    else {

                        if(mDatabaseHelper.countItems()==10){
                            log.setText("Reached maximum items");
                        }

                        else {

                            mDatabaseHelper.addData(item.getText().toString());

                            Intent myIntent = new Intent(Add.this, MainActivity.class);
                            startActivity(myIntent);

                        }

                    }
                }
                else{
                    log.setText("Input cannot be empty");
                }

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!(item.getText().toString().equals(""))){

                    if(mDatabaseHelper.isItem(item.getText().toString())==false){
                        log.setText("Item is not found");
                    }
                    else {
                        mDatabaseHelper.deleteData(item.getText().toString());

                        Intent myIntent = new Intent(Add.this, MainActivity.class);
                        startActivity(myIntent);
                    }
                }
                else{
                    log.setText("Input cannot be empty");
                }

            }
        });

        add_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(Add.this, MainActivity.class);
                startActivity(myIntent);

            }
        });

    }
}