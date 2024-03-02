package com.distribuida.appauthors;

import com.distribuida.appauthors.gateway.GatewayResource;
import io.helidon.microprofile.server.Server;
import java.io.IOException;

public final class Main {

    private Main() { } 

    public static void main(final String[] args) throws IOException {
        Server server = startServer();
        System.out.println("http://localhost:" + server.port() + "/authors");
    }

    static Server startServer() {
        return Server.builder()
                .addResourceClass(GatewayResource.class)
                .build()
                .start();
    }
}