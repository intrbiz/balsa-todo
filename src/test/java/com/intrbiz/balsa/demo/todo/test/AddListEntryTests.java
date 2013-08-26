package com.intrbiz.balsa.demo.todo.test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assume.*;

import org.junit.ClassRule;
import org.junit.Test;

import com.intrbiz.balsa.demo.todo.App;
import com.intrbiz.balsa.http.HTTP;
import com.intrbiz.balsa.scgi.SCGIClient.SCGIClientResponse;
import com.intrbiz.balsa.test.BalsaTestServer;

public class AddListEntryTests
{
    @ClassRule
    public static BalsaTestServer<App> balsa = new BalsaTestServer<App>(App.class);

    @Test
    public void add_entry_redirects_to_list() throws Exception
    {
        SCGIClientResponse res = balsa.client().http().post("/entry/new/tasks").param("title", "JUnit Test Entry").param("description", "A test entry added by the JUnit tests").param("key", balsa.app().getSecurityEngine().generateAccessTokenForURL("/entry/new/tasks")).complete().executeRequest();

        assumeThat(res, is(not(nullValue())));
        assertThat(res.getStatus(), is(equalTo(HTTP.HTTPStatus.valueOf(302))));
        assertThat(res.getHeaders().keySet(), hasItem("Location"));
    }
}
