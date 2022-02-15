package edu.escuelaing.arep;

import edu.escuelaing.arep.server.HttpServer;

import java.io.IOException;

public class App 
{
    public static void main( String[] args )
    {
        HttpServer httpServer = new HttpServer();
        try {
            httpServer.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
