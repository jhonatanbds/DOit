package edu.ufcg.jhonatanbds.entity;

import edu.ufcg.jhonatanbds.entity.category.Category;
import edu.ufcg.jhonatanbds.entity.category.StandardCategory;
import edu.ufcg.jhonatanbds.entity.priority.MediumPriority;
import edu.ufcg.jhonatanbds.entity.priority.Priority;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jhonatan on 10/01/2017.
 */

@Entity
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String name;
    private boolean completed;
    private String observation;
    private Priority priority;
    private Category category;
    private EntityFactory entityFactory;
    private List<SubToDo> subToDos;

    public ToDo(String name, String observation, String category, String priority) {
        this.entityFactory = new EntityFactory();
        this.name = name;
        this.completed = false;
        this.observation = observation;
        this.priority = this.entityFactory.makePriority(priority);
        this.category = this.entityFactory.makeCategory(category);
        this.subToDos = new ArrayList<>();
    }

    public ToDo() {
        this.entityFactory = new EntityFactory();
        this.completed = false;
        this.category = new StandardCategory();
        this.priority = new MediumPriority();
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
        return priority.toString();
    }

    public void setPriority(String priority) {
        this.priority = entityFactory.makePriority(priority);
    }

    public String getCategory() {
        return category.toString();
    }

    public void setCategory(String category) {
        this.category = entityFactory.makeCategory(category);
    }

    @Override
    public String toString() {
        return "ToDo{" +
                "name='" + name + '\'' +
                ", completed=" + completed + '\'' +
                ", observation=" + observation + '\'' +
                ", priority=" + priority + '\'' +
                ", category=" + category +
                '}';
    }

    public List<SubToDo> getSubToDos() {
        return subToDos;
    }

    public String getId() {
        return id;
    }

    public void addSubToDo(String name) {
        this.subToDos.add(new SubToDo(name));
    }
}
