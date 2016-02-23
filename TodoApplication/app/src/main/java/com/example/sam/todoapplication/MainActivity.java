package com.example.sam.todoapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView todoListView;
    ArrayList<String> items;
    ArrayAdapter<String> listViewItemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        todoListView = (ListView) findViewById(R.id.todoListView);
        items = new ArrayList<>();
        listViewItemsAdapter = new ArrayAdapter<> (this, android.R.layout.simple_list_item_1, items);
        todoListView.setAdapter(listViewItemsAdapter);
        listViewItemsAdapter.add("Start adding items! Here's one.");
        listViewItemsAdapter.add("Here's another!");
    }
}
