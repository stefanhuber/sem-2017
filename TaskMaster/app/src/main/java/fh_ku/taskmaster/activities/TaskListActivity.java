package fh_ku.taskmaster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import fh_ku.taskmaster.R;
import fh_ku.taskmaster.adapters.DividerItemDecorator;
import fh_ku.taskmaster.adapters.TaskAdapter;
import fh_ku.taskmaster.repositories.DatabaseHelper;
import fh_ku.taskmaster.repositories.TaskRepository;

public class TaskListActivity extends AppCompatActivity {

    public static TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView rv = (RecyclerView) findViewById(R.id.task_list);
        TaskRepository taskRepository = new TaskRepository(new DatabaseHelper(this));
        this.adapter = new TaskAdapter(taskRepository);

        rv.addItemDecoration(new DividerItemDecorator(this, DividerItemDecorator.VERTICAL_LIST));
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnUpdateTouchListener(new TaskAdapter.OnUpdateTouchListener() {
            @Override
            public void onUpdateTouch(int id) {
                Intent intent = new Intent(TaskListActivity.this, TaskDetailActivity.class);
                intent.putExtra("taskId", id);
                TaskListActivity.this.startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskListActivity.this.startActivity(new Intent(TaskListActivity.this,TaskDetailActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.init();
    }
}
