package com.hnam.mytodolist.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hnam.mytodolist.R;
import com.hnam.mytodolist.model.TodoItem;

/**
 * Created by hnam on 10/6/2016.
 */

public class TodoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{



    public interface  OnTodoViewListener{
        void onRowClick(int position);
        void onRowLongClick(int position);
    }

    private TextView tvName;
    private TextView tvDescription;
    private TextView tvPriority;

    private Context context;
    private int position = -1;
    private OnTodoViewListener mOnTodoViewListener;

    public TodoViewHolder(View itemView, OnTodoViewListener listener) {
        super(itemView);
        context = itemView.getContext();
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/" + context.getString(R.string.condensedBold_font));
        Typeface tf1 = Typeface.createFromAsset(context.getAssets(), "fonts/" + context.getString(R.string.bold_font));
        tvName = (TextView) itemView.findViewById(R.id.todo_tv_name);
        tvName.setTypeface(tf1);
        tvDescription = (TextView) itemView.findViewById(R.id.todo_tv_description);
        tvPriority = (TextView) itemView.findViewById(R.id.todo_tv_priority);
        tvPriority.setTypeface(tf);
        mOnTodoViewListener = listener;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void renderUi(int position, TodoItem item) {
        this.position = position;
        tvName.setText(item.getName());
        tvPriority.setText(generatePriorityText(item.getPriority()));
        tvPriority.setTextColor(ContextCompat.getColor(context, generateColor(item.getPriority())));
        String description = "Due to " + item.getDueToDate();
        tvDescription.setText(description);

    }

    private String generatePriorityText(int priority){
        switch (priority) {
            case TodoItem.PRIORITY_HIGH:
                return "HIGH";
            case TodoItem.PRIORITY_LOW:
                return "LOW";
            default:
                return "NORMAL";
        }
    }

    private int generateColor(int priority){
        switch (priority) {
            case TodoItem.PRIORITY_HIGH:
                return R.color.red;
            case TodoItem.PRIORITY_LOW:
                return R.color.green;
            default:
                return R.color.yellow;
        }
    }

    @Override
    public void onClick(View view) {
        if (mOnTodoViewListener != null) {
            mOnTodoViewListener.onRowClick(position);
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (mOnTodoViewListener != null){
            mOnTodoViewListener.onRowLongClick(position);
        }
        return true;
    }
}
