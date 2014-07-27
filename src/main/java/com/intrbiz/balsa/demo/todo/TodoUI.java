package com.intrbiz.balsa.demo.todo;

import java.io.IOException;
import java.util.UUID;

import com.intrbiz.balsa.demo.todo.db.TodoListDB;
import com.intrbiz.balsa.demo.todo.model.TodoList;
import com.intrbiz.balsa.demo.todo.model.TodoListEntry;
import com.intrbiz.balsa.engine.route.Router;
import com.intrbiz.balsa.error.http.BalsaNotFound;
import com.intrbiz.balsa.metadata.WithDataAdapter;
import com.intrbiz.metadata.Any;
import com.intrbiz.metadata.AsUUID;
import com.intrbiz.metadata.Get;
import com.intrbiz.metadata.Param;
import com.intrbiz.metadata.Post;
import com.intrbiz.metadata.Prefix;
import com.intrbiz.metadata.RequireValidAccessToken;
import com.intrbiz.metadata.RequireValidAccessTokenForURL;
import com.intrbiz.metadata.Template;

// Routes for the URL root
@Prefix("/")
@Template("layout/main")
public class TodoUI extends Router<App>
{
    @Any("/")
    @WithDataAdapter(TodoListDB.class)
    public void index(TodoListDB db)
    {
        // get the list of todo lists
        model("lists", db.getTodoLists());
        // encode the intro page
        encode("index");
    }
    
    @Any("/list/:name")
    @WithDataAdapter(TodoListDB.class)
    public void main(TodoListDB db, String name)
    {
        TodoList list = model("list", db.getTodoList(name));
        if (list == null) throw new BalsaNotFound();
        // get the list of todo lists
        model("lists", db.getTodoLists());
        // encode the main view
        encode("list");
    }
    
    @Post("/entry/new/:list")
    @RequireValidAccessTokenForURL()
    @WithDataAdapter(TodoListDB.class)
    public void newEntry(TodoListDB db, String listName, @Param("title") String title, @Param("description") String description) throws IOException
    {
        TodoList list = db.getTodoList(listName);
        if (list == null) throw new BalsaNotFound();
        //
        TodoListEntry entry = list.newEntry(title, description);
        db.setTodoListEntry(entry);
        //
        redirect("list/" + listName);
    }
    
    @Get("/entry/remove/:id")
    @RequireValidAccessToken()
    @WithDataAdapter(TodoListDB.class)
    public void removeEntry(TodoListDB db, @AsUUID UUID id) throws IOException
    {
        TodoListEntry entry = db.getTodoListEntry(id);
        if (entry == null) throw new BalsaNotFound();
        // remove
        db.removeTodoListEntry(entry.getId());
        // redirect to the list
        redirect("list/" + entry.getListName());
    }
    
    @Get("/entry/complete/:id")
    @RequireValidAccessToken()
    @WithDataAdapter(TodoListDB.class)
    public void completeEntry(TodoListDB db, @AsUUID UUID id) throws IOException
    {
        TodoListEntry entry = db.getTodoListEntry(id);
        if (entry == null) throw new BalsaNotFound();
        // update
        entry.setComplete(true);
        db.setTodoListEntry(entry);
        // redirect to the list
        redirect("list/" + entry.getListName());
    }
    
    @Get("/new/list")
    @WithDataAdapter(TodoListDB.class)
    public void showNewList(TodoListDB db)
    {
        // get the list of todo lists
        model("lists", db.getTodoLists());
        // encode the intro page
        encode("new");
    }
    
    @Post("/new/list")
    @RequireValidAccessToken()
    @WithDataAdapter(TodoListDB.class)
    public void newList(TodoListDB db, @Param("title") String title) throws IOException
    {
        String name = title.toLowerCase();
        TodoList list = new TodoList(name, title);
        db.setTodoList(list);
        // redirect to the list
        redirect("list/" + name);
    }
}
