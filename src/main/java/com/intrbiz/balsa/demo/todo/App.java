package com.intrbiz.balsa.demo.todo;

import com.intrbiz.balsa.BalsaApplication;
import com.intrbiz.balsa.demo.todo.db.TodoListDB;
import com.intrbiz.data.DataManager;
import com.intrbiz.util.pool.database.DatabasePool;

public class App extends BalsaApplication
{
    @Override
    protected void setup() throws Exception
    {
        // setup the database
        DataManager.getInstance().registerDefaultServer(DatabasePool.Default.with().postgresql().url("jdbc:postgresql://127.0.0.1/todo").username("todo").password("").build());
        // Setup the application routers
        router(new TodoUI());
        router(new MetricsUI());
        // ensure the database is installed and upgraded
        TodoListDB.install();
    }
    
    public static void main(String[] args) throws Exception
    {
        App app = new App();
        app.start();
    }
}
