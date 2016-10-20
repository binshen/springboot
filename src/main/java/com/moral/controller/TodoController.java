package com.moral.controller;

import com.moral.dao.TodoDao;
import com.moral.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private TodoDao todoDao;

    @GetMapping
    public ModelAndView index(HttpServletRequest request, Map model) {
        List<Todo> todoList = todoDao.selectTodoList();
        model.put("todoList", todoList);
        model.put("site_name", "TODO List");
        return new ModelAndView("todo/index", model);
    }
}
