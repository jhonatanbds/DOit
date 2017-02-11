package edu.ufcg.jhonatanbds.dao;

import edu.ufcg.jhonatanbds.entity.ToDoList;
import edu.ufcg.jhonatanbds.entity.ToDo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created by Jhonatan on 10/01/2017.
 */

@Repository
public interface ToDoListDAO extends MongoRepository<ToDoList, String>{

    List<ToDoList> findByName(String name);
}
