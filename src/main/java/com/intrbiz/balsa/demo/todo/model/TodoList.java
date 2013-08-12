package com.intrbiz.balsa.demo.todo.model;

import java.sql.Timestamp;
import java.util.List;

import com.intrbiz.balsa.demo.todo.db.TodoListDB;
import com.intrbiz.data.db.compiler.meta.SQLColumn;
import com.intrbiz.data.db.compiler.meta.SQLPrimaryKey;
import com.intrbiz.data.db.compiler.meta.SQLTable;
import com.intrbiz.data.db.compiler.meta.SQLVersion;

@SQLTable(
        name = "list",
        since = @SQLVersion(major = 1, minor = 0)
)
public class TodoList
{
    @SQLColumn(index = 1, name = "name")
    @SQLPrimaryKey()
    private String name;

    @SQLColumn(index = 2, name = "title")
    private String title;

    @SQLColumn(index = 3, name = "created")
    private Timestamp created;

    public TodoList()
    {
        super();
    }

    public TodoList(String name, String title)
    {
        super();
        this.name = name;
        this.title = title;
        this.created = new Timestamp(System.currentTimeMillis());
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Timestamp getCreated()
    {
        return created;
    }

    public void setCreated(Timestamp created)
    {
        this.created = created;
    }
    
    public TodoListEntry newEntry(String title, String description)
    {
        TodoListEntry entry = new TodoListEntry(this);
        entry.setTitle(title);
        entry.setDescription(description);
        return entry;
    }
    
    public List<TodoListEntry> getEntries()
    {
        try (TodoListDB db = TodoListDB.connect())
        {
            return db.getTodoListEntries(this.getName());
        }
    }
}
