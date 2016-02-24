package com.example.sam.todoapplication;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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
        listViewItemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        todoListView.setAdapter(listViewItemsAdapter);
        readItems();

        Button button1 = (Button) findViewById(R.id.buttonAddItem);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToDoItem();
                saveItems();
            }
        });

        setRemoveItemOnLongClickListener();
    }

    void setRemoveItemOnLongClickListener() {
        todoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                // Remove the item within array at position
                items.remove(pos);
                // Refresh the adapter
                listViewItemsAdapter.notifyDataSetChanged();
                // Return true consumes the long click event (marks it handled)
                return true;
            }

        });
    }

    void addToDoItem() {
        EditText editText1 = (EditText) findViewById(R.id.editTextNewItem);
        listViewItemsAdapter.add(editText1.getText().toString());
        editText1.setText("");
    }

    void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            listViewItemsAdapter.addAll(new ArrayList<>(FileUtils.readLines(todoFile)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void saveItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
