package com.hnam.mytodolist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    public static final String ARG_POSITION = "arg_position";
    public static final String ARG_BODY = "arg_body";

    public static void showEditItemScreen(Activity activity, int requestCode, int position, String itemBody){
        Intent i = new Intent(activity, EditItemActivity.class);
        i.putExtra(ARG_POSITION, position);
        i.putExtra(ARG_BODY, itemBody);
        activity.startActivityForResult(i, requestCode);
    }

    private EditText edtValue;
    private int mPosition;
    private String mBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Bundle data = getIntent().getExtras();
        if (data != null) {
            mPosition = data.getInt(ARG_POSITION);
            mBody = data.getString(ARG_BODY);
        }
        edtValue = (EditText) findViewById(R.id.edtValue);
        edtValue.setText("");
        edtValue.append(mBody);
    }

    public void onSaveClick(View view) {
        String body = edtValue.getText().toString().trim();
        Intent i = new Intent();
        i.putExtra(ARG_POSITION, mPosition);
        i.putExtra(ARG_BODY, body);
        setResult(RESULT_OK, i);
        finish();
    }
}
