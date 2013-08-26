package com.intrbiz.balsa.demo.todo.test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assume.*;

import org.junit.ClassRule;
import org.junit.Test;

import com.intrbiz.balsa.demo.todo.App;
import com.intrbiz.balsa.http.HTTP;
import com.intrbiz.balsa.http.HTTP.HTTPStatus;
import com.intrbiz.balsa.scgi.SCGIClient.SCGIClientResponse;
import com.intrbiz.balsa.test.BalsaTestServer;

public class BasicTodoUITests
{
    @ClassRule
    public static BalsaTestServer<App> balsa = new BalsaTestServer<App>(App.class);

    @Test
    public void index() throws Exception
    {
        SCGIClientResponse res = balsa.client().http().get("/").executeRequest();
        assumeThat(res, is(not(nullValue())));
        assertThat(res.getStatus(), is(equalTo(HTTPStatus.valueOf(200))));
        assertThat(res.getHeaders().keySet(), hasItem("Content-Type"));
        assertThat(res.getHeader("Content-Type"), containsString(HTTP.ContentTypes.TEXT_HTML));
        assertThat(res.getContentAsString(), containsString("Welcome to the Balsa Todolist."));
    }
    
    @Test
    public void listTasks() throws Exception
    {
        SCGIClientResponse res = balsa.client().http().get("/list/tasks").executeRequest();
        assumeThat(res, is(not(nullValue())));
        assertThat(res.getStatus(), is(equalTo(HTTPStatus.valueOf(200))));
        assertThat(res.getHeaders().keySet(), hasItem("Content-Type"));
        assertThat(res.getHeader("Content-Type"), containsString(HTTP.ContentTypes.TEXT_HTML));
        assertThat(res.getContentAsString(), not(containsString("Welcome to the Balsa Todolist.")));
    }
}
