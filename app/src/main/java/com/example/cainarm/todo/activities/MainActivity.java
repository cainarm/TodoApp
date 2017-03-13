package com.example.cainarm.todo.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cainarm.todo.R;
import com.example.cainarm.todo.dao.TodoDao;
import com.example.cainarm.todo.model.Todo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshTodoList();

    }

    @Override
    public void onResume(){
        super.onResume();
        refreshTodoList();
    }

    private void refreshTodoList(){
        TodoDao todoHelper = new TodoDao(this);
        ListView todoList = (ListView) findViewById(R.id.todoListView);
        ArrayList<Todo> todos = todoHelper.getAll();

        ArrayAdapter adapter = new ArrayAdapter<Todo>(this, android.R.layout.simple_list_item_1, todos);
        todoList.setAdapter(adapter);

        todoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Todo todo = (Todo) parent.getAdapter().getItem(position);
                Intent it = new Intent(MainActivity.this, Profile.class);
                it.putExtra("id",todo.id);
                startActivity(it);
            }
        });
    }
    public void addTodo(View view){
        EditText input = (EditText) findViewById(R.id.todoInput);
        Todo todo = new Todo(0, input.getText().toString());
        TodoDao helper = new TodoDao(this);

        if(todo.name.length() > 0){
            helper.insert(todo);
            refreshTodoList();

            input.setText("");
        }
    }


}
