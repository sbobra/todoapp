package com.example.sam.todoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {
    private EditText editText;
    private View finishedButton;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        editText = (EditText) findViewById(R.id.editText2);
        finishedButton = findViewById(R.id.button);
        editText.setText(getIntent().getStringExtra("itemToEdit"));
        position = getIntent().getIntExtra("position", 0);

        finishedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishEditTask();
            }
        });
    }

    void finishEditTask() {
        // Prepare data intent
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("editedTask", editText.getText().toString());
        data.putExtra("code", MainActivity.REQUEST_CODE);
        data.putExtra("position", position);

        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }
}
