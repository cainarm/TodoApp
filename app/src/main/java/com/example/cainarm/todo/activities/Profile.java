package com.example.cainarm.todo.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.cainarm.todo.R;
import com.example.cainarm.todo.dao.TodoDao;
import com.example.cainarm.todo.model.Todo;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();

        TodoDao helper = new TodoDao(this);
        Todo todo = helper.getById(intent.getIntExtra("id", 0));

        TextView name = (TextView) findViewById(R.id.todoName);
        name.setText(todo.name);
    }
    public void deleteBtnPressed(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmar Ação ");
        builder.setMessage("Tem certeza que deseja deletar esta tarefa ?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent = getIntent();
                delete(intent.getIntExtra("id", 0));
                onBackPressed();
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
    private void delete(int id){
        TodoDao helper = new TodoDao(this);
        helper.delete(id);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
        finish();
    }
}
