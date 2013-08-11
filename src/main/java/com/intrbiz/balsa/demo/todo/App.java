package com.intrbiz.balsa.demo.todo;

import java.sql.Timestamp;
import java.util.UUID;

import org.postgresql.Driver;

import com.intrbiz.balsa.BalsaApplication;
import com.intrbiz.balsa.demo.todo.db.TodoListDB;
import com.intrbiz.balsa.demo.todo.model.TodoList;
import com.intrbiz.balsa.demo.todo.model.TodoListEntry;
import com.intrbiz.data.DataManager;
import com.intrbiz.data.Transaction;
import com.intrbiz.util.pool.database.DatabasePool;

public class App extends BalsaApplication
{
    @Override
    protected void setup() throws Exception
    {
        // Setup the application routers
        router(new TodoUI());
        // setup the database
        DataManager.getInstance().registerDefaultServer(DatabasePool.Default.create(Driver.class, "jdbc:postgresql://127.0.0.1/todo", "todo", ""));
        // init the database
        try (TodoListDB todo = TodoListDB.connect())
        {
            todo.execute(new Transaction() {
                public void run() {
                    if (todo.getTodoList("work") == null) todo.setTodoList(new TodoList("work", "Work"));
                    if (todo.getTodoList("personal") == null) todo.setTodoList(new TodoList("personal", "Personal"));
                    if (todo.getTodoList("tasks") == null)
                    {
                        todo.setTodoList(new TodoList("tasks", "Tasks"));
                        // add an entry
                        TodoListEntry entry = new TodoListEntry();
                        entry.setListName("tasks");
                        entry.setTitle("Try Balsa");
                        entry.setId(UUID.randomUUID());
                        entry.setCreated(new Timestamp(System.currentTimeMillis()));
                        todo.setTodoListEntry(entry);
                    }
                }
            });
        }
    }
}
