package fh_ku.taskmaster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import fh_ku.taskmaster.R;
import fh_ku.taskmaster.models.Task;
import fh_ku.taskmaster.pickers.DatePicker;
import fh_ku.taskmaster.pickers.TimePicker;
import fh_ku.taskmaster.repositories.DatabaseHelper;
import fh_ku.taskmaster.repositories.TaskRepository;

public class TaskDetailActivity extends AppCompatActivity {

    private static String TAG = TaskDetailActivity.class.getName();
    private Task task;
    private String[] priorities;
    private TaskRepository taskRepository;

    private EditText nameInput;
    private Spinner  priorityInput;
    private TextView dateInput;
    private TextView timeInput;

    public void pickDueDate(String type) {
        if (type == "time") {
            TimePicker tp = new TimePicker() {
                @Override
                public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
                    super.onTimeSet(view, hourOfDay, minute);
                    TaskDetailActivity.this.task.setDueDate(this.getDate());
                    TaskDetailActivity.this.renderTask();
                }
            };
            tp.setDate(this.task.getDueDate());
            tp.show(getSupportFragmentManager(), type+"picker");
        } else if (type == "date") {
            DatePicker dp = new DatePicker() {
                @Override
                public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    super.onDateSet(view, year, monthOfYear, dayOfMonth);
                    TaskDetailActivity.this.task.setDueDate(this.getDate());
                    TaskDetailActivity.this.renderTask();
                }
            };
            dp.setDate(this.task.getDueDate());
            dp.show(getSupportFragmentManager(), type+"picker");
        }
    }

    public void renderTask() {
        Log.i("TASK DETAILS", "Task closed: " + this.task.isClosed());
        nameInput.setText(this.task.getName());
        dateInput.setText(Task.formatDate(this,task.getDueDate()));
        timeInput.setText(Task.formatTime(this,task.getDueDate()));
        priorityInput.setSelection(task.getPriority());
    }

    public void initTask() {
        Intent startIntent = getIntent();
        int taskId = startIntent.getIntExtra("taskId", -1);

        if (taskId >= 0) { // edit mode
            this.task = this.taskRepository.getTask(taskId);
        } else { // create mode
            this.task = new Task();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.taskRepository = new TaskRepository(new DatabaseHelper(this));
        this.priorities = getResources().getStringArray(R.array.task_priorities);
        this.initTask();

        nameInput = (EditText) findViewById(R.id.task_input_name);
        dateInput = (TextView) findViewById(R.id.task_input_date);
        timeInput = (TextView) findViewById(R.id.task_input_time);
        priorityInput = (Spinner) findViewById(R.id.task_input_priority);

        dateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskDetailActivity.this.pickDueDate("date");
            }
        });

        timeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskDetailActivity.this.pickDueDate("time");
            }
        });

        priorityInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TaskDetailActivity.this.task.setPriority(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.renderTask();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            this.task.setName(this.nameInput.getText().toString());

            if (this.task.getId() >= 0) {
                this.taskRepository.updateTask(this.task);
            } else {
                this.taskRepository.addTask(this.task);
            }

            this.startActivity(new Intent(this, TaskListActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
