package edu.ufcg.jhonatanbds.controller;

import edu.ufcg.jhonatanbds.dao.ToDoDAO;
import edu.ufcg.jhonatanbds.entity.Category;
import edu.ufcg.jhonatanbds.entity.Priority;
import edu.ufcg.jhonatanbds.entity.ToDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Jhonatan on 10/01/2017.
 */

@Controller
public class ToDoController {

    @Autowired
    private ToDoDAO toDoDAO;
    private final String HOME = "home";
    private final String TODOLIST = "todolist";

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String DOit(Model model) {
        model.addAttribute("page", HOME);
        return "index";
    }

    @RequestMapping(value = "/ToDos/addToDos", method = RequestMethod.GET)
    public String addToDo(Model model) {
        this.toDoDAO.save(new ToDo("nome", "fdshgd"));
        this.toDoDAO.save(new ToDo("nome1", "fasdfsdf"));
        this.toDoDAO.save(new ToDo("nome2", "agsrgw4qw"));
        model.addAttribute("todos", this.toDoDAO.findAll());
        return "index";
    }

    @RequestMapping(value = "/ToDos/{name}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteToDo(@PathVariable  String name){
        List<ToDo> toDos = this.toDoDAO.findByName(name);
        if(toDos.size() == 1) {
            ToDo toDo = toDos.get(0);
            this.toDoDAO.delete(toDo);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping("/ToDos")
    public String findAll(Model model) {
        model.addAttribute("todos", this.toDoDAO.findAll());
        return "index";
    }

    @RequestMapping("/ToDos/categ")
    public List<ToDo> findByCategory(@RequestParam(value = "category") Category category) {
        return this.toDoDAO.findByCategory(category);
    }

    @RequestMapping("/ToDos/prior")
    public List<ToDo> findByPriority(@RequestParam(value = "priority") Priority priority) {
        return this.toDoDAO.findByPriority(priority);
    }

    @RequestMapping("/ToDos/compl")
    public List<ToDo> findByCompleted(@RequestParam(value = "completed") boolean condition) {
        return this.toDoDAO.findByCompleted(condition);
    }

    @RequestMapping("ToDos/{name}")
    public List<ToDo> findByName(@PathVariable("name") String name) {
        return this.toDoDAO.findByName(name);
    }

    @RequestMapping(value = "/ToDos", method = RequestMethod.DELETE)
    public String clean() {
        this.toDoDAO.deleteAll();
        return "result";
    }
}
