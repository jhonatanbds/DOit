package edu.ufcg.jhonatanbds.service;

import edu.ufcg.jhonatanbds.config.MongoConfig;
import edu.ufcg.jhonatanbds.dao.ToDoDAO;
import edu.ufcg.jhonatanbds.entity.ToDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by Jhonatan on 06/02/2017.
 */

@Service
public class ToDoService {

    @Autowired
    private ToDoDAO toDoDAO;

    public List<ToDo> getReversedToDos() {
        List<ToDo> reversedToDos = this.toDoDAO.findAll();
        Collections.reverse(reversedToDos);

        if (reversedToDos.size() <= 6)
            return reversedToDos;
        else
            return reversedToDos.subList(0,5);
    }

    public List<ToDo> findById(String id) {
        return this.toDoDAO.findById(id);
    }

    public void save(ToDo toDo) {
        this.toDoDAO.save(toDo);
    }

    public List<ToDo> findAll() {
        return this.toDoDAO.findAll();
    }

    public void deleteAll() {
        this.toDoDAO.deleteAll();
    }

    public void completeToDo(String id) {
        MongoOperations mongoOperation = getMongoOperations();
        Query searchToDoQuery = new Query(Criteria.where("id").is(id));
        mongoOperation.updateFirst(searchToDoQuery,
                Update.update("completed", !getToDoById(id).isCompleted()),ToDo.class);
    }

    public void deleteToDo(String id) {
        MongoOperations mongoOperation = getMongoOperations();
        Query searchToDoQuery = new Query(Criteria.where("id").is(id));
        mongoOperation.remove(searchToDoQuery, ToDo.class);
    }

    private MongoOperations getMongoOperations() {
        ApplicationContext ctx =
                new AnnotationConfigApplicationContext(MongoConfig.class);
        return (MongoOperations) ctx.getBean("mongoTemplate");
    }

    private ToDo getToDoById(String id) {
        return this.toDoDAO.findById(id).get(0);
    }

}
