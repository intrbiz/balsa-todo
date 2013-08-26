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

/**
 * Test the metrics router
 */
public class MetricsUITests
{
    /**
     * Setup a balsa instance to test against
     */
    @ClassRule
    public static BalsaTestServer<App> balsa = new BalsaTestServer<App>(App.class);

    /**
     * Test that the /metrics route returns HTTP 200 OK
     */
    @Test
    public void metrics_returns_200_OK() throws Exception
    {
        // execute the SCGI request to the server
        SCGIClientResponse res = balsa.client().http().get("/metrics").executeRequest();
        // verify the response
        assumeThat(res, is(not(nullValue())));
        assertThat(res.getStatus(), is(equalTo(HTTPStatus.valueOf(200))));
    }
    
    @Test
    public void metrics_returns_html() throws Exception
    {
        SCGIClientResponse res = balsa.client().http().get("/metrics").executeRequest();
        assumeThat(res, is(not(nullValue())));
        assumeThat(res.getStatus(), is(equalTo(HTTPStatus.valueOf(200))));
        assertThat(res.getHeaders().keySet(), hasItem("Content-Type"));
        assertThat(res.getHeader("Content-Type"), containsString(HTTP.ContentTypes.TEXT_HTML));
    }
}
