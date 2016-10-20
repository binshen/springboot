package com.moral.controller;

import com.moral.dao.TodoDao;
import com.moral.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private TodoDao todoDao;

    @GetMapping("/index")
    @Transactional(readOnly = true)
    public ModelAndView index(HttpServletRequest request, Map model) {
        List<Todo> todoList = todoDao.selectTodoList();
        model.put("todoList", todoList);
        model.put("site_name", "TODO List");
        return new ModelAndView("todo/index", model);
    }

    @PostMapping("/new")
    @Transactional
    public ModelAndView add(HttpServletRequest request, Map model) {
        Todo todo = new Todo();
        todo.setTitle(request.getParameter("title"));
        todo.setPost_date(new Date());
        todo.setFinished(0);
        todoDao.insertTodo(todo);
        return new ModelAndView("redirect:/todo/index");
    }

    @GetMapping("/{id}/edit")
    @Transactional
    public ModelAndView edit(@PathVariable("id") int id, Map model) {
        model.put("site_name", "TODO List");
        model.put("todo", todoDao.selectTodo(id));
        return new ModelAndView("todo/edit");
    }

    @PostMapping("/{id}/save")
    @Transactional
    public ModelAndView save(@PathVariable("id") int id, HttpServletRequest request, Map model) {
        Todo todo = todoDao.selectTodo(id);
        todo.setTitle(request.getParameter("title"));
        todoDao.updateTodo(todo);
        return new ModelAndView("redirect:/todo/index");
    }

    @GetMapping("/{id}/delete")
    @Transactional
    public ModelAndView delete(@PathVariable("id") int id, Map model) {
        Todo todo = todoDao.selectTodo(id);
        todoDao.deleteTodo(todo);
        return new ModelAndView("redirect:/todo/index");
    }

    @GetMapping("/{id}/finish/{status}")
    @Transactional
    public ModelAndView finish(@PathVariable("id") int id, @PathVariable("status") int status, Map model) {
        Todo todo = todoDao.selectTodo(id);
        todo.setFinished(status);
        todoDao.updateTodo(todo);
        return new ModelAndView("redirect:/todo/index");
    }
}
