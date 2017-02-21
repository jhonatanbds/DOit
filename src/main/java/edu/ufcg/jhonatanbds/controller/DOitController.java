package edu.ufcg.jhonatanbds.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import edu.ufcg.jhonatanbds.comparator.PriorityComparator;
import edu.ufcg.jhonatanbds.converter.ToDosToPDFConverter;
import edu.ufcg.jhonatanbds.entity.SubToDo;
import edu.ufcg.jhonatanbds.entity.ToDo;
import edu.ufcg.jhonatanbds.entity.ToDoList;
import edu.ufcg.jhonatanbds.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

/**
 * Created by Jhonatan on 10/01/2017.
 */

@Controller
public class DOitController {

    @Autowired
    private ToDoListService toDoListService;
    private final String HOME = "home";
    private final String TODOS = "toDos";
    private final String TODOLIST = "toDoLists";

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("page", HOME);
        model.addAttribute("numToDos", toDoListService.getReversedToDos().size());
        model.addAttribute("reversedToDos", toDoListService.getReversedToDos());
        model.addAttribute("toDoLists", toDoListService.getAllLists());
        model.addAttribute("toDo", new ToDo());
        model.addAttribute("toComplete", this.toDoListService.toComplete());
        model.addAttribute("download", "Baixar todas tarefas");
        return "index";
    }

    @GetMapping("/downloadToDos")
    public HttpEntity<byte[]> createPdf(@RequestParam(value = "id", required = false) String id) throws IOException, DocumentException {
        if (id == null)
            return ToDosToPDFConverter.createPDF(this.toDoListService.getAllToDos());
        else
            return ToDosToPDFConverter.createPDF(this.toDoListService.getToDoList(id));
    }

    @GetMapping("ToDoLists")
    public String toDoLists(Model model) {
        model.addAttribute("page",TODOLIST);
        model.addAttribute("toDoList", new ToDoList());
        model.addAttribute("toDoLists", this.toDoListService.getAllLists());
        model.addAttribute("toComplete", this.toDoListService.toComplete());
        return "index";
    }

    @PostMapping("ToDoLists")
    public String addToDoList(@ModelAttribute ToDoList toDoList, Model model) {
        this.toDoListService.addToDoList(toDoList);
        return toDoLists(model);
    }

    @GetMapping("showList")
    public String showList(@RequestParam(value = "id") String id, Model model){
        model.addAttribute("toDoListId", id);
        model.addAttribute("toDoLists", toDoListService.getAllLists());
        model.addAttribute("toDos", this.toDoListService.getToDoList(id));
        model.addAttribute("percent", this.toDoListService.getPercentCompleted(id));
        model.addAttribute("completed", this.toDoListService.getCompletedNumber(id));
        model.addAttribute("total", this.toDoListService.getToDoList(id).size());
        model.addAttribute("toDo", new ToDo());
        model.addAttribute("page", TODOS);
        model.addAttribute("toComplete", this.toDoListService.toComplete());
        return "index";
    }

    @GetMapping("deleteList")
    public String deleteList(@RequestParam(value = "id") String id, Model model){
        if (id.equals("0"))
            this.toDoListService.deleteAllToDos();
        this.toDoListService.deleteToDoList(id);
        return myToDos(model);
    }

    @GetMapping("deleteAllLists")
    public String deleteAllLists(Model model){
        this.toDoListService.deleteAllLists();
        return myToDos(model);
    }



    @GetMapping("order")
    public String orderBy(@RequestParam(value = "By") String by, Model model) {
        List<ToDo> toDoList = this.toDoListService.getAllToDos();
        if (by.equals("name"))
            Collections.sort(toDoList);
        else
            Collections.sort(toDoList,new PriorityComparator());

        model.addAttribute("toDos", toDoList);

        modelToDos(model);
        return "index";
    }

    @GetMapping("show")
    public String show(@RequestParam(value = "Only") String only, Model model) {
        List<ToDo> toDoList = this.toDoListService.getAllToDos();
        switch (only) {
            case "all":
                model.addAttribute("toDos", toDoList);
                break;
            case "incomplete":
                model.addAttribute("toDos", this.toDoListService.incompleteToDos());
                break;
            case "highPriority":
                model.addAttribute("toDos", this.toDoListService.getToDosByPriority("HIGH"));
                break;
            case "mediumPriority":
                model.addAttribute("toDos", this.toDoListService.getToDosByPriority("MEDIUM"));
                break;
            case "lowPriority":
                model.addAttribute("toDos", this.toDoListService.getToDosByPriority("LOW"));
                break;
            case "standardCateg":
                model.addAttribute("toDos", this.toDoListService.getToDosByCategory("STANDARD"));
                break;
            case "recreationCateg":
                model.addAttribute("toDos", this.toDoListService.getToDosByCategory("RECREATION"));
                break;
            case "workCateg":
                model.addAttribute("toDos", this.toDoListService.getToDosByCategory("WORK"));
                break;
            case "commitmentCateg":
                model.addAttribute("toDos", this.toDoListService.getToDosByCategory("COMMITMENT"));
                break;
            default:
                return myToDos(model);
        }
        modelToDos(model);
        return "index";
    }

    @GetMapping("editToDo")
    public String editToDo(@RequestParam(value = "id") String id, Model model) {
        model.addAttribute("editableToDo", this.toDoListService.getToDoById(id));
        model.addAttribute("fragment", "edit");
        model.addAttribute("toDoLists", this.toDoListService.getAllLists());
        return myToDos(model);
    }

    @GetMapping("/detailToDo")
    public String detailToDo(@RequestParam(value = "id") String id, Model model) {
        model.addAttribute("detailedToDo", this.toDoListService.getToDoById(id));
        model.addAttribute("subToDo", new SubToDo());
        model.addAttribute("fragment", "detail");
        model.addAttribute("toDoId", id);
        return myToDos(model);
    }

    @PostMapping("/detailToDo")
    public String addSubToDo(@RequestParam(value = "id") String id, @ModelAttribute SubToDo subToDo, Model model) {
        this.toDoListService.addSubToDo(id, subToDo);
        model.addAttribute("detailedToDo", this.toDoListService.getToDoById(id));
        model.addAttribute("fragment", "detail");
        model.addAttribute("toDoId", id);
        return myToDos(model);
    }

    @GetMapping("subDelete")
    public String subDelete(@RequestParam(value = "sId") String sId,@RequestParam(value = "tId") String tId, Model model) {
        this.toDoListService.deleteSubToDo(sId, tId);
        model.addAttribute("toDoId", tId);
        return detailToDo(tId, model);
    }

    @GetMapping("subComplete")
    public String subComplete(@RequestParam(value = "sId") String sId,@RequestParam(value = "tId") String tId, Model model) {
        this.toDoListService.completeSubToDo(sId, tId);
        model.addAttribute("toDoId", tId);
        return detailToDo(tId, model);
    }

    @GetMapping("/indexComplete")
    public String indexCompleteToDo(@RequestParam(value = "id") String id, Model model) {
        this.toDoListService.completeToDo(id);
        return home(model);
    }

    @GetMapping("/listComplete")
    public String listCompleteToDo(@RequestParam(value = "id") String id, Model model) {
        this.toDoListService.completeToDo(id);
        return myToDos(model);
    }


    @GetMapping("/indexDelete")
    public String indexDeleteToDo(@RequestParam(value = "id") String id, Model model) {
        this.toDoListService.deleteToDo(id);
        return home(model);
    }

    @GetMapping("/listDelete")
    public String listDeleteToDo(@RequestParam(value = "id") String id, Model model) {
        this.toDoListService.deleteToDo(id);
        return myToDos(model);
    }

    @PostMapping("/")
    public String indexAddToDo(@ModelAttribute ToDo toDo, Model model) {
        this.toDoListService.addToDo(toDo);
        return home(model);
    }

    @PostMapping("/ToDos")
    public String listAddToDo(@RequestParam(value = "id", required = false) String id, @ModelAttribute ToDo toDo, Model model) {
        if (id != null && !toDo.getId().equals(id))
            toDo.setId(id);
        this.toDoListService.addToDo(toDo);
        return myToDos(model);
    }

    @GetMapping("/ToDos")
    public String myToDos(Model model) {
        model.addAttribute("toDos", this.toDoListService.getAllToDos());
        modelToDos(model);
        return "index";
    }

    private void modelToDos(Model model) {
        model.addAttribute("toDoLists", toDoListService.getAllLists());
        model.addAttribute("percent", this.toDoListService.getPercentCompleted());
        model.addAttribute("completed", this.toDoListService.getCompletedNumber());
        model.addAttribute("total", this.toDoListService.getAllToDos().size());
        model.addAttribute("toDoListId", "0");
        model.addAttribute("toDo", new ToDo());
        model.addAttribute("page", TODOS);
        model.addAttribute("toComplete", this.toDoListService.toComplete());
    }
}
