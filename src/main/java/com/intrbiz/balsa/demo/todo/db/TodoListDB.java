package com.intrbiz.balsa.demo.todo.db;

import java.util.List;
import java.util.UUID;

import com.intrbiz.balsa.demo.todo.model.TodoList;
import com.intrbiz.balsa.demo.todo.model.TodoListEntry;
import com.intrbiz.data.DataException;
import com.intrbiz.data.DataManager;
import com.intrbiz.data.cache.Cache;
import com.intrbiz.data.db.DatabaseAdapter;
import com.intrbiz.data.db.DatabaseConnection;
import com.intrbiz.data.db.compiler.DatabaseAdapterCompiler;
import com.intrbiz.data.db.compiler.meta.SQLGetter;
import com.intrbiz.data.db.compiler.meta.SQLOrder;
import com.intrbiz.data.db.compiler.meta.SQLParam;
import com.intrbiz.data.db.compiler.meta.SQLQuery;
import com.intrbiz.data.db.compiler.meta.SQLRemove;
import com.intrbiz.data.db.compiler.meta.SQLSchema;
import com.intrbiz.data.db.compiler.meta.SQLSetter;
import com.intrbiz.data.db.compiler.meta.SQLVersion;

@SQLSchema(
        name = "todo", 
        version = @SQLVersion({1, 1, 1}),
        tables = {
            TodoList.class,
            TodoListEntry.class           
        }
)
public abstract class TodoListDB extends DatabaseAdapter
{
    static
    {
        DataManager.getInstance().registerDatabaseAdapter(TodoListDB.class, DatabaseAdapterCompiler.defaultPGSQLCompiler().compileAdapterFactory(TodoListDB.class));
    }
    
    public static final TodoListDB connect()
    {
        return DataManager.getInstance().databaseAdapter(TodoListDB.class);
    }
    
    public TodoListDB(DatabaseConnection connection, Cache cache)
    {
        super(connection, cache);
    }
    
    public static void install()
    {
        DatabaseAdapterCompiler.defaultPGSQLCompiler("todo").install(DataManager.getInstance().connect(), TodoListDB.class);
    }
    
    // TodoList
    
    @SQLSetter(name = "set_todo_list", table = TodoList.class, since = @SQLVersion({1, 0, 0}))
    public abstract void setTodoList(TodoList list) throws DataException;
    
    @SQLRemove(name = "remove_todo_list", table = TodoList.class, since = @SQLVersion({1, 0, 0}))
    public abstract void removeTodoList(@SQLParam("name") String name) throws DataException;
    
    @SQLGetter(name = "get_todo_lists", table = TodoList.class, since = @SQLVersion({1, 0, 0}), orderBy = @SQLOrder("created"))
    public abstract List<TodoList> getTodoLists() throws DataException;
    
    @SQLGetter(name = "get_todo_list", table = TodoList.class, since = @SQLVersion({1, 0, 0}))
    public abstract TodoList getTodoList(@SQLParam("name") String name) throws DataException;
    
    // TodoListEntry
    
    @SQLSetter(name = "set_todo_list_entry", table = TodoListEntry.class, since = @SQLVersion({1, 0, 0}))
    public abstract void setTodoListEntry(TodoListEntry entry) throws DataException;
    
    @SQLRemove(name = "remove_todo_list_entry", table = TodoListEntry.class, since = @SQLVersion({1, 0, 0}))
    public abstract void removeTodoListEntry(@SQLParam("id") UUID id) throws DataException;
    
    @SQLGetter(name = "get_todo_list_entry", table = TodoListEntry.class, since = @SQLVersion({1, 0, 0}))
    public abstract TodoListEntry getTodoListEntry(@SQLParam("id") UUID id) throws DataException;
    
    @SQLGetter(name = "get_todo_list_entries", table = TodoListEntry.class, since = @SQLVersion({1, 0, 0}), orderBy = @SQLOrder("created"), query = @SQLQuery("SELECT * FROM todo.entry WHERE list_name = p_list_name AND complete = false"))
    public abstract List<TodoListEntry> getTodoListEntries(@SQLParam("list_name") String list) throws DataException;
    
    // patches
    
    // default values
    
    /*@SQLPatch(name = "Default task list", index = 1, type = ScriptType.INSTALL, version = @SQLVersion({1, 1, 0}))
    public static SQLScript defaultTaskList()
    {
        return new SQLScript(
                "INSERT INTO todo.list (name, title, created) VALUES ('tasks', 'Tasks', now())",
                "INSERT INTO todo.entry (id, list_name, title, created, complete, completed) VALUES ('" + UUID.randomUUID().toString() + "'::UUID,  'Try Balsa', NULL, now(), FALSE, NULL)"
        );
    }*/
}
