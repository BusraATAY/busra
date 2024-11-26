package com.example.loginpage;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {
    CalendarView calendarView;
    ListView taskListView;
    DBHelper dbHelper;
    ArrayList<String> taskList;
    ArrayAdapter<String> taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        // Bind UI components
        calendarView = findViewById(R.id.calendarView);
        taskListView = findViewById(R.id.taskListView);
        dbHelper = new DBHelper(this);

        // Set the CalendarView to start at November 2024
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2024);
        calendar.set(Calendar.MONTH, Calendar.NOVEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        long startDate = calendar.getTimeInMillis();
        calendarView.setDate(startDate, false, true);

        // Initialize task list and adapter
        taskList = new ArrayList<>();
        taskAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);
        taskListView.setAdapter(taskAdapter);

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // Format the selected date explicitly using Locale
            String selectedDate = String.format(Locale.US, "%04d-%02d-%02d", year, month + 1, dayOfMonth);
            loadTasksForDate(selectedDate);
        });


        // Handle task item clicks
        taskListView.setOnItemClickListener((parent, view, position, id) -> {
            String task = taskList.get(position);
            Intent intent = new Intent(this, TaskDetailsActivity.class);
            intent.putExtra("TASK_NAME", task);
            startActivity(intent);
        });
    }

    private void loadTasksForDate(String date) {
        taskList.clear(); // Clear the current list
        Cursor cursor = dbHelper.getTasksForDate(date);

        if (cursor != null) {
            try {
                if (cursor.getCount() > 0) {
                    int taskNameIndex = cursor.getColumnIndexOrThrow("task_name");
                    while (cursor.moveToNext()) {
                        taskList.add(cursor.getString(taskNameIndex));
                    }
                } else {
                    taskList.add("No tasks found for this date.");
                }
            } finally {
                cursor.close();
            }
        } else {
            taskList.add("No tasks found for this date.");
        }

        taskAdapter.notifyDataSetChanged(); // Update the ListView
    }
}

