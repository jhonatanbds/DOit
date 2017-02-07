package edu.ufcg.jhonatanbds.dao;

import edu.ufcg.jhonatanbds.entity.category.Category;
import edu.ufcg.jhonatanbds.entity.priority.Priority;
import edu.ufcg.jhonatanbds.entity.ToDo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created by Jhonatan on 10/01/2017.
 */

@Repository
public interface ToDoDAO extends MongoRepository<ToDo, String>{

    List<ToDo> findByCategory(Category category);

    List<ToDo> findByPriority(Priority priority);

    List<ToDo> findByCompleted(boolean condition);

    List<ToDo> findByName(String name);

    List<ToDo> findById(String id);
}
