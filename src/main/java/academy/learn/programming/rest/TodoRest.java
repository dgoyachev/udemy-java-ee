package academy.learn.programming.rest;

import academy.learn.programming.entity.Todo;
import academy.learn.programming.service.TodoService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;

@Path("todo")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TodoRest {

    @Inject
    TodoService todoService;

    @Path("new")
    @POST
    public Response createTodo(Todo todo){
        return Response.ok(
                todoService.createTodo(todo)
        ).build();
    }

    @Path("update")
    @PUT
    public Response updateTodo(Todo todo){
        return Response.ok(
                todoService.updateTodo(todo)
        ).build();
    }

    @Path("{id}")
    @GET
    public Todo getTodo(@PathParam("id") Long id){
        return todoService.findToDoById(id);
    }

    @Path("list")
    @GET
    public List<Todo> getTodos(){
        return todoService.getTodos();
    }

    @Path("{id}/status")
    @PUT
    public Response markAsComplete(@PathParam("id") Long id){
        Todo todo = todoService.findToDoById(id);
        todo.setCompleted(true);
        todo.setDateCompleted(LocalDate.now());
        todoService.updateTodo(todo);
        return Response.ok(todo).build();
    }
}