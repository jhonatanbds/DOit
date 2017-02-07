package edu.ufcg.jhonatanbds.controller;

import edu.ufcg.jhonatanbds.entity.ToDo;
import edu.ufcg.jhonatanbds.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Jhonatan on 10/01/2017.
 */

@Controller
public class ToDoController {

    @Autowired
    private ToDoService toDoService;
    private final String HOME = "home";
    private final String TODOLIST = "todolist";

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("page", HOME);
        model.addAttribute("numToDos", toDoService.getReversedToDos().size());
        model.addAttribute("reversedToDos", toDoService.getReversedToDos());
        model.addAttribute("toDo", new ToDo());
        return "index";
    }

    @GetMapping("/indexComplete")
    public String indexCompleteToDo(@RequestParam(value = "id") String id, Model model) {
        this.toDoService.completeToDo(id);
        return home(model);
    }

    @GetMapping("/listComplete")
    public String listCompleteToDo(@RequestParam(value = "id") String id, Model model) {
        this.toDoService.completeToDo(id);
        return myToDos(model);
    }


    @GetMapping("/indexDelete")
    public String indexDeleteToDo(@RequestParam(value = "id") String id, Model model) {
        this.toDoService.deleteToDo(id);
        return home(model);
    }

    @GetMapping("/listDelete")
    public String listDeleteToDo(@RequestParam(value = "id") String id, Model model) {
        this.toDoService.deleteToDo(id);
        return myToDos(model);
    }

    @PostMapping("/")
    public String indexAddToDo(@ModelAttribute ToDo toDo, Model model) {
        this.toDoService.save(toDo);
        return home(model);
    }

    @PostMapping("/ToDos")
    public String listAddToDo(@ModelAttribute ToDo toDo, Model model) {
        this.toDoService.save(toDo);
        return myToDos(model);
    }

    @GetMapping("/ToDos")
    public String myToDos(Model model) {
        model.addAttribute("toDo", new ToDo());
        model.addAttribute("toDos", this.toDoService.findAll());
        model.addAttribute("page", TODOLIST);
        return "index";
    }

    @DeleteMapping("/ToDos")
    public String clean() {
        this.toDoService.deleteAll();
        return "index";
    }
}
