package edu.ufcg.jhonatanbds.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Jhonatan on 05/02/2017.
 */

@Document
public class SubToDo {

    @Id
    private String id;
    private String name;
    private boolean completed;

    public SubToDo(String name) {
        this.name = name;
        this.completed = false;
    }

    public SubToDo() {
        this.completed = false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

