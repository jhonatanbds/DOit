package edu.ufcg.jhonatanbds.entity;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by Jhonatan on 10/01/2017.
 */

@Document
public class ToDo {

    private String name;
    private boolean completed;
    private String observation;
    @Field("priority")
    private Priority priority;
    @Field("category")
    private Category category;

    @PersistenceConstructor
    public ToDo(String name, String observation) {
        this.name = name;
        this.completed = false;
        this.observation = observation;
        this.priority = Priority.MEDIUM;
        this.category = Category.STANDARD;
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

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ToDo{" +
                ", name='" + name + '\'' +
                ", completed=" + completed + '\'' +
                ", observation=" + observation + '\'' +
                ", priority=" + priority + '\'' +
                ", category=" + category +
                '}';
    }
}
