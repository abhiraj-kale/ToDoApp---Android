package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

public class view_task extends AppCompatActivity {
    private DBHelper DB;
    private int id;
    private String title, content;
    private EditText view_task_title, view_task_content;
    private Button view_task_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        actionBar.setDisplayHomeAsUpEnabled(true);
        this.id = (int) getIntent().getSerializableExtra("id");

        DB = new DBHelper(this);
        Cursor cursor = DB.getInfo(id);
        cursor.moveToPosition(0);
        //Extract Contents from the cursor
        this.title = cursor.getString(0);
        this.content = cursor.getString(1);

        this.view_task_title = findViewById(R.id.view_task_title);
        view_task_title.setText(title);
        this.view_task_content = findViewById(R.id.view_task_content);
        view_task_content.setText(content);
        this.view_task_save = findViewById(R.id.view_task_save);

        // Save changes on save button click
        view_task_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_changes();
            }
        });
    }

    private void save_changes() {
        EditText newTitle = findViewById(R.id.view_task_title);
        EditText newContent = findViewById(R.id.view_task_content);
        try {
            DB.updateUserData(newTitle.getText().toString(), newContent.getText().toString(), id);
            Toast.makeText(view_task.this, "Saved Changes", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(view_task.this, "An Error Occurred", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                save_changes();
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}