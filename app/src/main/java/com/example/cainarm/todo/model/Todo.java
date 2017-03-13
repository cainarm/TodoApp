package com.example.cainarm.todo.model;

/**
 * Created by cainarm on 2/24/17.
 */

public class Todo {
    public int id;
    public String name;

    public Todo(int id_, String name_){
        this.id = id_;
        this.name = name_;
    }
    @Override
    public String toString(){
        return this.name;
    }
}
