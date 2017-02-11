package edu.ufcg.jhonatanbds.service;

import edu.ufcg.jhonatanbds.config.MongoConfig;
import edu.ufcg.jhonatanbds.dao.ToDoDAO;
import edu.ufcg.jhonatanbds.dao.ToDoListDAO;
import edu.ufcg.jhonatanbds.entity.SubToDo;
import edu.ufcg.jhonatanbds.entity.ToDo;
import edu.ufcg.jhonatanbds.entity.ToDoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * Created by Jhonatan on 06/02/2017.
 */

@Service
public class ToDoListService {

    @Autowired
    private ToDoListDAO toDoListDAO;
    @Autowired
    private ToDoDAO toDoDAO;

    public List<ToDo> getReversedToDos() {

        List<ToDo> reversedToDos = this.getAllToDos();
        Collections.reverse(reversedToDos);

        if (reversedToDos.size() <= 6)
            return reversedToDos;
        else
            return reversedToDos.subList(0,5);
    }


    public void addToDo(ToDo toDo) {

        if (this.toDoListDAO.findAll().size() == 0)
            toDoListDAO.save(new ToDoList("all"));

        if (toDo.getId() == null)
            this.toDoDAO.save(toDo);

        updateToDo(toDo);
    }

    private void updateToDo(ToDo toDo) {
        if (toDo.getAssociatedListId() != null)
            updateAssociatedList(toDo);
        if (toDo.getSubToDos() != null)
            updateSubToDos(toDo);
        if (toDo.getObservation() != null)
            updateObservation(toDo);
        updateCategory(toDo);
        updatePriority(toDo);
        updateCompleted(toDo);


    }

    private void updateCompleted(ToDo toDo) {
        toDo.setCompleted(toDo.isCompleted());
        MongoOperations mongoOperation = getMongoOperations();
        Query searchToDoQuery = new Query(where("id").is(toDo.getId()));
        mongoOperation.updateFirst(searchToDoQuery,
                Update.update("completed", toDo.isCompleted()),ToDo.class);
    }

    private void updateObservation(ToDo toDo) {
        toDo.setObservation(toDo.getObservation());
        MongoOperations mongoOperation = getMongoOperations();
        Query searchToDoQuery = new Query(where("id").is(toDo.getId()));
        mongoOperation.updateFirst(searchToDoQuery,
                Update.update("observation", toDo.getObservation()),ToDo.class);
    }

    private void updateSubToDos(ToDo toDo) {
        toDo.setSubToDos(toDo.getSubToDos());
        MongoOperations mongoOperation = getMongoOperations();
        Query searchToDoQuery = new Query(where("id").is(toDo.getId()));
        mongoOperation.updateFirst(searchToDoQuery,
                Update.update("subToDos", toDo.getSubToDos()),ToDo.class);
    }

    private void updatePriority(ToDo toDo) {
        toDo.setPriority(toDo.getPriority());
        MongoOperations mongoOperation = getMongoOperations();
        Query searchToDoQuery = new Query(where("id").is(toDo.getId()));
        mongoOperation.updateFirst(searchToDoQuery,
                Update.update("priority", toDo.getPriority()),ToDo.class);
    }

    private void updateCategory(ToDo toDo) {
        toDo.setCategory(toDo.getCategory());
        MongoOperations mongoOperation = getMongoOperations();
        Query searchToDoQuery = new Query(where("id").is(toDo.getId()));
        mongoOperation.updateFirst(searchToDoQuery,
                Update.update("category", toDo.getCategory()),ToDo.class);
    }

    private void updateAssociatedList(ToDo toDo) {
        toDo.setAssociatedList(this.toDoListDAO.findOne(toDo.getAssociatedListId()));
        MongoOperations mongoOperation = getMongoOperations();
        Query searchToDoQuery = new Query(where("id").is(toDo.getId()));
        mongoOperation.updateFirst(searchToDoQuery,
                Update.update("associatedList", toDo.getAssociatedList()),ToDo.class);
        mongoOperation.updateFirst(searchToDoQuery,
                Update.update("associatedListId", toDo.getAssociatedListId()),ToDo.class);
    }


    public void deleteAllToDos() {
        this.toDoDAO.deleteAll();
    }

    public void completeToDo(String id) {
        MongoOperations mongoOperation = getMongoOperations();
        Query searchToDoQuery = new Query(where("id").is(id));
        mongoOperation.updateFirst(searchToDoQuery,
                Update.update("completed", !getToDoById(id).isCompleted()),ToDo.class);

    }

    public void deleteToDo(String id) {
        MongoOperations mongoOperation = getMongoOperations();
        Query searchToDoQuery = new Query(where("id").is(id));
        mongoOperation.remove(searchToDoQuery, ToDo.class);
    }

    private MongoOperations getMongoOperations() {
        ApplicationContext ctx =
                new AnnotationConfigApplicationContext(MongoConfig.class);
        return (MongoOperations) ctx.getBean("mongoTemplate");
    }

    public ToDo getToDoById(String id) {
        return this.toDoDAO.findOne(id);
    }

    public List<ToDo> getAllToDos() {
        return this.toDoDAO.findAll();
    }

    public ToDoList getMainList() {
        return this.toDoListDAO.findByName("all").get(0);
    }

    public void addSubToDo(String id, SubToDo subToDo) {

        ToDo toDo = this.toDoDAO.findOne(id);
        toDo.addSubToDo(subToDo);

        MongoOperations mongoOperation = getMongoOperations();
        Query searchToDoQuery = new Query(where("id").is(id));
        mongoOperation.updateFirst(searchToDoQuery,
                Update.update("subToDos", toDo.getSubToDos()),ToDo.class);


    }

    public void deleteSubToDo(String sId, String tId) {
        ToDo toDo = this.toDoDAO.findOne(tId);
        toDo.getSubToDos().remove(findSubToDo(sId, toDo));
        MongoOperations mongoOperation = getMongoOperations();
        Query searchToDoQuery = new Query(where("id").is(tId));
        mongoOperation.updateFirst(searchToDoQuery,
                Update.update("subToDos", toDo.getSubToDos()),ToDo.class);
    }

    public void completeSubToDo(String sId, String tId) {
        ToDo toDo = this.toDoDAO.findOne(tId);
        SubToDo subToDo = findSubToDo(sId, toDo);
        subToDo.setCompleted(!subToDo.isCompleted());
        MongoOperations mongoOperation = getMongoOperations();
        Query searchToDoQuery = new Query(where("id").is(tId));
        mongoOperation.updateFirst(searchToDoQuery,
                Update.update("subToDos", toDo.getSubToDos()),ToDo.class);
    }

    private SubToDo findSubToDo(String id, ToDo toDo) {
        for (SubToDo subToDo : toDo.getSubToDos())
            if(subToDo.getId().equals(id)) return subToDo;
        return null;
    }

    public List<ToDo> incompleteToDos() {
        List<ToDo> incompleteToDos = new ArrayList<>();
        for (ToDo toDo : getAllToDos())
            if (!toDo.isCompleted())
                incompleteToDos.add(toDo);
        return incompleteToDos;
    }

    public List<ToDoList> getAllLists() {
        return this.toDoListDAO.findAll();
    }

    public void addToDoList(ToDoList toDoList) {
        this.toDoListDAO.save(toDoList);
    }

    public List<ToDo> getToDoList(String id) {
        return findByAssociatedListId(id);
    }

    public List<ToDo> getToDosByPriority(String priority) {
        return this.toDoDAO.findByPriority(priority);
    }

    public List<ToDo> getToDosByCategory(String category) {
        return this.toDoDAO.findByCategory(category);
    }

    public float getPercentCompleted(String id) {
        List<ToDo> toDos = findByAssociatedListId(id);
        if (toDos.size() == 0)
            return 0;
        int total= 0;
        for (ToDo toDo : toDos)
            if (toDo.isCompleted())
                total++;
        return (total*100)/toDos.size();
    }

    public float getPercentCompleted() {
        List<ToDo> toDos = this.toDoDAO.findAll();
        if (toDos.size() == 0)
            return 0;
        int total= 0;
        for (ToDo toDo : toDos)
            if (toDo.isCompleted())
                total++;

        return (total*100)/toDos.size();
    }

    public int getCompletedNumber() {
        List<ToDo> toDos = this.toDoDAO.findAll();
        int total= 0;
        for (ToDo toDo : toDos)
            if (toDo.isCompleted()) {
                total++;
            }
        return total;
    }

    public int getCompletedNumber(String id) {
        List<ToDo> toDos = findByAssociatedListId(id);
        int total= 0;
        for (ToDo toDo : toDos)
            if (toDo.isCompleted()) {
                total++;
            }
        return total;
    }

    private List<ToDo> findByAssociatedListId(String id) {
        MongoOperations mongoOperation = getMongoOperations();
        Query searchToDoQuery = new Query(where("associatedListId").is(id));
        return mongoOperation.find(searchToDoQuery, ToDo.class);
    }

    public void deleteToDoList(String id) {
        MongoOperations mongoOperation = getMongoOperations();
        Query searchToDoQuery = new Query(where("associatedListId").is(id));
        Query searchToDoListQuery = new Query(where("id").is(id));
        mongoOperation.remove(searchToDoQuery, ToDo.class);
        mongoOperation.remove(searchToDoListQuery, ToDoList.class);

    }

    public void deleteAllLists() {
        this.toDoListDAO.deleteAll();
    }

    public int toComplete() {
        List<ToDo> toDos = this.toDoDAO.findAll();
        int total = 0;
        for (ToDo toDo : toDos) {
            if (!toDo.isCompleted())
                total++;
        }
        return total;
    }
}

