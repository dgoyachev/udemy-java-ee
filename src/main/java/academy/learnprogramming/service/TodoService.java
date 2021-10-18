package academy.learnprogramming.service;

import academy.learnprogramming.entity.Todo;
import academy.learnprogramming.entity.User;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.core.SecurityContext;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Stateless
public class TodoService {

    @Inject
    private EntityManager entityManager;

    @Inject
    private QueryService queryService;

    @Inject
    private SecurityUtil securityUtil;

    public User saveUser(User user) {

        long count = ((BigInteger) queryService.countUserByEmail(user.getEmail()).get(0)).longValue();

        if (user.getId() == null && count == 0) {
            Map<String, String> credMap = securityUtil.hashPassword(user.getPassword());

            user.setPassword(credMap.get(SecurityUtil.HASHED_PASSWORD_KEY));
            user.setSalt(credMap.get(SecurityUtil.SALT_KEY));

            entityManager.persist(user);
            credMap.clear();
        }

        return user;
    }

    public Todo createTodo(Todo todo, SecurityContext securityContext) {

        User userByEmail = queryService.findUserByEmail(securityContext.getUserPrincipal().getName());

        if (userByEmail != null) {
            todo.setTodoOwner(userByEmail);
            entityManager.persist(todo);
        }
        return todo;
    }

    public Todo updateTodo(Todo todo) {
        entityManager.merge(todo);
        return todo;
    }

    public Todo findToDoById(Long id, SecurityContext securityContext) {
        return queryService.findTodoById(id, securityContext);
    }

    public List<Todo> getTodos(SecurityContext securityContext) {
        return queryService.getAllTodos(securityContext);
    }
}