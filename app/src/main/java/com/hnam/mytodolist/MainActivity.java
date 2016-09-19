package com.hnam.mytodolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Button btnAddItem;

    private ArrayList<String> mItems;
    private ArrayAdapter<String> mAdapter;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddItem = (Button) findViewById(R.id.btnAddItem);
        lv = (ListView) findViewById(R.id.lvItems);

        mItems = new ArrayList<>();
        readItems();

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mItems);
        lv.setAdapter(mAdapter);


        btnAddItem.setOnClickListener(onAddItemClick);
        setListenerForListView();

    }

    private View.OnClickListener onAddItemClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EditText edt = (EditText) findViewById(R.id.edtNewItem);
            String newItem = edt.getText().toString().trim();
            mAdapter.add(newItem);
            edt.setText("");
            writeItems(); // write all items on file
        }
    };

    private void setListenerForListView(){
        lv.setOnItemClickListener(mOnItemClickListener);
        lv.setOnItemLongClickListener(mOnItemLongClickListener);
    }

    private AdapterView.OnItemLongClickListener mOnItemLongClickListener =
            new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            mItems.remove(i);
            mAdapter.notifyDataSetChanged();
            writeItems(); //writel all items on file
            return true;
        }
    };

    private static final int REQUEST_CODE_EDIT_ITEM = 1;
    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String body = mAdapter.getItem(i);
            EditItemActivity.showEditItemScreen(MainActivity.this, REQUEST_CODE_EDIT_ITEM, i, body);

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_EDIT_ITEM && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            int position = bundle.getInt(EditItemActivity.ARG_POSITION);
            String body = bundle.getString(EditItemActivity.ARG_BODY);
            mItems.set(position, body);
            mAdapter.notifyDataSetChanged();
            writeItems();
        }
    }

    private void readItems(){
        File dir = getFilesDir();
        File file = new File(dir, "mytodo.txt");
        Log.e(TAG,"path file --> " + file.getAbsolutePath());
        try {
            mItems = new ArrayList<>(FileUtils.readLines(file));
        } catch (IOException e) {
            mItems = new ArrayList<>();
        }
    }

    private void writeItems(){
        File dir = getFilesDir();
        File file = new File(dir, "mytodo.txt");
        try {
            FileUtils.writeLines(file, mItems);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
