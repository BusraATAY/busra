package com.example.loginpage;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TaskDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        TextView taskDetails = new TextView(this);
        taskDetails.setTextSize(20);
        taskDetails.setPadding(23, 23, 23, 23);

        String taskName = getIntent().getStringExtra("TASK_NAME");
        if (taskName == null || taskName.isEmpty()) {
            taskName = getString(R.string.no_task_name_provided); // Handle null/empty task names
        }

        // Use the string resource with a placeholder
        taskDetails.setText(getString(R.string.task_details, taskName));

        setContentView(taskDetails);
    }
}
