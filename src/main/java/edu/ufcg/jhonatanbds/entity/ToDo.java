package edu.ufcg.jhonatanbds.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jhonatan on 10/01/2017.
 */

@Document(collection = "newDB")
public class ToDo implements Comparable<ToDo> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @Field(value = "priority")
    private String priority;
    @Field(value = "category")
    private String category;
    private String name;
    @Field(value = "observation")
    private String observation;
    @DBRef
    private ToDoList associatedList;
    private String associatedListId;
    private boolean completed;
    @DBRef
    private List<SubToDo> subToDos = new ArrayList<>();

    public ToDo() {
    }

    public void setSubToDos(List<SubToDo> subToDos) {
        this.subToDos = subToDos;
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

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<SubToDo> getSubToDos() {
        return subToDos;
    }

    public String getId() {
        return id;
    }

    public void addSubToDo(SubToDo subToDo) {
        this.subToDos.add(subToDo);
    }

    public void setId(String id) {
        this.id = id;
    }

    public ToDoList getAssociatedList() {
        return associatedList;
    }

    public void setAssociatedList(ToDoList associatedList) {
        this.associatedList = associatedList;
    }

    public String getAssociatedListId() {
        return associatedListId;
    }

    public void setAssociatedListId(String associatedListId) {
        this.associatedListId = associatedListId;
    }

    @Override
    public int compareTo(ToDo o) {
        return this.name.toLowerCase().compareTo(o.getName().toLowerCase());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ToDo toDo = (ToDo) o;

        return id != null ? id.equals(toDo.id) : toDo.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ToDo [id=" + id + ", name=" + name + ", associatedList=" + associatedList + ", associatedListId=" + associatedListId + ", observation=" + observation + ", priority=" + priority +
                ", category=" + category + "]";
    }
}

