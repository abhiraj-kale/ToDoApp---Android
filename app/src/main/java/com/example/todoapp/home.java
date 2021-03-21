package com.example.todoapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class home extends Fragment {
    private DBHelper DB;
    private Cursor cursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.task_list);

        this.DB = new DBHelper(getActivity());
        this.cursor = DB.getAllData();

        final int N = cursor.getCount();
        final TextView[] myTextViews = new TextView[N];

        if (N==0){
            TextView t = new TextView(getActivity());
            t.setText("Looks Empty here");
            t.setTextSize(25);
            t.setGravity(Gravity.CENTER);
            linearLayout.addView(t);
        }else{
            try {
                int count = 0;
                while(cursor.moveToNext()){
                    final TextView textView = new TextView(getActivity());
                    textView.setId(Integer.parseInt(cursor.getString(0)));
                    textView.setText(cursor.getString(1));
                    textView.setTextSize(25);
                    textView.setPadding(50,20,10,10);
                    textView.setBackground(getResources().getDrawable(R.drawable.border));

                    // On click show the task on new Fragment
                    textView.setOnClickListener(v -> {
                        Intent i=new Intent(getActivity(), view_task.class);
                        i.putExtra("id",v.getId());
                        startActivity(i);
                    });

                    // On long click give options to delete
                    textView.setOnLongClickListener(v -> {
                        new AlertDialog.Builder(getActivity())
                                .setMessage("Delete this task?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Toast.makeText(getActivity(), String.valueOf(v.getId()), Toast.LENGTH_SHORT).show();
                                        try {
                                            DB.deleteUserData(v.getId());
                                            textView.setVisibility(View.GONE);
                                            Toast.makeText(getActivity(), "Task Deleted", Toast.LENGTH_SHORT).show();
                                        }catch (Exception ignored){}
                                    }
                                }).setNegativeButton("No", null)
                                .show();
                        return true;
                    });

                    linearLayout.addView(textView);
                    myTextViews[count++] = textView;
                }
            }catch (Exception ignored){}
            finally {
                cursor.close();
            }
        }

        return view;
    }

}