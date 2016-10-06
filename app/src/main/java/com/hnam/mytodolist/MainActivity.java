package com.hnam.mytodolist;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.hnam.mytodolist.adapter.TodoAdapter;
import com.hnam.mytodolist.database.TodoDataSource;
import com.hnam.mytodolist.model.TodoItem;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.name;

public class MainActivity extends AppCompatActivity implements
        AddTodoFragment.OnAddTodoFragmentListener, TodoAdapter.OnTodoAdapterListener,
        EditTodoFragment.OnEditTodoFragmentListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    private Button btnAddItem;

    private RecyclerView rvItems;
    private TodoAdapter mTodoAdapter;
    private List<TodoItem> mTodoItems;

    private TodoDataSource mDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddItem = (Button) findViewById(R.id.btnAddItem);
        rvItems = (RecyclerView) findViewById(R.id.rvItems);

        mDataSource = new TodoDataSource(this);
        readItems();

        mTodoAdapter = new TodoAdapter(MainActivity.this, this);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rvItems.setHasFixedSize(true);
        rvItems.setLayoutManager(lm);
        rvItems.setAdapter(mTodoAdapter);
        mTodoAdapter.setData(mTodoItems);


        btnAddItem.setOnClickListener(onAddItemClick);
    }

    private View.OnClickListener onAddItemClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FragmentManager fm = getFragmentManager();
            AddTodoFragment addDialogFragment = AddTodoFragment.newInstance();
            addDialogFragment.show(fm, "fragment_add_name");
        }
    };


    private void readItems(){
        mTodoItems = mDataSource.getTodoList();
    }

    @Override
    public void onAddTodo(String name, String dueTo, int priority) {
        if (isNameValid(name)) {
            mDataSource.addTodoItem(name, dueTo, priority);
            notifyDateChanges();
        } else {
            showWarningDialog("Please enter task name");
        }
    }

    private boolean isNameValid(String name){
        if (name == null || name.isEmpty()){
            return false;
        }
        return true;
    }

    private void notifyDateChanges(){
        readItems();
        mTodoAdapter.setData(mTodoItems);
    }

    @Override
    public void onRowClick(TodoItem item) {
        FragmentManager fm = getFragmentManager();
        EditTodoFragment editDialogFragment = EditTodoFragment.newInstance(item);
        editDialogFragment.show(fm, "fragment_edit_name");
    }

    @Override
    public void onRowLongClick(TodoItem item) {
        final TodoItem item1 = item;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert")
                .setMessage("Do you want to delete this Todo ?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        mDataSource.removeTodo(item1);
                        notifyDateChanges();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public void onEditTodo(TodoItem item) {
        mDataSource.updateTodoItem(item);
        notifyDateChanges();
    }

    private void showWarningDialog(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert")
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
