package com.intrbiz.balsa.demo.todo.model;

import java.sql.Timestamp;
import java.util.UUID;

import com.intrbiz.balsa.demo.todo.db.TodoListDB;
import com.intrbiz.data.db.compiler.meta.Action;
import com.intrbiz.data.db.compiler.meta.SQLColumn;
import com.intrbiz.data.db.compiler.meta.SQLForeignKey;
import com.intrbiz.data.db.compiler.meta.SQLPatch;
import com.intrbiz.data.db.compiler.meta.SQLPrimaryKey;
import com.intrbiz.data.db.compiler.meta.SQLTable;
import com.intrbiz.data.db.compiler.meta.SQLVersion;
import com.intrbiz.data.db.compiler.meta.ScriptType;
import com.intrbiz.data.db.compiler.util.SQLScript;

@SQLTable(schema = TodoListDB.class, name = "entry", since = @SQLVersion({1, 0, 0}))
public class TodoListEntry
{
    @SQLColumn(index = 1, name = "id", since = @SQLVersion({1, 0, 0}))
    @SQLPrimaryKey()
    private UUID id = UUID.randomUUID();

    @SQLColumn(index = 2, name = "list_name", since = @SQLVersion({1, 0, 0}))
    @SQLForeignKey(references = TodoList.class, on = "name", onDelete = Action.CASCADE, since = @SQLVersion({1, 0, 0}))
    private String listName;

    @SQLColumn(index = 3, name = "title", since = @SQLVersion({1, 0, 0}))
    private String title;

    @SQLColumn(index = 4, name = "description", since = @SQLVersion({1, 0, 0}))
    private String description;

    @SQLColumn(index = 5, name = "created", since = @SQLVersion({1, 0, 0}))
    private Timestamp created;

    @SQLColumn(index = 6, name = "complete", since = @SQLVersion({1, 0, 0}))
    private boolean complete = false;

    @SQLColumn(index = 7, name = "completed", since = @SQLVersion({1, 0, 0}))
    private Timestamp completed;

    @SQLColumn(index = 8, name = "due", since = @SQLVersion({1, 1, 0}))
    private Timestamp due;

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

    public Timestamp getDue()
    {
        return due;
    }

    public void setDue(Timestamp due)
    {
        this.due = due;
    }
    
    // upgrade
    
    @SQLPatch(name = "Add due column", type = ScriptType.UPGRADE, index = 1, version = @SQLVersion({1, 1, 0}))
    public static SQLScript addDueColumn()
    {
        return new SQLScript(
               "ALTER TABLE todo.entry ADD COLUMN due TIMESTAMP WITH TIME ZONE",
               "ALTER TYPE todo.t_entry ADD ATTRIBUTE due TIMESTAMP WITH TIME ZONE",
               "DROP FUNCTION todo.set_todo_list_entry(uuid, text, text, text, timestamp with time zone, boolean, timestamp with time zone)"
        );
    }
}
