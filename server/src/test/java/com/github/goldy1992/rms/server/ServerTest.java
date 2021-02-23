package com.github.goldy1992.rms.server;

import com.github.goldy1992.rms.message.Request.RegisterClientRequest;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class ServerTest {

    private Server server;

    @Before
    public void setUp() {
        server = new Server();
    }

    @Test
    public void registerKitchenClient() throws Exception {
        // GIVEN: an instance of a server class with no clients
        String address = "algo";

        // WHEN: a kitchen client is registered
        server.registerClient(RegisterClientRequest.ClientType.KITCHEN, address);

        // THEN: the server's kitchen client is registered correctly
        assertThat(server.getKitchenClient(), CoreMatchers.equalTo(address));
        assertThat(server.getBarClient(), CoreMatchers.nullValue());
        assertThat(server.getWaiterClient().size(), CoreMatchers.equalTo(0));
        assertThat(server.getTillClient().size(), CoreMatchers.equalTo(0));
    }

    @Test
    public void registerBarClient() throws Exception {
        // GIVEN: an instance of a server class with no clients
        String address = "algo";

        // WHEN: a bar client is registered
        server.registerClient(RegisterClientRequest.ClientType.BAR, address);

        // THEN: the server's bar client is registered correctly
        assertThat(server.getKitchenClient(), CoreMatchers.nullValue());
        assertThat(server.getBarClient(), CoreMatchers.equalTo(address));
        assertThat(server.getWaiterClient().size(), CoreMatchers.equalTo(0));
        assertThat(server.getTillClient().size(), CoreMatchers.equalTo(0));
    }

    @Test
    public void registerWaiterClient() throws Exception {
        // GIVEN: an instance of a server class with no clients
        String address = "algo";

        // WHEN: a waiter client is registered
        server.registerClient(RegisterClientRequest.ClientType.WAITER, address);

        // THEN: the server's bar client is registered correctly
        assertThat(server.getKitchenClient(), CoreMatchers.nullValue());
        assertThat(server.getBarClient(), CoreMatchers.nullValue());
        assertThat(server.getWaiterClient().size(), CoreMatchers.is(1));
        assertThat(server.getWaiterClient().contains(address), CoreMatchers.is(true));
        assertThat(server.getTillClient().size(), CoreMatchers.is(0));
    }

    @Test
    public void removeClient() throws Exception {

    }
} // class