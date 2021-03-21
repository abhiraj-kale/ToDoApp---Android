package com.example.todoapp;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class create_fragment extends Fragment {
    private EditText title_field, content_field;
    private Button createTask;
    DBHelper DB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_fragment, container, false);
        DB = new DBHelper(getActivity());
        createTask = (Button) view.findViewById(R.id.create_task);

        createTask.setOnClickListener((View.OnClickListener) v -> {
            title_field = (EditText) view.findViewById(R.id.task_name);
            String title = title_field.getText().toString();
            content_field = (EditText) view.findViewById(R.id.task_content);
            String content = content_field.getText().toString();
            // Check if strings are not empty and proceed
            if(title.matches("") || content.matches(""))
                Toast.makeText(getActivity(), "Fields can't be empty", Toast.LENGTH_SHORT).show();
            else{
                // Make entries in Database
                if (DB.insertUserData(title, content)) {
                    Toast.makeText(getActivity(), "Task Created", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    home NAME = new home();
                    fragmentTransaction.replace(R.id.flFragment, NAME);
                    fragmentTransaction.commit();
                }
                else
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();

                /*
                Cursor cursor = DB.getAllData();
                StringBuffer str = new StringBuffer();
                while(cursor.moveToNext()){
                    str.append("ID: ").append(cursor.getString(0)).append("\n");
                    str.append("title: ").append(cursor.getString(1)).append("\n");
                    str.append("content: ").append(cursor.getString(2)).append("\n");
                }
                 */
            }
        });
            return view;
    }

}