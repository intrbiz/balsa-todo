package com.intrbiz.balsa.demo.todo.model;

import java.sql.Timestamp;
import java.util.UUID;

import com.intrbiz.data.db.compiler.meta.Action;
import com.intrbiz.data.db.compiler.meta.SQLColumn;
import com.intrbiz.data.db.compiler.meta.SQLForeignKey;
import com.intrbiz.data.db.compiler.meta.SQLPrimaryKey;
import com.intrbiz.data.db.compiler.meta.SQLTable;
import com.intrbiz.data.db.compiler.meta.Version;

@SQLTable(
        name = "entry",
        since = @Version(major = 1, minor = 0)
)
public class TodoListEntry
{
    @SQLColumn(index = 1, name = "id")
    @SQLPrimaryKey()
    private UUID id = UUID.randomUUID();

    @SQLColumn(index = 2, name = "list_name")
    @SQLForeignKey(references = TodoList.class, on = "name", onDelete = Action.CASCADE)
    private String listName;

    @SQLColumn(index = 3, name = "title")
    private String title;

    @SQLColumn(index = 4, name = "description")
    private String description;

    @SQLColumn(index = 5, name = "created")
    private Timestamp created;

    @SQLColumn(index = 6, name = "complete")
    private boolean complete = false;

    @SQLColumn(index = 7, name = "completed")
    private Timestamp completed;

    public TodoListEntry()
    {
        super();
    }

    public TodoListEntry(TodoList list)
    {
        super();
        this.listName = list.getName();
        this.created = new Timestamp(System.currentTimeMillis());
    }

    public UUID getId()
    {
        return id;
    }

    public void setId(UUID id)
    {
        this.id = id;
    }

    public String getListName()
    {
        return listName;
    }

    public void setListName(String listName)
    {
        this.listName = listName;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Timestamp getCreated()
    {
        return created;
    }

    public void setCreated(Timestamp created)
    {
        this.created = created;
    }

    public boolean isComplete()
    {
        return complete;
    }
    
    public boolean getComplete()
    {
        return complete;
    }

    public void setComplete(boolean complete)
    {
        this.complete = complete;
    }

    public Timestamp getCompleted()
    {
        return completed;
    }

    public void setCompleted(Timestamp completed)
    {
        this.completed = completed;
    }
}
