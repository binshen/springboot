package com.moral.dao;

import com.moral.model.Todo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by bin.shen on 20/10/2016.
 */

@Repository
public class TodoDaoImpl implements TodoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Todo> selectTodoList() {
        return entityManager.createQuery("SELECT t FROM Todo t", Todo.class).getResultList();
    }

    @Override
    public Todo selectTodo(int id) {
        return entityManager.find(Todo.class, id);
    }

    @Override
    public int insertTodo(Todo todo) {
        entityManager.persist(todo);
        return 1;
    }

    @Override
    public int deleteTodo(Todo todo) {
        entityManager.remove(todo);
        return 1;
    }

    @Override
    public int updateTodo(Todo todo) {
        entityManager.merge(todo);
        return 1;
    }
}
