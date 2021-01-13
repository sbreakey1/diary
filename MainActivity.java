package com.example.diary;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.example.diary.R.*;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton add;
    FloatingActionButton main_clear;
    com.example.diary.DatabaseHelper mDatabaseHelper;
    TextView main_date;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        add = findViewById(id.add);
        main_clear = findViewById(R.id.main_clear);
        main_date = findViewById(R.id.main_date);
        mDatabaseHelper = new DatabaseHelper(getApplication());
        String currentDate = new SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault()).format(new Date());
        main_date.setText(currentDate);

        Cursor data = mDatabaseHelper.getData();
        ArrayList<String> items = new ArrayList();

        while(data.moveToNext()){
            items.add(data.getString(1));
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(MainActivity.this, Add.class);
                startActivity(myIntent);

            }
        });

        main_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDatabaseHelper.clearData();

                Intent myIntent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(myIntent);

            }
        });

        final ListView li = (ListView) findViewById(id.checkable_list);
        li.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, layout.rowlayout2, id.task_name2, items);
        li.setAdapter(adapter);

        li.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = ((TextView)view.findViewById(id.task_name2)).getText().toString();

                CheckedTextView ctv = (CheckedTextView)view.findViewById(R.id.task_name2);
                if(ctv.isChecked()){
                    ctv.setChecked(false);
                    mDatabaseHelper.removeCompleted(selectedItem);
                }else{
                    ctv.setChecked(true);
                    mDatabaseHelper.setCompleted(selectedItem);
                }


            }

        });

        li.post(new Runnable() {
            @Override
            public void run() {

                Cursor data = mDatabaseHelper.getData();

                while(data.moveToNext()){
                    if(data.getInt(2)==1){
                        View newsItemView = li.getChildAt(data.getPosition());
                        CheckedTextView ctv = (CheckedTextView)newsItemView.findViewById(R.id.task_name2);
                        ctv.setChecked(true);
                    }
                }

            }
        });



    }

}