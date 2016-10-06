package com.hnam.mytodolist;


import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hnam.mytodolist.model.TodoItem;

import java.util.Calendar;

/**
 * Created by hnam on 10/6/2016.
 */

public class AddTodoFragment extends DialogFragment {

    private static final String TAG = AddTodoFragment.class.getSimpleName();
    private EditText mEdtName;
    private DatePicker mDatePicker;
    private RadioGroup mRadioGroup;
    private TextView mTvCancel;
    private TextView mTvSave;

    public interface OnAddTodoFragmentListener{
        void onAddTodo(String name, String dueTo, int priority);
    }

    private OnAddTodoFragmentListener mListener;

    public AddTodoFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static AddTodoFragment newInstance() {
        AddTodoFragment frag = new AddTodoFragment();
        return frag;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG, "onAttach");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_dialog_add_edit_todo, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setTitle("Add new Todo");
        mEdtName = (EditText) view.findViewById(R.id.dialog_edt_name);
        mDatePicker = (DatePicker) view.findViewById(R.id.dialog_datePicker);
        mRadioGroup = (RadioGroup) view.findViewById(R.id.dialog_radioGroup);
        mTvSave = (TextView) view.findViewById(R.id.dialog_tv_cancel);
        mTvCancel = (TextView) view.findViewById(R.id.dialog_tv_save);


        setDialogButtonListener();
        initDatePicker();
        initPriorityRadioGroup();

        // Show soft keyboard automatically and request focus to field
        mEdtName.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public void onResume() {
        // Store access variables for window and blank point
        Window window = getDialog().getWindow();
        Point size = new Point();
        // Store dimensions of the screen in `size`
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        // Set the width of the dialog proportional to 75% of the screen width
        window.setLayout((int) (size.x * 0.85), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        // Call super onResume after sizing
        super.onResume();
    }


    public View.OnClickListener mOnCancelClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dismiss();
        }
    };

    public View.OnClickListener mOnSaveClick = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            dismiss();
            saveTodo();
        }
    };

    private void setDialogButtonListener(){
        mTvSave.setOnClickListener(mOnCancelClick);
        mTvCancel.setOnClickListener(mOnSaveClick);
    }

    private int mYear;
    private int mMonth;
    private int mDate;

    private void initDatePicker(){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDate = c.get(Calendar.DAY_OF_MONTH);
        Log.e(TAG, mYear + "/" + mMonth + "/" + mDate);
        mDatePicker.init(mYear, mMonth, mDate, mOnDateChangedListener);
    }

    private DatePicker.OnDateChangedListener mOnDateChangedListener = new DatePicker.OnDateChangedListener() {
        @Override
        public void onDateChanged(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
            mYear = selectedYear;
            mMonth = selectedMonth;
            mDate = selectedDay;
        }
    };


    private void initPriorityRadioGroup(){
        mRadioGroup.check(R.id.dialog_rbtn_priority_normal);
        mRadioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);
    }

    private int mPriority = TodoItem.PRIORITY_NORMAL;
    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch (i){
                case R.id.dialog_rbtn_priority_high:
                    mPriority = TodoItem.PRIORITY_HIGH;
                    break;
                case R.id.dialog_rbtn_priority_low:
                    mPriority = TodoItem.PRIORITY_LOW;
                    break;
                default:
                    mPriority = TodoItem.PRIORITY_NORMAL;
                    break;
            }
        }
    };

    private void saveTodo(){
        String name = mEdtName.getText().toString().trim();
        mListener = (OnAddTodoFragmentListener) getActivity();
        String dueTo = String.format("%s/%s/%s", mDate, mMonth+1, mYear);
        mListener.onAddTodo(name, dueTo, mPriority);
    }



}
