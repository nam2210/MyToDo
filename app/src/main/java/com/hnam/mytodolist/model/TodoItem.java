package com.hnam.mytodolist.model;

import java.io.Serializable;

/**
 * Created by hnam on 10/6/2016.
 */

public class TodoItem implements Serializable{
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_NORMAL = 2;
    public static final int PRIORITY_LOW = 3;


    private long id;
    private String name;
    private String dueToDate;
    private int priority;

    public TodoItem(long id, String name, String dueToDate, int priority) {
        this.id = id;
        this.name = name;
        this.dueToDate = dueToDate;
        this.priority = priority;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDueToDate() {
        return dueToDate;
    }

    public int getPriority() {
        return priority;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDueToDate(String dueToDate) {
        this.dueToDate = dueToDate;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "TodoItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dueToDate='" + dueToDate + '\'' +
                ", priority=" + priority +
                '}';
    }
}
