package com.moral.controller;

import com.moral.dao.TodoDao;
import com.moral.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping
    @Transactional(readOnly = true)
    public ModelAndView index(HttpServletRequest request, Map model) {
        List<Todo> todoList = todoDao.selectTodoList();
        model.put("todoList", todoList);
        model.put("site_name", "TODO List");
        return new ModelAndView("todo/index", model);
    }

    @RequestMapping("/new")
    public String add(HttpServletRequest request, Map model) {
        Todo todo = new Todo();
        todo.setTitle(request.getParameter("title"));
        todo.setPost_date(new Date());
        todo.setFinished(0);
        todoDao.insertTodo(todo);
        return "redirect:/todo/index";
    }

    @RequestMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Map model) {
        model.put("site_name", "TODO List");
        model.put("todo", todoDao.selectTodo(id));
        return "edit";
    }

    @RequestMapping("/{id}/save")
    public String save(@PathVariable("id") int id, HttpServletRequest request, Map model) {
        Todo todo = todoDao.selectTodo(id);
        todo.setTitle(request.getParameter("title"));
        todoDao.updateTitle(todo);
        return "redirect:/todo/index";
    }

    @RequestMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id, Map model) {
        Todo todo = todoDao.selectTodo(id);
        todoDao.deleteTodo(todo);
        return "redirect:/todo/index";
    }

    @RequestMapping("/{id}/finish/{status}")
    public String finish(@PathVariable("id") int id, @PathVariable("status") int status, Map model) {
        Todo todo = todoDao.selectTodo(id);
        todo.setFinished(status);
        todoDao.updateStatus(todo);
        return "redirect:/todo/index";
    }
}
