package com.example.sam.todoapplication;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.content.Intent;
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
    // REQUEST_CODE can be any value we like, used to determine the result type later
    public static final int REQUEST_CODE = 20;

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
        setOnItemClickListener();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name and position values from result extras
            String editedTask = data.getExtras().getString("editedTask");
            int position = data.getExtras().getInt("position");

            items.set(position, editedTask);
            listViewItemsAdapter.notifyDataSetChanged();
        }
    }

    void setOnItemClickListener() {
        todoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                i.putExtra("itemToEdit", listViewItemsAdapter.getItem(position));
                i.putExtra("position", position);
                startActivityForResult(i, REQUEST_CODE);
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
