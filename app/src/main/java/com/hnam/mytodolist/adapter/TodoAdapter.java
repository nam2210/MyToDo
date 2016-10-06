package com.hnam.mytodolist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hnam.mytodolist.R;
import com.hnam.mytodolist.model.TodoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hnam on 10/6/2016.
 */

public class TodoAdapter extends RecyclerView.Adapter<TodoViewHolder> implements TodoViewHolder.OnTodoViewListener {

    public interface OnTodoAdapterListener{
        void onRowClick(TodoItem item);
        void onRowLongClick(TodoItem item);
    }

    private static final String TAG = TodoAdapter.class.getSimpleName();
    LayoutInflater mInflater;
    private List<TodoItem> items;
    private OnTodoAdapterListener mListener;

    public TodoAdapter(Context context, OnTodoAdapterListener listener){
        mInflater = LayoutInflater.from(context);
        items = new ArrayList<>();
        mListener = listener;
    }

    public void setData(List<TodoItem> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }


    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_todo, parent, false);
        return new TodoViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {
        TodoItem item = items.get(position);
        holder.renderUi(position, item);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public void onRowClick(int position) {
        TodoItem item = items.get(position);
        mListener.onRowClick(item);
    }

    @Override
    public void onRowLongClick(int position) {
        TodoItem item = items.get(position);
        mListener.onRowLongClick(item);
    }
}
