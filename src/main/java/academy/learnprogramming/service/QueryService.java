package academy.learnprogramming.service;

import academy.learnprogramming.entity.Todo;
import academy.learnprogramming.entity.User;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

@Stateless
public class QueryService {

    @Inject
    EntityManager entityManager;

    @Inject
    SecurityUtil securityUtil;

    public User findUserByEmail(String email) {
        return entityManager.createNamedQuery(User.FIND_USER_BY_EMAIL, User.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    public List<?> countUserByEmail(String email) {
        return entityManager.createNativeQuery("select count (id) from TodoUser where exists (select id from TodoUser where email = ?)")
                .setParameter(1, email)
                .getResultList();
    }

    public Todo findTodoById(Long id, SecurityContext securityContext) {
        return entityManager.createNamedQuery(Todo.FIND_TODO_BY_ID, Todo.class)
                .setParameter("id", id)
                .setParameter("email", securityContext.getUserPrincipal().getName())
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    public List<Todo> getAllTodos(SecurityContext securityContext) {
        return entityManager.createNamedQuery(Todo.FIND_ALL_TODOS_BY_USER, Todo.class)
                .setParameter("email", securityContext.getUserPrincipal().getName())
                .getResultList();
    }

    public List<Todo> getAllTodosByTask(String taskText, SecurityContext securityContext) {
        return entityManager.createNamedQuery(Todo.FIND_TODO_BY_TASK, Todo.class)
                .setParameter("email", securityContext.getUserPrincipal().getName())
                .setParameter("task", "%" + taskText + "%")
                .getResultList();
    }
}
