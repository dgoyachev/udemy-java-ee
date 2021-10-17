package academy.learnprogramming.rest;

import academy.learnprogramming.entity.Todo;
import academy.learnprogramming.service.QueryService;
import academy.learnprogramming.service.TodoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

@Path("todo")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Authz
public class TodoRest {

    @Inject
    TodoService todoService;

    @Inject
    QueryService queryService;

    @Context SecurityContext securityContext;

    @Path("new")
    @POST
    public Response createTodo(Todo todo){
        todoService.createTodo(todo, securityContext);

        return Response.ok(todo).build();
    }


//    @Path("update")
//    @PUT
//    public Response updateTodo(Todo todo){
//        todoService.updateTodo(todo);
//
//         return Response.ok(todo).build();
//    }

    @Path("{id}")
    @GET
    public Todo getTodo(@PathParam("id") Long id){
        return queryService.findTodoById(id, securityContext);
    }

    @Path("list")
    @GET
    public List<Todo> getTodos(){
        return queryService.getAllTodos(securityContext);
    }

    @Path("status")
    @POST
    public Response markAsComplete(@QueryParam("id") Long id){
        Todo todo = todoService.findToDoById(id, securityContext);
        todo.setIsCompleted(true);
        todoService.updateTodo(todo);

        return Response.ok(todo).build();
    }
}