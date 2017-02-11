package edu.ufcg.jhonatanbds.dao;

import edu.ufcg.jhonatanbds.entity.ToDo;
import edu.ufcg.jhonatanbds.entity.ToDoList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import java.util.List;

/**
 * Created by Jhonatan on 07/02/2017.
 */

@Repository
public interface ToDoDAO extends MongoRepository<ToDo, String> {

    List<ToDo> findByPriority(String priority);
    List<ToDo> findByCategory(String category);
}
